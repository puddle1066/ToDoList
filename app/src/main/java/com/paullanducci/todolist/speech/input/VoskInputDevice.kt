package com.paullanducci.todolist.speech.input

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.util.Log
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager
import com.paullanducci.todolist.BuildConfig
import com.paullanducci.todolist.R
import com.paullanducci.todolist.util.LocaleUtils
import com.paullanducci.todolist.util.ResourcesProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import org.vosk.LibVosk
import org.vosk.LogLevel
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class VoskInputDevice(private var activity: Activity, useLocale: Locale) : SpeechInputDevice() {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private var downloadingBroadcastReceiver: BroadcastReceiver? = null
    private var currentModelDownloadId: Long? = null
    private var speechService: SpeechService? = null
    private var currentlyInitializingRecognizer = false
    private var startListeningOnLoaded = false
    private var currentlyListening = false
    private var useLocale = useLocale
    private val resourcesProvider = ResourcesProvider(activity)

    override fun load() {
        load(false) // the user did not press on a button, so manual=false
    }

    /**
     * @param manual if this is true and the model is not already downloaded, do not start
     * downloading it. See [tryToGetInput].
     */
    private fun load(manual: Boolean) {
        if (speechService == null && !currentlyInitializingRecognizer) {
            if (File(modelDirectory, "ivector").exists()) {
                // one directory is in the correct place, so everything should be ok
                Log.e(TAG, "Model in place")
                currentlyInitializingRecognizer = true
                onLoading()
                disposables.add(
                    Completable.fromAction { initializeRecognizer() }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            currentlyInitializingRecognizer = false
                            if (startListeningOnLoaded) {
                                startListeningOnLoaded = false
                                tryToGetInput(manual)
                            } else {
                                onInactive()
                            }
                        }, { throwable: Throwable ->
                            currentlyInitializingRecognizer = false
                            if ("Failed to initialize recorder. Microphone might be already in use."
                                == throwable.message
                            ) {
                                notifyError(UnableToAccessMicrophoneException())
                            } else {
                                notifyError(throwable)
                            }
                            onInactive()
                        })
                )
            } else {
                Log.e(TAG, "Model Not in place")
                val downloadManager =
                    activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                if (currentModelDownloadId == null) {
                    Log.e(TAG, "Model is NOT already being downloaded")
                    if (manual) {
                        // the model needs to be downloaded and no download has already started;
                        // the user manually triggered the input device, so he surely wants the
                        // model to be downloaded, so we can proceed
                        onLoading()
                        try {
                            val result = LocaleUtils.resolveSupportedLocale(
                                LocaleListCompat.create(useLocale),
                                MODEL_URLS.keys
                            )
                            startDownloadingModel(downloadManager, result.supportedLocaleString)
                        } catch (e: LocaleUtils.UnsupportedLocaleException) {
                            onError(resourcesProvider.getString(R.string.vosk_model_unsupported_language))
                            e.printStackTrace()
                            onRequiresDownload()
                        }
                    } else {
                        // loading the model would require downloading it, but the user didn't
                        // explicitly tell the voice recognizer to download files, so notify them
                        // that a download is required
                        onRequiresDownload()
                    }
                } else {
                    Log.e(TAG, "Model already being downloaded: $currentModelDownloadId")
                }
            }
        }
    }

    override fun cleanup() {
        super.cleanup()
        disposables.clear()
        speechService?.apply {
            stop()
            shutdown()
            speechService = null
        }
        currentModelDownloadId?.also {
            val downloadManager: DownloadManager =
                activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.remove(it)
            updateCurrentDownloadId(null)
        }
        if (downloadingBroadcastReceiver != null) {
            activity.unregisterReceiver(downloadingBroadcastReceiver)
            downloadingBroadcastReceiver = null
        }
    }

    @Synchronized
    override fun tryToGetInput(manual: Boolean) {
        if (currentlyInitializingRecognizer) {
            startListeningOnLoaded = true
            return
        }

        val speechService = speechService ?: run {
            startListeningOnLoaded = true
            load(manual) // not loaded before, retry
            return // recognizer not ready
        }

        if (currentlyListening) {
            return
        }
        currentlyListening = true
        super.tryToGetInput(manual)

        Log.e(TAG, "starting recognizer")
        speechService.startListening(object : RecognitionListener {
            override fun onPartialResult(s: String) {
                if (!currentlyListening) {
                    return
                }
                var partialInput: String? = null
                try {
                    partialInput = JSONObject(s).getString("partial")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                partialInput?.also {
                    if (it.isNotBlank()) {
                        notifyPartialInputReceived(it)
                    }
                }
            }

            override fun onResult(inputString: String) {
                if (!currentlyListening) {
                    return
                }
                stopRecognizer()
                var result = ""
                try {
                    var lastConfidence = 0.0
                    val jsonResult = JSONObject(inputString)
                    val size: Int = jsonResult.getJSONArray("alternatives").length()

                    for (i in 0 until size) {
                        val res = jsonResult.getJSONArray("alternatives")
                        val text = res.getJSONObject(i).getString("text")
                        val confidence = res.getJSONObject(i).getString("confidence").toDouble()

                        //Initialise values for first pass
                        if (lastConfidence == 0.0) {
                            lastConfidence = confidence
                            result = text
                        }

                        //Pick the best result based on the confidence factor
                        if (confidence > lastConfidence) {
                            result = text
                        }

                        Log.e(TAG, "checking $text confidnce = $confidence")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                if (result.isEmpty()) {
                    notifyNoInputReceived()
                } else {
                    notifyInputReceived(result)
                }
            }

            override fun onFinalResult(s: String) {
                //   notifyInputReceived(s)
            }

            override fun onError(e: Exception) {
                Log.e(TAG, "onError called")
                stopRecognizer()
                notifyError(e)
            }

            override fun onTimeout() {
                Log.e(TAG, "onTimeout called")
                stopRecognizer()
                notifyNoInputReceived()
            }
        })
        onListening()
    }

    override fun cancelGettingInput() {
        if (currentlyListening) {
            speechService?.stop()
            notifyNoInputReceived()

            // call onInactive() only if we really were listening, so that the SpeechInputDevice
            // state icon is preserved if something different from "microphone on" was being shown
            onInactive()
        }
        startListeningOnLoaded = false
        currentlyListening = false
    }

    ////////////////////
    // Initialization //
    ////////////////////
    @Synchronized
    @Throws(IOException::class)
    private fun initializeRecognizer() {
        Log.e(TAG, "initializing recognizer")
        LibVosk.setLogLevel(if (BuildConfig.DEBUG) LogLevel.DEBUG else LogLevel.WARNINGS)
        val model = Model(modelDirectory.absolutePath)
        val recognizer = Recognizer(model, SAMPLE_RATE)
        recognizer.setMaxAlternatives(5)
        speechService = SpeechService(recognizer, SAMPLE_RATE)
    }

    private fun stopRecognizer() {
        currentlyListening = false
        speechService?.stop()
        onInactive()
    }

    ////////////////////
    // Model download //
    ////////////////////
    private fun startDownloadingModel(
        downloadManager: DownloadManager,
        language: String
    ) {
        onError(resourcesProvider.getString(R.string.vosk_model_downloading))

        val modelZipFile = modelZipFile
        modelZipFile.delete() // if existing, delete the model zip file (should never happen)

        // build download manager request
        val modelUrl = MODEL_URLS[language]
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(modelUrl))
            .setTitle(activity.getString(R.string.vosk_model_notification_title))
            .setDescription(
                activity.getString(
                    R.string.vosk_model_notification_description, language
                )
            )
            .setDestinationUri(Uri.fromFile(modelZipFile))

        // setup download completion listener
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        downloadingBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.e(TAG, "Got intent for downloading broadcast receiver: $intent")
                if (downloadingBroadcastReceiver == null) {
                    return  // just to be sure there are no issues with threads
                }
                if (intent.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                    val id: Long = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)

                    if (currentModelDownloadId == null || id != currentModelDownloadId) {
                        Log.e(TAG, "Download complete listener notified with unknown id: $id")
                        return  // do not unregister broadcast receiver
                    }
                    if (downloadingBroadcastReceiver != null) {
                        Log.e(TAG, "Unregistering downloading broadcast receiver")
                        activity.unregisterReceiver(downloadingBroadcastReceiver)
                        downloadingBroadcastReceiver = null
                    }

                    if (downloadManager.getMimeTypeForDownloadedFile(id)
                        == null
                    ) {
                        Log.e(TAG, "Failed to download vosk model")
                        onError(resourcesProvider.getString(R.string.vosk_model_download_error))
                        downloadManager.remove(id)
                        updateCurrentDownloadId(null)
                        onInactive()
                        return
                    }
                    Log.e(TAG, "Model download complete, extracting from zip")
                    disposables.add(
                        Completable
                            .fromAction { extractModelZip() }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                onError(resourcesProvider.getString(R.string.vosk_model_ready))
                                downloadManager.remove(id)
                                updateCurrentDownloadId(null)

                                // surely the user pressed a button a while ago that
                                // triggered the download process, so manual=true
                                load(true)
                            }, { throwable: Throwable ->
                                onError(resourcesProvider.getString(R.string.vosk_model_extraction_error))
                                throwable.printStackTrace()
                                downloadManager.remove(id)
                                updateCurrentDownloadId(null)
                                onInactive()
                            })
                    )
                }
            }
        }
        activity.registerReceiver(downloadingBroadcastReceiver, filter)

        // launch download
        Log.e(TAG, "Starting vosk model download: $request")
        updateCurrentDownloadId(downloadManager.enqueue(request))
    }

    @Throws(IOException::class)
    private fun extractModelZip() {

        onError(resourcesProvider.getString(R.string.vosk_model_extracting))

        ZipInputStream(FileInputStream(modelZipFile)).use { zipInputStream ->
            // cycles through all entries
            while (true) {
                val entry: ZipEntry = zipInputStream.nextEntry ?: break
                val destinationFile = getDestinationFile(entry.name)
                if (entry.isDirectory) {
                    // create directory
                    if (!destinationFile.mkdirs()) {
                        throw IOException("mkdirs failed: $destinationFile")
                    }
                } else {
                    // copy file
                    BufferedOutputStream(FileOutputStream(destinationFile)).use { outputStream ->
                        val buffer = ByteArray(1024)
                        var length: Int
                        while (zipInputStream.read(buffer).also { length = it } > 0) {
                            outputStream.write(buffer, 0, length)
                        }
                        outputStream.flush()
                    }
                }
                zipInputStream.closeEntry()
            }
        }
    }

    ////////////////////
    // File utilities //
    ////////////////////
    @Throws(IOException::class)
    private fun getDestinationFile(entryName: String): File {
        // model files are under a subdirectory, so get the path after the first /
        val filePath = entryName.substring(entryName.indexOf('/') + 1)
        val destinationDirectory = modelDirectory

        // protect from Zip Slip vulnerability (!)
        val destinationFile = File(destinationDirectory, filePath)
        if (destinationDirectory.canonicalPath != destinationFile.canonicalPath
            && !destinationFile.canonicalPath.startsWith(
                destinationDirectory.canonicalPath + File.separator
            )
        ) {
            throw IOException("Entry is outside of the target dir: $entryName")
        }
        return destinationFile
    }

    private val modelDirectory: File
        get() = File(activity.filesDir, MODEL_PATH)
    private val modelZipFile: File
        get() = File(activity.getExternalFilesDir(null), MODEL_ZIP_FILENAME)

    private fun updateCurrentDownloadId(id: Long?) {
        // this field is used anywhere except in static contexts, where the preference is used
        currentModelDownloadId = id
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val downloadIdKey = activity.getString(R.string.pref_key_vosk_download_id)
        if (id == null) {
            // remove completely, used to notify of null values, check getDownloadIdFromPreferences
            prefs.edit().remove(downloadIdKey).apply()
        } else {
            prefs.edit().putLong(downloadIdKey, id).apply()
        }
    }

    /////////////////////
    // Other utilities //
    /////////////////////

    companion object {
        val TAG: String = VoskInputDevice::class.java.simpleName
        const val MODEL_PATH = "/vosk-model"
        const val MODEL_ZIP_FILENAME = "model.zip"
        const val SAMPLE_RATE = 44100.0f

        /**
         * All small models from [Vosk](https://alphacephei.com/vosk/models)
         */
        val MODEL_URLS = mapOf(
            "en" to "https://alphacephei.com/vosk/models/vosk-model-small-en-us-0.15.zip",
            "en-in" to "https://alphacephei.com/vosk/models/vosk-model-small-en-in-0.4.zip",
            "cn" to "https://alphacephei.com/vosk/models/vosk-model-small-cn-0.22.zip",
            "ru" to "https://alphacephei.com/vosk/models/vosk-model-small-ru-0.22.zip",
            "fr" to "https://alphacephei.com/vosk/models/vosk-model-small-fr-0.22.zip",
            "de" to "https://alphacephei.com/vosk/models/vosk-model-small-de-0.15.zip",
            "es" to "https://alphacephei.com/vosk/models/vosk-model-small-es-0.42.zip",
            "pt" to "https://alphacephei.com/vosk/models/vosk-model-small-pt-0.3.zip",
            "tr" to "https://alphacephei.com/vosk/models/vosk-model-small-tr-0.3.zip",
            "vn" to "https://alphacephei.com/vosk/models/vosk-model-small-vn-0.3.zip",
            "it" to "https://alphacephei.com/vosk/models/vosk-model-small-it-0.22.zip",
            "nl" to "https://alphacephei.com/vosk/models/vosk-model-small-nl-0.22.zip",
            "ca" to "https://alphacephei.com/vosk/models/vosk-model-small-ca-0.4.zip",
            "fa" to "https://alphacephei.com/vosk/models/vosk-model-small-fa-0.4.zip",
            "ph" to "https://alphacephei.com/vosk/models/vosk-model-tl-ph-generic-0.6.zip",
            "uk" to "https://alphacephei.com/vosk/models/vosk-model-small-uk-v3-nano.zip",
            "kz" to "https://alphacephei.com/vosk/models/vosk-model-small-kz-0.15.zip",
            "ja" to "https://alphacephei.com/vosk/models/vosk-model-small-ja-0.22.zip",
            "eo" to "https://alphacephei.com/vosk/models/vosk-model-small-eo-0.42.zip",
            "hi" to "https://alphacephei.com/vosk/models/vosk-model-small-hi-0.22.zip",
            "cs" to "https://alphacephei.com/vosk/models/vosk-model-small-cs-0.4-rhasspy.zip",
            "pl" to "https://alphacephei.com/vosk/models/vosk-model-small-pl-0.22.zip",
            "uz" to "https://alphacephei.com/vosk/models/vosk-model-small-uz-0.22.zip",
            "ko" to "https://alphacephei.com/vosk/models/vosk-model-small-ko-0.22.zip",
        )

        /**
         * Deletes the Vosk model downloaded in the [Context.getFilesDir] if it exists. It also
         * stops any Vosk model download currently in progress based on the id stored in settings.
         * @param context the Android context used to get the download manager and the files dir
         */
        fun deleteCurrentModel(context: Context) {
            val downloadManager: DownloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val modelDownloadId = getDownloadIdFromPreferences(context, downloadManager)
            if (modelDownloadId != null) {
                downloadManager.remove(modelDownloadId)
            }
            deleteFolder(File(context.filesDir, MODEL_PATH))
        }

        private fun deleteFolder(file: File) {
            val subFiles = file.listFiles()
            if (subFiles != null) {
                for (subFile in subFiles) {
                    if (subFile.isDirectory) {
                        deleteFolder(subFile)
                    } else {
                        subFile.delete()
                    }
                }
            }
            file.delete()
        }

        ///////////////////////////
        // Download id utilities //
        ///////////////////////////
        private fun getDownloadIdFromPreferences(
            context: Context,
            manager: DownloadManager
        ): Long? {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val downloadIdKey = context.getString(R.string.pref_key_vosk_download_id)
            return if (prefs.contains(downloadIdKey)) {
                val id: Long = prefs.getLong(downloadIdKey, 0)
                if (manager.query(DownloadManager.Query().setFilterById(id)).count == 0) {
                    // no download in progress or being extracted with this id, reset setting
                    null
                } else {
                    id
                }
            } else {
                null
            }
        }
    }
}
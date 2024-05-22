package com.paullanducci.speech.input

import com.paullanducci.todolist.ui.main.MainActivity.Companion.setState

abstract class SpeechInputDevice() : InputDevice() {

    class UnableToAccessMicrophoneException internal constructor() : Exception(
        "Unable to access microphone."
                + " Microphone might be already in use or the permission was not granted."
    )


    override fun cleanup() {
        super.cleanup()
    }

    /**
     * Prepares the speech recognizer. If doing heavy work, run it in an asynchronous thread.
     * <br></br><br></br>
     * Overriding functions must call [onLoading] when they start loading and [onInactive] when instead they have finished loading. Errors should be reported to [notifyError]. Note that the starting icon for a [SpeechInputDevice] is
     * already the loading indicator.
     */
    abstract override fun load()

    /**
     * Listens for some spoken input from the microphone. Should run in an asynchronous thread.
     * <br></br><br></br>
     * Overriding functions should report partial results to [notifyPartialInputReceived], final
     * results to [notifyInputReceived] or [notifyNoInputReceived] (based on whether some input was
     * received or not) and errors to [notifyError]. They must call [onListening] when they turn on
     * the microphone and [onInactive] when instead they turn it off.
     *
     * @param manual true if and only if the user manually pressed on the specific button that
     * activates this input device, false otherwise. This might be useful to prevent
     * e.g. voice model downloads from starting in case the user didn't explicitly
     * trigger the input device.
     */
    @Suppress("RedundantOverride")
    override fun tryToGetInput(manual: Boolean) {
        super.tryToGetInput(manual) // overridden just to provide a more detailed documentation ^
    }

    /**
     * Stops listening and turns off the microphone after [tryToGetInput] was
     * called. Should do nothing if called while not listening. Any partial result is discarded.
     * Called for example when the user leaves the app.
     * <br></br><br></br>
     * Overriding functions should call [notifyNoInputReceived] and [onInactive]
     * when they turn off the microphone.
     */
    abstract override fun cancelGettingInput()

    /**
     * This must be called by functions overriding [tryToGetInput] if the `manual` parameter is `false` and loading the voice model would require downloading
     * files from the internet. It must also be called by [load] when loading the voice
     * model would require downloading files from the internet (since [load] is never
     * called after a user action but automatically, which is equivalent to having `manual=false` for [tryToGetInput]). A download icon will be shown.
     */
    protected fun onRequiresDownload() {
        setState(VoiceEngineState.REQUIRES_DOWNLOAD)
    }

    /**
     * This must be called by functions overriding [tryToGetInput] when they have
     * started listening, so that the microphone on icon can be shown.
     */
    protected fun onLoading() {
        setState(VoiceEngineState.LOADING)
    }

    /**
     * This must be called by functions overriding [tryToGetInput] when they have
     * finished listening or by functions overriding [load] when they have finished
     * loading, so that the so that the microphone off icon can be shown.
     */
    protected fun onInactive() {
        setState(VoiceEngineState.INACTIVE)
    }

    /**
     * This must be called by functions overriding [tryToGetInput] when they have
     * started listening, so that the microphone on icon can be shown.
     */
    protected fun onListening() {
        setState(VoiceEngineState.LISTENING)
    }

    protected fun onError(error: String) {
        setState(VoiceEngineState.ERROR)
    }

}
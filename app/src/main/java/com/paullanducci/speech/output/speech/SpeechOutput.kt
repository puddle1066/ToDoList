package com.paullanducci.speech.output.speech

interface SpeechOutputDevice {
    fun speak(speechOutput: String)
    fun stopSpeaking()
    val isSpeaking: Boolean
    fun runWhenFinishedSpeaking(runnable: Runnable)

    fun cleanup()
}



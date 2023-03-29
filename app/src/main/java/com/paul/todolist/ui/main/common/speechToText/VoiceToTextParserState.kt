package com.paul.todolist.ui.main.common.speechToText

data class VoiceToTextParserState(
    val isSpeaking: Boolean = false,
    val spokenText: String = "",
    val error: String? = null
)

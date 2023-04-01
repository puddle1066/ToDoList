package com.paul.todolist.ui.main.common.speechToText

data class VoiceToTextParserState(
    val isSpeaking: Boolean = false,
    var spokenText: String = "",
    val error: String? = null
)

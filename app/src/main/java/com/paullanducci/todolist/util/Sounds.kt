package com.paullanducci.todolist.util

import android.content.Context
import android.media.AudioManager

fun playClick(ctx: Context) {
    val audioManager = ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, 1.0f)
}
package com.paullanducci.todolist.ui.widgets

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import androidx.core.content.res.ResourcesCompat
import com.paullanducci.todolist.R

class CustomNumberPicker(val ctx: Context) : NumberPicker(ctx, null) {
    val tfs: Typeface? = ResourcesCompat.getFont(context, R.font.montserrat_medium)

    override fun addView(child: View) {
        super.addView(child)
        updateView(child)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        updateView(child)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        super.addView(child, params)
        updateView(child)
    }

    private fun updateView(view: View) {
        val txtColor: Int = 0xFFFFFFFF.toInt()

        if (view is EditText) {
            view.setTypeface(tfs)
            view.textSize = 18f
            view.setTextColor(txtColor)
        }
    }
}
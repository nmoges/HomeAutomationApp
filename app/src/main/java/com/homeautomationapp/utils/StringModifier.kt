package com.homeautomationapp.utils

import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import com.homeautomationapp.R

object StringModifier {

    /**
     * Returns a colored SpannableStringBuilder.
     * @param text : String to color
     * @param resources : Application resources
     */
    @Suppress("DEPRECATION")
    fun getColoredText(text: String, resources: Resources): SpannableStringBuilder {
        val builder = SpannableStringBuilder()

        builder.apply {
            this.append(text)
            if (text.contains("ON")) {
                this.setSpan(StyleSpan(Typeface.BOLD), text.length-2, text.length, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    this.setSpan(
                        ForegroundColorSpan(
                        resources.getColor(R.color.green, null)), text.length-2, text.length, 0)
                else
                    this.setSpan(
                        ForegroundColorSpan(
                        resources.getColor(R.color.green)), text.length-2, text.length, 0)
            }
            else {
                this.setSpan(StyleSpan(Typeface.BOLD), text.length-3, text.length, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    this.setSpan(
                        ForegroundColorSpan(
                        resources.getColor(R.color.red, null)), text.length-3, text.length, 0)
                else this.setSpan(
                    ForegroundColorSpan(
                    resources.getColor(R.color.red)), text.length-3, text.length, 0)
            }
        }
        return builder
    }
}
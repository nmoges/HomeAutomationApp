package com.homeautomationapp.utils

import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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
                this.setSpan(StyleSpan(Typeface.BOLD), 8, 10, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    this.setSpan(
                        ForegroundColorSpan(
                        resources.getColor(R.color.green, null)), 8, 10, 0)
                else
                    this.setSpan(
                        ForegroundColorSpan(
                        resources.getColor(R.color.green)), 8, 10, 0)
            }
            else {
                this.setSpan(StyleSpan(Typeface.BOLD), 8, 11, 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    this.setSpan(
                        ForegroundColorSpan(
                        resources.getColor(R.color.red, null)), 8, 11, 0)
                else this.setSpan(
                    ForegroundColorSpan(
                    resources.getColor(R.color.red)), 8, 11, 0)
            }
        }
        return builder
    }
}
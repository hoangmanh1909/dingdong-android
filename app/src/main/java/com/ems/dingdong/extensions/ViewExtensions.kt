package com.ems.dingdong.extensions

import android.graphics.Typeface
import android.text.*
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.ems.dingdong.views.CustomTypefaceSpan
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun TextView.fillColor(text: String?, colorRes: Int, fontRes: Int? = null, isBackground: Boolean = false, colorRes2: Int = -1) {
    var font: Typeface? = null
    fontRes?.let {
        font = ResourcesCompat.getFont(this.context, it)
    }

    if (text == null) return

    val indexOfText = this.text.indexOf(text)

    if (indexOfText == -1) return

    val spannable = SpannableString(this.text)
            .apply {
                if (isBackground) {
                    setSpan(BackgroundColorSpan(ContextCompat.getColor(context, colorRes)), indexOfText, indexOfText + text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(ForegroundColorSpan(ContextCompat.getColor(context, colorRes2)), indexOfText, indexOfText + text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                } else {
                    setSpan(ForegroundColorSpan(ContextCompat.getColor(context, colorRes)), indexOfText, indexOfText + text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                font?.let {
                    setSpan(CustomTypefaceSpan("", it), indexOfText, indexOfText + text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
    this.text = spannable
}

fun Calendar.dateToString(format: String): String {
    val format = SimpleDateFormat(format, Locale.getDefault())
    return format.format(this.time)
}

fun EditText.editTextListener() {
    var current=""
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString() != current) {
                removeTextChangedListener(this)
                var cleanString = s.toString().replace(" VNĐ","")
                cleanString = cleanString.replace(Regex("[$,.]"), "")
                if(cleanString !="") {
                    val parsed = cleanString.toDouble()
                    val formatter = DecimalFormat("#,###")
                    val formatted = formatter.format(parsed)
                    current = "$formatted VNĐ"
                    setText(current)
                    setSelection(formatted?.length!!)
                }
                addTextChangedListener(this)
            }
        }
    })

}
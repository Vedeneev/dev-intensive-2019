package ru.skillbranch.devintensive.extensions

import android.text.Html
import android.text.TextUtils
import androidx.core.text.HtmlCompat

fun String.truncate(quantity:Int = 16) : String{
    var resultString = this
    if (resultString.length>quantity){
        resultString = resultString.substring(0, quantity-1)
        resultString = resultString.trimEnd()
        resultString = resultString + "..."
    }
    return  resultString
}


fun String.stripHtml() : String {
    var resultString = this
    resultString = resultString.replace(Regex("<(.*?)\\>"), "")
    resultString = resultString.replace(Regex("\\s+"), " ")
    resultString = resultString.replace("\\n", "")
    resultString = resultString.replace("&nbsp;", "")
    resultString = resultString.replace("&amp;", "")
    return resultString
}
//fun String.stripHtml() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
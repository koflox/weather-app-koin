package com.koflox.common_jvm_util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.*

fun Int.formatToLocalTime(pattern: String, offsetInSeconds: Int = 0): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    val currentTime = TimeUnit.SECONDS.toMillis(this.toLong())
    val offset = TimeUnit.SECONDS.toMillis(offsetInSeconds.toLong())
    val date = Date(currentTime + offset)
    return dateFormat.format(date)
}

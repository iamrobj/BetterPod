package com.robj.betterpod.ui

import android.content.res.Resources
import com.robj.betterpod.R
import com.robj.betterpod.networking.models.Episode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.toFormattedTime(): String {
    val hours = this / 3600
    val minutes = ((this - TimeUnit.HOURS.toSeconds(hours)) % 3600) / 60

    val sb = StringBuilder()
    hours.takeIf { it > 0 }?.apply {
        sb.append(this)
        sb.append("h ")
    }
    minutes.takeIf { it > 0 }?.apply {
        sb.append(this)
        sb.append("m")
    }
    return sb.toString()
}

fun Episode.getEpisodeNumber(res: Resources): String {
    return when (episodeType) {
        "trailer" -> res.getString(R.string.trailer)
        "bonus" -> res.getString(R.string.bonus)
        else -> {
            StringBuilder().apply {
                if (season > 0) {
                    append("S$season ")
                }
                append("E$episode")
            }.toString()
        }
    }
}

fun Episode.getFormattedDate(res: Resources): String {
    val correctedDateInUtc =
        datePublished - 18000L //Corrected to match with field datePublishedPretty
    val date = Date(correctedDateInUtc * 1000)
    val localDateTime = LocalDateTime.ofInstant(
        date.toInstant(),
        TimeZone.getDefault().toZoneId()
    )
    return when {
        localDateTime.isToday() -> res.getString(R.string.today)
        localDateTime.isYesterday() -> res.getString(R.string.yesterday)
        else -> localDateTime.format(DateTimeFormatter.ofPattern("EEE dd MMM yyyy"))
    }
}

fun LocalDateTime.isToday(): Boolean {
    val today = LocalDateTime.now(TimeZone.getDefault().toZoneId())
    return this.dayOfYear == today.dayOfYear && this.year == today.year
}

fun LocalDateTime.isYesterday(): Boolean {
    val yesterday = LocalDateTime.now(TimeZone.getDefault().toZoneId()).minusDays(1)
    return this.dayOfYear == yesterday.dayOfYear && this.year == yesterday.year
}
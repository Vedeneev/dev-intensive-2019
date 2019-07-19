package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = java.text.SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var humanizedDate: String = ""
    val timeDiff = date.time - this.time
//    println("timeDiff - $timeDiff")
    val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff)
//    println("seconds - $diffInSeconds")
//    println("minutes - ${TimeUnit.MILLISECONDS.toMinutes(timeDiff)}")
//    println("hours - ${TimeUnit.MILLISECONDS.toHours(timeDiff)}")
//    println("days - ${TimeUnit.MILLISECONDS.toDays(timeDiff)}")
    if (timeDiff > 0) {
        when (abs(diffInSeconds)) {
            in 0..1 -> humanizedDate = "только что"
            in 1..45 -> humanizedDate = "несколько секунд назад"
            in 45..75 -> humanizedDate = "минуту назад"
            in 75..2700 -> {
                val minutesDiff = abs(TimeUnit.MILLISECONDS.toMinutes(timeDiff).toInt())
                when {
                    minutesDiff in 5..20 || minutesDiff % 10 == 5 || minutesDiff % 10 == 6 || minutesDiff % 10 == 7 ||
                            minutesDiff % 10 == 8 || minutesDiff % 10 == 9 || minutesDiff % 10 == 0 ->
                            humanizedDate = "$minutesDiff минут назад"
                    minutesDiff % 10 == 1 -> humanizedDate = "$minutesDiff минуту назад"
                    minutesDiff % 10 == 2 || minutesDiff % 10 == 3 || minutesDiff % 10 == 4 ->
                        humanizedDate = "$minutesDiff минуты назад"
                }
            }
            in 2700..4500 -> humanizedDate = "час назад"
            in 4500..79200 -> {
                val hoursDiff = abs(TimeUnit.MILLISECONDS.toHours(timeDiff).toInt())
                when (hoursDiff) {
                    in 2..4, in 22..24 -> humanizedDate = "$hoursDiff часа назад"
                    21 -> humanizedDate = "$hoursDiff час назад"
                    else -> humanizedDate = "$hoursDiff часов назад"
                }
            }
            in 79200..93600 -> humanizedDate = "день назад"
            in 93600..31104000 -> {
                val daysDiff = abs(TimeUnit.MILLISECONDS.toDays(timeDiff).toInt())
                when {
                    daysDiff % 10 == 2 && daysDiff != 12 && daysDiff != 112 && daysDiff != 212 && daysDiff != 312 ->
                        humanizedDate = "$daysDiff дня назад"
                    daysDiff % 10 == 3 && daysDiff != 13 && daysDiff != 113 && daysDiff != 213 && daysDiff != 313 ->
                        humanizedDate = "$daysDiff дня назад"
                    daysDiff % 10 == 4 && daysDiff != 14 && daysDiff != 114 && daysDiff != 214 && daysDiff != 314 ->
                        humanizedDate = "$daysDiff дня назад"
                    daysDiff % 100 == 1 -> humanizedDate = "$daysDiff день назад"
                    else -> humanizedDate = "$daysDiff дней назад"
                }
            }
            else -> humanizedDate = "более года назад"
        }
    } else {
        when (abs(diffInSeconds)) {
            in 0..1 -> humanizedDate = "только что"
            in 1..45 -> humanizedDate = "через несколько секунд"
            in 45..75 -> humanizedDate = "через минуту"
            in 75..2700 -> {
                val minutesDiff = abs(TimeUnit.MILLISECONDS.toMinutes(timeDiff).toInt())
                when {
                    minutesDiff in 5..20 || minutesDiff % 10 == 5 || minutesDiff % 10 == 6 || minutesDiff % 10 == 7 ||
                            minutesDiff % 10 == 8 || minutesDiff % 10 == 9 || minutesDiff % 10 == 0 ->
                        humanizedDate = "через $minutesDiff минут"
                    minutesDiff % 10 == 1 -> humanizedDate = "через $minutesDiff минуту"
                    minutesDiff % 10 == 2 || minutesDiff % 10 == 3 || minutesDiff % 10 == 4 ->
                        humanizedDate = "через $minutesDiff минуты"
                }
            }
            in 2700..4500 -> humanizedDate = "через час"
            in 4500..79200 -> {
                val hoursDiff = abs(TimeUnit.MILLISECONDS.toHours(timeDiff).toInt())
                when (hoursDiff) {
                    in 2..4, in 22..24 -> humanizedDate = "через $hoursDiff часа"
                    21 -> humanizedDate = "через $hoursDiff час"
                    else -> humanizedDate = "через $hoursDiff часов"
                }
            }
            in 79200..93600 -> humanizedDate = "через день"
            in 93600..31104000 -> {
                val daysDiff = abs(TimeUnit.MILLISECONDS.toDays(timeDiff).toInt())
                when {
                    daysDiff % 10 == 2 && daysDiff != 12 && daysDiff != 112 && daysDiff != 212 && daysDiff != 312 ->
                        humanizedDate = "через $daysDiff дня"
                    daysDiff % 10 == 3 && daysDiff != 13 && daysDiff != 113 && daysDiff != 213 && daysDiff != 313 ->
                        humanizedDate = "через $daysDiff дня"
                    daysDiff % 10 == 4 && daysDiff != 14 && daysDiff != 114 && daysDiff != 214 && daysDiff != 314 ->
                        humanizedDate = "через $daysDiff дня"
                    daysDiff % 100 == 1 -> humanizedDate = "через $daysDiff день"
                    else -> humanizedDate = "через $daysDiff дней"
                }
            }
            else -> humanizedDate = "более чем через год"
        }
    }

    return humanizedDate
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int) : String {
        var resultString = ""
        when(this) {
            SECOND -> {
                when{
                    value == 1 || value % 100 == 1 || value % 1000 == 1 -> resultString = "$value секунда"
                    value % 10 == 2 && value != 12 && value % 100 != 12 && value % 1000 != 12 -> resultString = "$value секунды"
                    value % 10 == 3 && value != 13 && value % 100 != 13 && value % 1000 != 13 -> resultString = "$value секунды"
                    value % 10 == 4 && value != 14 && value % 100 != 14 && value % 1000 != 14 -> resultString = "$value секунды"
                    else -> resultString = "$value секунд"
                }
            }
            MINUTE -> {
                when{
                    value == 1 || value % 100 == 1 || value % 1000 == 1 -> resultString = "$value минута"
                    value % 10 == 2 && value != 12 && value % 100 != 12 && value % 1000 != 12 -> resultString = "$value минуты"
                    value % 10 == 3 && value != 13 && value % 100 != 13 && value % 1000 != 13 -> resultString = "$value минуты"
                    value % 10 == 4 && value != 14 && value % 100 != 14 && value % 1000 != 14 -> resultString = "$value минуты"
                    else -> resultString = "$value минут"
                }
            }
            HOUR -> when{
                value == 1 || value % 100 == 1 || value % 1000 == 1 -> resultString = "$value час"
                value % 10 == 2 && value != 12 && value % 100 != 12 && value % 1000 != 12 -> resultString = "$value часа"
                value % 10 == 3 && value != 13 && value % 100 != 13 && value % 1000 != 13 -> resultString = "$value часа"
                value % 10 == 4 && value != 14 && value % 100 != 14 && value % 1000 != 14 -> resultString = "$value часа"
                else -> resultString = "$value часов"
            }
            DAY -> when{
                value == 1 || value % 100 == 1 || value % 1000 == 1 -> resultString = "$value день"
                value % 10 == 2 && value != 12 && value % 100 != 12 && value % 1000 != 12 -> resultString = "$value дня"
                value % 10 == 3 && value != 13 && value % 100 != 13 && value % 1000 != 13 -> resultString = "$value дня"
                value % 10 == 4 && value != 14 && value % 100 != 14 && value % 1000 != 14 -> resultString = "$value дня"
                else -> resultString = "$value дней"
            }
        }
        return resultString
    }
}
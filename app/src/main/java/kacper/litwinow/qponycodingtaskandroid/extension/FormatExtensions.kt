package kacper.litwinow.qponycodingtaskandroid.extension

import kacper.litwinow.qponycodingtaskandroid.constants.DD_MM_YYYY
import kacper.litwinow.qponycodingtaskandroid.constants.YYYY_MM_DD
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

fun Double.formatPriceValues(): String =
    String.format("%.4f", BigDecimal(this).setScale(4, RoundingMode.DOWN)).replace(".", ",")


fun Long.formatToStringFromLong(formatDate: String): String =
    SimpleDateFormat(formatDate, Locale.getDefault()).format(Date(this))

fun String.formatToDateFromString(formatDate: String): Date? =
    SimpleDateFormat(formatDate, Locale.getDefault()).parse(this)

fun Date.formatToStringFromDate(formatDate: String): String =
    SimpleDateFormat(formatDate, Locale.getDefault()).format(this)

fun String?.formatDate(): String? =
    this?.formatToDateFromString(YYYY_MM_DD)
        ?.formatToStringFromDate(DD_MM_YYYY)

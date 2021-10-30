package ru.grapecoffe.spring.Service

import java.text.SimpleDateFormat
import java.util.*

 class OperationsWithTime
{
    private var timeCountStart: Long = 0
    private var totalTime: Long = 0

    companion object
    {
        private const val DATE_FORMAT = "mm:ss"
        private const val DATE_FORMAT_ASPECT = "ms"
        private const val DATE_FORMAT_HISTORY = "dd.MM.yyyy - $DATE_FORMAT"
    }

    fun getResultTime(totalTime: Boolean = false, formatAspect: Boolean = false) =
        SimpleDateFormat(if (formatAspect) DATE_FORMAT_ASPECT else DATE_FORMAT)
        .format(Date(if (totalTime) this.totalTime else (getDataNow()
                - this.timeCountStart).processingDiff())) as String

    private fun Long.processingDiff(): Long
    {
        this@OperationsWithTime.totalTime += this
        return this
    }

    fun startCountTime() { this.timeCountStart = getDataNow() }
    fun getTimeTest() = this.timeCountStart

    private fun getDataNow() = Date().time
    fun getNowTime() = SimpleDateFormat(DATE_FORMAT_HISTORY).format(Date()) as String
}
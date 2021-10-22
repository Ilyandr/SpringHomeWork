package ru.grapecoffe.spring.DataAccessObject

import java.io.FileReader

internal interface UnifiedProcessing
{
    val informationHandler: HashMap<Any, Any>

    fun connectionResource(): FileReader?
    fun readFileInfo(): HashMap<Any, Any>?
}
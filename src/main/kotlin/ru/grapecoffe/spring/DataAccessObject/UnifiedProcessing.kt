package ru.grapecoffe.spring.DataAccessObject

import org.springframework.aop.framework.AopContext
import ru.grapecoffe.spring.Entity.UserInformation
import ru.grapecoffe.spring.Service.OperationsWithTime

internal interface UnifiedProcessing
{
    val informationHandler: HashMap<Any, Any>
    var inputMode: Short

    fun readFileInfo(): Unit?
    fun readTXTFile()
    fun readCSVFile()

    fun updateHistoryData(userInfo: UserInformation
      , resultTest: Int
      , completeTimeManager: OperationsWithTime?): Boolean

    fun requireContext() = AopContext.currentProxy() as UnifiedProcessing
}
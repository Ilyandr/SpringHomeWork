package ru.grapecoffe.spring.Service

import org.springframework.aop.framework.AopContext
import ru.grapecoffe.spring.DataAccessObject.UnifiedProcessing
import ru.grapecoffe.spring.Entity.UserInformation
import java.util.*
import kotlin.collections.ArrayList


internal interface UnifiedWork
{
    val inputScanner: Scanner

    fun inputDataFromDAO(inputDataTest: String, objectDAO: UnifiedProcessing, itsTestCall: Boolean)
    fun basicWorkWithInfo(itsTestCall: Boolean)
    fun setTimeControl(getManager: OperationsWithTime?) {}

    fun saveResultTest(testResult: Int, itsTestCall: Boolean)
    fun outputGeneralResultTest(readyInfo: String)
    fun getTestResult(answerInfo: ArrayList<Boolean>): Int?

    fun requireContext() = AopContext.currentProxy() as UnifiedWork
    fun initialUser(itsTestCall: Boolean): UserInformation
    fun setUserAnswer(inputAnswer: Any)
}
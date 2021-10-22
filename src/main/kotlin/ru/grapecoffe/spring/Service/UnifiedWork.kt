package ru.grapecoffe.spring.Service

import ru.grapecoffe.spring.Entity.UserInformation

internal interface UnifiedWork
{
    fun inputDataFromDAO(inputDataTest: String?)

    fun initialUser(inputTestData: UserInformation?): UserInformation
    fun basicWorkWithInfo(inputInfo: HashMap<Any, Any>?, inputMode: Short, itsTestCall: Boolean)
    fun setTimeControl(getManager: OperationsWithTime) {}

    fun saveResultTest(testResult: Int, itsTestCall: Boolean)
    fun outputGeneralResultTest(readyInfo: String)
    fun getTestResult(answerInfo: ArrayList<Boolean>): Int
}
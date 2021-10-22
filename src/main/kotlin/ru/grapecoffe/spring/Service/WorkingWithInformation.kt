package ru.grapecoffe.spring.Service

import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.Entity.OutputUserInfo.ERROR_INFO
import ru.grapecoffe.spring.Entity.UserInformation
import ru.grapecoffe.spring.DataAccessObject.InformationHandler
import ru.grapecoffe.spring.DataAccessObject.InformationHandler.Companion.MODE_CSV
import ru.grapecoffe.spring.DataAccessObject.InformationHandler.Companion.MODE_TXT
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

internal class WorkingWithInformation(private val inputScanner: Scanner
, private val DAO_info: InformationHandler): UnifiedWork
{
    private lateinit var userInformation: UserInformation
    private var timeManger: OperationsWithTime? = null

    companion object
    { private const val PRICE_SINGLE_ANSWER: Short = 20 }

    override fun inputDataFromDAO(inputDataTest: String?)
    {
        DAO_info.inputMode = when (inputDataTest ?: inputScanner.next())
        {
            OutputUserInfo.TYPE_OPERATION_TEST -> MODE_CSV
            OutputUserInfo.TYPE_OPERATION_HISTORY -> MODE_TXT
            else -> 0
        }
        if (DAO_info.inputMode.toInt() == 0)
        { println(ERROR_INFO); return }

        basicWorkWithInfo(DAO_info.readFileInfo()
            , DAO_info.inputMode
            , (inputDataTest != null))
    }

    override fun initialUser(inputTestData: UserInformation?): UserInformation
    {
        return inputTestData ?: UserInformation(this.inputScanner.next()
            , this.inputScanner.next()
            , this.inputScanner.nextShort())
    }

    override fun basicWorkWithInfo(inputInfo: HashMap<Any, Any>?
     , inputMode: Short
     , itsTestCall: Boolean)
    {
        if (inputInfo == null) return
        print("\n")

        if (inputMode == MODE_CSV && !itsTestCall)
        {
            val userAnswer = ArrayList<Boolean>()
            if (!itsTestCall) print(OutputUserInfo.outputUserInformation)

            this.userInformation = initialUser(if (!itsTestCall) null
            else OutputUserInfo.testUserData)

            val countTime = (timeManger != null)

            inputInfo.forEach { (key, value) -> print("$key Ответ: ")
                if (countTime) timeManger!!.startCountTime()
                userAnswer.add(inputScanner.next() == value.toString())
                if (countTime) print("-> Время ответа: ${timeManger!!.getResultTime()}\n")
            }
            saveResultTest(getTestResult(userAnswer), false)
        }
        else if (inputMode == MODE_CSV && itsTestCall)
            saveResultTest(getTestResult(
                arrayListOf(true, false, true, true, true)), true)

        else if (inputMode == MODE_TXT)
            inputInfo.forEach{ (key, value) -> println("$key $value")}
    }

    override fun saveResultTest(testResult: Int, itsTestCall: Boolean)
    {
        if (!itsTestCall) outputGeneralResultTest(
           if (DAO_info.updateHistoryData(userInformation, testResult, this.timeManger))
          "${OutputUserInfo.SUCCESSFUL_SAVE_INFO}$userInformation" +
          "\n${OutputUserInfo.USER_INFO} $testResult %.\nОбщее затраченное время (cек): " +
          "${this.timeManger?.getResultTime(true) ?: "-"}."
           else ERROR_INFO)
        else
        {
            if (DAO_info.updateHistoryData(OutputUserInfo.testUserData
                    , OutputUserInfo.testResultTest
                    , this.timeManger))
            {
                OutputUserInfo.successfulActionTest()
                return
            }
        }
    }

    override fun getTestResult(answerInfo: ArrayList<Boolean>): Int
    {
        var returnResult = 0
        answerInfo.forEach { returnResult += if (it) PRICE_SINGLE_ANSWER else 0 }
        return returnResult
    }

    override fun outputGeneralResultTest(readyInfo: String) = println(readyInfo)
    override fun setTimeControl(getManager: OperationsWithTime) { this.timeManger = getManager }
}
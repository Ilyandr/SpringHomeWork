package ru.grapecoffe.spring.Service

import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.Entity.OutputUserInfo.ERROR_INFO
import ru.grapecoffe.spring.Entity.UserInformation
import ru.grapecoffe.spring.DataAccessObject.InformationHandler.Companion.MODE_CSV
import ru.grapecoffe.spring.DataAccessObject.InformationHandler.Companion.MODE_TXT
import ru.grapecoffe.spring.DataAccessObject.UnifiedProcessing
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

internal open class WorkingWithInformation(override val inputScanner: Scanner): UnifiedWork
{
    private var timeManger: OperationsWithTime? = null
    private val userAnswers = ArrayList<Boolean>()
    private lateinit var objectDAO: UnifiedProcessing

    var singleAnswer: Any by Delegates.observable("")
    { property, oldValue, newValue ->

    }

    companion object
    { private const val PRICE_SINGLE_ANSWER: Short = 20 }

    override fun inputDataFromDAO(inputDataTest: String
     , objectDAO: UnifiedProcessing
     , itsTestCall: Boolean)
    {
        this.objectDAO = objectDAO.requireContext()

        this.objectDAO.inputMode = when (inputDataTest)
        {
            OutputUserInfo.TYPE_OPERATION_TEST -> MODE_CSV
            OutputUserInfo.TYPE_OPERATION_HISTORY -> MODE_TXT
            else -> 0
        }

        if (this.objectDAO.inputMode.toInt() == 0)
        { println(ERROR_INFO); return }

        requireContext()
            .basicWorkWithInfo(itsTestCall)
    }

    override fun initialUser(itsTestCall: Boolean): UserInformation =
        if (!itsTestCall) UserInformation(this.inputScanner.next()
            , this.inputScanner.next()
            , this.inputScanner.nextShort())
        else OutputUserInfo.testUserData

    override fun basicWorkWithInfo(itsTestCall: Boolean) = when
    {
        (this.objectDAO.inputMode == MODE_CSV && !itsTestCall) ->
        {
            println("\n")

            val countTime = (timeManger != null)
            this.objectDAO.requireContext().readFileInfo()

            this.objectDAO.requireContext()
                .informationHandler.forEach { (key, value) -> print("$key Ответ: ")
                    if (countTime) timeManger!!.startCountTime()
                    userAnswers.add(requireContext().inputScanner.next() == value.toString())
                    if (countTime) print("-> Время ответа: ${timeManger!!.getResultTime()}\n")
                }

            requireContext().saveResultTest(requireContext()
                .getTestResult(userAnswers)!!
                , false)
        }

        (this.objectDAO.inputMode == MODE_CSV && itsTestCall) ->
            requireContext().saveResultTest(requireContext().getTestResult(
                arrayListOf(true, false, true, true, true))!!, true)

        (this.objectDAO.inputMode == MODE_TXT) ->
        {
            this.objectDAO.requireContext().readFileInfo()

            this.objectDAO.requireContext()
                .informationHandler.forEach{ (key, value) -> println("$key $value")}
        }

        else -> System.err.println(ERROR_INFO)
    }

    override fun saveResultTest(testResult: Int, itsTestCall: Boolean)
    {
        print(OutputUserInfo.outputUserInformation)
        val userInfo = requireContext().initialUser(itsTestCall)

        if (!itsTestCall) outputGeneralResultTest(
           if (this.objectDAO.requireContext().updateHistoryData(userInfo, testResult, this.timeManger))
          "${OutputUserInfo.SUCCESSFUL_SAVE_INFO}${userInfo}" +
          "\n${OutputUserInfo.USER_INFO} $testResult %.\nОбщее затраченное время (cек): " +
          "${this.timeManger?.getResultTime(true) ?: "-"}."
           else ERROR_INFO)

        else
        {
            if (objectDAO.updateHistoryData(OutputUserInfo.testUserData
                    , OutputUserInfo.testResultTest
                    , this.timeManger))
            {
                OutputUserInfo.successfulActionTest()
                return
            }
        }
    }

    override fun getTestResult(answerInfo: ArrayList<Boolean>): Int?
    {
        var returnResult = 0

        answerInfo.forEach { returnResult +=
            if (it) PRICE_SINGLE_ANSWER else 0 }

        return returnResult
    }

    override fun outputGeneralResultTest(readyInfo: String) =
        println(readyInfo)

    override fun setTimeControl(getManager: OperationsWithTime?)
    { this.timeManger = getManager }

    override fun setUserAnswer(inputAnswer: Any)
    { this.singleAnswer = inputAnswer }
}
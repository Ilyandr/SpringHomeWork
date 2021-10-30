package ru.grapecoffe.spring.AspectLogging

import org.aspectj.lang.ProceedingJoinPoint
import ru.grapecoffe.spring.Configuration.RegisteringWorker
import ru.grapecoffe.spring.GeneralClassApplication
import ru.grapecoffe.spring.Service.OperationsWithTime
import ru.grapecoffe.spring.ShellManager.GeneralShellCommands

internal abstract class GeneralAspectsData: GeneralClassApplication()
    , GeneralResults
{
    private var timeManager: OperationsWithTime? = null

    companion object
    {
        @JvmStatic
        fun getLogResult() = RegisteringWorker.benchmarkFunMap
            .forEach { (nameFun: String, timeWorkFun: Long) ->
            System.err.println("Название функции: $nameFun." +
                    " Время обработки: $timeWorkFun мс.") }

        internal const val readFileInfo =
           "execution(* ru.grapecoffe.spring.DataAccessObject.InformationHandler.readFileInfo*(..))"

        internal const val readCSVFile =
           "execution(* ru.grapecoffe.spring.DataAccessObject.InformationHandler.readCSVFile*(..))"

        internal const val readTXTFile =
           "execution(* ru.grapecoffe.spring.DataAccessObject.InformationHandler.readTXTFile*(..))"

        internal const val updateHistoryData =
           "execution(* ru.grapecoffe.spring.DataAccessObject.InformationHandler.updateHistoryData*(..))"

        internal const val inputDataFromDAO =
           "execution(* ru.grapecoffe.spring.Service.WorkingWithInformation.inputDataFromDAO*(..))"

        internal const val basicWorkWithInfo =
           "execution(* ru.grapecoffe.spring.Service.WorkingWithInformation.basicWorkWithInfo*(..))"

        internal const val saveResultTest =
           "execution(* ru.grapecoffe.spring.Service.WorkingWithInformation.saveResultTest*(..))"

        internal const val getTestResult =
           "execution(* ru.grapecoffe.spring.Service.WorkingWithInformation.getTestResult*(..))"

        internal const val initialUser =
           "execution(* ru.grapecoffe.spring.Service.WorkingWithInformation.initialUser*(..))"
    }

    protected fun generalBenchVoid(inputJoinPoint: ProceedingJoinPoint)
    {
        initHashMap()
        inputJoinPoint.executingVoid(this.timeManager!!::startCountTime)

        RegisteringWorker.benchmarkFunMap.addFunAction(
            inputJoinPoint.signature.name
            , this.timeManager!!::getResultTime)
    }

    protected fun generalBenchObject(inputJoinPoint: ProceedingJoinPoint): Any
    {
        initHashMap()
        val valueResult = inputJoinPoint
            .executingObject(this.timeManager!!::startCountTime)

        RegisteringWorker.benchmarkFunMap.addFunAction(
            inputJoinPoint.signature.name
            , this.timeManager!!::getResultTime)

        return valueResult
    }

    private fun initHashMap()
    {
        if (this.timeManager == null)
            this.timeManager = contextApp.getTimeManagerBean()
    }
}
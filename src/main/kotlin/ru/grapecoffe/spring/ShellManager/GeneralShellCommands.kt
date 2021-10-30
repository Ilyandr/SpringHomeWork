package ru.grapecoffe.spring.ShellManager

import org.springframework.shell.Availability
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellMethodAvailability
import ru.grapecoffe.spring.DataAccessObject.InformationHandler
import ru.grapecoffe.spring.DataAccessObject.InformationHandler.Companion.MODE_CSV
import ru.grapecoffe.spring.DataAccessObject.UnifiedProcessing
import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.Entity.OutputUserInfo.startProgramInfo
import ru.grapecoffe.spring.GeneralClassApplication
import ru.grapecoffe.spring.Service.OperationsWithTime
import ru.grapecoffe.spring.Service.UnifiedWork
import ru.grapecoffe.spring.Service.WorkingWithInformation

@ShellComponent
internal open class GeneralShellCommands: GeneralClassApplication()
{
    private var basicWorker: UnifiedWork? = null
    private lateinit var timeManager: OperationsWithTime
    private var objectDAO: UnifiedProcessing? = null

    companion object { private const val TIME_MODE_ON = "on" }

    @ShellMethod(value = "Initial test information", key = ["i", "info"])
    fun getInitialAppInfo() = startProgramInfo()

    @ShellMethod(value = "Mode time manger (input: on / off)", key = ["modeT", "modeTimeManager"])
    fun getInitialAppInfo(timeMode: String)
    {
        if (timeMode == TIME_MODE_ON)
            this.timeManager = contextApp.getBean(OperationsWithTime::class.java)
    }

    @ShellMethod(value = "Mode work application", key = ["m", "mode"])
    fun initWorkModeApp(inputMode: String)
    {
       this.basicWorker = contextApp.getBean(WorkingWithInformation::class.java)
       this.objectDAO = contextApp.getBean(InformationHandler::class.java)

       this.basicWorker!!.setTimeControl(this.timeManager)

       this.basicWorker!!.inputDataFromDAO(inputDataTest = inputMode
           , objectDAO = this.objectDAO!!
           , itsTestCall = false)
    }

    @ShellMethod(value = "Give user answer", key = ["a", "answer"])
    @ShellMethodAvailability(value = ["isCorrectInit"])
    fun giveUserAnswer()
    {

    }

    private fun isCorrectInit(): Availability
    {
        return if ((this.basicWorker != null) && (this.objectDAO != null))
        {
            if (this.objectDAO!!.inputMode == MODE_CSV) Availability.available()
            else Availability.unavailable(OutputUserInfo.SHELL_ERROR_MODE)
        }
        else Availability.unavailable(OutputUserInfo.SHELL_ERROR_INIT)
    }
}
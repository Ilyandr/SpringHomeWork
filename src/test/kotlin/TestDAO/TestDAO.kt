package TestDAO

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import ru.grapecoffe.spring.DataAccessObject.InformationHandler
import ru.grapecoffe.spring.DataAccessObject.UnifiedProcessing
import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.GeneralClassApplication
import ru.grapecoffe.spring.Service.OperationsWithTime
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@DisplayName("Testing DAO class: \"InformationHandler\"")
internal class TestDAO
{
    @Test
    @DisplayName("Testing DAO data -> File CSV")
    fun checkSuccessUpdateCSV()
    {
        val informationHandler =
            setInformationHandler(InformationHandler.MODE_CSV)

        assertEquals(InformationHandler.MODE_CSV
            , informationHandler.requireContext().inputMode)

       generalTests(informationHandler)
    }

    @Test
    @DisplayName("Testing DAO data -> File TXT")
    fun checkSuccessUpdateTXT()
    {
        val informationHandler =
            setInformationHandler(InformationHandler.MODE_TXT)

        assertEquals(InformationHandler.MODE_TXT
            , informationHandler.inputMode)

        generalTests(informationHandler)
    }

    private fun generalTests(informationHandler: UnifiedProcessing)
    {
        assertNotEquals(informationHandler.readFileInfo(), null)
        assertNotEquals(informationHandler.getUpdateResult(), false)
    }

    private fun setInformationHandler(inputMode: Short): UnifiedProcessing
    {
       val returnValue = AnnotationConfigApplicationContext(GeneralClassApplication::class.java)
            .getBean(InformationHandler::class.java).requireContext()

        returnValue.inputMode = inputMode
        return returnValue
    }


    private fun UnifiedProcessing.getUpdateResult() =
        this.updateHistoryData(OutputUserInfo.testUserData
            , OutputUserInfo.testResultTest
            , OperationsWithTime())
}
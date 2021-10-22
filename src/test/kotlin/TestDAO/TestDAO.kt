package TestDAO

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.grapecoffe.spring.DataAccessObject.InformationHandler
import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.Service.OperationsWithTime
import java.io.FileReader
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
            , informationHandler.inputMode)

        assertNotEquals(informationHandler.connectionResource(), null)

        assertEquals(informationHandler.connectionResource()!!.encoding
            , FileReader(InformationHandler.CSV_FILE_PATH).encoding)

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

        assertNotEquals(informationHandler.connectionResource(), null)

        assertEquals(informationHandler.connectionResource()!!.encoding
            , FileReader(InformationHandler.TXT_FILE_PATH).encoding)

        generalTests(informationHandler)
    }


    private fun generalTests(informationHandler: InformationHandler)
    {
        assertNotEquals(informationHandler.readFileInfo(), null)
        assertNotEquals(informationHandler.getUpdateResult(), false)
    }

    private fun setInformationHandler(inputMode: Short) =
        InformationHandler(HashMap(), inputMode)


    private fun InformationHandler.getUpdateResult() =
        this.updateHistoryData(OutputUserInfo.testUserData
            , OutputUserInfo.testResultTest
            , OperationsWithTime())
}
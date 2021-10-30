package ru.grapecoffe.spring.DataAccessObject

import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.Entity.UserInformation
import au.com.bytecode.opencsv.CSVReader
import ru.grapecoffe.spring.Service.OperationsWithTime
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception
import kotlin.collections.HashMap
import java.util.Arrays

internal open class InformationHandler(override val informationHandler: HashMap<Any, Any>
, override var inputMode: Short = 0) : UnifiedProcessing
{
    companion object
    {
        const val MODE_CSV: Short = 1
        const val MODE_TXT: Short = 2

        const val TXT_FILE_PATH = "testingHistory.txt"
        const val CSV_FILE_PATH = "askInfo.csv"
        private const val DELIMITER_ASK_ANSWER = "<-->"
    }

    override fun readFileInfo() = when (requireContext().inputMode)
    {
       MODE_CSV -> requireContext().readCSVFile()
       MODE_TXT -> requireContext().readTXTFile()
       else -> null
    }

    override fun readCSVFile() = try
    {
        val csvReader = CSVReader(FileReader(CSV_FILE_PATH))
        val data = csvReader.readAll()

        for (datum in data)
        {
            val bigDataInfo = Arrays.toString(datum)
                .cleanSymbolAsk()

            requireContext().informationHandler[bigDataInfo.getAsk()] =
                bigDataInfo.getAnswer()
        }

        data.clear()
        csvReader.close()
      } catch (e: IOException) { print(e.message) }

    override fun readTXTFile() = try
    {
        FileReader(TXT_FILE_PATH).use { reader ->
           val bigDataInfo = reader.readLines()

           for (i in bigDataInfo.indices)
           this.informationHandler["${i + 1})"] = bigDataInfo[i]

           reader.close()
       }
    } catch (e: IOException) { print(e.message) }

   override fun updateHistoryData(userInfo: UserInformation
        , resultTest: Int
        , completeTimeManager: OperationsWithTime?) = try
    {
       val writeNewInfo = FileWriter(File(TXT_FILE_PATH), true)

       writeNewInfo.write(
           if (userInfo.hashCode() != OutputUserInfo.testUserData.hashCode())
               "\n${completeTimeManager?.getNowTime() ?: ""}. Тестируемый:" +
               " $userInfo. ${OutputUserInfo.USER_INFO} $resultTest%." +
               " Время прохождения (сек): ${completeTimeManager?.getResultTime(true) ?: "-"}." else "")

       writeNewInfo.close()
       true
    } catch (e: Exception) { false }
    
    private fun String.getAsk() = this.split(DELIMITER_ASK_ANSWER)[0]
    private fun String.getAnswer() = this.split(DELIMITER_ASK_ANSWER)[1]

    private fun String.cleanSymbolAsk() = this.replace("[", "")
        .replace("]", "")
}
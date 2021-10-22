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
import java.util.*
import kotlin.collections.HashMap
import java.util.Arrays

internal class InformationHandler(override val informationHandler: HashMap<Any, Any>
, var inputMode: Short = 0) : UnifiedProcessing
{
    private lateinit var csvReader: CSVReader

    companion object
    {
        const val MODE_CSV: Short = 1
        const val MODE_TXT: Short = 2

        const val TXT_FILE_PATH = "testingHistory.txt"
        const val CSV_FILE_PATH = "askInfo.csv"
        private const val DELIMITER_ASK_ANSWER = "<-->"
    }

    override fun connectionResource() = when (this.inputMode)
    {
        MODE_CSV -> FileReader(CSV_FILE_PATH)
        MODE_TXT -> FileReader(TXT_FILE_PATH)
        else -> null
    }

    override fun readFileInfo() = when (this.inputMode)
    {
       MODE_CSV -> readCSVFile()
       MODE_TXT -> readTXTFile()
       else -> null
    }

    private fun readCSVFile(): HashMap<Any, Any>? = try
    {
          this.csvReader = CSVReader(run(::connectionResource))
          val data = this.csvReader.readAll()

          for (datum in data)
          {
              val bigDataInfo = Arrays.toString(datum).cleanSymbolAsk()
              this.informationHandler[bigDataInfo.getAsk()] = bigDataInfo.getAnswer()
          }

          data.clear()
          this.csvReader.close()
          this.informationHandler
      } catch (e: IOException) { print(e.message); null }


    private fun readTXTFile() = try
    {
       (run(::connectionResource)).use { reader ->
           if (reader == null) return@use null
           val bigDataInfo = reader.readLines()

           for (i in bigDataInfo.indices)
           this.informationHandler["${i + 1})"] = bigDataInfo[i]

           reader.close()
           this.informationHandler
       }
    } catch (ex: IOException) { null }

    fun updateHistoryData(userInfo: UserInformation
        , resultTest: Int
        , completeTimeManager: OperationsWithTime?) = try
    {
       val writeNewInfo = FileWriter(File(TXT_FILE_PATH), true)

       writeNewInfo.write(if (userInfo.hashCode() != OutputUserInfo.testUserData.hashCode())
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
package TestService

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import ru.grapecoffe.spring.DataAccessObject.InformationHandler
import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.GeneralClassApplication
import ru.grapecoffe.spring.Service.WorkingWithInformation


@DisplayName("Service class: \"WorkingWithInformation\"")
internal class TestService
{
    private val testContextApp: WorkingWithInformation
    private val informationHandler: InformationHandler

    init
    {
        this.testContextApp =
            AnnotationConfigApplicationContext(GeneralClassApplication::class.java)
            .getBean(WorkingWithInformation::class.java)

        this.informationHandler =
            AnnotationConfigApplicationContext(GeneralClassApplication::class.java)
            .getBean(InformationHandler::class.java)
    }

    @Test
    @DisplayName("Testing a class service -> CSV mode")
     fun testReturnDataTestCSV() = this.testContextApp
        .inputDataFromDAO(OutputUserInfo.TYPE_OPERATION_TEST
            , this.informationHandler)

    @Test
    @DisplayName("Testing a class service -> TXT mode")
     fun testReturnDataTestTXT() = this.testContextApp
        .inputDataFromDAO(OutputUserInfo.TYPE_OPERATION_HISTORY
            , this.informationHandler)
}
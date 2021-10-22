package TestService

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import ru.grapecoffe.spring.Application
import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.Service.UnifiedWork
import ru.grapecoffe.spring.Service.WorkingWithInformation


@DisplayName("Service class: \"WorkingWithInformation\"")
internal class TestService
{
    private val testContextApp: UnifiedWork
    init
    {
        this.testContextApp =
        AnnotationConfigApplicationContext(Application::class.java)
            .getBean(WorkingWithInformation::class.java)
    }

    @Test
    @DisplayName("Testing a class service -> CSV mode")
     fun testReturnDataTestCSV() = this.testContextApp
        .inputDataFromDAO(OutputUserInfo.TYPE_OPERATION_TEST)

    @Test
    @DisplayName("Testing a class service -> TXT mode")
     fun testReturnDataTestTXT() = this.testContextApp
        .inputDataFromDAO(OutputUserInfo.TYPE_OPERATION_HISTORY)
}
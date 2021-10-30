package TestService

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.grapecoffe.spring.Service.OperationsWithTime
import kotlin.test.assertNotEquals


@DisplayName("Service class: \"OperationsWithTime\"")
internal class TestTimeManager
{
    private val operationsWithTime: OperationsWithTime
    init { this.operationsWithTime = OperationsWithTime() }

    @Test
    @DisplayName("Testing the method \"getResultTime\"")
    fun testGetResultTime()
    {
        assertNotEquals(this.operationsWithTime.getResultTime(), "")
        assertNotEquals(this.operationsWithTime.getResultTime(totalTime = true), "")
    }

    @Test
    @DisplayName("Testing the method \"getNowTime\"")
     fun testGetNowTime() =
        assertNotEquals(this.operationsWithTime.getNowTime(), "")

    @Test
    @DisplayName("Testing setter value \"timeCountStart\"")
    fun testSetterValueCountStart()
    {
        this.operationsWithTime.startCountTime()
        assertNotEquals(operationsWithTime.getTimeTest(), 0)
    }
}
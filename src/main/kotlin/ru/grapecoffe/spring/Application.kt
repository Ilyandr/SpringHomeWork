package ru.grapecoffe.spring

import ru.grapecoffe.spring.Service.WorkingWithInformation
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.grapecoffe.spring.Entity.OutputUserInfo
import ru.grapecoffe.spring.Service.OperationsWithTime
import ru.grapecoffe.spring.Service.UnifiedWork

@Configuration
@ComponentScan
internal open class Application
{
    companion object
    {
        @JvmStatic
        fun main(arr: Array<String>)
        {
            val context = AnnotationConfigApplicationContext(Application::class.java)
            val basicWorker: UnifiedWork = context.getBean(WorkingWithInformation::class.java)

            OutputUserInfo.startProgramInfo()
            basicWorker.setTimeControl(context.getBean(OperationsWithTime::class.java))

            basicWorker.inputDataFromDAO(null)
            context.close()
        }
    }
}
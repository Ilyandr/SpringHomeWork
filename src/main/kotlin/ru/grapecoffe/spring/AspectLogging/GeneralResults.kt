package ru.grapecoffe.spring.AspectLogging

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.context.ConfigurableApplicationContext
import ru.grapecoffe.spring.Service.OperationsWithTime

internal interface GeneralResults
{
    companion object
    {
        val setMap: HashMap<String, Long>
        get() = HashMap()
    }

    fun HashMap<String, Long>.addFunAction(signName: String
         , resultTime: (Boolean, Boolean) -> String) =
        this.put(signName, (run {resultTime(false, true )}).toLong())

    fun ConfigurableApplicationContext.getTimeManagerBean() =
        this.getBean(OperationsWithTime::class.java) as OperationsWithTime

    fun ProceedingJoinPoint.executingVoid(countTime: () -> Unit)
    {
        run { countTime }
        this.proceed()
    }

    fun ProceedingJoinPoint.executingObject(countTime: () -> Unit): Any
    {
        run { countTime }
        return this.proceed()
    }
}
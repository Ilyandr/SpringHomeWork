package ru.grapecoffe.spring.AspectLogging

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.stereotype.Component

@EnableAutoConfiguration
@EnableAspectJAutoProxy(exposeProxy = true)
@Component
@Aspect
internal class AspectServiceManager: GeneralAspectsData()
{
    @Around(inputDataFromDAO)
    fun logInputDataFromDAO(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchVoid(inputJoinPoint)

    @Around(basicWorkWithInfo)
    fun logBasicWorkWithInfo(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchVoid(inputJoinPoint)

    @Around(saveResultTest)
    fun logSaveResultTest(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchVoid(inputJoinPoint)

    @Around(getTestResult)
    fun logGetTestResult(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchObject(inputJoinPoint)

    @Around(initialUser)
    fun logInitialUser(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchObject(inputJoinPoint)
}
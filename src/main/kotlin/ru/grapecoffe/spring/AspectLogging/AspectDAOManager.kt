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
internal class AspectDAOManager: GeneralAspectsData()
{
    @Around(readFileInfo)
    fun logReadFileInfo(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchVoid(inputJoinPoint)

    @Around(readCSVFile)
    fun logReadCSVFile(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchVoid(inputJoinPoint)

    @Around(readTXTFile)
    fun logReadTXTFile(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchVoid(inputJoinPoint)

    @Around(updateHistoryData)
    fun logUpdateHistoryData(inputJoinPoint: ProceedingJoinPoint) =
        generalBenchObject(inputJoinPoint)
}
package ru.grapecoffe.spring.Configuration

import ru.grapecoffe.spring.Service.WorkingWithInformation
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import ru.grapecoffe.spring.AspectLogging.GeneralResults
import java.util.*

@Service
internal class RegisteringWorker
{
    @Bean
    fun workingWithInformation(): WorkingWithInformation =
        WorkingWithInformation(Scanner(System.`in`))

    companion object
    { @JvmField val benchmarkFunMap = GeneralResults.setMap }
}
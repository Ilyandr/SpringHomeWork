package ru.grapecoffe.spring.Configuration

import ru.grapecoffe.spring.Service.WorkingWithInformation
import ru.grapecoffe.spring.DataAccessObject.InformationHandler
import ru.grapecoffe.spring.Service.UnifiedWork
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.util.*

@Service
internal class RegisteringWorker
{
    @Bean
    fun workingWithInformation(): UnifiedWork = WorkingWithInformation(Scanner(System.`in`)
        , InformationHandler(HashMap()))
}
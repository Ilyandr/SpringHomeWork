package ru.grapecoffe.spring.Configuration

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import ru.grapecoffe.spring.Service.OperationsWithTime

@Service
internal class RegisteringTimeManager
{
    @Bean
    fun operationsWithTime() = OperationsWithTime()
}
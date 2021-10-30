package ru.grapecoffe.spring.Configuration

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import ru.grapecoffe.spring.DataAccessObject.InformationHandler
import ru.grapecoffe.spring.DataAccessObject.UnifiedProcessing
import kotlin.collections.HashMap

@Service
internal class RegisteringDAO
{
    @Bean
    fun informationHandler(): UnifiedProcessing = InformationHandler(HashMap())
}
package ru.grapecoffe.spring.Entity

 data class UserInformation(val userFirstName: String
, val userSecondName: String
, val userAge: Short)
{ override fun toString() = "$userFirstName $userSecondName $userAge" }

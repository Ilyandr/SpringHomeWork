package ru.grapecoffe.spring.Entity

internal object OutputUserInfo
{
   const val TYPE_OPERATION_TEST = "Старт"
   const val TYPE_OPERATION_HISTORY = "История"

   private const val startProgramInfo = "\nДобро пожаловать в небольшой тест" +
           ". Правила прохождения: отвечайте на вопросы (Да / Нет), либо вариантом из вопроса" +
           ".\nЕсли вы хотите посмотреть историю тестирования - введите \"История\"" +
           ", если хотите начать тестирование - введите \"Старт\": "
   private const val SUCCESSFUL_TEST = "The test has successfully reached the save level"
   const val outputUserInformation = "\nКонец теста.\nВведите: \"Имя Фамилия Возраст\" (через пробел): "
   const val ERROR_INFO = "Возникла непредвиденная ошибка - повторите попытку действия"
   const val SUCCESSFUL_SAVE_INFO = "\nРезультаты тестирование успешно сохранены. Тестируемый: "
   const val USER_INFO = "Результат прохождения теста:"

   const val SHELL_ERROR_INIT = "Ошибка - укажите режим работы программы (Shell: m, mode)"
   const val SHELL_ERROR_MODE = "Ошибка - данный режим работы программы не поддерживает такую команду (Shell: m, mode)"

   const val testResultTest = 80
   val testUserData = UserInformation("John_Test"
         , "Mnemonic"
         , 256)

   @JvmStatic fun startProgramInfo() = print(startProgramInfo)
   fun successfulActionTest() = print(SUCCESSFUL_TEST)
}
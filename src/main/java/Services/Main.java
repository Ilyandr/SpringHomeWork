package Services;

import Controllers.Ask;
import Controllers.AskManager;
import Entity.LearnerInfo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.Scanner;

public class Main
{
    public static void main(String... args)
    {
        final ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("/IoC.xml");

        System.out.print("\n\nДобро пожаловать в небольшой тест" +
                " - время выполнения будет фиксироваться.\nДля начала теста введите слово \"старт\": ");
        final Scanner inputData = new Scanner(System.in);

        if (!inputData.next().equals("старт"))
        {
            System.out.println("Возникла ошибка - начните тест заного.");
            return;
        }

        System.out.print("Для начала заполните ваши данные через пробел (Имя Фамилия Возраст): ");
        final LearnerInfo singleLearner = applicationContext.getBean("learnerInfo", LearnerInfo.class);
        singleLearner.setInfo(inputData.next()
                , inputData.next()
                , inputData.nextShort());

        final Ask askManager = applicationContext.getBean("askManager", AskManager.class);
        String userAnswer, ask;

        long dateStart;
        short scoreAsk = askManager.getCountAsk();

        System.out.println("\nНачало теста\n\n");
        while (scoreAsk != 0)
        {
            ask = askManager.getAsk();
            System.out.print(ask + ". Ответ: ");

            dateStart = new Date().getTime();
            userAnswer = inputData.next();

            askManager.writeResult(ask
                    , new Date().getTime() - dateStart
                    , userAnswer);

            scoreAsk--;
        }

        System.out.println(askManager.getResultTest(singleLearner));
        askManager.finishTest();
    }
}


package ru.grapecoffe.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.grapecoffe.spring.AspectLogging.GeneralAspectsData;

@EnableAspectJAutoProxy
@SpringBootApplication
public class GeneralClassApplication
{
    protected static ConfigurableApplicationContext contextApp;

    public static void main(String... args)
    {
        contextApp = SpringApplication.run(GeneralClassApplication.class, args);

        contextApp.close();
        GeneralAspectsData.getLogResult();
    }
}

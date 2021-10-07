package Controllers;

import Entity.Assay;
import Entity.LearnerInfo;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AskManager implements Ask
{
    private final List<String> allAsk;
    private final List<Assay> dataAllAsk;
    private short numberAsk = 0;

    @SuppressWarnings("resource")
    public AskManager()
    {
        this.allAsk = new ArrayList<>();
        this.dataAllAsk = new ArrayList<>();
        final CSVReader csvReader;

        try
        {
            csvReader = new CSVReader(new FileReader("ask.csv"));

            final List<String[]> data = csvReader.readAll();
            for (String[] datum : data) this.allAsk.add(Arrays.toString(datum));

            data.clear();
            csvReader.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public String getAsk()
    {
        this.numberAsk++;
        return this.allAsk.get(this.numberAsk - 1);
    }

    @Override
    public void writeResult(String ask, Long time, Object answer)
    {
       this.dataAllAsk.add(new Assay(ask
               , answer
               , new SimpleDateFormat("mm:ss").format(new Date(time))));
    }

    @Override
    public String getResultTest(LearnerInfo learnerInfo)
    {
       final Object[] infoFalse = {null, null, null, null, null};
       final int scoreAsk = dataAllAsk.size();
       short resultScore = 100;

       for (int i = 0; i < scoreAsk; i++)
       {
           if (this.dataAllAsk.get(i).getAnswer() != null)
           {
               switch (i)
               {
                   case 0:
                       if (!Objects.equals(this.dataAllAsk.get(i).getAnswer(), "Нет"))
                       {
                           resultScore -= 20;
                           infoFalse[i] = "Нет";
                       }
                       break;

                   case 2:
                       if (!Objects.equals(this.dataAllAsk.get(i).getAnswer(), "public"))
                       {
                           resultScore -= 20;
                           infoFalse[i] = "public";
                       }
                       break;

                   case 3:
                       if (!Objects.equals(this.dataAllAsk.get(i).getAnswer(), "object"))
                       {
                           resultScore -= 20;
                           infoFalse[i] = "object";
                       }
                       break;

                   default:
                       if (!Objects.equals(this.dataAllAsk.get(i).getAnswer(), "Да"))
                       {
                           resultScore -= 20;
                           infoFalse[i] = "Да";
                       }
               }
           } else resultScore -= 20;
       }

        return "\nУченик: " + learnerInfo.getLastName()
                + " " + learnerInfo.getFirstName()
                + ", возраст: " + learnerInfo.getAge() + "\n------------\nРезультаты теста:\n"
                + infoSingleAsk(infoFalse) + "\n------------\nОбщий результат теста: "
                + resultScore + "%.";
    }

    private String infoSingleAsk(Object[] infoFalse)
    {
        this.numberAsk = 0;
        final StringBuilder returnInfo = new StringBuilder();

        for (int i = 0; i < infoFalse.length; i++)
            returnInfo.append(getAsk()).append(" - ")
                    .append(infoFalse[this.numberAsk - 1] != null
                    ? "неверно, правильный ответ: " + infoFalse[this.numberAsk - 1] : "верно")
                    .append(". Время выполения (в минутах): ")
                    .append(dataAllAsk.get(this.numberAsk - 1).getTimeAnswer())
                    .append(".\n");

       return returnInfo.toString();
    }

    @Override
    public short getCountAsk() { return (short) this.allAsk.size(); }

    @Override
    public void finishTest()
    {
        this.allAsk.clear();
        this.dataAllAsk.clear();
    }
}

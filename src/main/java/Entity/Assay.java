package Entity;

public class Assay
{
    private final String ask;
    private final Object answer;
    private final String timeAnswer;

    public Assay(String ask, Object answer, String timeAnswer)
    {
        this.ask = ask;
        this.answer = answer;
        this.timeAnswer = timeAnswer;
    }

    public String getAsk() { return ask; }
    public Object getAnswer() { return answer; }
    public String getTimeAnswer() { return timeAnswer; }
}

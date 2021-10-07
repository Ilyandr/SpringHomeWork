package Controllers;

import Entity.LearnerInfo;

public interface Ask
{
    String getAsk();
    String getResultTest(LearnerInfo learnerInfo);

    short getCountAsk();

    void writeResult(String ask, Long time, Object answer);
    void finishTest();
}

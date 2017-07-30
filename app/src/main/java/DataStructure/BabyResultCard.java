package DataStructure;

import questions.MonthQuestions;

/**
 * Created by SuperSong on 2017-03-28.
 */

public class BabyResultCard {
    private String title;
    private int[] results;
    MonthQuestions myMonth;

    public BabyResultCard(String title, int[] results, MonthQuestions _month) {
        this.title = title;
        this.results = results;
        this.myMonth = _month;

    }

    public String getTitle() {
        return title;
    }

    public int[] getResults() {
        return results;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResults(int[] results) {
        this.results = results;
    }

    public void setMyMonth(MonthQuestions myMonth) {
        this.myMonth = myMonth;
    }

    public MonthQuestions getMyMonth() {

        return myMonth;
    }
}

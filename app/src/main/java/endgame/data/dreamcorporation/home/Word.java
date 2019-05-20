package endgame.data.dreamcorporation.home;

public class Word {

    //String for Title
    private String title;

    //String for word
    private String time;

    //String for amount
    private String amount;

    public Word(String title, String time, String amount) {
        this.title = title;
        this.time = time;
        this.amount = amount;
    }

    public String getTitle(){
        return this.title;
    }

    public String getTime(){
        return this.time;
    }

    public String getAmount() {
        return amount;
    }
}

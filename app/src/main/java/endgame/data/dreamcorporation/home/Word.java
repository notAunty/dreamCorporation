package endgame.data.dreamcorporation.home;

public class Word {

    //String for Title
    private String title;

    //String for word
    private String time;

    //String for amount
    private String amount;

    //int image for copy
//    private int image;

    public Word(String title, String time, String amount) {
//            , int image
        this.title = title;
        this.time = time;
        this.amount = amount;
//        this.image = image;
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

    //    public int getImage(){
//        return this.image;
//    }
}

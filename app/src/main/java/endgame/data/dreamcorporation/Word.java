package endgame.data.dreamcorporation;

public class Word {

    //String for Title
    private String title;
    //String for word
    private String item;

    public Word(String title, String item) {
        this.title = title;
        this.item = item;
    }

    public String getTitle(){
        return this.title;
    }

    public String getItem(){
        return this.item;
    }
}

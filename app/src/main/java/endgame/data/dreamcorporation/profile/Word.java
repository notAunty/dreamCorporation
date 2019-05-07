package endgame.data.dreamcorporation.profile;

import android.widget.ImageView;

import endgame.data.dreamcorporation.R;

public class Word {

    //String for Title
    private String title;
    //String for word
    private String item;
    //int image for copy
    private int image;

    public Word(String title, String item, int image) {
        this.title = title;
        this.item = item;
        this.image = image;
    }

    public String getTitle(){
        return this.title;
    }

    public String getItem(){
        return this.item;
    }

    public int getImage(){
        return this.image;
    }
}

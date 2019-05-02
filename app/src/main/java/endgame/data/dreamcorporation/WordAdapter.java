package endgame.data.dreamcorporation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(Activity context, ArrayList<Word> word){
        super(context, 0, word);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Word currentWord = getItem(position);

        TextView titleTextView = (TextView) listView.findViewById(R.id.title);
        titleTextView.setText(currentWord.getTitle());

        TextView itemTextView = (TextView) listView.findViewById(R.id.item);
        itemTextView.setText(currentWord.getItem());

        return listView;
    }
}

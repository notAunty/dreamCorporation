package endgame.data.dreamcorporation.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import endgame.data.dreamcorporation.R;

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
        Word current = getItem(position);

        TextView titleTextView = (TextView) listView.findViewById(R.id.recent_title);
        titleTextView.setText(current.getTitle());

        TextView subtitleTextView = (TextView) listView.findViewById(R.id.recent_subtitle);
        subtitleTextView.setText(current.getTime());

        TextView amountTextView = (TextView) listView.findViewById(R.id.recent_amount);
        amountTextView.setText(current.getAmount());

//        ImageView imageView = (ImageView)listView.findViewById(R.id.image);
//        imageView.setImageResource(current.getImage());

        return listView;
    }
}

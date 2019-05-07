package endgame.data.dreamcorporation.profile;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import endgame.data.dreamcorporation.R;

public class WordAdapter extends ArrayAdapter<Word> {

    ClipboardManager clipboardManager;
    String text;
    private FirebaseAuth mAuth;

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

        TextView titleTextView = (TextView) listView.findViewById(R.id.title);
        titleTextView.setText(current.getTitle());

        TextView itemTextView = (TextView) listView.findViewById(R.id.item);
        itemTextView.setText(current.getItem());

        ImageView imageView = (ImageView) listView.findViewById(R.id.image);
        imageView.setImageResource(current.getImage());

        return listView;
    }

}

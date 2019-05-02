package endgame.data.dreamcorporation;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    final Context context = getActivity();
    private TextView decrypt;
    private String key;
    private ListView listView;
    private ArrayList<Word> words;
    private String tempUID = "Null";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth // IMPORTANT
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//    final FragmentManager manager = getFragmentManager();

//        TextView uidField = (TextView) view.findViewById(R.id.uid);
        if (!tempUID.isEmpty()) tempUID = mAuth.getUid();

        decrypt = (TextView) view.findViewById(R.id.profile_name);

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        keyDialog.show(getFragmentManager(), "a");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // Get the layout inflater
                LayoutInflater inflater = requireActivity().getLayoutInflater();

//        builder.setTitle("Title");
//        builder.setMessage("Do you want to delete ?");
                View temp = inflater.inflate(R.layout.dialog_key, null);
                final EditText key = (EditText) temp.findViewById(R.id.key);
                builder.setView(temp)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),
                                        "Welcome, " + key.getText() + " !", Toast.LENGTH_LONG).show();
                                decrypt.setText(key.getText());
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View temp = layoutInflater.inflate(R.layout.dialog,null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(temp);
//        final EditText editText = (EditText)temp.findViewById(R.id.Text);
//        builder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
//          public void onClick(DialogInterface dialog,int id) {
//          // get user input and set it to result
//            // edit text
//            textView.setText(editText.getText());
//          }
//        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//          public void onClick(DialogInterface dialog,int id) {
//            dialog.cancel();
//          }
//        });
//
//        // create alert dialog
//        AlertDialog alertDialog = builder.create();
//
//        // show it
//        alertDialog.show();
            }
        });

        words = new ArrayList<Word>();
        words.add(new Word("User UID", tempUID));
        words.add(new Word("If this helped you","This is placeholder"));
        words.add(new Word("CHANGE TO BETTER ONE, this no ripple then tapped","CHANGE TO BETTER ONE, this no ripple then tapped"));
        words.add(new Word("We respect your privacy", "Privacy Policy"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        words.add(new Word("Application by", "FUCKING CHANGE THIS"));
        WordAdapter itemAdapter = new WordAdapter(getActivity(),  words);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(itemAdapter);

        return view;
    }


//  public void onCreateDialog() {
//    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//    // Get the layout inflater
//    LayoutInflater inflater = requireActivity().getLayoutInflater();
//
//    // Inflate and set the layout for the dialog
//    // Pass null as the parent view because its going in the dialog layout
//    builder.setView(inflater.inflate(R.layout.dialog_key, null))
//            // Add action buttons
//            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//              @Override
//              public void onClick(DialogInterface dialog, int id) {
////                key = ((EditText) getActivity().findViewById(R.id.login_userid)).getText().toString();
//              }
//            });
//    AlertDialog dialog = builder.create();
//    dialog.create();
////    return builder.create();
//  }
}
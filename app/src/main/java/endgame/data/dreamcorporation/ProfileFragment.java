package endgame.data.dreamcorporation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import endgame.data.dreamcorporation.profile.Word;
import endgame.data.dreamcorporation.profile.WordAdapter;

import static android.content.Context.CLIPBOARD_SERVICE;



public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private TextView decrypt, user_key;
    private String text;
    private ListView listView;
    private ArrayList<Word> words;
    private String tempUID = "Null";
    private ClipboardManager clipboardManager;
    private ImageView copy_key, qrImage;
    private FloatingActionButton floatingActionButton;
    private Button done;
    private QRGEncoder qrgEncoder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth // IMPORTANT
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//    final FragmentManager manager = getFragmentManager();

        user_key = (TextView) view.findViewById(R.id.user_key);
//        TextView uidField = (TextView) view.findViewById(R.id.uid);
        if (!tempUID.isEmpty()) user_key.setText(mAuth.getUid());

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
//                                Toast.makeText(getContext(),
//                                        "Welcome, " + key.getText() + " !", Toast.LENGTH_LONG).show();
//                                decrypt.setText(key.getText());
                                if(key.getText().toString().equals(mAuth.getUid())){
                                    Toast.makeText(getContext(),
                                            "Welcome, " + "TAN WEI PENG" + " !", Toast.LENGTH_LONG).show();
                                    decrypt.setText("TAN WEI PENG");
                                }else
                                    Toast.makeText(getContext(),
                                            "Wrong! Please try again!", Toast.LENGTH_SHORT).show();
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

        displayList(view);

        clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        copy_key = (ImageView) view.findViewById(R.id.clipboard);
        copy_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = mAuth.getUid();
                ClipData clipData = ClipData.newPlainText("Text", text);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(),"Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton = view.findViewById(R.id.qr_code);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View temp = inflater.inflate(R.layout.qr_code, null);

                done = (Button) temp.findViewById(R.id.back);
                qrImage = (ImageView) temp.findViewById(R.id.qr_image);
                String word = mAuth.getUid();
                qrgEncoder = new QRGEncoder(word,null, QRGContents.Type.TEXT,800);
                builder.setView(temp);
                try {
                    Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                    qrImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    Toast.makeText(getContext(),"No QR Code",Toast.LENGTH_SHORT);
                }
                final AlertDialog alertDialog = builder.create();
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                alertDialog.show();
            }
        });

        return view;
    }


    public void displayList(View view){
        words = new ArrayList<Word>();
//        words.add(new Word(getResources().getString(R.string.user_key), tempUID));
        words.add(new Word(getResources().getString(R.string.example),getResources().getString(R.string.example)));
        words.add(new Word("CHANGE TO BETTER ONE, this no ripple then tapped",getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
        WordAdapter itemAdapter = new WordAdapter(getActivity(),  words);
        listView = (ListView) view.findViewById(R.id.profile_listView);
        listView.setAdapter(itemAdapter);
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
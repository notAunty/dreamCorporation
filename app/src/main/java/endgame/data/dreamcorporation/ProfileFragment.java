package endgame.data.dreamcorporation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

  private FirebaseAuth mAuth;
  final Context context = getActivity();
  private TextView textView;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth // IMPORTANT
    View view = inflater.inflate(R.layout.fragment_profile, container, false);

    TextView uidField = (TextView) view.findViewById(R.id.uid);
    String tempUID = mAuth.getUid();

    if (!tempUID.isEmpty()) uidField.setText(tempUID);

    textView = (TextView) view.findViewById(R.id.testing);

    ImageView imageView = (ImageView) view.findViewById(R.id.opendialog);
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View temp = layoutInflater.inflate(R.layout.dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(temp);
        final EditText editText = (EditText)temp.findViewById(R.id.Text);
        builder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog,int id) {
          // get user input and set it to result
            // edit text
            textView.setText(editText.getText());
          }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog,int id) {
            dialog.cancel();
          }
        });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
      }
    });

    return view;
  }

}

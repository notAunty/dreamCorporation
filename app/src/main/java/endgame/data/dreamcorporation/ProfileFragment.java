package endgame.data.dreamcorporation;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

  private FirebaseAuth mAuth;
  final Context context = getActivity();
  private TextView decrypt;
  private String key;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
    mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth // IMPORTANT
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
//    final FragmentManager manager = getFragmentManager();


    TextView uidField = (TextView) view.findViewById(R.id.uid);
    String tempUID = mAuth.getUid();
    if (!tempUID.isEmpty()) uidField.setText(tempUID);

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

        builder.setView(inflater.inflate(R.layout.dialog_key, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(),
                            "Click-Click!!", Toast.LENGTH_LONG).show();
                    dialog.cancel();
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
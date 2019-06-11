package endgame.data.dreamcorporation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ProfileFragment extends Fragment {

  private String text;
  private String tempUID = "null";
  private String encryptedFullName;
  private Encryption encryption = new Encryption();
  private TextView decrypt, user_key, adminTextView;
  private RelativeLayout admin, copy_key, log_out;
  private ClipboardManager clipboardManager;
  private FirebaseAuth mAuth;
  private ImageView copy_image_key;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
    mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth, IMPORTANT
    View view = inflater.inflate(R.layout.fragment_profile, container, false);

    user_key = (TextView) view.findViewById(R.id.user_key);
    if (!tempUID.isEmpty()) user_key.setText(mAuth.getUid());

    adminTextView = (TextView) view.findViewById(R.id.profile_admin_textview);
    admin = (RelativeLayout) view.findViewById(R.id.profile_admin);
    if (mAuth.getUid().equals(GetFirebase.getAdminUid())) adminTextView.setText("Admin Tools");
    admin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getContext(), ActivityTestingData.class);
        startActivity(intent);
      }
    });


    encryptedFullName = GetFirebase.getUsers(mAuth.getUid()).getFullName();

    decrypt = (TextView) view.findViewById(R.id.profile_name);
    final String tempStr = decrypt.getText().toString();

    decrypt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View temp = inflater.inflate(R.layout.dialog_key, null);
        final EditText key = (EditText) temp.findViewById(R.id.key);

        builder.setView(temp).setPositiveButton("OK", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {

            String tempDec = encryption.decodeDirectly(encryptedFullName);
            encryption = new Encryption();
            if (!key.getText().toString().equals(mAuth.getUid())) {
              Toast.makeText(getContext(),
                      "Wrong! Please try again!", Toast.LENGTH_SHORT).show();
            } else {
              decrypt.setText(tempDec);
              decrypt.setEnabled(false);
            }
          }
        });

        AlertDialog alert = builder.create();
        alert.show();
      }
    });

    clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
    copy_key = view.findViewById(R.id.clipboard);
    copy_key.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        text = mAuth.getUid();
        ClipData clipData = ClipData.newPlainText("Text", text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
      }
    });
    copy_image_key = view.findViewById(R.id.user_key_image);
    copy_image_key.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        text = mAuth.getUid();
        ClipData clipData = ClipData.newPlainText("Text", text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
      }
    });

    log_out = view.findViewById(R.id.log_out);
    log_out.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        GetFirebase.usersUid = new ArrayList();
        GetFirebase.users = new ArrayList();
        GetFirebase.transactions = new ArrayList();
        GetFirebase.transactionId = new ArrayList();
        Intent backToLogIn = new Intent(getContext(), MainActivity.class);
        backToLogIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().finish();
        startActivity(backToLogIn);
      }
    });

    return view;
  }
}
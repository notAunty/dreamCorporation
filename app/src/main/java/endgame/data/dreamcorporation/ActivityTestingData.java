package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ActivityTestingData extends AppCompatActivity {

  private String selectedUserUid;
  private FirebaseAuth mAuth;
  private Spinner adminMode;
  private Encryption encryption = new Encryption();

  private EditText userName;
  private EditText fullName;
  private EditText password;
  private EditText balance;
  private EditText upId;
  private EditText dwId;
  private EditText fee;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_testing_data);

    mAuth = FirebaseAuth.getInstance();

    if (!mAuth.getUid().equals(GetFirebase.getAdminUid())) onBackPressed();

    userName = (EditText) findViewById(R.id.testing_username);
    fullName = (EditText) findViewById(R.id.testing_fullName);
    password = (EditText) findViewById(R.id.testing_pw);
    balance = (EditText) findViewById(R.id.testing_balance);
    upId = (EditText) findViewById(R.id.testing_upline);
    dwId = (EditText) findViewById(R.id.testing_downline);
    fee = (EditText) findViewById(R.id.testing_fee);

    Toolbar toolbar = findViewById(R.id.testing_toolbar);
    setSupportActionBar(toolbar);

    //get the spinner from the xml.
    adminMode = findViewById(R.id.testing_mode);
    //create a list of items for the spinner.
    String[] adminList = new String[]{"Create", "Retrieve", "Update", "Delete"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
    ArrayAdapter<String> adminModeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, adminList);
    //set the spinners adapter to the previously created one.
    adminMode.setAdapter(adminModeAdapter);

    final Spinner usersListSpinner = findViewById(R.id.testing_usersList);
    String[] usersList = GetFirebase.usersUid.toArray(new String[GetFirebase.usersUid.size()]);
    ArrayAdapter<String> usersListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
    usersListAdapter.add("Select user");
    usersListAdapter.addAll(usersList);

    usersListSpinner.setAdapter(usersListAdapter);
    usersListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Users thisUser;
        if (position > 0) selectedUserUid = GetFirebase.usersUid.get(position - 1);

        if (position == 0) thisUser = new Users("","", 0);
        else thisUser = GetFirebase.getUsers(GetFirebase.usersUid.get(position - 1)); // TODO check this

        userName.setText(thisUser.getUserName());
        fullName.setText(encryption.decodeDirectly(thisUser.getFullName()));
        encryption = new Encryption();
        balance.setText(String.valueOf(thisUser.getBalance()));
        fee.setText(String.valueOf(GetFirebase.getFee()));
        upId.setText(thisUser.getUplineUid());
        dwId.setText(thisUser.getDownlineUid().toString());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) { }
    });

    Button fab = findViewById(R.id.testing_fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int adminModePosition = adminMode.getSelectedItemPosition();
        final View tempView = view;

        switch (adminModePosition) {
          case 0:  //CREATE
            if (password.length() < 6 || userName.length() < 1) {
              Toast.makeText(view.getContext(), "Password longer a bit can ah??",
                      Toast.LENGTH_SHORT).show();
            } else if (GetFirebase.existUser(userName.toString())) {
              Toast.makeText(view.getContext(), "Username unavailable",
                      Toast.LENGTH_SHORT).show();
            } else {
              if (!GetFirebase.existUser(userName.toString())) {
                mAuth.createUserWithEmailAndPassword(userName + "@asdfggfdsa.com", password.toString())
                        .addOnCompleteListener(ActivityTestingData.this, new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                              // Add to database
                              String tempEncFn = encryption.encode(fullName.toString());
                              GetFirebase.usersRef.child(mAuth.getUid()).child("uN").setValue(userName);
                              GetFirebase.usersRef.child(mAuth.getUid()).child("fN").setValue(tempEncFn);
                              GetFirebase.usersRef.child(mAuth.getUid()).child("b").setValue(0);

                              // Sign in success, update UI with the signed-in user's information
                              Log.d("signup: ", "createUserWithEmail:success");
                            } else {
                              // If sign in fails, display a message to the user.
                              Log.w("signup: ", "createUserWithEmail:failure", task.getException());
                              Toast.makeText(tempView.getContext(), "Signup failed.",
                                      Toast.LENGTH_SHORT).show();
                            }
                          }
                        });
              } else Toast.makeText(view.getContext(), "Username taken.", Toast.LENGTH_SHORT).show();
            }

          case 2: // UPDATE
            if (!userName.getText().toString().isEmpty()) {
              GetFirebase.usersRef.child(selectedUserUid).child("uN").setValue(userName.getText().toString());
              GetFirebase.usersRef.child(selectedUserUid).child("fN").setValue(encryption.encode(fullName.getText().toString()));
              GetFirebase.usersRef.child(selectedUserUid).child("b").setValue(Double.parseDouble(balance.getText().toString()));
              GetFirebase.usersRef.child(selectedUserUid).child("upId").setValue(upId.getText().toString());
            }
            GetFirebase.adminRef.child("fee").setValue(Double.parseDouble(fee.getText().toString()));
            encryption = new Encryption();

          case 3: // DELETE
            if (mAuth.getUid().equals(GetFirebase.getAdminUid())) return;
            String tempUpline = GetFirebase.getUsers(selectedUserUid).getUplineUid();
            ArrayList<String> downlines = GetFirebase.getUsers(selectedUserUid).getDownlineUid();

            for (String a : downlines) {
              GetFirebase.usersRef.child(a).child("upId").setValue(tempUpline);
            }

            GetFirebase.usersRef.child(selectedUserUid).removeValue();
        }
        GetFirebase.fetchUsers();
      }});
  }

}

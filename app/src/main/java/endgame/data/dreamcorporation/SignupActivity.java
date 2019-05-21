package endgame.data.dreamcorporation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;
  private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

  private Encryption encryption = new Encryption();

  private RelativeLayout loading;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);

    TextView login = (TextView) findViewById(R.id.to_login_screen);
    Button signup_button = (Button) findViewById(R.id.signup_button);
    mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth

    signup_button.setOnClickListener(new View.OnClickListener(){
      @Override
      //On click function
      public void onClick(final View view) {
        final String userFn = ((EditText) findViewById(R.id.signup_fullName)).getText().toString();
        final String userId = ((EditText) findViewById(R.id.signup_userId)).getText().toString();
        String userPw = ((EditText) findViewById(R.id.signup_userPw)).getText().toString();
        loading = findViewById(R.id.signup_loading);
        loading.setVisibility(View.VISIBLE);

        if (userPw.length() < 6 || userId.length() < 1) {
          Toast.makeText(view.getContext(), "Password longer a bit can ah??",
                  Toast.LENGTH_SHORT).show();
          loading.setVisibility(View.GONE);
        } else if (GetFirebase.existUser(userId)) {
          Toast.makeText(view.getContext(), "Username unavailable",
                  Toast.LENGTH_SHORT).show();
          loading.setVisibility(View.GONE);
        } else {
          if (!GetFirebase.existUser(userId.toString())) {
            mAuth.createUserWithEmailAndPassword(userId + "@asdfggfdsa.com", userPw)
                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          // Add to database
                          String tempEncFn = encryption.encode(userFn);
                          GetFirebase.usersRef.child(mAuth.getUid()).child("uN").setValue(userId);
                          GetFirebase.usersRef.child(mAuth.getUid()).child("fN").setValue(tempEncFn);
                          GetFirebase.usersRef.child(mAuth.getUid()).child("b").setValue(0);

                          // Sign in success, update UI with the signed-in user's information
                          Log.d("signup: ", "createUserWithEmail:success");
                          Intent intent = new Intent(view.getContext(), MainActivity.class);
                          startActivity(intent);
                          onBackPressed(); // To close this activity
                        } else {
                          // If sign in fails, display a message to the user.
                          Log.w("signup: ", "createUserWithEmail:failure", task.getException());
                          Toast.makeText(view.getContext(), "Signup failed.",
                                  Toast.LENGTH_SHORT).show();
                          loading.setVisibility(View.GONE);
                        }
                      }
                    });
          } else Toast.makeText(view.getContext(), "Username taken.", Toast.LENGTH_SHORT).show();
        }
      }
    });

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      //On click function
      public void onClick(View view) {
        //Create the intent to start another activity
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
      }
    });
  }
}

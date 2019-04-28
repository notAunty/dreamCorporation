package endgame.data.dreamcorporation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);

//    Button login_button = (Button) findViewById(R.id.login_button);
    TextView login = (TextView) findViewById(R.id.to_login_screen);
//    String userId = (String) findViewById(R.)

//    login_button.setOnClickListener(new View.OnClickListener(){
//      @Override
//      //On click function
//      public void onClick(View view) {
//        //Create the intent to start another activity
//        Intent intent = new Intent(view.getContext(), HomeActivity.class);
//        startActivity(intent);
//      }
//    });

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      //On click function
      public void onClick(View view) {
        //Create the intent to start another activity
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
      }
    });

    // Initialize Firebase Auth
//    mAuth = FirebaseAuth.getInstance();

//    mAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//              @Override
//              public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                  // Sign in success, update UI with the signed-in user's information
//                  Log.d(TAG, "createUserWithEmail:success");
//                  FirebaseUser user = mAuth.getCurrentUser();
//                  updateUI(user);
//                } else {
//                  // If sign in fails, display a message to the user.
//                  Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                  Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                          Toast.LENGTH_SHORT).show();
//                  updateUI(null);
//                }
//
//                // ...
//              }
//            });
  }
}

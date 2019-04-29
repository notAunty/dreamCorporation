package endgame.data.dreamcorporation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;

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
        String userId = ((EditText) findViewById(R.id.signup_userid)).getText().toString();
        String userPw = ((EditText) findViewById(R.id.signup_userpw)).getText().toString();

//        //Create the intent to start another activity
//        Intent intent = new Intent(view.getContext(), HomeActivity.class);
//        startActivity(intent);


        mAuth.createUserWithEmailAndPassword(userId + "@gmail.com", userPw)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                      // Sign in success, update UI with the signed-in user's information
                      Log.d("signup: ", "createUserWithEmail:success");
                      FirebaseUser user = mAuth.getCurrentUser();
                      Toast.makeText(view.getContext(), "Signup success!",
                              Toast.LENGTH_SHORT).show();
//                      updateUI(user);
                    } else {
                      // If sign in fails, display a message to the user.
                      Log.w("signup: ", "createUserWithEmail:failure", task.getException());
                      Toast.makeText(view.getContext(), "Signup failed.",
                              Toast.LENGTH_SHORT).show();
//                      updateUI(null);
                    }
                    // ...
                  }
                });
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

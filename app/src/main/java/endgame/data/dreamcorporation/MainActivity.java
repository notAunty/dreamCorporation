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

public class MainActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    Button login_button = (Button) findViewById(R.id.login_button);
    TextView signup = (TextView) findViewById(R.id.to_signup_screen);
    mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth


    login_button.setOnClickListener(new View.OnClickListener() {
      @Override
      //On click function
      public void onClick(final View view) {

        String userId = ((EditText) findViewById(R.id.login_userid)).getText().toString();
        String userPw = ((EditText) findViewById(R.id.login_userpw)).getText().toString();

        if (userPw.length() < 7 || userId.length() < 1) {
          Toast.makeText(view.getContext(), "Why so empty?",
                  Toast.LENGTH_SHORT).show();
        } else {
          mAuth.signInWithEmailAndPassword(userId + "@asdfggfdsa.com", userPw)
                  .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("login: ", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        //Create the intent to start another activity
                        Intent intent = new Intent(view.getContext(), HomeActivity.class);
                        startActivity(intent);
                        onBackPressed(); // To close this activity
                      } else {
                        // If sign in fails, display a message to the user.
                        Log.w("login: ", "signInWithEmail:failure", task.getException());
                        Toast.makeText(view.getContext(), "Username or password incorrect.",
                                Toast.LENGTH_SHORT).show();
//                      updateUI(null);
                      }
                      // ...
                    }
                  });
        }
      }
    });

    signup.setOnClickListener(new View.OnClickListener() {
      @Override
      //On click function
      public void onClick(View view) {
        //Create the intent to start another activity
        Intent intent = new Intent(view.getContext(), SignupActivity.class);
        startActivity(intent);
      }
    });

    // Initialize Firebase Auth
    mAuth = FirebaseAuth.getInstance();


  }
}

package endgame.data.dreamcorporation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    Button login_button = (Button) findViewById(R.id.login_button);
    TextView signup = (TextView) findViewById(R.id.to_signup_screen);


    login_button.setOnClickListener(new View.OnClickListener() {
      @Override
      //On click function
      public void onClick(View view) {
        //Create the intent to start another activity
        Intent intent = new Intent(view.getContext(), HomeActivity.class);
        startActivity(intent);

        onBackPressed(); // To close this activity
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

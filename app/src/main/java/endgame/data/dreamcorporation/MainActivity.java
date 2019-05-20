package endgame.data.dreamcorporation;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

  private FirebaseAuth mAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
//    GetFirebase.prepareFirebase();
    GetFirebase.getFirebase();


    if(!isConnectedToInternet()) {
      new AlertDialog.Builder(MainActivity.this)
              .setTitle("Not connection")
              .setCancelable(false)
              .setMessage("Connect to Internet and restart app.")

              // Specifying a listener allows you to take an action before dismissing the dialog.
              // The dialog is automatically dismissed when a dialog button is clicked.
              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  android.os.Process.killProcess(android.os.Process.myPid());
                }
              })
              .show();

//      Toast.makeText(getApplicationContext(), "Connect to Internet and restart app.",
//              Toast.LENGTH_LONG).show();
    }

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    Button login_button = (Button) findViewById(R.id.login_button);
    TextView signup = (TextView) findViewById(R.id.to_signup_screen);
    mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth  //wp use this


    login_button.setOnClickListener(new View.OnClickListener() {
      @Override
      //On click function
      public void onClick(final View view) {

        String userId = ((EditText) findViewById(R.id.login_userid)).getText().toString();
        String userPw = ((EditText) findViewById(R.id.login_userpw)).getText().toString();

        // TODO remove this
        if (userPw.length() == 0 || userId.length() == 0) {
          //Create the intent to start another activity
          Intent intent = new Intent(view.getContext(), HomeActivity.class);
          startActivity(intent);

          onBackPressed(); // To close this activity
        }

        if (userPw.length() < 6 || userId.length() < 1) {
          Toast.makeText(view.getContext(), "Invalid username or password.",
                  Toast.LENGTH_SHORT).show();
        } else {
//          //Create the intent to start another activity
//          Intent intent = new Intent(view.getContext(), HomeActivity.class);
//          startActivity(intent);
//          onBackPressed(); // To close this activity

          mAuth.signInWithEmailAndPassword(userId + "@asdfggfdsa.com", userPw)
                  .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("login: ", "signInWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();


//                        Toast.makeText(view.getContext(), "UID is " + mAuth.getUid(),
//                                Toast.LENGTH_SHORT).show();

                        //Create the intent to start another activity
                        Intent intent = new Intent(view.getContext(), HomeActivity.class);
                        startActivity(intent);

                        onBackPressed(); // To close this activity
                      } else {
                        // If sign in fails, display a message to the user.
                        Log.w("login: ", "signInWithEmail:failure", task.getException());
                        Toast.makeText(view.getContext(), "Invalid username or password.",
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
        onBackPressed();
        Intent intent = new Intent(view.getContext(), SignupActivity.class);
        startActivity(intent);
      }
    });
  }

  public boolean isConnectedToInternet(){
    ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
    if (connectivity != null)
    {
      NetworkInfo[] info = connectivity.getAllNetworkInfo();
      if (info != null)
        for (int i = 0; i < info.length; i++)
          if (info[i].getState() == NetworkInfo.State.CONNECTED)
          {
            return true;
          }
    }
    return false;
  }
}

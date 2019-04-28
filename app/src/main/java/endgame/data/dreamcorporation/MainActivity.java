package endgame.data.dreamcorporation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    Button login_button = (Button) findViewById(R.id.login_button);

    login_button.setOnClickListener(new View.OnClickListener(){
      @Override
      //On click function
      public void onClick(View view) {
        //Create the intent to start another activity
        Intent intent = new Intent(view.getContext(), HomeActivity.class);
        startActivity(intent);
      }
    });


  }
}

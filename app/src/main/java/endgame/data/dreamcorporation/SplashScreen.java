package endgame.data.dreamcorporation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    Thread welcomeThread = new Thread() {

      @Override
      public void run() {
        try {
          super.run();
          sleep(250);  //Delay of 1 seconds
        } catch (Exception e) {}
        finally {
          Intent i = new Intent(SplashScreen.this, MainActivity.class);
          startActivity(i);
          finish();
        }
      }
    };

    welcomeThread.start();
  }
}

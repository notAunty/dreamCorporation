package endgame.data.dreamcorporation;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

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
          sleep(1000);  //Delay of 1 seconds
        } catch (Exception e) {} finally {
          Intent i = new Intent(SplashScreen.this,
                  MainActivity.class);
          startActivity(i);
          finish();
        }
      }
    };

    welcomeThread.start();
  }
}

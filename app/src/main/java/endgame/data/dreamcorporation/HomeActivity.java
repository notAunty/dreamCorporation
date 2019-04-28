package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
    bottomNav.setOnNavigationItemSelectedListener(navListener);

    getSupportFragmentManager().beginTransaction().
            setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
            replace(R.id.fragment_container, new HomeFragment()).commit();
  }

  private BottomNavigationView.OnNavigationItemSelectedListener navListener =
          new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
              Fragment selectedFragment = null;

              switch (menuItem.getItemId()) {
                case R.id.nav_home:
                  selectedFragment = new HomeFragment();
                  break;
                case R.id.nav_network:
                  selectedFragment = new NetworkFragment();
                  break;
                case R.id.nav_profile:
                  selectedFragment = new ProfileFragment();
                  break;
              }
              getSupportFragmentManager().beginTransaction().
                      setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
                      replace(R.id.fragment_container, selectedFragment).commit();

              return true;
            }
          };
}

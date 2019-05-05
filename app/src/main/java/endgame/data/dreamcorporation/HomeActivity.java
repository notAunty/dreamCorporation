package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

  private HomeFragment homeF = new HomeFragment();
  private NetworkFragment networkF = new NetworkFragment();
  private ProfileFragment profileF = new ProfileFragment();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
    bottomNav.setOnNavigationItemSelectedListener(navListener);

    getSupportFragmentManager().beginTransaction().
//            setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).
            replace(R.id.fragment_container, homeF).commit();
  }

  private BottomNavigationView.OnNavigationItemSelectedListener navListener =
          new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//              Fragment selectedFragment = null;

              switch (menuItem.getItemId()) {
                case R.id.nav_home:
                  getSupportFragmentManager().beginTransaction().
                          setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
                          replace(R.id.fragment_container, homeF).commit();
                  break;
                case R.id.nav_network:
                  getSupportFragmentManager().beginTransaction().
                          setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
                          replace(R.id.fragment_container, networkF).commit();
                  break;
                case R.id.nav_profile:
                  getSupportFragmentManager().beginTransaction().
                          setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).
                          replace(R.id.fragment_container, profileF).commit();
                  break;
              }


              // PHILEMON SAY THIS IMPLEMETATION IS BAD, as it doesn't save state
              // Use ViewPager, FragmentPagerAdapter, FragmentStatePagerAdapter
              // But I'm too lazy haha


//              getSupportFragmentManager().beginTransaction().
//                      replace(R.id.fragment_container, selectedFragment).commit();

              return true;
            }
          };
}

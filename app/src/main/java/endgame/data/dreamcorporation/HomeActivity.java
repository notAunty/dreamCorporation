package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

  private HomeFragment homeF = new HomeFragment();
  private NetworkFragment networkF = new NetworkFragment();
  private ProfileFragment profileF = new ProfileFragment();
  private ViewPager vpPager;
  TabsPagerAdapter adapter;
  MenuItem prevMenuItem;
  BottomNavigationView bottomNav;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    bottomNav = findViewById(R.id.bottom_nav);
    bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
          case R.id.nav_home:
            vpPager.setCurrentItem(0);
            break;
          case R.id.nav_network:
            vpPager.setCurrentItem(1);
            break;
          case R.id.nav_profile:
            vpPager.setCurrentItem(2);
            break;
        }


        // PHILEMON SAY THIS IMPLEMETATION IS BAD, as it doesn't save state
        // Use ViewPager, FragmentPagerAdapter, FragmentStatePagerAdapter
        // But I'm too lazy haha


//              getSupportFragmentManager().beginTransaction().
//                      replace(R.id.fragment_container, selectedFragment).commit();

        return true;
      }
    });


    getSupportFragmentManager().beginTransaction().
            setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).
            replace(R.id.fragment_container, homeF).commit();


    vpPager = (ViewPager) findViewById(R.id.viewpager);
    vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        if (prevMenuItem != null) {
          prevMenuItem.setChecked(false);
        }
        else
        {
          bottomNav.getMenu().getItem(0).setChecked(false);
        }
//        Log.e("page", "onPageSelected: "+position);
        bottomNav.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNav.getMenu().getItem(position);

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });

    setupViewPager(vpPager);
    vpPager.setOffscreenPageLimit(2);
//    myAdapter = new TabsPagerAdapter(getSupportFragmentManager());
//    vpPager.setAdapter(myAdapter);
  }

  private void setupViewPager(ViewPager viewPager) {
    adapter = new TabsPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(homeF);
    adapter.addFragment(networkF);
    adapter.addFragment(profileF);
    viewPager.setAdapter(adapter);
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


//              getSupportFragmentManager().beginTransaction().
//                      replace(R.id.fragment_container, selectedFragment).commit();

              return true;
            }
          };

}

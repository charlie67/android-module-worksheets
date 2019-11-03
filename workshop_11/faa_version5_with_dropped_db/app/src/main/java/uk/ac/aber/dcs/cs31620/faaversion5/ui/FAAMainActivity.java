package uk.ac.aber.dcs.cs31620.faaversion5.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import uk.ac.aber.dcs.cs31620.faaversion5.R;
import uk.ac.aber.dcs.cs31620.faaversion5.ui.cats.CatsFragment;
import uk.ac.aber.dcs.cs31620.faaversion5.ui.faa_users.FAAUsersFragment;
import uk.ac.aber.dcs.cs31620.faaversion5.ui.fosterers.FosterersFragment;
import uk.ac.aber.dcs.cs31620.faaversion5.ui.home.HomeFragment;
import uk.ac.aber.dcs.cs31620.faaversion5.ui.kittens.KittensFragment;
/**
 * Main activity for the FAA app. Supports navigation drawer, and tabbing for app functions.
 *
 * @author Chris Loftus
 * @version 1 18/06/2018.
 */
public class FAAMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int HOME_TAB = 0;
    private static final int KITTENS_TAB = 1;
    private static final int CATS_TAB = 2;
    private static final int FOSTERERS_TAB = 3;
    private static final int USERS_TAB = 4;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faamain);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Create ActionBar toggle (burger icon) for the navigation drawer
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this,
                        drawer,
                        toolbar,
                        R.string.nav_open_drawer,
                        R.string.nav_close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState(); // sync the state of the icon with drawer state

        // Find the navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Handle navigation drawer option selections
        navigationView.setNavigationItemSelectedListener(this);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        pager.addOnPageChangeListener(addPageChangeListener());
    }

    private ViewPager.OnPageChangeListener addPageChangeListener() {
        return new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                AppBarLayout.LayoutParams layoutParams =
                        (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

                switch (position){
                    case HOME_TAB:
                        // Switch toolbar scrolling on
                        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                        break;
                    default:
                        // Switch toolbar scrolling off
                        layoutParams.setScrollFlags(0);
                        break;
                }
                toolbar.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
    }

    /**
     * Called when an item in the navigation drawer is selected
     * @param item The item that was selected
     * @return true if te item was handled
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Toast.makeText(this, "Item id: " + item.getItemId(), Toast.LENGTH_LONG).show();
        return true;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case HOME_TAB: return getString(R.string.home_tab);
                case KITTENS_TAB: return getString(R.string.kitten_tab);
                case CATS_TAB: return getString(R.string.cat_tab);
                case FOSTERERS_TAB: return getString(R.string.fosterer_tab);
                case USERS_TAB: return getString(R.string.faa_user_tab);
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case HOME_TAB: return new HomeFragment();
                case KITTENS_TAB: return new KittensFragment();
                case CATS_TAB: return new CatsFragment();
                case FOSTERERS_TAB: return new FosterersFragment();
                case USERS_TAB: return new FAAUsersFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}

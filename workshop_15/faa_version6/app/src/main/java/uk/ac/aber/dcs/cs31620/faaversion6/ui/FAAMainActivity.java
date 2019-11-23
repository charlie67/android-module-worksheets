package uk.ac.aber.dcs.cs31620.faaversion6.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import uk.ac.aber.dcs.cs31620.faaversion6.R;
import uk.ac.aber.dcs.cs31620.faaversion6.ui.cats.CatsFragment;
import uk.ac.aber.dcs.cs31620.faaversion6.ui.faa_users.FAAUsersFragment;
import uk.ac.aber.dcs.cs31620.faaversion6.ui.fosterers.FosterersFragment;
import uk.ac.aber.dcs.cs31620.faaversion6.ui.home.HomeFragment;
import uk.ac.aber.dcs.cs31620.faaversion6.ui.kittens.KittensFragment;

/**
 * Main activity for the FAA: setting out the toolbar, navigation drawer, action bar toggle (burger),
 * tabbing via a FragementPagerAdapter
 *
 * @author Chris Loftus
 * @version 2 25/07/2019.
 */
public class FAAMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_CONTACT = 1;
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

        // Place the toolbar
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

        // CF the private inner class. Note that this creates a FragmentPagerAdapter
        // that allows swipe left and right of the tabbed fragments.
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        //pager.setOnClickListener(new ClickListener());

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        pager.addOnPageChangeListener(addPageChangeListener());

        // Check to see if there's an incoming Intent object
        Intent callingIntent = getIntent();
        if (callingIntent != null) {
            String incomingAction = callingIntent.getAction();
            if (incomingAction != null && incomingAction.equals(getString(R.string.action_view_cats))) {
                Uri incomingData = callingIntent.getData();
                if (incomingData != null && incomingData.toString().equals(getString(R.string.cats_uri))) {
                    pager.setCurrentItem(CATS_TAB);
                }
            }
        }

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
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Toast.makeText(this, "Item id: " + item.getItemId(), Toast.LENGTH_LONG).show();
        return true;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case HOME_TAB: return getResources().getText(R.string.home_tab);
                case KITTENS_TAB: return getResources().getText(R.string.kitten_tab);
                case CATS_TAB: return getResources().getText(R.string.cat_tab);
                case FOSTERERS_TAB: return getResources().getText(R.string.fosterer_tab);
                case USERS_TAB: return getResources().getText(R.string.faa_user_tab);
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

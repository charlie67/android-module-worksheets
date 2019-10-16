package to.charlie.faa.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import to.charlie.faa.R;
import to.charlie.faa.ui.cats.CatsFragment;
import to.charlie.faa.ui.faa_users.UsersFragment;
import to.charlie.faa.ui.fosterers.FosterersFragment;
import to.charlie.faa.ui.home.HomeFragment;
import to.charlie.faa.ui.kittens.KittensFragment;

public class FAAMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faamain);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                        drawer,
                        toolbar,
                        R.string.nav_open_drawer,
                        R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Find the navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Handle navigation drawer option selections
        navigationView.setNavigationItemSelectedListener(this);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        Toast.makeText(this, "Item id: " + menuItem.getItemId(), Toast.LENGTH_LONG).show();
        return true;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new KittensFragment();
                case 2:
                    return new CatsFragment();
                case 3:
                    return new FosterersFragment();
                case 4:
                    return new UsersFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0: return getText(R.string.home_tab);
                case 1: return getText(R.string.kitten_tab);
                case 2: return getText(R.string.cat_tab);
                case 3: return getText(R.string.fosterer_tab);
                case 4: return getText(R.string.user_tab);
            }

            return null;
        }
    }
}
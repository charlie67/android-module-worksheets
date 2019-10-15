package to.charlie.faa.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import to.charlie.faa.R;
import to.charlie.faa.ui.cats.CatsFragment;
import to.charlie.faa.ui.faa_users.UsersFragment;
import to.charlie.faa.ui.fosterers.FosterersFragment;
import to.charlie.faa.ui.home.HomeFragment;
import to.charlie.faa.ui.kittens.KittensFragment;

public class FAAMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faamain);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
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
    }
}
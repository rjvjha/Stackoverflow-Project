package com.example.rajeevjha.stackoverflow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rajeevjha.stackoverflow.adapters.QuestionFragmentsPagerAdapter;
import com.example.rajeevjha.stackoverflow.data.PreferenceHelper;
import com.example.rajeevjha.stackoverflow.fragments.HotQuestionFragment;
import com.example.rajeevjha.stackoverflow.fragments.WeeklyQuestionFragment;
import com.example.rajeevjha.stackoverflow.fragments.YourQuestionFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// This Activity is build on MVVM architecture
public class QuestionListActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG_KEY = "tag";
    private List<String> selectedTags;
    private DrawerLayout mDrawerLayout;
    private QuestionFragmentsPagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ActionBar mActionBar;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        selectedTags = convertSelectedTagsSetToList();
        tag = selectedTags.get(0);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        setNavItemsTitle(menu);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setTitle(getString(R.string.trending_label) + " in " + selectedTags.get(0));

        // setup viewPager and adapter
        pagerAdapter = new QuestionFragmentsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

        // onPageChangeListener
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Fragment activeFragment = pagerAdapter.getItem(i);
                if (i == 0) {
                    ((YourQuestionFragment) activeFragment).refreshQuestionList(tag);
                }

                if (i == 1) {
                    ((HotQuestionFragment) activeFragment).refreshQuestionList(tag);
                }

                if (i == 2) {
                    ((WeeklyQuestionFragment) activeFragment).refreshQuestionList(tag);
                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        menuItem.setChecked(true);

        switch (id) {

            case R.id.nav_tag1:
                //switchTag.setTag(selectedTags.get(0));
                tag = selectedTags.get(0);
                updateDataInFragments(tag);
                mActionBar.setTitle(getString(R.string.trending_label) + " in " + selectedTags.get(0));
                break;

            case R.id.nav_tag2:
                //switchTag.setTag(selectedTags.get(1));
                tag = selectedTags.get(1);
                updateDataInFragments(tag);
                mActionBar.setTitle(getString(R.string.trending_label) + " in " + selectedTags.get(1));
                break;

            case R.id.nav_tag3:
                //switchTag.setTag(selectedTags.get(2));
                tag = selectedTags.get(2);
                updateDataInFragments(tag);
                mActionBar.setTitle(getString(R.string.trending_label) + " in " + selectedTags.get(2));
                break;

            case R.id.nav_tag4:
                //switchTag.setTag(selectedTags.get(3));
                tag = selectedTags.get(3);
                updateDataInFragments(tag);
                mActionBar.setTitle(getString(R.string.trending_label) + " in " + selectedTags.get(3));
                break;


        }


        mDrawerLayout.closeDrawers();
        return false;
    }

    // private helper method to set MenuItem title
    private void setNavItemsTitle(Menu menu) {

        System.out.println(selectedTags);
        int i;

        for (i = 0; i < selectedTags.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            String tag = selectedTags.get(i);

            if (tag != null && !tag.isEmpty()) {
                // set tagName
                menuItem.setTitle(tag);
            }

        }

        for (; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            menuItem.setEnabled(false);
            menuItem.setVisible(false);

        }

    }

    private void updateDataInFragments(String tag) {
        int pos = mViewPager.getCurrentItem();
        Fragment activeFragment = pagerAdapter.getItem(pos);
        if (pos == 0) {
            ((YourQuestionFragment) activeFragment).refreshQuestionList(tag);
        }

        if (pos == 1) {
            ((HotQuestionFragment) activeFragment).refreshQuestionList(tag);
        }

        if (pos == 2) {
            ((WeeklyQuestionFragment) activeFragment).refreshQuestionList(tag);
        }

    }


    private List<String> convertSelectedTagsSetToList() {
        Set<String> tagsSet = PreferenceHelper.getTagsStringSet(new ArraySet<String>());
        List<String> tagsSetList = new ArrayList<>();
        tagsSetList.addAll(tagsSet);
        return tagsSetList;
    }
}

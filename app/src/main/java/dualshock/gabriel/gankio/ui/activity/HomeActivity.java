package dualshock.gabriel.gankio.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import dualshock.gabriel.gankio.R;
import dualshock.gabriel.gankio.adapter.HomeAdapter;
import dualshock.gabriel.gankio.ui.custom.RatioImageView;
import dualshock.gabriel.gankio.ui.fragment.BaseFragment;
import dualshock.gabriel.gankio.ui.fragment.KnowledgeFragment;
import dualshock.gabriel.gankio.util.ConstantValue;
import retrofit2.Retrofit;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.nv_menu)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.home_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    private static final String TAG = "HomeActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);

        //绑定drawerLayout和toolBar,实现菜单侧滑动画效果
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.app_name, R.string.app_name);
        toggle.syncState();
        View header = navigationView.getHeaderView(0);

        RatioImageView ratioImageView = (RatioImageView) header.findViewById(R.id.navigation_header);
        Drawable drawable = ratioImageView.getDrawable();
        ratioImageView.setRatio(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        mDrawerLayout.addDrawerListener(toggle);
        mViewPager.setAdapter(new HomeAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

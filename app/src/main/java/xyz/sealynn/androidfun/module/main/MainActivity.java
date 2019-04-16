package xyz.sealynn.androidfun.module.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import xyz.sealynn.androidfun.APP;
import xyz.sealynn.androidfun.R;
import xyz.sealynn.androidfun.base.BaseActivity;
import xyz.sealynn.androidfun.base.Constants;
import xyz.sealynn.androidfun.model.User;
import xyz.sealynn.androidfun.model.Result;
import xyz.sealynn.androidfun.module.AboutActivity;
import xyz.sealynn.androidfun.module.SettingsActivity;
import xyz.sealynn.androidfun.module.collection.CollectionActivity;
import xyz.sealynn.androidfun.module.guidance.GuidanceFragment;
import xyz.sealynn.androidfun.module.home.HomeFragment;
import xyz.sealynn.androidfun.module.knowledgetree.KnowledgeTreeFragment;
import xyz.sealynn.androidfun.module.login.LoginActivity;
import xyz.sealynn.androidfun.module.project.ProjectFragment;
import xyz.sealynn.androidfun.module.search.SearchActivity;
import xyz.sealynn.androidfun.module.todo.TodoActivity;
import xyz.sealynn.androidfun.module.wechat.WeChatFragment;
import xyz.sealynn.androidfun.module.year.YearProgressActivity;
import xyz.sealynn.androidfun.net.interceptor.SaveCookiesInterceptor;
import xyz.sealynn.androidfun.receiver.NightModeChangeReceiver;
import xyz.sealynn.androidfun.utils.ActivityUtils;

public class MainActivity extends BaseActivity<MainContract.Presenter>
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    View headerView;
    AppCompatImageView avatar;
    AppCompatTextView username;
    ActionBar actionBar;

    NightModeChangeReceiver receiver;

    List<Fragment> listFragment = new ArrayList<>();
    private int lastFragment;   //用于记录上个选择的Fragment

    public static final int REQUEST_LOGIN = 1;
    public static final int ITEM_LOGOUT_ID = 120;

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void prepareData() {
    }

    @Override
    protected void initView() {
//        if (getSupportActionBar()!=null)
        actionBar = getSupportActionBar();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, drawer.getRootView().findViewById(R.id.toolbar)
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initNavigationView();
        initBottomNavigationView();
    }

    /**
     * 检查登录状态
     */
    private void checkUser() {
        if (Result.getResultBean(this, "login", Result.class) != null) {
            Result<User> data = Result.getResultBean(MainActivity.this, "login", new TypeToken<Result<User>>() {
            }.getType());
            username.setText(data.getData().getUsername());
            //在Menu添加注销选项
            navigationView.getMenu().addSubMenu(1, ITEM_LOGOUT_ID, 0, R.string.exit)
                    .add(1, ITEM_LOGOUT_ID, 0, R.string.logout)
                    .setIcon(R.drawable.ic_action_logout);
        }
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomNavigationView() {
        listFragment.add(new HomeFragment());
        listFragment.add(new KnowledgeTreeFragment());
        listFragment.add(new WeChatFragment());
        listFragment.add(new GuidanceFragment());
        listFragment.add(new ProjectFragment());

        actionBar.setTitle(R.string.home);
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), listFragment.get(0), R.id.content);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            switch (id) {
                case R.id.home:
                    switchFragment(0);
                    actionBar.setTitle(R.string.home);
                    return true;
                case R.id.knowledge_tree:
                    switchFragment(1);
                    actionBar.setTitle(R.string.knowledge_tree);
                    return true;
                case R.id.wechat:
                    switchFragment(2);
                    actionBar.setTitle(R.string.wechat);
                    return true;
                case R.id.guidance:
                    switchFragment(3);
                    actionBar.setTitle(R.string.guidance);
                    return true;
                case R.id.project:
                    switchFragment(4);
                    actionBar.setTitle(R.string.project);
                    return true;
            }
            return false;
        });
    }

    /**
     * 切换Fragment
     *
     * @param index
     */
    private void switchFragment(int index) {
        if (lastFragment != index) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(listFragment.get(lastFragment));//隐藏上个Fragment
            if (!listFragment.get(index).isAdded()) {
                transaction.add(R.id.content, listFragment.get(index));
            }
            transaction.show(listFragment.get(index)).commit();
            lastFragment = index;
        }
    }

    /**
     * 初始化侧滑抽屉
     */
    private void initNavigationView() {
        headerView = navigationView.getHeaderView(0);
        avatar = headerView.findViewById(R.id.imageView);
        username = headerView.findViewById(R.id.textView);
        navigationView.setNavigationItemSelectedListener(this);
        checkUser();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        headerView.setOnClickListener(v -> {
            if (!getContext().getSharedPreferences(Constants.CONFIG_DEFAULT, Context.MODE_PRIVATE).contains("login")) {
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_LOGIN);
            }
        });
        getPresenter().checkYearProgress();

        //创建广播
        receiver = new NightModeChangeReceiver(this);
        //注册广播
        registerReceiver(receiver, new IntentFilter(Constants.NIGHT_MODE_CHANGE_INTENT));
    }

    /**
     * 重写返回键方法
     * 在DrawerLayout打开时收起DrawerLayout
     * 在主界面双击退出APP
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            APP.exitApp();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ActivityUtils.startActivity(this, SearchActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOGIN:
                if (resultCode == RESULT_OK)
                    checkUser();
                break;
        }
    }

    /**
     * 监听抽屉item点击事件
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_play_android) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_todo) {
            ActivityUtils.startActivity(MainActivity.this, TodoActivity.class);
        } else if (id == R.id.nav_year_progress) {
            ActivityUtils.startActivity(MainActivity.this, YearProgressActivity.class);
        } else if (id == R.id.nav_collection) {
            ActivityUtils.startActivity(MainActivity.this, CollectionActivity.class);
        } else if (id == R.id.nav_setting) {
            ActivityUtils.startActivity(MainActivity.this, SettingsActivity.class);
        } else if (id == R.id.nav_about) {
            ActivityUtils.startActivity(MainActivity.this, AboutActivity.class);
        } else if (id == ITEM_LOGOUT_ID) {
            Logger.d("注销");
            getPresenter().logout();
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void logout() {
        getContext()
                .getSharedPreferences(Constants.CONFIG_DEFAULT, Context.MODE_PRIVATE)
                .edit().remove("login").apply();
        SaveCookiesInterceptor.clearCookie(MainActivity.this);
        Logger.d(navigationView.getMenu().getItem(4).getItemId());
        navigationView.getMenu().removeGroup(1);
        username.setText(R.string.login_or_regist);
    }
}

package com.k22.nhom1.moneysaver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.k22.nhom1.moneysaver.database.DB4OProvider;
import com.k22.nhom1.moneysaver.fragment.BalanceFragment;
import com.k22.nhom1.moneysaver.fragment.CategoryFragment;
import com.k22.nhom1.moneysaver.fragment.HomeFragment;
import com.k22.nhom1.moneysaver.fragment.LimitFragment;
import com.k22.nhom1.moneysaver.fragment.NavigationDrawerFragment;
import com.k22.nhom1.moneysaver.fragment.ReportFragment;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String IS_LOGGED = "islogged";
    public static final String USER_NAME = "userName";
    public static final String EMAIL = "email";

    SharedPreferences sharedpreferences;
    DB4OProvider db;
    Context mContext;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mContext = getApplicationContext();
        relativeLayout = (RelativeLayout) findViewById(R.id.realativeLayout);
        try {
            db = new OpenDBTask(mContext).execute(null, null, null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!isLogged()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        String name = sharedpreferences.getString(USER_NAME, "Thanh");
        String email = sharedpreferences.getString(EMAIL, "thanhm2515029@gstudent.ctu.edu.vn");
        mNavigationDrawerFragment.setUserData(name, email, BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HomeFragment fragment = new HomeFragment();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = sharedpreferences.getString(USER_NAME, "Thanh");
        String email = sharedpreferences.getString(EMAIL, "thanhm2515029@gstudent.ctu.edu.vn");
        mNavigationDrawerFragment.setUserData(name, email, BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
    }

    private boolean isLogged() {
        String islogged = sharedpreferences.getString(IS_LOGGED, "");
        return !TextUtils.isEmpty(islogged);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        //Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        Fragment fragment;
        switch (position) {
            case 0: //home
                fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, HomeFragment.TAG).commit();
                break;
            case 1: //report
                fragment = getSupportFragmentManager().findFragmentByTag(ReportFragment.TAG);
                if (fragment == null) {
                    fragment = new ReportFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, ReportFragment.TAG).commit();
                break;
            case 2: //category
                fragment = getSupportFragmentManager().findFragmentByTag(CategoryFragment.TAG);
                if (fragment == null) {
                    fragment = new CategoryFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, CategoryFragment.TAG).commit();
                break;
            case 3: //account balance
                fragment = getSupportFragmentManager().findFragmentByTag(BalanceFragment.TAG);
                if (fragment == null) {
                    fragment = new BalanceFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, BalanceFragment.TAG).commit();
                break;
            case 4: //limit
                fragment = getSupportFragmentManager().findFragmentByTag(LimitFragment.TAG);
                if (fragment == null) {
                    fragment = new LimitFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, LimitFragment.TAG).commit();
                break;
            case 5: //logout
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(USER_NAME, "");
                editor.putString(EMAIL, "");
                editor.putString(IS_LOGGED, "");
                editor.commit();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    public class OpenDBTask extends AsyncTask<Void, Void, DB4OProvider> {
        public OpenDBTask(Context mContext) {
        }

        @Override
        protected DB4OProvider doInBackground(Void... voids) {
            DB4OProvider db = DB4OProvider.getInstance(mContext);
            db.db();
            return db;
        }
    }
}

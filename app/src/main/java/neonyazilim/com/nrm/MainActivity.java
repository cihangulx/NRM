package neonyazilim.com.nrm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import neonyazilim.com.nrm.Activitys.DepartmanEkle;
import neonyazilim.com.nrm.Activitys.GorevEkle;
import neonyazilim.com.nrm.Activitys.KullaniciEkle;
import neonyazilim.com.nrm.Activitys.LoginActivity;
import neonyazilim.com.nrm.Activitys.ProjeEkle;
import neonyazilim.com.nrm.Activitys.TalepOlustur;
import neonyazilim.com.nrm.Activitys.UnvanEkle;
import neonyazilim.com.nrm.Adapters.TalepAdapter;
import neonyazilim.com.nrm.Fragments.Home.MainFragment;
import neonyazilim.com.nrm.Managers.UserManager;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.Models.UserResponse;
import neonyazilim.com.nrm.Network.Db;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = preferences.edit();

        if (getSharedPreferences("user", MODE_PRIVATE).getString("userId",null)!=null){
            S.userId=getSharedPreferences("user", MODE_PRIVATE).getString("userId",null);
            S.userToken=getSharedPreferences("user", MODE_PRIVATE).getString("token",null);
        }

        MainFragment mainFragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // transaction.remove(mainFragment);
        transaction.replace(R.id.content_main, mainFragment);
        transaction.attach(mainFragment);
        transaction.setAllowOptimization(true);
        transaction.addToBackStack("main");
        transaction.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        try {
            if (S.userId.equals("null")) {
                MenuItem menuItem = menu.findItem(R.id.action_giris);
                menuItem.setTitle("Giriş Yap");
            } else {
                MenuItem menuItem = menu.findItem(R.id.action_giris);
                menuItem.setTitle("Çıkış Yap");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_giris) {
            if (S.userId.equals("null")){
                startActivity(new Intent(this, LoginActivity.class));
            }else {
                logout();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_home) {
            recreate();
        } else if (id == R.id.action_add_project) {
            startActivity(new Intent(this, ProjeEkle.class));
        } else if (id == R.id.action_add_request) {
            startActivity(new Intent(this, TalepOlustur.class));
        } else if (id == R.id.action_add_user) {
            startActivity(new Intent(this, KullaniciEkle.class));
        } else if (id == R.id.action_add_department) {
            startActivity(new Intent(this, DepartmanEkle.class));
        } else if (id == R.id.action_add_unvan) {
            startActivity(new Intent(this, UnvanEkle.class));
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){
        Log.e("logout","logout()");
        RequestBody requestBody = new RequestBody(S.userId,S.userToken);
        Call<UserResponse> call = Db.getConnect().logout(requestBody);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code()==200){
                    Log.e("logout","logout()1");
                    if (!response.body().isLogin()){
                        // Çıkış Başarılı
                        Log.e("logout","responso()"+response.code());
                        S.userId="null";
                        S.userToken="13224btfrtdefgdrttg";
                        editor.remove("userId");
                        editor.remove("token");
                        editor.commit();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));

                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("logout","logout()"+t.getMessage());
            }
        });
    }

}

package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import neonyazilim.com.nrm.Adapters.DepartmanAdapter;
import neonyazilim.com.nrm.Adapters.TalepAdapter;
import neonyazilim.com.nrm.Adapters.TalepListAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Taleplerim extends AppCompatActivity {

    ListView listView;

    String[] siralamalar = {"Önce En Yeni", "Önce En Eski"};
    String[] durumlar = {"Tüm Durumlar", "İşlemde", "Reddedildi", "Sonuçlandı", "Düzeltme İstendi"};

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    getGelenTalepler();
                    return true;
                case R.id.navigation_dashboard:
                    getGidenTalepler();
                    return true;
                case R.id.navigation_notifications:
                    showFiltreDialog();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taleplerim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listView = findViewById(R.id.listview);

        getGelenTalepler();

    }

    public void getGelenTalepler() {

        RequestBody requestBody = new RequestBody(S.userId, S.userToken);
        requestBody.setDurum("Gelen");
        Call<List<Talep>> call = Db.getConnect().getTalep(requestBody);
        call.enqueue(new Callback<List<Talep>>() {
            @Override
            public void onResponse(Call<List<Talep>> call, Response<List<Talep>> response) {

                Log.d("TF.onResponse()", "" + response.body().size() + "item listed.");
                if (response.code() == 200) {
                    Log.d("TF.onResponse()", "" + response.body().size() + "item listed.");
                    // Veri alımı Başarılı


                    //  Log.e("talep",gson.toJson(response.body().get(0)));
                    Log.e("date", "" + new Date().toString());


                    TalepListAdapter talepListAdapter = new TalepListAdapter(Taleplerim.this, response.body());
                    listView.setAdapter(talepListAdapter);


                } else {
                    // veri alımı başarısız sayfa yok veya server kapalı
                }

            }

            @Override
            public void onFailure(Call<List<Talep>> call, Throwable t) {
                //Parse hatası veya liste boş.
                t.printStackTrace();
            }
        });

    }

    public void getGidenTalepler() {

        RequestBody requestBody = new RequestBody(S.userId, S.userToken);
        requestBody.setDurum("Giden");
        Call<List<Talep>> call = Db.getConnect().getTalep(requestBody);
        call.enqueue(new Callback<List<Talep>>() {
            @Override
            public void onResponse(Call<List<Talep>> call, Response<List<Talep>> response) {

                Log.d("TF.onResponse()", "" + response.body().size() + "item listed.");
                if (response.code() == 200) {
                    Log.d("TF.onResponse()", "" + response.body().size() + "item listed.");
                    // Veri alımı Başarılı


                    //  Log.e("talep",gson.toJson(response.body().get(0)));
                    Log.e("date", "" + new Date().toString());


                    TalepListAdapter talepListAdapter = new TalepListAdapter(Taleplerim.this, response.body());
                    listView.setAdapter(talepListAdapter);


                } else {
                    // veri alımı başarısız sayfa yok veya server kapalı
                }

            }

            @Override
            public void onFailure(Call<List<Talep>> call, Throwable t) {
                //Parse hatası veya liste boş.
                t.printStackTrace();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showFiltreDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.talep_filtre_dialog, null);


        final Spinner sirala = view.findViewById(R.id.sp_sirala);
        final Spinner durum = view.findViewById(R.id.sp_durum);
        final Spinner departman = view.findViewById(R.id.sp_departman);
        final Button filtrele = view.findViewById(R.id.filter);

        ArrayAdapter<String> siralaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, siralamalar);
        sirala.setAdapter(siralaAdapter);

        ArrayAdapter<String> durumAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, durumlar);
        durum.setAdapter(durumAdapter);


        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        Call<List<Departman>> call = Db.getConnect().getDepartman();
        call.enqueue(new Callback<List<Departman>>() {
            @Override
            public void onResponse(Call<List<Departman>> call, Response<List<Departman>> response) {
                if (response.code() == 200) {


                    List<Departman> departmanList = new ArrayList<Departman>();
                    Departman d = new Departman();
                    d.setId("Tüm");
                    d.setBaslik("Tüm Departmanlar");
                    departmanList.add(d);

                    departmanList.addAll(response.body());

                    DepartmanAdapter departmanAdapter = new DepartmanAdapter(Taleplerim.this, departmanList);
                    departman.setAdapter(departmanAdapter);

                    filtrele.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String[] filtreler = new String[3];

                            filtreler[0] = (String) sirala.getSelectedItem();
                            filtreler[1] = (String) durum.getSelectedItem();
                            filtreler[2] = ((Departman) departman.getSelectedItem()).getId();

                            getTalepler(filtreler);
                            alertDialog.cancel();
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<List<Departman>> call, Throwable t) {

            }
        });


        alertDialog.show();
    }

    private void getTalepler(String[] filtreler) {

        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(S.userId);
        requestBody.setFiltreler(filtreler);

        Call<List<Talep>> call = Db.getConnect().getFilteredTalep(requestBody);
        call.enqueue(new Callback<List<Talep>>() {
            @Override
            public void onResponse(Call<List<Talep>> call, Response<List<Talep>> response) {
                if (response.code() == 200) {
                    TalepListAdapter talepListAdapter = new TalepListAdapter(Taleplerim.this, response.body());
                    listView.setAdapter(talepListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Talep>> call, Throwable t) {

            }
        });


    }

}

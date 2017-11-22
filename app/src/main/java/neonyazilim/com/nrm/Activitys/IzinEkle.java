package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import neonyazilim.com.nrm.Adapters.KullaniciAdapter;
import neonyazilim.com.nrm.Adapters.KullaniciIzinAdapter;
import neonyazilim.com.nrm.Adapters.ProjeAdapter;
import neonyazilim.com.nrm.Adapters.ProjeListAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Izin;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.Models.ProjeIzin;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static neonyazilim.com.nrm.R.id.kullanici_listview;

public class IzinEkle extends AppCompatActivity {


    Spinner sp_kullanici, sp_proje;

    CheckBox departmanEkle, sorumluEkle, gorevEkle, duzenle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin_ekle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("İzin Ekle");

        sp_kullanici = findViewById(R.id.sp_kullanici);
        sp_proje = findViewById(R.id.sp_proje);


        sorumluEkle = findViewById(R.id.sorumlu_ekle);
        departmanEkle = findViewById(R.id.departman_ekle);
        gorevEkle = findViewById(R.id.gorev_ekle);
        duzenle = findViewById(R.id.duzenle);


        getKullanici();
        getProjeler();


        Button gonder = findViewById(R.id.gonder);

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIzin();
            }
        });
    }

    private void getKullanici() {
        RequestBody requestBody = new RequestBody(S.userId, S.userToken);

        Gson gson = new Gson();
        Log.e("req", gson.toJson(requestBody));
        Call<List<Kullanici>> call = Db.getConnect().getUsers(requestBody);
        call.enqueue(new Callback<List<Kullanici>>() {
            @Override
            public void onResponse(Call<List<Kullanici>> call, Response<List<Kullanici>> response) {
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        //
                        Log.e("code:", "" + response.code());


                        KullaniciAdapter kullaniciAdapter = new KullaniciAdapter(IzinEkle.this, response.body());

                        sp_kullanici.setAdapter(kullaniciAdapter);


                    }
                }
            }

            @Override
            public void onFailure(Call<List<Kullanici>> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });
    }


    private void getProjeler() {
        Log.e("responseb", "getProjeler();");
        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(S.userId);
        requestBody.setToken(S.userToken);
        Call<List<Proje>> call = Db.getConnect().getProjeler(requestBody);
        call.enqueue(new Callback<List<Proje>>() {
            @Override
            public void onResponse(Call<List<Proje>> call, Response<List<Proje>> response) {
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        //Projeler Alındı

                        ProjeListAdapter projeListAdapter = new ProjeListAdapter(IzinEkle.this, response.body());
                        sp_proje.setAdapter(projeListAdapter);

                    }
                }
                Log.e("responseb", "" + response.code());
            }

            @Override
            public void onFailure(Call<List<Proje>> call, Throwable t) {
                Log.e("responseb", "" + t.getMessage());
            }
        });
    }

    private void sendIzin() {
/**
 *
 * Silmeler eklenecek
 *
 *
 */
        RequestBody requestBody = new RequestBody();

        requestBody.setUserId(S.userId);
        requestBody.setToken(S.userToken);

        ProjeIzin projeIzin = new ProjeIzin();


        Proje p = (Proje) sp_proje.getSelectedItem();

        Log.e("p", p.toString());
        Kullanici k = (Kullanici) sp_kullanici.getSelectedItem();
        projeIzin.setUserId(k.getId());
        Log.e("k", k.toString());
        projeIzin.setProjeId(p.get_id());
        projeIzin.setGoruntule(true);
        projeIzin.setDepartmanEkle(departmanEkle.isChecked());
        projeIzin.setSorumluEkle(sorumluEkle.isChecked());
        projeIzin.setGorevEkle(gorevEkle.isChecked());
        projeIzin.setProjeyiDuzenle(duzenle.isChecked());

        requestBody.setProjeIzin(projeIzin);


        Call<Izin> call = Db.getConnect().sendIzin(requestBody);
        call.enqueue(new Callback<Izin>() {
            @Override
            public void onResponse(Call<Izin> call, Response<Izin> response) {
                if (response.code() == 200) {
                    Toast.makeText(IzinEkle.this, "İzin Eklendi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Izin> call, Throwable t) {

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


}

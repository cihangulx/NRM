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
import android.widget.ListView;
import android.widget.Spinner;

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

public class TalepYonetimi extends AppCompatActivity {

    Spinner durum,sp_departman,sp_tarih;
    String [] durumlar = {"Tüm Talepler","İşlemde","Tamamlandı","Red Edildi"};
    String [] tarih = {"Tüm Zamanlar","Bugün","Bu hafta","Bu Ay","Bu Yıl"};

    ListView talep_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talep_yonetimi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Talep Yönetimi");


        durum=findViewById(R.id.durum);
        sp_tarih=findViewById(R.id.tarih);
        sp_departman=findViewById(R.id.sp_departman);
        talep_list_view=findViewById(R.id.talep_list_view);

        ArrayAdapter<String> durumadapter = new ArrayAdapter<String>(this,R.layout.unvan_item_view,durumlar);
        ArrayAdapter<String> tarihAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,tarih);
        durum.setAdapter(durumadapter);
        sp_tarih.setAdapter(tarihAdapter);
        getDepartman();
        getTalep();
    }


    private void getDepartman() {
        Call<List<Departman>> call = Db.getConnect().getDepartman();
        call.enqueue(new Callback<List<Departman>>() {
            @Override
            public void onResponse(Call<List<Departman>> call, final Response<List<Departman>> response) {
                if (response.code() == 200) {

                    DepartmanAdapter departmanAdapter = new DepartmanAdapter(TalepYonetimi.this, response.body());

                    sp_departman.setAdapter(departmanAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Departman>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }



    private void getTalep() {

        RequestBody requestBody = new RequestBody(S.userId,S.userToken);
        Call<List<Talep>> call = Db.getConnect().getTalepler(requestBody);
        call.enqueue(new Callback<List<Talep>>() {
            @Override
            public void onResponse(Call<List<Talep>> call, Response<List<Talep>> response) {

                Log.e("TY.onResponse()",""+response.body().size()+"item listed.");
                if (response.code() == 200) {
                    Log.e("TF.onResponse()",""+response.body().size()+"item listed.");
                    // Veri alımı Başarılı

                    TalepListAdapter talepListAdapter = new TalepListAdapter(TalepYonetimi.this,filter(response.body()));
                    talep_list_view.setAdapter(talepListAdapter);


                } else {
                    // veri alımı başarısız sayfa yok veya server kapalı
                }

            }

            @Override
            public void onFailure(Call<List<Talep>> call, Throwable t) {
                //Parse hatası veya liste boş.
                t.printStackTrace();
                Log.e("failh",t.getMessage());
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


    private List<Talep> filter(List<Talep> talepList){

        List<Talep> taleps =talepList;

        return taleps;


    }

}

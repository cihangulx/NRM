package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import neonyazilim.com.nrm.Adapters.DepartmanAdapter;
import neonyazilim.com.nrm.Adapters.KullaniciAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Models.Gorev;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static neonyazilim.com.nrm.R.id.baslik;
import static neonyazilim.com.nrm.R.id.sorumlu_list_view;

public class ProjeDetay extends AppCompatActivity {


    SeekBar seekbar;
    Proje proje;
    TextView projeBaslik, projeAciklama, projeTarih, progressText;
    ListView departman_listview,sorumlu_listview,gorevler_list_view;

    LinearLayout deparman_root,sorumlu_root,gorev_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje_detay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        projeBaslik = (TextView) findViewById(R.id.proje_baslik);
        projeAciklama = (TextView) findViewById(R.id.proje_aciklama);
        projeTarih = (TextView) findViewById(R.id.proje_tarih);
        seekbar = findViewById(R.id.progress);
        progressText = (TextView) findViewById(R.id.ilerleme);
        deparman_root =(LinearLayout)findViewById(R.id.deparman_root);
        sorumlu_root =(LinearLayout)findViewById(R.id.sorumlu_root);
        gorev_root =(LinearLayout)findViewById(R.id.gorev_root);

        departman_listview = (ListView) findViewById(R.id.departman_list_view);
        sorumlu_listview=(ListView)findViewById(R.id.sorumlu_list_view);
        gorevler_list_view =(ListView)findViewById(R.id.gorevler_list_view);
        try {
            final String stringProje = getIntent().getExtras().getString("proje");
            Gson gson = new Gson();
            proje = gson.fromJson(stringProje, Proje.class);

            projeBaslik.setText(proje.getBaslik());
            projeAciklama.setText(proje.getAciklama());
            seekbar.setProgress(proje.getProgress());
            //seekbar.setEnabled(false);
            progressText.setText("%" + proje.getProgress());
            projeTarih.setText("" + proje.getTarih());

            seekbar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

                  /*  Intent intent = new Intent(ProjeDetay.this, GorevEkle.class);
                    intent.putExtra("proje", stringProje);
                    startActivity(intent);*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        getDepartman();
        getSorumlular();
        getGorevler();
    }
    private void getDepartman() {

        Call<List<Departman>> call = Db.getConnect().getDepartman();
        call.enqueue(new Callback<List<Departman>>() {
            @Override
            public void onResponse(Call<List<Departman>> call, Response<List<Departman>> response) {
                if (response.code() == 200) {

                    List<Departman> departmanList = response.body();
                    String[] departmanlar = proje.getDepartmanlar();
                    List<Departman> filtered = new ArrayList<Departman>();
                    for (int i = 0; i < departmanList.size(); i++) {
                        for (int j = 0; j < departmanlar.length; j++) {
                            if (departmanList.get(i).getId().equals(departmanlar[j])) {
                                filtered.add(departmanList.get(i));
                            }
                        }
                    }

                    DepartmanAdapter departmanAdapter = new DepartmanAdapter(ProjeDetay.this, filtered);
                    departman_listview.setAdapter(departmanAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Departman>> call, Throwable t) {

            }
        });

    }
    private void getSorumlular() {
        RequestBody requestBody = new RequestBody(S.userId, S.userToken);

        Gson gson = new Gson();
        Log.e("req",gson.toJson(requestBody));
        Call<List<Kullanici>> call = Db.getConnect().getUsers(requestBody);
        call.enqueue(new Callback<List<Kullanici>>() {
            @Override
            public void onResponse(Call<List<Kullanici>> call, Response<List<Kullanici>> response) {
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        //
                        Log.e("code:",""+response.code());
                        List<Kullanici> kullaniciList = response.body();
                        String[] sorumlular = proje.getSorumlular();
                        List<Kullanici> filtered = new ArrayList<Kullanici>();
                        for (int i = 0; i < kullaniciList.size(); i++) {
                            for (int j = 0; j < sorumlular.length; j++) {
                                if (kullaniciList.get(i).getId().equals(sorumlular[j])){
                                    filtered.add(kullaniciList.get(i));
                                }
                            }
                        }

                        KullaniciAdapter kullaniciAdapter = new KullaniciAdapter(ProjeDetay.this,filtered);

                        sorumlu_listview.setAdapter(kullaniciAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Kullanici>> call, Throwable t) {
                Log.e("fail",t.getMessage());
            }
        });
    }
    private void getGorevler() {
        RequestBody requestBody=new RequestBody(proje.getId(),S.userToken);
        Call<List<Gorev>> call = Db.getConnect().getGorevler(requestBody);
        call.enqueue(new Callback<List<Gorev>>() {
            @Override
            public void onResponse(Call<List<Gorev>> call, Response<List<Gorev>> response) {
                Log.e("code:",""+response.code());
                if (response.code()==200){


                    String [] gorevler = new String[response.body().size()];
                    for (int i=0;i<response.body().size();i++){
                        gorevler[i]=response.body().get(i).getBaslik();
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter(ProjeDetay.this,android.R.layout.simple_list_item_1,gorevler);
                    gorevler_list_view.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Gorev>> call, Throwable t) {
                Log.e("fail",t.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.proje_detay_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id==R.id.action_add_department){
         deparman_root.setVisibility(View.VISIBLE);
        }else if (id==R.id.action_add_sorumlu){
          sorumlu_root.setVisibility(View.VISIBLE);
        }else if (id==R.id.action_add_gorev){
           gorev_root.setVisibility(View.VISIBLE);
        }


        return super.onOptionsItemSelected(item);
    }
}

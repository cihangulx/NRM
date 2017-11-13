package neonyazilim.com.nrm.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import neonyazilim.com.nrm.Adapters.DepartmanAdapter;
import neonyazilim.com.nrm.Adapters.GorevAdapter;
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
import static neonyazilim.com.nrm.R.id.talep_aciklama;

public class ProjeDetay extends AppCompatActivity {


    Proje proje;
    TextView projeBaslik, projeAciklama, projeTarih;
    ListView departman_listview, sorumlu_listview, gorevler_list_view;
    DonutProgress donutProgress;
    LinearLayout deparman_root, sorumlu_root, gorev_root;

    TextView durum, gorev_sayisi, sorumlu_sayisi, departman_sayisi;
    EditText et_aciklama, et_baslik;
    Button guncelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje_detay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        projeBaslik = (TextView) findViewById(R.id.proje_baslik);
        projeAciklama = (TextView) findViewById(R.id.proje_aciklama);
        projeTarih = (TextView) findViewById(R.id.proje_tarih);

        deparman_root = (LinearLayout) findViewById(R.id.deparman_root);
        sorumlu_root = (LinearLayout) findViewById(R.id.sorumlu_root);
        gorev_root = (LinearLayout) findViewById(R.id.gorev_root);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        departman_listview = (ListView) findViewById(R.id.departman_list_view);
        sorumlu_listview = (ListView) findViewById(R.id.sorumlu_list_view);
        gorevler_list_view = (ListView) findViewById(R.id.gorevler_list_view);
        et_aciklama = (EditText) findViewById(R.id.et_aciklama);
        et_baslik = (EditText) findViewById(R.id.et_baslik);

        gorev_sayisi = findViewById(R.id.gorev_sayisi);
        durum = findViewById(R.id.durum);
        departman_sayisi = findViewById(R.id.departman_sayisi);
        sorumlu_sayisi = findViewById(R.id.sorumlu_sayisi);
        guncelle = findViewById(R.id.guncelle);

        try {
            final String stringProje = getIntent().getExtras().getString("proje");
            Gson gson = new Gson();
            proje = gson.fromJson(stringProje, Proje.class);

            Log.e("str", stringProje);
            projeBaslik.setText(proje.getBaslik());
            projeAciklama.setText(proje.getAciklama());

            if (proje.getBaslik().length() > 8) {
                setTitle(proje.getBaslik().substring(0, 11) + "...");
            } else {
                setTitle(proje.getBaslik());
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(proje.getTarih());
            int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            projeTarih.setText("" + day + "/" + (month) + "/" + year);

            donutProgress.setProgress(proje.getProgress());

                  /*  Intent intent = new Intent(ProjeDetay.this, GorevEkle.class);
                    intent.putExtra("proje", stringProje);
                    startActivity(intent);*/

            if (proje.getProgress() != 100) {
                durum.setText("Durum: " + "İşlemde");
            } else {
                durum.setText("Durum:" + "Tamamlandı");
            }

            getDepartman();
            getSorumlular();
            getGorevler();


        } catch (Exception e) {
            e.printStackTrace();
        }


        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guncelle();
            }
        });

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

                    departman_sayisi.setText("Departman Sayısı: " + filtered.size());
                    DepartmanAdapter departmanAdapter = new DepartmanAdapter(ProjeDetay.this, filtered);
                    departman_listview.setAdapter(departmanAdapter);

                    ViewGroup.LayoutParams params = departman_listview.getLayoutParams();
                    params.height = (departman_listview.getAdapter().getCount() * 140);
                    departman_listview.setLayoutParams(params);
                    departman_listview.requestLayout();

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
        Log.e("req", gson.toJson(requestBody));
        Call<List<Kullanici>> call = Db.getConnect().getUsers(requestBody);
        call.enqueue(new Callback<List<Kullanici>>() {
            @Override
            public void onResponse(Call<List<Kullanici>> call, Response<List<Kullanici>> response) {
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        //
                        Log.e("code:", "" + response.code());
                        List<Kullanici> kullaniciList = response.body();
                        String[] sorumlular = proje.getSorumlular();
                        List<Kullanici> filtered = new ArrayList<Kullanici>();
                        for (int i = 0; i < kullaniciList.size(); i++) {
                            for (int j = 0; j < sorumlular.length; j++) {
                                if (kullaniciList.get(i).getId().equals(sorumlular[j])) {
                                    filtered.add(kullaniciList.get(i));
                                }
                            }
                        }


                        sorumlu_sayisi.setText("Sorumlu Sayısı: " + filtered.size());
                        KullaniciAdapter kullaniciAdapter = new KullaniciAdapter(ProjeDetay.this, filtered);

                        sorumlu_listview.setAdapter(kullaniciAdapter);


                        ViewGroup.LayoutParams params = sorumlu_listview.getLayoutParams();
                        params.height = (sorumlu_listview.getAdapter().getCount() * 140);
                        sorumlu_listview.setLayoutParams(params);
                        sorumlu_listview.requestLayout();


                    }
                }
            }

            @Override
            public void onFailure(Call<List<Kullanici>> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });
    }

    private void getGorevler() {
        RequestBody requestBody = new RequestBody(proje.getId(), S.userToken);

        Call<List<Gorev>> call = Db.getConnect().getGorevler(requestBody);
        call.enqueue(new Callback<List<Gorev>>() {
            @Override
            public void onResponse(Call<List<Gorev>> call, Response<List<Gorev>> response) {
                Log.e("code:", "" + response.code());
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        Log.e("res", response.body().get(0).getBaslik());

                        double bolum = 100f / response.body().size();
                        double carpan = getTamamlananGorevler(response.body());

                        float sonuc = new Float(bolum * carpan);
                        List<Gorev> gorevList = response.body();

                        for (int i = 0; i < response.body().size(); i++) {
                            gorevList.get(i).setProjeProgress((int) sonuc);
                        }
                        GorevAdapter gorevAdapter = new GorevAdapter(ProjeDetay.this, response.body());
                        gorevler_list_view.setAdapter(gorevAdapter);

                        ViewGroup.LayoutParams params = gorevler_list_view.getLayoutParams();
                        params.height = (gorevler_list_view.getAdapter().getCount() * 140);
                        gorevler_list_view.setLayoutParams(params);
                        gorevler_list_view.requestLayout();


                        gorev_sayisi.setText("Görev Sayısı: " + response.body().size());

                        // Toast.makeText(GorevDetay.this,""+bolum,Toast.LENGTH_LONG).show();
                        donutProgress.setProgress(Math.round(sonuc));
                        updateProje((int) sonuc);
                    } else {
                        durum.setText("Durum: " + "Görev Eklenmesi Bekleniyor");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Gorev>> call, Throwable t) {
                Log.e("fail", t.getMessage());
                durum.setText("Durum: " + "Görev Eklenmesi Bekleniyor");
            }
        });
    }

    private void updateProje(int sonuc) {

        Proje proje1 = proje;
        proje1.setProgress(sonuc);


    }

    private void projeYiSil() {
        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(proje.getId());
        Call<Proje> call = Db.getConnect().projeyiSil(requestBody);
        call.enqueue(new Callback<Proje>() {
            @Override
            public void onResponse(Call<Proje> call, Response<Proje> response) {
                Log.e("sil", "" + response.code());
                if (response.code() == 200) {
                    Toast.makeText(ProjeDetay.this, "Proje Silindi", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ProjeDetay.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Proje> call, Throwable t) {
                Log.e("fail", t.getMessage());
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

        if (id == R.id.action_add_department) {
            deparman_root.setVisibility(View.VISIBLE);
        } else if (id == R.id.action_add_sorumlu) {
            sorumlu_root.setVisibility(View.VISIBLE);
        } else if (id == R.id.action_add_gorev) {
            Gson gson = new Gson();
            String projeString = gson.toJson(proje);
            Intent intent = new Intent(this, AddGorev.class);
            intent.putExtra("proje", projeString);
            startActivity(intent);
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            NavUtils.navigateUpTo(this, intent);
            return true;
        } else if (id == R.id.action_delete) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Projeyi Silmek İstediğinize Emin misiniz ? Bu işlem geri alınamaz.");
            dialog.setCancelable(false);
            dialog.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    projeYiSil();
                }
            });
            dialog.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.create().show();
        } else if (id == R.id.action_edit) {
            et_aciklama.setVisibility(View.VISIBLE);
            et_baslik.setVisibility(View.VISIBLE);
            et_aciklama.setText(proje.getAciklama());
            projeAciklama.setVisibility(View.GONE);
            projeBaslik.setVisibility(View.GONE);
            et_baslik.setText(proje.getBaslik());
            guncelle.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    private int getTamamlananGorevler(List<Gorev> gorevList) {
        int tamamlanan = 0;
        for (int i = 0; i < gorevList.size(); i++) {
            if (gorevList.get(i).getProgress() > 99) {
                tamamlanan++;
            }
        }
        return tamamlanan;
    }

    private void guncelle(){

        Proje proje1 = proje;
        proje1.setBaslik(et_baslik.getText().toString());
        proje1.setAciklama(et_aciklama.getText().toString());

        Call<Proje> call = Db.getConnect().projeyiGuncelle(proje1);
        call.enqueue(new Callback<Proje>() {
            @Override
            public void onResponse(Call<Proje> call, Response<Proje> response) {
                if (response.code()==200){
                    Toast.makeText(ProjeDetay.this,"Proje Güncellendi",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Proje> call, Throwable t) {

            }
        });




    }
}

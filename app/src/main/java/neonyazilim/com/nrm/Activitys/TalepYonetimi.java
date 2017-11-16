package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import neonyazilim.com.nrm.Models.Islem;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TalepYonetimi extends AppCompatActivity {

    Spinner durum, sp_departman, sp_tarih;
    String[] durumlar = {"Tüm Talepler", "İşlemde", "Tamamlandı", "Red Edildi"};
    String[] tarih = {"Tüm Zamanlar", "Bugün", "Bu hafta", "Bu Ay", "Bu Yıl"};

    ListView talep_list_view;

    List<Talep> mTalepList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talep_yonetimi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Talep Yönetimi");


        durum = findViewById(R.id.durum);
        sp_tarih = findViewById(R.id.tarih);
        sp_departman = findViewById(R.id.sp_departman);
        talep_list_view = findViewById(R.id.talep_list_view);

        ArrayAdapter<String> durumadapter = new ArrayAdapter<String>(this, R.layout.unvan_item_view, durumlar);
        ArrayAdapter<String> tarihAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, tarih);
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

        RequestBody requestBody = new RequestBody(S.userId, S.userToken);
        Call<List<Talep>> call = Db.getConnect().getTalepler(requestBody);
        call.enqueue(new Callback<List<Talep>>() {
            @Override
            public void onResponse(Call<List<Talep>> call, Response<List<Talep>> response) {

                Log.e("TY.onResponse()", "" + response.body().size() + "item listed.");
                if (response.code() == 200) {
                    Log.e("TF.onResponse()", "" + response.body().size() + "item listed.");
                    // Veri alımı Başarılı


                    final List<Talep> talepList = response.body();
                    mTalepList = talepList;

                    TalepListAdapter talepListAdapter = new TalepListAdapter(TalepYonetimi.this, filter(mTalepList));
                    talep_list_view.setAdapter(talepListAdapter);

                    durum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            TalepListAdapter talepListAdapter = new TalepListAdapter(TalepYonetimi.this, filter(talepList));
                            talep_list_view.setAdapter(talepListAdapter);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                    sp_departman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            TalepListAdapter talepListAdapter = new TalepListAdapter(TalepYonetimi.this, filter(talepList));
                            talep_list_view.setAdapter(talepListAdapter);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else {
                    // veri alımı başarısız sayfa yok veya server kapalı
                }

            }

            @Override
            public void onFailure(Call<List<Talep>> call, Throwable t) {
                //Parse hatası veya liste boş.
                t.printStackTrace();
                Log.e("failh", t.getMessage());
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


    private List<Talep> filter(List<Talep> talepList) {

        List<Talep> taleps = new ArrayList<>();

        if (durum.getSelectedItemPosition() == 0) {
            for (int i = 0; i < talepList.size(); i++) {
                taleps.add(talepList.get(i));
            }
        } else if (durum.getSelectedItemPosition() == 1) {
            for (int i = 0; i < talepList.size(); i++) {
                if (talepList.get(i).getDurum().equals("İşlemde")) {
                    taleps.add(talepList.get(i));
                }
            }
        } else if (durum.getSelectedItemPosition() == 2) {
            for (int i = 0; i < talepList.size(); i++) {
                if (talepList.get(i).getDurum().equals("Sonuçlandı")) {
                    taleps.add(talepList.get(i));
                }
            }
        } else if (durum.getSelectedItemPosition() == 3) {
            for (int i = 0; i < talepList.size(); i++) {
                if (talepList.get(i).getDurum().equals("Reddedildi")) {
                    taleps.add(talepList.get(i));
                }
            }
        }

        return taleps;


    }


    public void updateTalep(Talep talep,String durum, String aciklama) {

        RequestBody requestBody = new RequestBody(S.userId, S.userToken);
        requestBody.setDurum(durum);
        String[] ids = {talep.getId()};
        requestBody.setDepartmanList(ids);

        Islem islem = new Islem();


        islem.setIslem(durum);
        islem.setIslemiYapan(S.userId);
        islem.setIslemTarihi(new Date());
        islem.setIslemAciklamasi(aciklama);
        islem.setIslemOncesiDurum(talep.getDurum());

        List<Islem> islemler = new ArrayList<>();
        islemler.add(islem);
        requestBody.setIslem(islemler);

        Call<Talep> call = Db.getConnect().updateTalep(requestBody);
        call.enqueue(new Callback<Talep>() {
            @Override
            public void onResponse(Call<Talep> call, Response<Talep> response) {
                if (response.code() == 200) {
                    Log.e("codet", "" + response.code());
                   // startActivity(new Intent(TalepYonetimi.this, TalepYonetimi.class));
                    recreate();
                    Toast.makeText(TalepYonetimi.this,"Talep Güncellendi",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Talep> call, Throwable t) {

            }
        });

    }
    public void showDialog(final Talep talep, String mBaslik, final String islem) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        TextView baslik = view.findViewById(R.id.baslik);
        final EditText message = view.findViewById(R.id.messagem);
        Button iptal = view.findViewById(R.id.iptal);
        Button gonder = view.findViewById(R.id.gonder);

        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        baslik.setText(mBaslik + "!");
        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (!islem.equals("Sonuçlandı ")) {
                        if (message.getText().toString().isEmpty()) {
                            message.setError("Açıklama Alanı Zorunludur.");
                            YoYo.with(Techniques.Shake).duration(500).playOn(message);
                            return;
                        } else {

                        }

                        String aciklama = message.getText().toString().trim();
                        updateTalep(talep,islem, aciklama);
                        alertDialog.cancel();
                    } else {
                        if (message.getText().toString().isEmpty()) {
                            // message.setError("Açıklama Alanı Zorunludur.");
                            //  YoYo.with(Techniques.Shake).duration(500).playOn(message);
                            String aciklama = "Açıklama Eklenmedi!";
                            updateTalep(talep,islem, aciklama);
                            alertDialog.cancel();

                        } else {
                            YoYo.with(Techniques.Shake).duration(500).playOn(message);
                        }


                    }

                } catch (Exception e) {

                }


            }
        });


        alertDialog.show();

    }
    public void talebiSil(Talep talep) {
        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(talep.getId());

        Call<Talep> call = Db.getConnect().talebiSil(requestBody);
        call.enqueue(new Callback<Talep>() {
            @Override
            public void onResponse(Call<Talep> call, Response<Talep> response) {

                Log.e("sil", "" + response.code());
                if (response.code() == 200) {
                    Toast.makeText(TalepYonetimi.this, "Talep Silindi", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(TalepYonetimi.this, TalepYonetimi.class));
                }
            }

            @Override
            public void onFailure(Call<Talep> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });

    }




}

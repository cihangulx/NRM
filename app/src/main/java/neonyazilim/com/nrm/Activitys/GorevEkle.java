package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import neonyazilim.com.nrm.Adapters.AdimAdapter;
import neonyazilim.com.nrm.Adapters.KullaniciAdapter;
import neonyazilim.com.nrm.Models.Adim;
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

public class GorevEkle extends AppCompatActivity {

    ListView adim_list_view, gorevli_listview;

    EditText et_baslik, et_aciklama, et_adim_baslik;
    Spinner sp_gorevli;
    Button bt_gorevli_ekle, bt_adim_ekle, bt_gonder;

    TextInputLayout t_baslik, t_aciklama;

    List<Adim> adimList = new ArrayList<>();
    List<Kullanici> gorevliList = new ArrayList<>();

    Proje proje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorev_ekle);

        sp_gorevli = (Spinner) findViewById(R.id.sp_gorevli);
        try {
            final String stringProje = getIntent().getExtras().getString("proje");
            Gson gson = new Gson();
            proje = gson.fromJson(stringProje, Proje.class);

            Log.e("object", stringProje);
            getUser(proje.getDepartmanlar());

        } catch (Exception e) {
            e.printStackTrace();
        }

        et_baslik = (EditText) findViewById(R.id.et_baslik);
        et_aciklama = (EditText) findViewById(R.id.et_aciklama);
        et_adim_baslik = (EditText) findViewById(R.id.et_adim_baslik);


        t_baslik = (TextInputLayout) findViewById(R.id.t_baslik);
        t_aciklama = (TextInputLayout) findViewById(R.id.t_aciklama);

        bt_gorevli_ekle = findViewById(R.id.bt_gorevli_ekle);
        bt_adim_ekle = (Button) findViewById(R.id.bt_adim_ekle);
        bt_gonder = (Button) findViewById(R.id.bt_gonder);

        adim_list_view = (ListView) findViewById(R.id.adim_listview);
        gorevli_listview = (ListView) findViewById(R.id.gorevli_listview);

        bt_adim_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adimEkle();
            }
        });

        bt_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGorev();
            }
        });


    }

    private void adimEkle() {

        Adim adim = new Adim();
        if (!et_adim_baslik.getText().toString().isEmpty()) {
            adim.setBaslik(et_adim_baslik.getText().toString());
            adim_list_view.setVisibility(View.VISIBLE);
        } else {
            return;
        }

        adimList.add(adim);
        et_adim_baslik.setText("");

        if (adim_list_view.getAdapter() == null) {
            AdimAdapter adimAdapter = new AdimAdapter(this, adimList);
            adim_list_view.setAdapter(adimAdapter);
        } else {
            AdimAdapter adapter = (AdimAdapter) adim_list_view.getAdapter();
            adapter.notifyDataSetChanged();
        }


    }


    private void getUser(String[] departmanList) {
        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(S.userId);
        requestBody.setToken(S.userToken);
        requestBody.setDepartmanList(departmanList);

        Call<List<Kullanici>> call = Db.getConnect().getUser(requestBody);
        call.enqueue(new Callback<List<Kullanici>>() {
            @Override
            public void onResponse(Call<List<Kullanici>> call, final Response<List<Kullanici>> response) {
                if (response.code() == 200) {


                    String[] isimler = new String[response.body().size()];
                    for (int i = 0; i < response.body().size(); i++) {
                        isimler[i] = response.body().get(i).getIsim() + " " + response.body().get(i).getSoyIsim();
                    }

                    ArrayAdapter<String> kullaniciAdapter = new ArrayAdapter<String>(GorevEkle.this, android.R.layout.simple_dropdown_item_1line, isimler);
                    sp_gorevli.setAdapter(kullaniciAdapter);

                    final List<Kullanici> selectedKullanici = new ArrayList<Kullanici>();
                    final KullaniciAdapter selectedKullaniciAdapter = new KullaniciAdapter(GorevEkle.this, selectedKullanici);
                    bt_gorevli_ekle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("kullanici",  response.body().get(sp_gorevli.getSelectedItemPosition()).getId());
                            selectedKullanici.add( response.body().get(sp_gorevli.getSelectedItemPosition()));
                            gorevliList.add( response.body().get(sp_gorevli.getSelectedItemPosition()));
                            selectedKullaniciAdapter.notifyDataSetChanged();
                            gorevli_listview.setVisibility(View.VISIBLE);
                        }
                    });
                    sp_gorevli.setAdapter(kullaniciAdapter);
                    Log.e("code", "" + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Kullanici>> call, Throwable t) {
                Log.e("gorevli", t.getMessage());
            }
        });

    }

    private void sendGorev() {
        Gorev gorev = new Gorev();

        gorev.setProje(proje.getId());
        gorev.setBaslik(et_baslik.getText().toString());
        gorev.setAciklama(et_aciklama.getText().toString());
        String[] adimlar = new String[adim_list_view.getAdapter().getCount()];

        String[] gorevliler = new String[gorevliList.size()];
        for (int i = 0; i < gorevliList.size(); i++) {
            gorevliler[i] = gorevliList.get(i).getId();
        }
        gorev.setGorevli(gorevliler);

        for (int i = 0; i < adim_list_view.getAdapter().getCount(); i++) {
            adimlar[i] = ((Adim) adim_list_view.getAdapter().getItem(i)).getBaslik();
        }
        gorev.setAdimlar(adimlar);

        Call<Gorev> call = Db.getConnect().gorevEkle(gorev);
        call.enqueue(new Callback<Gorev>() {
            @Override
            public void onResponse(Call<Gorev> call, Response<Gorev> response) {
                Log.e("response", "" + response.code());
                if (response.code() == 200) {
                    if (!response.body().getId().isEmpty()) {
                        //Görev eklendi
                        Log.e("gorev", "Görev eklendi");
                    }
                }
            }

            @Override
            public void onFailure(Call<Gorev> call, Throwable t) {
                //   Log.e("gorev",t.getMessage());
            }
        });
    }

}

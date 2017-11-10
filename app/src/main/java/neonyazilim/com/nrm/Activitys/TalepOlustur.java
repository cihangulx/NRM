package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import neonyazilim.com.nrm.Adapters.KullaniciAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TalepOlustur extends AppCompatActivity {
    EditText et_baslik, et_aciklama;
    Spinner sp_departman, sp_alici;

    Button bt_gonder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talep_olustur);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Talep Oluştur");
        et_baslik = (EditText) findViewById(R.id.et_baslik);
        et_aciklama = (EditText) findViewById(R.id.et_aciklama);
        sp_departman = findViewById(R.id.sp_departman);
        sp_alici = findViewById(R.id.sp_alici);
        bt_gonder = (Button) findViewById(R.id.bt_gonder);
        bt_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(et_baslik.getText().toString().toString().trim(), et_aciklama.getText().toString().trim())) {
                    sendTalep();
                }
            }
        });

        getDepartman();
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


    private void getDepartman() {
        Call<List<Departman>> call = Db.getConnect().getDepartman();
        call.enqueue(new Callback<List<Departman>>() {
            @Override
            public void onResponse(Call<List<Departman>> call, final Response<List<Departman>> response) {
                if (response.code() == 200) {

                    String[] departman_baslik_list = new String[response.body().size()];
                    for (int i = 0; i < response.body().size(); i++) {
                        departman_baslik_list[i] = response.body().get(i).getBaslik();
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TalepOlustur.this, android.R.layout.simple_dropdown_item_1line, departman_baslik_list);

                    sp_departman.setAdapter(arrayAdapter);

                    sp_departman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            getUser(response.body());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Departman>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getUser(List<Departman> departman) {

        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(S.userId);
        requestBody.setToken(S.userToken);

        String []departmanlar = new String[departman.size()];
        for (int i=0;i<departman.size();i++){
            departmanlar[i]=departman.get(i).getId();
        }


        requestBody.setDepartmanList(departmanlar);

        Call<List<Kullanici>> call = Db.getConnect().getUser(requestBody);
        call.enqueue(new Callback<List<Kullanici>>() {
            @Override
            public void onResponse(Call<List<Kullanici>> call, Response<List<Kullanici>> response) {
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        KullaniciAdapter kullaniciAdapter = new KullaniciAdapter(TalepOlustur.this,response.body());
                        sp_alici.setAdapter(kullaniciAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Kullanici>> call, Throwable t) {

            }
        });
    }
    private void sendTalep() {
        Kullanici kullanici = (Kullanici) sp_alici.getSelectedItem();
        Talep talep = new Talep(et_baslik.getText().toString(),
                et_aciklama.getText().toString(),
                sp_departman.getSelectedItem().toString(),
                kullanici.getId(),
                new Date());

        Call<Talep> call = Db.getConnect().talepGonder(talep);
        call.enqueue(new Callback<Talep>() {
            @Override
            public void onResponse(Call<Talep> call, Response<Talep> response) {

                if (response.code() == 200) {
                    Toast.makeText(TalepOlustur.this, "Talep Gönderildi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Talep> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private boolean isValid(String baslik, String aciklama) {
        if (!baslik.isEmpty()) {
            if (!aciklama.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}

package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import neonyazilim.com.nrm.Adapters.DepartmanAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.Unvan;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KullaniciEkle extends AppCompatActivity {

    Spinner sp_departman, sp_unvan;

    CheckBox talepAcabilir;
    CheckBox projeAcabilir;

    CheckBox sorumluEkleyebilir;
    CheckBox gorevliEkleyebilir;

    CheckBox gorevEkleyebilir;
    CheckBox talepSilebilir;

    CheckBox projeSilebilir;
    CheckBox sorumluSilebilir;

    CheckBox gorevliSilebilir;
    CheckBox gorevSilebilir;

    Button bt_gonder;

    TextInputLayout t_isim, t_soyisim, t_mail;

    EditText et_isim, et_soy_isim, et_mail, et_adress, et_telefon, et_sifre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_ekle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Kullanıcı Ekle");
        sp_departman = (Spinner) findViewById(R.id.sp_departman);
        sp_unvan = (Spinner) findViewById(R.id.sp_unvan);
        getDepartman();
        getUnvan();

        et_isim = findViewById(R.id.et_isim);
        et_soy_isim = findViewById(R.id.et_soy_isim);
        et_mail = findViewById(R.id.et_mail);
        et_sifre = findViewById(R.id.et_sifre);
        et_adress = findViewById(R.id.et_adress);
        et_telefon = findViewById(R.id.et_telefon);



        et_sifre.setText(generateRandomHexToken(3));

        t_isim = (TextInputLayout) findViewById(R.id.t_isim);
        t_soyisim = (TextInputLayout) findViewById(R.id.t_soyisim);
        t_mail = (TextInputLayout) findViewById(R.id.t_mail);

        bt_gonder = (Button) findViewById(R.id.bt_gonder);


        talepAcabilir = (CheckBox) findViewById(R.id.talepAcabilir);
        talepSilebilir = (CheckBox) findViewById(R.id.talepSilebilir);

        sorumluEkleyebilir = (CheckBox) findViewById(R.id.sorumluEkleyebilir);
        sorumluSilebilir = (CheckBox) findViewById(R.id.sorumluSilebilir);

        projeAcabilir = (CheckBox) findViewById(R.id.projeAcabilir);
        projeSilebilir = (CheckBox) findViewById(R.id.projeSilebilir);

        gorevliEkleyebilir = (CheckBox) findViewById(R.id.gorevliEkleyebilir);
        gorevliSilebilir = (CheckBox) findViewById(R.id.gorevliSilebilir);

        gorevEkleyebilir = (CheckBox) findViewById(R.id.gorevEkleyebilir);
        gorevSilebilir = (CheckBox) findViewById(R.id.gorevSilebilir);


        bt_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    sendUser();
                }
            }
        });

    }

    public  String generateRandomHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(6); //hex encoding
    }

    private void sendUser() {

        Kullanici kullanici = new Kullanici();

        kullanici.setIsim(et_isim.getText().toString());
        kullanici.setSoyIsim(et_soy_isim.getText().toString());
        kullanici.setePosta(et_mail.getText().toString());

        if (!et_telefon.getText().toString().isEmpty()) {
            kullanici.setTelefon(et_telefon.getText().toString());
        } else {
            kullanici.setTelefon("");
        }
        if (!et_adress.getText().toString().isEmpty()) {
            kullanici.setAdres(et_adress.getText().toString());
        } else {
            kullanici.setAdres("");
        }

        Departman departman = (Departman) sp_departman.getSelectedItem();
        kullanici.setUnvan(sp_unvan.getSelectedItem().toString());
        kullanici.setDepartman(departman.getId());

        kullanici.setTalepAcabilir(talepAcabilir.isChecked());
        kullanici.setTalepSilebilir(talepSilebilir.isChecked());
        kullanici.setSifre(et_sifre.getText().toString());
        kullanici.setSorumluEkleyebilir(sorumluEkleyebilir.isChecked());
        kullanici.setSorumluSilebilir(sorumluSilebilir.isChecked());
        kullanici.setProjeAcabilir(sorumluEkleyebilir.isChecked());
        kullanici.setProjeSilebilir(projeSilebilir.isChecked());
        kullanici.setGorevliEkleyebilir(gorevliEkleyebilir.isChecked());
        kullanici.setGorevliSilebilir(gorevliSilebilir.isChecked());
        kullanici.setGorevEkleyebilir(gorevEkleyebilir.isChecked());
        kullanici.setGorevliSilebilir(gorevSilebilir.isChecked());


        Call<Kullanici> call = Db.getConnect().sendUser(kullanici);
        call.enqueue(new Callback<Kullanici>() {
            @Override
            public void onResponse(Call<Kullanici> call, Response<Kullanici> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (!response.body().getIsim().isEmpty()) {
                            //Kulklanıcı başarıyla kayıt edildi.
                            Toast.makeText(KullaniciEkle.this, "Kullanıcı Kayıt Edildi", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(KullaniciEkle.this, MainActivity.class));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Kullanici> call, Throwable t) {
                //Kayıt başarısız parse hatası veya empty body
                t.printStackTrace();
            }
        });


    }

    private void getDepartman() {
        Call<List<Departman>> call = Db.getConnect().getDepartman();
        call.enqueue(new Callback<List<Departman>>() {
            @Override
            public void onResponse(Call<List<Departman>> call, Response<List<Departman>> response) {
                if (response.code() == 200) {

                    DepartmanAdapter departmanAdapter = new DepartmanAdapter(KullaniciEkle.this, response.body());

                    sp_departman.setAdapter(departmanAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Departman>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getUnvan() {
        Call<List<Unvan>> call = Db.getConnect().getUnvan();
        call.enqueue(new Callback<List<Unvan>>() {
            @Override
            public void onResponse(Call<List<Unvan>> call, Response<List<Unvan>> response) {
                if (response.code() == 200) {
                    String[] unvan_baslik_list = new String[response.body().size()];
                    for (int i = 0; i < response.body().size(); i++) {
                        unvan_baslik_list[i] = response.body().get(i).getBaslik();
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(KullaniciEkle.this, android.R.layout.simple_dropdown_item_1line, unvan_baslik_list);
                    final List<Unvan> unvanList = response.body();

                    sp_unvan.setAdapter(arrayAdapter);


                    sp_unvan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Unvan unvan = unvanList.get(sp_unvan.getSelectedItemPosition());

                            talepAcabilir.setChecked(unvan.isTalepAcabilir());
                            talepSilebilir.setChecked(unvan.isTalepSilebilir());

                            sorumluEkleyebilir.setChecked(unvan.isSorumluEkleyebilir());
                            sorumluSilebilir.setChecked(unvan.isSorumluSilebilir());

                            projeAcabilir.setChecked(unvan.isProjeAcabilir());
                            projeSilebilir.setChecked(unvan.isProjeSilebilir());

                            gorevliEkleyebilir.setChecked(unvan.isGorevliEkleyebilir());
                            gorevliSilebilir.setChecked(unvan.isGorevliSilebilir());

                            gorevEkleyebilir.setChecked(unvan.isGorevEkleyebilir());
                            gorevSilebilir.setChecked(unvan.isGorevSilebilir());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Unvan>> call, Throwable t) {

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

    private boolean isValid() {
        if (!et_isim.getText().toString().trim().isEmpty()) {
            if (!et_soy_isim.getText().toString().trim().isEmpty()) {
                if (!et_mail.getText().toString().isEmpty()) {
                    return true;
                } else {
                    t_mail.setError("Mail Alanı Boş Bırakılamaz");
                    return false;
                }
            } else {
                t_soyisim.setError("Soyisim Alanı Boş Bırakılamaz");
                return false;
            }
        } else {
            t_isim.setError("İsim Alanı Boş Bırakılamaz");
            return false;
        }
    }
}

package neonyazilim.com.nrm.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import neonyazilim.com.nrm.Adapters.IslemAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Islem;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TalepDetay extends AppCompatActivity {

    private Talep talep;

    ImageView talepGonderenResim;
    TextView talepBaslik, talepAciklama, talep_gonderen, talepTarih, talep_gonderen_unvan;
    ListView islem_list_view;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talep_detay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        talepBaslik = (TextView) findViewById(R.id.talep_baslik);
        talepAciklama = (TextView) findViewById(R.id.talep_aciklama);
        talep_gonderen = (TextView) findViewById(R.id.talep_gonderen);
        talepTarih = (TextView) findViewById(R.id.talep_tarih);
        talepGonderenResim = (ImageView) findViewById(R.id.talep_resim);
        talep_gonderen_unvan = findViewById(R.id.talep_gonderen_unvan);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        islem_list_view = findViewById(R.id.islem_list_view);

        try {
            final String stringProje = getIntent().getExtras().getString("talep");
            Gson gson = new Gson();
            talep = gson.fromJson(stringProje, Talep.class);

            Log.e("str", stringProje);


            Calendar calendar = new GregorianCalendar();
            calendar.setTime(talep.getTarih());
            int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            talepTarih.setText("" + day + "/" + (month) + "/" + year);


            getUser();
            talepBaslik.setText(talep.getBaslik());
            talepAciklama.setText(talep.getAciklama());

            IslemAdapter islemAdapter = new IslemAdapter(this, talep.getIslemler());
            islem_list_view.setAdapter(islemAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }


        //Picasso.with(this).load(talep.getAlici()).into(talepGonderenResim);
//        talep_gonderen.setText(talep.getGonderen());


        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_sonuclandir:
                        showDialog("Sonuçlandır", "Sonuçlandı");
                        break;
                    case R.id.action_reddet:
                        showDialog("Reddet", "Reddedildi");
                        break;
                    case R.id.action_restore:
                        showDialog("Düzeltme İste", "Düzeltme İstendi");
                        break;
                    case R.id.action_isleme_al:
                        showDialog("İşleme Geri Al", "İşlemde");
                        break;
                    case R.id.action_delete:

                        AlertDialog.Builder dialog = new AlertDialog.Builder(TalepDetay.this);
                        dialog.setMessage("Talebi Silmek İstediğinize Emin misiniz ?\nBu işlem geri alınamaz.");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                talebiSil();
                            }
                        });
                        dialog.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialog.create().show();
                        break;

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.talep_detay_bottom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_sonuclandir:
                showDialog("Sonuçlandır", "Sonuçlandı");
                break;
            case R.id.action_reddet:
                showDialog("Reddet", "Reddedildi");
                break;
            case R.id.action_restore:
                showDialog("Düzeltme İste", "Düzeltme İstendi");
                break;
            case R.id.action_isleme_al:
                showDialog("İşleme Geri Al", "İşlemde");
                break;
            case R.id.action_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(TalepDetay.this);
                dialog.setMessage("Talebi Silmek İstediğinize Emin misiniz ?\nBu işlem geri alınamaz.");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        talebiSil();
                    }
                });
                dialog.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();
                break;
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                NavUtils.navigateUpTo(this, intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    private void updateTalep(String durum, String aciklama) {

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

        final List<Islem> islemler = new ArrayList<>();
        islemler.add(islem);
        requestBody.setIslem(islemler);

        Call<Talep> call = Db.getConnect().updateTalep(requestBody);
        call.enqueue(new Callback<Talep>() {
            @Override
            public void onResponse(Call<Talep> call, Response<Talep> response) {
                if (response.code() == 200) {
                    Log.e("codet", "" + response.code());
                    Toast.makeText(TalepDetay.this, "Talep Güncellendi", Toast.LENGTH_SHORT).show();

                   /* Gson gson = new Gson();
                    String projeString = gson.toJson(response.body());
                    Intent intent = new Intent(TalepDetay.this, TalepDetay.class);
                    intent.putExtra("talep", projeString);
                    startActivity(intent);*/

                   List<Islem> newList = talep.getIslemler();
                    newList.addAll(islemler);

                    IslemAdapter islemAdapter = new IslemAdapter(TalepDetay.this,newList);
                    islem_list_view.setAdapter(islemAdapter);

                    islem_list_view.setSelection(islemAdapter.getCount()-1);

                }
            }

            @Override
            public void onFailure(Call<Talep> call, Throwable t) {

            }
        });

    }

    private void getUser() {
        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(talep.getGonderen());

        Call<Kullanici> call = Db.getConnect().getUserss(requestBody);
        call.enqueue(new Callback<Kullanici>() {
            @Override
            public void onResponse(Call<Kullanici> call, Response<Kullanici> response) {
                if (response.code() == 200) {
                    talep_gonderen.setText(response.body().getIsim() + " " + response.body().getSoyIsim());
                    talep_gonderen_unvan.setText(response.body().getUnvan());
                }
            }

            @Override
            public void onFailure(Call<Kullanici> call, Throwable t) {

            }
        });

    }

    private void talebiSil() {
        RequestBody requestBody = new RequestBody();
        requestBody.setUserId(talep.getId());

        Call<Talep> call = Db.getConnect().talebiSil(requestBody);
        call.enqueue(new Callback<Talep>() {
            @Override
            public void onResponse(Call<Talep> call, Response<Talep> response) {

                Log.e("sil", "" + response.code());
                if (response.code() == 200) {
                    Toast.makeText(TalepDetay.this, "Talep Silindi", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(TalepDetay.this, TalepYonetimi.class));
                }
            }

            @Override
            public void onFailure(Call<Talep> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });

    }

    private void showDialog(String mBaslik, final String islem) {
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
                        updateTalep(islem, aciklama);
                        alertDialog.cancel();
                    } else {
                        if (message.getText().toString().isEmpty()) {
                            // message.setError("Açıklama Alanı Zorunludur.");
                            //  YoYo.with(Techniques.Shake).duration(500).playOn(message);
                            String aciklama = "Açıklama Eklenmedi!";
                            updateTalep(islem, aciklama);
                            alertDialog.cancel();
                        } else {

                        }


                    }

                } catch (Exception e) {

                }


            }
        });


        alertDialog.show();

    }

}

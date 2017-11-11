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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;

import neonyazilim.com.nrm.MainActivity;
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
    TextView talepBaslik, talepAciklama, talep_gonderen, talepTarih;

    Button reddet,sonuclandir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talep_detay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        talepBaslik = (TextView) findViewById(R.id.talep_baslik);
        talepAciklama=(TextView)findViewById(R.id.talep_aciklama);
        talep_gonderen = (TextView) findViewById(R.id.talep_gonderen);
        talepTarih = (TextView) findViewById(R.id.talep_tarih);
        talepGonderenResim = (ImageView) findViewById(R.id.talep_resim);

        reddet=findViewById(R.id.reddet);
        sonuclandir=findViewById(R.id.sonuclandir);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            String id =getIntent().getExtras().getString("id");
            String baslik = getIntent().getExtras().getString("baslik");
            String aciklama = getIntent().getExtras().getString("aciklama");
            String gonderen = getIntent().getExtras().getString("gonderen");
            String alici = getIntent().getExtras().getString("alici");
            String tarih = getIntent().getExtras().getString("tarih");
            talep = new Talep(baslik, aciklama, gonderen, alici, new Date());
            talep.setId(id);

        } catch (Exception e) {

            e.printStackTrace();
        }

        talepBaslik.setText(talep.getBaslik());
        talepAciklama.setText(talep.getAciklama());
        Picasso.with(this).load(talep.getAlici()).into(talepGonderenResim);
//        talep_gonderen.setText(talep.getGonderen());
        talepTarih.setText(new Date().toString());


        reddet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTalep("Reddedildi");
            }
        });

        sonuclandir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTalep("Sonuçlandı");
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


    private void updateTalep(String durum){

        RequestBody requestBody = new RequestBody(S.userId,S.userToken);
        requestBody.setDurum(durum);
        String [] ids ={talep.getId()};
        requestBody.setDepartmanList(ids);

        Call<Talep> call = Db.getConnect().updateTalep(requestBody);
        call.enqueue(new Callback<Talep>() {
            @Override
            public void onResponse(Call<Talep> call, Response<Talep> response) {
                if (response.code()==200){
                    Log.e("codet",""+response.code());
                    startActivity(new Intent(TalepDetay.this,MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Talep> call, Throwable t) {

            }
        });

    }
    private void getUser(){

    }

}

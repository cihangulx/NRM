package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;

import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.R;

public class TalepDetay extends AppCompatActivity {

    private Talep talep;

    ImageView talepGonderenResim;
    TextView talepBaslik, talepAciklama, talep_gonderen, talepTarih;

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

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            String baslik = getIntent().getExtras().getString("baslik");
            String aciklama = getIntent().getExtras().getString("aciklama");
            String gonderen = getIntent().getExtras().getString("gonderen");
            String alici = getIntent().getExtras().getString("alici");
            String tarih = getIntent().getExtras().getString("tarih");
            talep = new Talep(baslik, aciklama, gonderen, alici, new Date());

        } catch (Exception e) {

            e.printStackTrace();
        }

        talepBaslik.setText(talep.getBaslik());
        talepAciklama.setText(talep.getAciklama());
        Picasso.with(this).load(talep.getAlici()).into(talepGonderenResim);
//        talep_gonderen.setText(talep.getGonderen());
        talepTarih.setText(new Date().toString());


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

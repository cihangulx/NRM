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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.R;

import static neonyazilim.com.nrm.R.id.baslik;

public class ProjeDetay extends AppCompatActivity {


    SeekBar seekbar;
    Proje proje;
    TextView projeBaslik, projeAciklama, projeTarih, progressText;
    Button bt_gorev_ekle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje_detay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        projeBaslik = (TextView) findViewById(R.id.proje_baslik);
        projeAciklama = (TextView) findViewById(R.id.proje_aciklama);
        projeTarih = (TextView) findViewById(R.id.proje_tarih);
        seekbar = findViewById(R.id.progress);
        progressText = (TextView) findViewById(R.id.ilerleme);

        bt_gorev_ekle = (Button) findViewById(R.id.bt_gorev_ekle);


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

            bt_gorev_ekle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProjeDetay.this, GorevEkle.class);
                    intent.putExtra("proje", stringProje);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, proje.getBaslik(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
}

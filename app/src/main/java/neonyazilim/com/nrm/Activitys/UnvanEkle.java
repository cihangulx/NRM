package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Unvan;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnvanEkle extends AppCompatActivity {

    EditText et_unvan_baslik,et_unvan_aciklama;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unvan_ekle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Ünvan Ekle ");


        bt_gonder=(Button)findViewById(R.id.bt_gonder);
        et_unvan_baslik=(EditText)findViewById(R.id.et_unvan_baslik);
        et_unvan_aciklama=(EditText)findViewById(R.id.et_unvan_aciklama);

        talepAcabilir=(CheckBox)findViewById(R.id.talepAcabilir);
        talepSilebilir=(CheckBox)findViewById(R.id.talepSilebilir);

        sorumluEkleyebilir=(CheckBox)findViewById(R.id.sorumluEkleyebilir);
        sorumluSilebilir=(CheckBox)findViewById(R.id.sorumluSilebilir);

        projeAcabilir=(CheckBox)findViewById(R.id.projeAcabilir);
        projeSilebilir=(CheckBox)findViewById(R.id.projeSilebilir);

        gorevliEkleyebilir=(CheckBox)findViewById(R.id.gorevliEkleyebilir);
        gorevliSilebilir=(CheckBox)findViewById(R.id.gorevliSilebilir);

        gorevEkleyebilir=(CheckBox)findViewById(R.id.gorevEkleyebilir);
        gorevSilebilir=(CheckBox)findViewById(R.id.gorevSilebilir);



        bt_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (isValid(et_unvan_baslik.getText().toString().trim(),et_unvan_aciklama.getText().toString().trim())){
                   unvanEkle();
               }
            }
        });
    }
    /*
      public Unvan(String baslik, String aciklama,
                 boolean talepAcabilir,
                 boolean projeAcabilir,
                 boolean sorumluEkleyebilir,
                 boolean gorevliEkleyebilir,
                 boolean gorevEkleyebilir,

                 boolean talepSilebilir,
                 boolean projeSilebilir,
                 boolean sorumluSilebilir,
                 boolean gorevliSilebilir,
                 boolean gorevSilebilir) {
     */
    private void unvanEkle(){
        Unvan unvan =getUnvan();

        Call<Unvan> call = Db.getConnect().unvanEkle(unvan);
        call.enqueue(new Callback<Unvan>() {
            @Override
            public void onResponse(Call<Unvan> call, Response<Unvan> response) {
                if (response.code()==200){
                    Toast.makeText(UnvanEkle.this,"Unvan Eklendi",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Unvan> call, Throwable t) {

            }
        });


    }

    private boolean isValid(String baslik,String aciklama){
        if (!baslik.isEmpty()){
            if (!aciklama.isEmpty()){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    private Unvan getUnvan(){
        Unvan unvan = new Unvan(et_unvan_baslik.getText().toString(),
                et_unvan_aciklama.getText().toString(),
                talepAcabilir.isChecked(),
                projeAcabilir.isChecked(),
                sorumluEkleyebilir.isChecked(),
                gorevliEkleyebilir.isChecked(),
                gorevEkleyebilir.isChecked(),
                talepSilebilir.isChecked(),
                projeSilebilir.isChecked(),
                sorumluSilebilir.isChecked(),
                gorevliSilebilir.isChecked(),
                gorevSilebilir.isChecked());
        return unvan;
    }

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

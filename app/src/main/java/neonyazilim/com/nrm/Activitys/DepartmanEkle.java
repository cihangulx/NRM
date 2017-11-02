package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmanEkle extends AppCompatActivity {

    Button bt_gonder;
    EditText et_departman_baslik,et_departman_aciklama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departman_ekle);
        setTitle("Departman Ekle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_departman_baslik=(EditText)findViewById(R.id.et_departman_baslik);
        et_departman_aciklama=(EditText)findViewById(R.id.et_departman_aciklama);
        bt_gonder=(Button)findViewById(R.id.bt_gonder);

        bt_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(et_departman_baslik.getText().toString().trim(),et_departman_aciklama.getText().toString().trim())){
                    departmanEkle(et_departman_baslik.getText().toString().trim(),et_departman_aciklama.getText().toString().trim());
                }
            }
        });


    }

    private void departmanEkle(String baslik,String aciklama){
        Departman departman = new Departman(baslik,aciklama);

        Call<Departman> call = Db.getConnect().departmanEkle(departman);
        call.enqueue(new Callback<Departman>() {
            @Override
            public void onResponse(Call<Departman> call, Response<Departman> response) {
                if (response.code()==200){
                    Toast.makeText(DepartmanEkle.this,"Departman Eklendi",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Departman> call, Throwable t) {
                Toast.makeText(DepartmanEkle.this,t.getMessage().toString(),Toast.LENGTH_LONG).show();
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

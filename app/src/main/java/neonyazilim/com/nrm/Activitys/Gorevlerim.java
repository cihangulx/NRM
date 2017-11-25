package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import neonyazilim.com.nrm.Adapters.GorevAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Gorev;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gorevlerim extends AppCompatActivity {

    ListView gorevler_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorevlerim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Görevlerim");

        gorevler_list_view = findViewById(R.id.gorevler_list_view);

        getGorevler();
    }

    private void getGorevler() {
        RequestBody requestBody = new RequestBody();

        Call<List<Gorev>> call = Db.getConnect().getGorevlerim(requestBody);
        call.enqueue(new Callback<List<Gorev>>() {
            @Override
            public void onResponse(Call<List<Gorev>> call, Response<List<Gorev>> response) {


                Log.e("code:", "" + response.code());
                if (response.code() == 200) {
                    if (response.body().size() > 0) {
                        Log.e("res", response.body().get(0).getBaslik());



                        GorevAdapter gorevAdapter = new GorevAdapter(Gorevlerim.this, response.body(), new Proje());
                        gorevler_list_view.setAdapter(gorevAdapter);

                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Gorev>> call, Throwable t) {
                Log.e("fail", t.getMessage());
                //    durum.setText("Durum: " + "Görev Eklenmesi Bekleniyor");
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

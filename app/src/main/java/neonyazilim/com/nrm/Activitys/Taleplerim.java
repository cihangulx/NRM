package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import neonyazilim.com.nrm.Adapters.TalepAdapter;
import neonyazilim.com.nrm.Adapters.TalepListAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Taleplerim extends AppCompatActivity {

    ListView listView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taleplerim);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listView=findViewById(R.id.listview);

        getGelenTalepler();

    }

    public void getGelenTalepler() {
        RequestBody requestBody = new RequestBody(S.userId, S.userToken);
        Call<List<Talep>> call = Db.getConnect().getTalep(requestBody);
        call.enqueue(new Callback<List<Talep>>() {
            @Override
            public void onResponse(Call<List<Talep>> call, Response<List<Talep>> response) {

                Log.d("TF.onResponse()", "" + response.body().size() + "item listed.");
                if (response.code() == 200) {
                    Log.d("TF.onResponse()", "" + response.body().size() + "item listed.");
                    // Veri alımı Başarılı

                    //  Log.e("talep",gson.toJson(response.body().get(0)));
                    Log.e("date", "" + new Date().toString());

                    TalepListAdapter talepAdapter = new TalepListAdapter(Taleplerim.this, response.body());
                    listView.setAdapter(talepAdapter);

                } else {
                    // veri alımı başarısız sayfa yok veya server kapalı
                }

            }

            @Override
            public void onFailure(Call<List<Talep>> call, Throwable t) {
                //Parse hatası veya liste boş.
                t.printStackTrace();
            }
        });


    }

    public void getGidenTalepler() {

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

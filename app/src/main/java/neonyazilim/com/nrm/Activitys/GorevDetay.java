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
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import neonyazilim.com.nrm.Adapters.AdimAdapter;
import neonyazilim.com.nrm.Adapters.GorevAdapter;
import neonyazilim.com.nrm.Adapters.KullaniciAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Adim;
import neonyazilim.com.nrm.Models.Gorev;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GorevDetay extends AppCompatActivity {

    TextView gorev_baslik, gorev_aciklama, durum, gorevli_sayisi, adim_sayisi, tamalanan_adimlar, gorev_tarih;
    ListView gorevli_listview, adim_listview;

    Gorev gorev;
    Proje proje;

    DonutProgress donutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorev_detay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        gorev_aciklama = findViewById(R.id.gorev_aciklama);
        gorev_baslik = findViewById(R.id.gorev_baslik);

        durum = findViewById(R.id.durum);
        gorevli_sayisi = findViewById(R.id.gorevli_sayisi);
        adim_sayisi = findViewById(R.id.adim_sayisi);
        tamalanan_adimlar = findViewById(R.id.tamamlanan_adimlar);
        gorevli_listview = findViewById(R.id.gorevli_listview);
        adim_listview = findViewById(R.id.adim_listview);
        donutProgress = findViewById(R.id.donut_progress);


        try {
            final String stringGorev = getIntent().getExtras().getString("gorev");
            Gson gson = new Gson();
            gorev = gson.fromJson(stringGorev, Gorev.class);

            gorev_baslik.setText(gorev.getBaslik());
            gorev_aciklama.setText(gorev.getAciklama());

            if (gorev.getBaslik().length() > 8) {
                setTitle(gorev.getBaslik().substring(0, 11) + "...");
            }

            final String stringProje = getIntent().getExtras().getString("proje");
            Gson gson1 = new Gson();
            proje = gson1.fromJson(stringProje, Proje.class);

            Log.e("gorev",stringGorev);
           /* Calendar calendar = new GregorianCalendar();
            calendar.setTime(gorev.getTarih());
            int year = calendar.get(Calendar.YEAR);
//Add one to month {0 - 11}
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            gorev_tarih.setText("" + day + "/" + (month) + "/" + year);
*/
           double bolum =100f/gorev.getAdimlar().size();
           double carpan = getTamamlananAdimlar(gorev.getAdimlar());

            float sonuc =new Float(bolum*carpan);

          //  Toast.makeText(this,""+bolum,Toast.LENGTH_LONG).show();
            donutProgress.setProgress(Math.round(sonuc));

                  /*  Intent intent = new Intent(ProjeDetay.this, GorevEkle.class);
                    intent.putExtra("proje", stringProje);
                    startActivity(intent);*/

            gorevli_sayisi.setText("Görevli Sayısı: "+gorev.getGorevli().length);
            adim_sayisi.setText("Adım Sayısı: "+gorev.getAdimlar().size());
            tamalanan_adimlar.setText("Tamamlanan Adımlar: "+getTamamlananAdimlar(gorev.getAdimlar()));

            if (getTamamlananAdimlar(gorev.getAdimlar())==gorev.getAdimlar().size()){
                durum.setText("Görev Tamamlandı");
            }else {
                durum.setText("Devam Ediyor");
            }

            getAdimlar(gorev.getAdimlar());

            Log.e("gorev",stringGorev);

            getGorevliler();

        } catch (Exception e) {
            Log.e("e",e.getMessage());
            Toast.makeText(this,"Hatam:"+e.getMessage(),Toast.LENGTH_LONG).show();
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
            Gson gson = new Gson();
            String projeString = gson.toJson(proje);
            Intent intent = new Intent(this, ProjeDetay.class);
            intent.putExtra("proje", projeString);
            startActivity(intent);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private int getTamamlananAdimlar(List<Adim> adimList){

        int tamalanan =0;
        for (int i=0;i<adimList.size();i++){
            if (adimList.get(i).isBitti()){
                tamalanan++;
            }
        }

        return tamalanan;
    }


    private void getGorevliler(){
        RequestBody requestBody = new RequestBody();
        requestBody.setDepartmanList(gorev.getGorevli());
        Call<List<Kullanici>> call = Db.getConnect().getGorevliler(requestBody);
        call.enqueue(new Callback<List<Kullanici>>() {
            @Override
            public void onResponse(Call<List<Kullanici>> call, Response<List<Kullanici>> response) {
                if (response.code()==200){
                    Log.e("codem",""+response.code());
                    Log.e("res",response.body().get(0).getIsim());

                    KullaniciAdapter kullaniciAdapter = new KullaniciAdapter(GorevDetay.this,response.body());
                    gorevli_listview.setAdapter(kullaniciAdapter);

                    ViewGroup.LayoutParams params = gorevli_listview.getLayoutParams();
                    params.height = (gorevli_listview.getAdapter().getCount()*140);
                    gorevli_listview.setLayoutParams(params);
                    gorevli_listview.requestLayout();

                    Log.e("size",""+(gorevli_listview.getAdapter().getCount()*140));

                }
                Log.e("codem",""+response.code());
            }

            @Override
            public void onFailure(Call<List<Kullanici>> call, Throwable t) {
                Log.e("fail",t.getMessage());
            }
        });
    }
    private void getAdimlar(List<Adim> adimList){

      //  Toast.makeText(this,"2"+adimList.get(0).getBaslik(),Toast.LENGTH_LONG).show();
        AdimAdapter adimAdapter = new AdimAdapter(this,adimList);
        adim_listview.setAdapter(adimAdapter);
        ViewGroup.LayoutParams params = adim_listview.getLayoutParams();
        params.height = (adim_listview.getAdapter().getCount()*140);
        adim_listview.setLayoutParams(params);
        adim_listview.requestLayout();



    }

    public void updateAdim(Adim adim,int position,boolean bitti){

        Log.e("update",""+position+" "+bitti);

        List<Adim> adimList = gorev.getAdimlar();
        Adim adim1 = adim;
        adim1.setBitti(bitti);
        adimList.set(position,adim1);

        final Gorev gorev1 = gorev;
        gorev1.setAdimlar(adimList);

        double bolum =100f/gorev1.getAdimlar().size();
        double carpan = getTamamlananAdimlar(gorev1.getAdimlar());
        float sonuc =new Float(bolum*carpan);
        gorev1.setProgress(Math.round(sonuc));

        RequestBody requestBody = new RequestBody(gorev1.getProje(), S.userToken);

        Call<List<Gorev>> call1 = Db.getConnect().getGorevler(requestBody);
        call1.enqueue(new Callback<List<Gorev>>() {
            @Override
            public void onResponse(Call<List<Gorev>> call, Response<List<Gorev>> response) {
                Log.e("code:", "" + response.code());
                if (response.code() == 200) {
                    if (response.body().size()>0){
                        Log.e("res",response.body().get(0).getBaslik());

                        double bolum =100f/response.body().size();
                        double carpan = getTamamlananGorevler(response.body());

                        float sonuc =new Float(bolum*carpan);
                        gorev1.setProjeProgress((int)sonuc);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Gorev>> call, Throwable t) {
                Log.e("fail", t.getMessage());
            }
        });



        Call<Gorev> call = Db.getConnect().updateGorev(gorev1);
        call.enqueue(new Callback<Gorev>() {
            @Override
            public void onResponse(Call<Gorev> call, Response<Gorev> response) {
                Log.e("codes",""+response.code());
                if (response.code()==200){
                    if (!response.body().getId().isEmpty()){
                        Log.e("görev Eklendi","Görev:"+response.body().getId());

                        Gorev mGorev=response.body();
                        gorev=response.body();
                        tamalanan_adimlar.setText("Tamamlanan Adımlar: "+getTamamlananAdimlar(mGorev.getAdimlar()));
                        AdimAdapter adimAdapter = new AdimAdapter(GorevDetay.this,mGorev.getAdimlar());
                        adim_listview.setAdapter(adimAdapter);

                        double bolum =100f/mGorev.getAdimlar().size();
                        double carpan = getTamamlananAdimlar(mGorev.getAdimlar());

                        float sonuc =new Float(bolum*carpan);

                       // Toast.makeText(GorevDetay.this,""+bolum,Toast.LENGTH_LONG).show();
                        donutProgress.setProgress(Math.round(sonuc));

                    }
                }
            }

            @Override
            public void onFailure(Call<Gorev> call, Throwable t) {
                Log.e("fails",t.getMessage());
            }
        });


    }
    private int getTamamlananGorevler(List<Gorev> gorevList){
        int tamamlanan =0;
        for (int i =0;i<gorevList.size();i++){
            if (gorevList.get(i).getProgress()>99){
                tamamlanan++;
            }
        }
        return tamamlanan;
    }

}

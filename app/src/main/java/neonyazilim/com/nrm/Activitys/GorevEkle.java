package neonyazilim.com.nrm.Activitys;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import neonyazilim.com.nrm.Models.Adim;
import neonyazilim.com.nrm.Models.Gorev;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GorevEkle extends AppCompatActivity {

    ListView  adim_list_view;

    EditText et_baslik, et_aciklama,et_adim_baslik;
    Spinner  sp_gorevli;
    Button bt_gorevli_ekle,bt_adim_ekle, bt_gonder;

    TextInputLayout t_baslik,t_aciklama;

    List<Adim> adimList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorev_ekle);



        et_baslik=(EditText)findViewById(R.id.et_baslik);
        et_aciklama=(EditText)findViewById(R.id.et_aciklama);
        et_adim_baslik=(EditText)findViewById(R.id.et_adim_baslik);
        sp_gorevli=(Spinner)findViewById(R.id.sp_gorevli);

        t_baslik=(TextInputLayout)findViewById(R.id.t_baslik);
        t_aciklama=(TextInputLayout)findViewById(R.id.t_aciklama);

        bt_gorevli_ekle=findViewById(R.id.bt_gorevli_ekle);
        bt_adim_ekle=(Button)findViewById(R.id.bt_adim_ekle);
        bt_gonder=(Button)findViewById(R.id.bt_gonder);

        adim_list_view=(ListView)findViewById(R.id.adim_listview);


        bt_adim_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adimEkle();
            }
        });

        bt_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGorev();
            }
        });

    }

    private void adimEkle() {

        Adim adim = new Adim();
        if (!et_adim_baslik.getText().toString().isEmpty()){
            adim.setBaslik(et_adim_baslik.getText().toString());
        }




    }

    private void sendGorev(){
         Gorev gorev = new Gorev();

        gorev.setBaslik(et_baslik.getText().toString());
        gorev.setAciklama(et_aciklama.getText().toString());


        Call<Gorev> call = Db.getConnect().gorevEkle(gorev);
        call.enqueue(new Callback<Gorev>() {
            @Override
            public void onResponse(Call<Gorev> call, Response<Gorev> response) {
                Log.e("response",""+response.code());
                if (response.code()==200){
                    if (!response.body().getId().isEmpty()){
                        //Görev eklendi
                        Log.e("gorev","Görev eklendi");
                    }
                }
            }

            @Override
            public void onFailure(Call<Gorev> call, Throwable t) {
             //   Log.e("gorev",t.getMessage());
            }
        });
    }

}

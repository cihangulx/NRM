package neonyazilim.com.nrm.Activitys;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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
    }

    private void sendGorev(){
        final Gorev gorev = new Gorev();

        Call<Gorev> call = Db.getConnect().gorevEkle(gorev);
        call.enqueue(new Callback<Gorev>() {
            @Override
            public void onResponse(Call<Gorev> call, Response<Gorev> response) {
                if (response.code()==200){
                    if (!gorev.getId().isEmpty()){
                        //Görev eklendi
                        Log.e("gorev","Görev eklendi");
                    }
                }
            }

            @Override
            public void onFailure(Call<Gorev> call, Throwable t) {

            }
        });
    }

}

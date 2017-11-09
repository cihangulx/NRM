package neonyazilim.com.nrm.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import neonyazilim.com.nrm.Adapters.DepartmanAdapter;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TalepYonetimi extends AppCompatActivity {

    Spinner durum;
    String [] durumlar = {"Tüm Talepler","İşlemde","Tamamlandı","Red Edildi"};
    String [] tarih = {"Tüm Zamanlar","Bugün","Bu hafta","Bu Ay","Bu Yıl"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talep_yonetimi);
        durum=findViewById(R.id.durum);
        ArrayAdapter<String> durumadapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,durumlar);
        durum.setAdapter(durumadapter);
    }


  /*  private void getDepartman() {
        Call<List<Departman>> call = Db.getConnect().getDepartman();
        call.enqueue(new Callback<List<Departman>>() {
            @Override
            public void onResponse(Call<List<Departman>> call, final Response<List<Departman>> response) {
                if (response.code() == 200) {

                    DepartmanAdapter departmanAdapter = new DepartmanAdapter(ProjeEkle.this, response.body());

                    sp_departman.setAdapter(departmanAdapter);


                    final List<Departman> selectedDepartman = new ArrayList<Departman>();
                    final DepartmanAdapter seledtedAdapter = new DepartmanAdapter(ProjeEkle.this, selectedDepartman);

                    bt_add_departman.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedDepartman.add((Departman) sp_departman.getSelectedItem());
                            seledtedAdapter.notifyDataSetChanged();
                            deparman_list_view.setAdapter(seledtedAdapter);

                            deparman_list_view.setVisibility(View.VISIBLE);
                            getUser(selectedDepartman);
                        }
                    });


                    deparman_list_view.setAdapter(seledtedAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Departman>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }*/

}

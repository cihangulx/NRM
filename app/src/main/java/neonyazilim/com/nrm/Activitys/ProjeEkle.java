package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import neonyazilim.com.nrm.Adapters.DepartmanAdapter;
import neonyazilim.com.nrm.Adapters.KullaniciAdapter;
import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjeEkle extends AppCompatActivity {

    ListView sorumlu_listview, deparman_list_view;

    EditText et_baslik, et_aciklama;
    Spinner sp_departman, sp_alici, sp_sorumlu;
    Button bt_sorumlu_ekle, bt_gonder, bt_add_departman;

    TextInputLayout t_baslik,t_aciklama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proje_ekle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Proje Ekle");

        et_baslik = (EditText) findViewById(R.id.et_baslik);
        et_aciklama = (EditText) findViewById(R.id.et_aciklama);

        t_baslik=(TextInputLayout)findViewById(R.id.t_baslik);
        t_aciklama=(TextInputLayout)findViewById(R.id.t_aciklama);

        bt_gonder = findViewById(R.id.bt_gonder);

        bt_sorumlu_ekle = (Button) findViewById(R.id.bt_sorumlu_ekle);
        bt_add_departman = (Button) findViewById(R.id.add_departman);
        sp_departman = (Spinner) findViewById(R.id.sp_departman);
        sp_alici = (Spinner) findViewById(R.id.sp_alici);
        sp_sorumlu = (Spinner) findViewById(R.id.sp_sorumlu);

        sorumlu_listview = (ListView) findViewById(R.id.sorumlu_listview);
        deparman_list_view = (ListView) findViewById(R.id.departman_listview);

        getDepartman();

        bt_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid(et_baslik.getText().toString(),et_aciklama.getText().toString())){
                    sendProject();
                }else {

                }

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


    private void getDepartman() {
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
    }

    private void getUser(List<Departman> departmanList) {
        RequestBody requestBody = new RequestBody();
        String [] userList = new String[departmanList.size()];
        for (int i=0;i<departmanList.size();i++){
            userList[i]=departmanList.get(i).getId();
        }
        requestBody.setDepartmanList(userList);

        Call<List<Kullanici>> call = Db.getConnect().getUser(requestBody);
        call.enqueue(new Callback<List<Kullanici>>() {
            @Override
            public void onResponse(Call<List<Kullanici>> call, Response<List<Kullanici>> response) {
                if (response.code()==200){
                    KullaniciAdapter kullaniciAdapter = new KullaniciAdapter(ProjeEkle.this,response.body());
                    sp_sorumlu.setAdapter(kullaniciAdapter);

                    final List<Kullanici> selectedUsers = new ArrayList<Kullanici>();
                    final KullaniciAdapter selectedUserAdapter = new KullaniciAdapter(ProjeEkle.this,selectedUsers);
                    bt_sorumlu_ekle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedUsers.add(((Kullanici)sp_sorumlu.getSelectedItem()));
                            sorumlu_listview.setAdapter(selectedUserAdapter);
                            selectedUserAdapter.notifyDataSetChanged();
                            sorumlu_listview.setVisibility(View.VISIBLE);
                        }
                    });
                    selectedUserAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Kullanici>> call, Throwable t) {

            }
        });

    }


    private boolean isValid(String baslik, String aciklama) {
        if (!baslik.isEmpty()) {
            if (!aciklama.isEmpty()) {
                return true;
            } else {
                et_aciklama.setError("Açıklama Alanı Boş Bırakılamaz!");
                YoYo.with(Techniques.Shake).duration(1000).playOn(et_aciklama);
                return false;
            }
        } else {
            et_baslik.setError("Başlık Alanı Boş Bırakılamaz!");
            YoYo.with(Techniques.Shake).duration(1000).playOn(et_baslik);
            return false;
        }
    }

    private void sendProject() {

        if (deparman_list_view.getAdapter()!=null){
            if (deparman_list_view.getAdapter().getCount()>0){

            }else {
                YoYo.with(Techniques.Shake).duration(1000).playOn(sp_departman);
                return;
            }
        }else {
            YoYo.with(Techniques.Shake).duration(1000).playOn(sp_departman);
            return;
        }

        if (sorumlu_listview.getAdapter()!=null){
            if (deparman_list_view.getAdapter().getCount()>0){

            }else {
                YoYo.with(Techniques.Shake).duration(1000).playOn(sp_sorumlu);
                return;
            }
        }else {
            YoYo.with(Techniques.Shake).duration(1000).playOn(sp_sorumlu);
            return;
        }



        String[] departmanList = new String[deparman_list_view.getAdapter().getCount()];
        for (int i = 0; i < deparman_list_view.getAdapter().getCount(); i++) {
            Log.e("selected", ((Departman) deparman_list_view.getAdapter().getItem(i)).getBaslik());
            departmanList[i]=((Departman) deparman_list_view.getAdapter().getItem(i)).getId();
        }

        String [] sorumluList = new String[sorumlu_listview.getAdapter().getCount()];
        for (int i=0;i<sorumlu_listview.getAdapter().getCount();i++){
            sorumluList[i]=((Kullanici)sorumlu_listview.getAdapter().getItem(i)).getId();
        }

        String gonderen = S.userId;

        final Proje proje = new Proje();
        proje.setBaslik(et_baslik.getText().toString().trim());
        proje.setAciklama(et_aciklama.getText().toString().trim());

        proje.setGonderen(gonderen);
        proje.setToken(S.userToken);
        proje.setDepartmanlar(departmanList);
        proje.setSorumlular(sorumluList);
        Call<Proje> call = Db.getConnect().sendProject(proje);
        call.enqueue(new Callback<Proje>() {
            @Override
            public void onResponse(Call<Proje> call, Response<Proje> response) {
                if (response.code() == 200) {

                    if (!response.body().getId().isEmpty()){
                        Toast.makeText(ProjeEkle.this,"Proje Eklendi",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ProjeEkle.this,MainActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<Proje> call, Throwable t) {

            }
        });
    }
}

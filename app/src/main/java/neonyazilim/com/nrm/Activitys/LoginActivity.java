package neonyazilim.com.nrm.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import neonyazilim.com.nrm.MainActivity;
import neonyazilim.com.nrm.Managers.UserManager;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.UserRequest;
import neonyazilim.com.nrm.Models.UserResponse;
import neonyazilim.com.nrm.Network.Db;
import neonyazilim.com.nrm.R;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText ePosta;
    private EditText sifre;

    Button girisYap;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ePosta = findViewById(R.id.e_posta);
        sifre = findViewById(R.id.sifre);
        girisYap = (Button) findViewById(R.id.giris_yap);


        preferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = preferences.edit();


        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(ePosta.getText().toString(),sifre.getText().toString())){
                    login(ePosta.getText().toString().trim(),sifre.getText().toString().trim());
                }
            }
        });

    }

    private void login(String ePosta, String sifre) {
        UserRequest userRequest = new UserRequest(ePosta, sifre);

        Call<UserResponse> call = Db.getConnect().loginRequest(userRequest);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.code() == 200) {
                    if (response.body().isLogin()) {
                        String token = response.body().getToken();
                        Log.e("login0",token);
                        /**
                         *
                         * Giriş Başarılı Token değerini kaydediyoruz.
                         *
                         * Token ile kullanıcı bilgilerini almak için getUser metodunu kullanıyoruz.
                         *
                         *
                         */

                        getUser(response.body());

                    }else {
                        /**
                         *
                         * Giriş Başarısız
                         *
                         */
                    }
                }else if(response.code()==400){
                    /**
                     *
                     * Giriş Başarısız şifre veya parola hatalı.
                     *
                     */
                Toast.makeText(LoginActivity.this,"Şifre veya E-Posta Hatalı",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this,"Şifre veya E-Posta Hatalı",Toast.LENGTH_LONG).show();
                    /**
                     *
                     * Sayfa Bulunamadı Server aktif olmayabilir.
                     *
                     */
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                /**
                 *
                 * Giriş Başarısız
                 *
                 */
                Toast.makeText(LoginActivity.this,"Şifre veya E-Posta Hatalı",Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }
    private boolean isValid(String ePosta,String sifre){

        if (!ePosta.isEmpty()){
            if (!sifre.isEmpty()){
              return true;
            }else {
                Toast.makeText(this,"Şifre Alanı Boş Bırakılamaz",Toast.LENGTH_LONG).show();
                return false;
            }
        }else {
            Toast.makeText(this,"E-Posta alanı Boş Bırakılamaz",Toast.LENGTH_LONG).show();
            return false;
        }


    }

    private void getUser(UserResponse userResponse){
        Call<Kullanici> call = Db.getConnect().getKullanici(userResponse);
        call.enqueue(new Callback<Kullanici>() {
            @Override
            public void onResponse(Call<Kullanici> call, Response<Kullanici> response) {
                if (response.code()==200){

                    //Toast.makeText(LoginActivity.this,""+response.body().getId(),Toast.LENGTH_LONG).show();

                    S.userId=response.body().getId();
                    S.userToken=response.body().getToken();

                    editor.putString("userId",response.body().getId());
                    editor.putString("token",response.body().getToken());
                    editor.commit();

                    Gson gson = new Gson();
                    String jsonObj =gson.toJson(response.body());

                    Log.e("json",jsonObj);
                   startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    /**
                     *
                     * Kullanıcı bilgileri alındı local veritabanına kayıt edilebilir
                     *
                     */

                }else {
                    Log.e("login",""+response.code());
                    /**
                     *
                     * Herhangi bir veri alınamadı
                     *
                     * Oturum açmadan veri alınmaya çalışılıyor olabilir.
                     *
                     */
                }
            }

            @Override
            public void onFailure(Call<Kullanici> call, Throwable t) {

                t.printStackTrace();
                /**
                 *
                 * Parse hatası
                 * Kullanıcının gerekli bilgileri kayıt edilmemiş olabilir
                 *
                 */
            }
        });


    }

}

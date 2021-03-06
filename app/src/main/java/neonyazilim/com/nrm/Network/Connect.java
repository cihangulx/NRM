package neonyazilim.com.nrm.Network;

import java.util.List;

import neonyazilim.com.nrm.Models.Departman;
import neonyazilim.com.nrm.Models.Gorev;
import neonyazilim.com.nrm.Models.Izin;
import neonyazilim.com.nrm.Models.Kullanici;
import neonyazilim.com.nrm.Models.Proje;
import neonyazilim.com.nrm.Models.ProjeIzin;
import neonyazilim.com.nrm.Models.RequestBody;
import neonyazilim.com.nrm.Models.Talep;
import neonyazilim.com.nrm.Models.Unvan;
import neonyazilim.com.nrm.Models.UserRequest;
import neonyazilim.com.nrm.Models.UserResponse;
import neonyazilim.com.nrm.S;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by cihan on 25.10.2017.
 */

public interface Connect {


    /**
     * @param requestBody //kullanıcı id ve token içerir
     *                    <p>
     *                    Talepleri almak için kullanıcı idsi ve güvenlik için token gönderiyoruz
     *                    eğer kullancı oturum açmış ise id ye göre talepler bulunup getirilecek. Oturum açılmamışsa hata mesajı döndürülecek.
     */
    @POST("getTalep" + S.publickey)
    Call<List<Talep>> getTalep(@Body RequestBody requestBody);


    @POST("getTalepler" + S.publickey)
    Call<List<Talep>> getTalepler(@Body RequestBody requestBody);


    @POST("updateTalep" + S.publickey)
    Call<Talep> updateTalep(@Body RequestBody requestBody);

    /**
     * @param userRequest //eposta ve şifre içerir
     *                    Giriş yapma isteği gönderiyoruz ve geriye token değeri alıyoruz.
     *                    Gelen tokeni localde saklıyoruz. Oturum kapatılana kadar bu token localde kayıtlı kalıyor.
     */
    @POST("login" + S.publickey)
    Call<UserResponse> loginRequest(@Body UserRequest userRequest);


    @POST("logout" + S.publickey)
    Call<UserResponse> logout(@Body RequestBody requestBody);

    /**
     * @param userResponse
     * @return Oturum açtığımızda aldığımız token ile kullanıcı bilgilerini alıyoruz.
     */
    @POST("getKullanici" + S.publickey)
    Call<Kullanici> getKullanici(@Body UserResponse userResponse);


    @POST("addUser" + S.publickey)
    Call<Kullanici> sendUser(@Body Kullanici kullanici);


    @GET("getDepartman" + S.publickey)
    Call<List<Departman>> getDepartman();

    @GET("getUnvan" + S.publickey)
    Call<List<Unvan>> getUnvan();

    /**
     * Departman eklemek için kullanılır
     *
     * @param departman başlık ve açıklama içerir
     */
    @POST("departmanEkle" + S.publickey)
    Call<Departman> departmanEkle(@Body Departman departman);


    @POST("unvanEkle" + S.publickey)
    Call<Unvan> unvanEkle(@Body Unvan unvan);

    @POST("talepEkle" + S.publickey)
    Call<Talep> talepGonder(@Body Talep talep);

    @POST("gorevEkle" + S.publickey)
    Call<Gorev> gorevEkle(@Body Gorev gorev);


    @POST("getUser" + S.publickey)
    Call<List<Kullanici>> getUser(@Body RequestBody departman);

    @POST("getUsers" + S.publickey)
    Call<List<Kullanici>> getUsers(@Body RequestBody departman);

    @POST("getUserss" + S.publickey)
    Call<Kullanici> getUserss(@Body RequestBody departman);

    @POST("getUsersss" + S.publickey)
    Call<Kullanici> getUsersss(@Body RequestBody departman);

    @POST("sendProject" + S.publickey)
    Call<Proje> sendProject(@Body Proje proje);

    @POST("getProjeler" + S.publickey)
    Call<List<Proje>> getProjeler(@Body RequestBody requestBody);

    @POST("getGorevler" + S.publickey)
    Call<List<Gorev>> getGorevler(@Body RequestBody requestBody);

    @POST("getGorevlerim" + S.publickey)
    Call<List<Gorev>> getGorevlerim(@Body RequestBody requestBody);

    @POST("getGorevliler" + S.publickey)
    Call<List<Kullanici>> getGorevliler(@Body RequestBody requestBody);

    @POST("updateAdim" + S.publickey)
    Call<Gorev> updateGorev(@Body Gorev gorev);

    @POST("projeyiSil" + S.publickey)
    Call<Proje> projeyiSil(@Body RequestBody requestBody);

    @POST("talebiSil" + S.publickey)
    Call<Talep> talebiSil(@Body RequestBody requestBody);

    @POST("projeyiGuncelle" + S.publickey)
    Call<Proje> projeyiGuncelle(@Body Proje proje);

    @POST("projeyeDepartmanEkle" + S.publickey)
    Call<Proje> projeyeDepartmanEkle(@Body RequestBody requestBody);

    @POST("projeyeSorumluEkle" + S.publickey)
    Call<Proje> projeyeSorumluEkle(@Body RequestBody requestBody);

    @POST("getFilteredTalep" + S.publickey)
    Call<List<Talep>> getFilteredTalep(@Body RequestBody requestBody);


    @POST("sendIzin" + S.publickey)
    Call<Izin> sendIzin(@Body RequestBody requestBody);

    @POST("getIzin" + S.publickey)
    Call<List<Izin>> getIzin(@Body RequestBody requestBody);


    @POST("getProjeIzin"+S.publickey)
    Call<List<ProjeIzin>> getProjeIzin(@Body RequestBody requestBody);

}

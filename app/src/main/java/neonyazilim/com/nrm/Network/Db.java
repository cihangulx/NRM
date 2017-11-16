package neonyazilim.com.nrm.Network;

import neonyazilim.com.nrm.Models.Kullanici;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cihan on 25.10.2017.
 */

public class Db {
public static final String BASE_URL="http://cihangul.org/";

    public static Retrofit getRetrofit(){


        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Connect getConnect(){
        return getRetrofit().create(Connect.class);
    }


}

package neonyazilim.com.nrm.Managers;

import neonyazilim.com.nrm.Models.Kullanici;

/**
 * Created by cihan on 1.11.2017.
 */

public class UserManager {

    static Kullanici kullanici = new Kullanici();

    public static void setKullanici(Kullanici mUser){
       kullanici =mUser;
    }

    public static Kullanici getUser(){
        return kullanici;
    }


}

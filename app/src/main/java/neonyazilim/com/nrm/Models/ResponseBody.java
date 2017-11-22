package neonyazilim.com.nrm.Models;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cihan on 20.11.2017.
 */

public class ResponseBody {



    private String _id;
    private String token;


    private String [] dataSet;


    private List<Proje> projeList; // Response olarak proje listesi aldığımızda kullanılacak değişken.
    private Proje proje; // Response olarak proje aldığımızda kullanılacak değişken.

    private List<Talep> talepList;
    private Talep talep;

    private List<Departman> departmanList; //Response olarak departman listesi aldığımızda kullanılacak değişken
    private Departman departman; // Response olarak departman aldığımızda kullanılacak değişken

    private List<Kullanici> kullaniciList; // Response olarak kullanıcı listesi aldığımnızda kullanılacak değişken
    private Kullanici kullanici; //Respınse olarak kullnıcı aldığımzıda kullanılacak değişken

    private List<Izin> izinList; // Rsponse olarak izin listesi alacağımız zamna kulanılacak değişken;
    private Izin izin; // Response olarak izin aldığımızda kullanıclacka değişken;

    private List<Islem> islemList; // Response olarak işlem listesi aldığımızda kullanılacak değişken
    private Islem islem; // Response olarak işlem alacağımızda kullanılacak değişken

    private List<Gorev> gorevList; // Rsponse olarak görev listesi aldığımızda kullanılacak değişken
    private Gorev gorev; //Response olarak görev aldığımızda kullanılacak değişken


    public ResponseBody(String token, String _id, String[] dataSet, List<Proje> projeList, Proje proje, List<Talep> talepList, Talep talep, List<Departman> departmanList, Departman departman, List<Kullanici> kullaniciList, Kullanici kullanici, List<Izin> izinList, Izin izin, List<Islem> islemList, Islem islem, List<Gorev> gorevList, Gorev gorev) {
        this.token = token;
        this._id = _id;
        this.dataSet = dataSet;
        this.projeList = projeList;
        this.proje = proje;
        this.talepList = talepList;
        this.talep = talep;
        this.departmanList = departmanList;
        this.departman = departman;
        this.kullaniciList = kullaniciList;
        this.kullanici = kullanici;
        this.izinList = izinList;
        this.izin = izin;
        this.islemList = islemList;
        this.islem = islem;
        this.gorevList = gorevList;
        this.gorev = gorev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseBody)) return false;

        ResponseBody that = (ResponseBody) o;

        if (!get_id().equals(that.get_id())) return false;
        if (getToken() != null ? !getToken().equals(that.getToken()) : that.getToken() != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(getDataSet(), that.getDataSet())) return false;
        if (getProjeList() != null ? !getProjeList().equals(that.getProjeList()) : that.getProjeList() != null)
            return false;
        if (getProje() != null ? !getProje().equals(that.getProje()) : that.getProje() != null)
            return false;
        if (getTalepList() != null ? !getTalepList().equals(that.getTalepList()) : that.getTalepList() != null)
            return false;
        if (getTalep() != null ? !getTalep().equals(that.getTalep()) : that.getTalep() != null)
            return false;
        if (getDepartmanList() != null ? !getDepartmanList().equals(that.getDepartmanList()) : that.getDepartmanList() != null)
            return false;
        if (getDepartman() != null ? !getDepartman().equals(that.getDepartman()) : that.getDepartman() != null)
            return false;
        if (getKullaniciList() != null ? !getKullaniciList().equals(that.getKullaniciList()) : that.getKullaniciList() != null)
            return false;
        if (getKullanici() != null ? !getKullanici().equals(that.getKullanici()) : that.getKullanici() != null)
            return false;
        if (getIzinList() != null ? !getIzinList().equals(that.getIzinList()) : that.getIzinList() != null)
            return false;
        if (getIzin() != null ? !getIzin().equals(that.getIzin()) : that.getIzin() != null)
            return false;
        if (getIslemList() != null ? !getIslemList().equals(that.getIslemList()) : that.getIslemList() != null)
            return false;
        if (getIslem() != null ? !getIslem().equals(that.getIslem()) : that.getIslem() != null)
            return false;
        if (getGorevList() != null ? !getGorevList().equals(that.getGorevList()) : that.getGorevList() != null)
            return false;
        return getGorev() != null ? getGorev().equals(that.getGorev()) : that.getGorev() == null;

    }

    @Override
    public int hashCode() {
        int result = get_id().hashCode();
        result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getDataSet());
        result = 31 * result + (getProjeList() != null ? getProjeList().hashCode() : 0);
        result = 31 * result + (getProje() != null ? getProje().hashCode() : 0);
        result = 31 * result + (getTalepList() != null ? getTalepList().hashCode() : 0);
        result = 31 * result + (getTalep() != null ? getTalep().hashCode() : 0);
        result = 31 * result + (getDepartmanList() != null ? getDepartmanList().hashCode() : 0);
        result = 31 * result + (getDepartman() != null ? getDepartman().hashCode() : 0);
        result = 31 * result + (getKullaniciList() != null ? getKullaniciList().hashCode() : 0);
        result = 31 * result + (getKullanici() != null ? getKullanici().hashCode() : 0);
        result = 31 * result + (getIzinList() != null ? getIzinList().hashCode() : 0);
        result = 31 * result + (getIzin() != null ? getIzin().hashCode() : 0);
        result = 31 * result + (getIslemList() != null ? getIslemList().hashCode() : 0);
        result = 31 * result + (getIslem() != null ? getIslem().hashCode() : 0);
        result = 31 * result + (getGorevList() != null ? getGorevList().hashCode() : 0);
        result = 31 * result + (getGorev() != null ? getGorev().hashCode() : 0);
        return result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String[] getDataSet() {
        return dataSet;
    }

    public void setDataSet(String[] dataSet) {
        this.dataSet = dataSet;
    }

    public List<Proje> getProjeList() {
        return projeList;
    }

    public void setProjeList(List<Proje> projeList) {
        this.projeList = projeList;
    }

    public Proje getProje() {
        return proje;
    }

    public void setProje(Proje proje) {
        this.proje = proje;
    }

    public List<Talep> getTalepList() {
        return talepList;
    }

    public void setTalepList(List<Talep> talepList) {
        this.talepList = talepList;
    }

    public Talep getTalep() {
        return talep;
    }

    public void setTalep(Talep talep) {
        this.talep = talep;
    }

    public List<Departman> getDepartmanList() {
        return departmanList;
    }

    public void setDepartmanList(List<Departman> departmanList) {
        this.departmanList = departmanList;
    }

    public Departman getDepartman() {
        return departman;
    }

    public void setDepartman(Departman departman) {
        this.departman = departman;
    }

    public List<Kullanici> getKullaniciList() {
        return kullaniciList;
    }

    public void setKullaniciList(List<Kullanici> kullaniciList) {
        this.kullaniciList = kullaniciList;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public List<Izin> getIzinList() {
        return izinList;
    }

    public void setIzinList(List<Izin> izinList) {
        this.izinList = izinList;
    }

    public Izin getIzin() {
        return izin;
    }

    public void setIzin(Izin izin) {
        this.izin = izin;
    }

    public List<Islem> getIslemList() {
        return islemList;
    }

    public void setIslemList(List<Islem> islemList) {
        this.islemList = islemList;
    }

    public Islem getIslem() {
        return islem;
    }

    public void setIslem(Islem islem) {
        this.islem = islem;
    }

    public List<Gorev> getGorevList() {
        return gorevList;
    }

    public void setGorevList(List<Gorev> gorevList) {
        this.gorevList = gorevList;
    }

    public Gorev getGorev() {
        return gorev;
    }

    public void setGorev(Gorev gorev) {
        this.gorev = gorev;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

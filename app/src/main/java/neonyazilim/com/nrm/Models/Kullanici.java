package neonyazilim.com.nrm.Models;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by cihan on 24.10.2017.
 */

public class Kullanici {

    //Kişisel bilgiler
    private String _id;
    private String isim;
    private String soyIsim;
    private String ePosta;
    private String sifre;
    private String adres;
    private String telefon;
    private String sehir;
    private String resim;
    private String token;
    //
    private String departman; //Departman id si
    private String unvan;

    //İzinler
    private boolean talepAcabilir;//
    private boolean projeAcabilir;//
    private boolean sorumluEkleyebilir;//
    private boolean gorevliEkleyebilir;
    private boolean gorevEkleyebilir;//

    private boolean talepSilebilir; //
    private boolean projeSilebilir;//
    private boolean sorumluSilebilir;//
    private boolean gorevliSilebilir;
    private boolean gorevSilebilir;//

    private boolean kullaniciEkleyebilir;
    private boolean kullaniciSilebilir;
    private boolean kullaniciDuzenleyebilir;

    private boolean departmanEkleyebilir;
    private boolean departmanSilebilir;

    private boolean superVisor;

    public Kullanici() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public Kullanici(String isim, String soyIsim, String ePosta, String adres, String telefon, String departman, String unvan, boolean talepAcabilir, boolean projeAcabilir, boolean sorumluEkleyebilir, boolean gorevliEkleyebilir, boolean gorevEkleyebilir, boolean talepSilebilir, boolean projeSilebilir, boolean sorumluSilebilir, boolean gorevliSilebilir, boolean gorevSilebilir, boolean kullaniciEkleyebilir, boolean kullaniciSilebilir, boolean kullaniciDuzenleyebilir, boolean departmanEkleyebilir, boolean departmanSilebilir, boolean superVisor) {
        this.isim = isim;
        this.soyIsim = soyIsim;
        this.ePosta = ePosta;
        this.adres = adres;
        this.telefon = telefon;
        this.departman = departman;
        this.unvan = unvan;
        this.talepAcabilir = talepAcabilir;
        this.projeAcabilir = projeAcabilir;
        this.sorumluEkleyebilir = sorumluEkleyebilir;
        this.gorevliEkleyebilir = gorevliEkleyebilir;
        this.gorevEkleyebilir = gorevEkleyebilir;
        this.talepSilebilir = talepSilebilir;
        this.projeSilebilir = projeSilebilir;
        this.sorumluSilebilir = sorumluSilebilir;
        this.gorevliSilebilir = gorevliSilebilir;
        this.gorevSilebilir = gorevSilebilir;
        this.kullaniciEkleyebilir = kullaniciEkleyebilir;
        this.kullaniciSilebilir = kullaniciSilebilir;
        this.kullaniciDuzenleyebilir = kullaniciDuzenleyebilir;
        this.departmanEkleyebilir = departmanEkleyebilir;
        this.departmanSilebilir = departmanSilebilir;
        this.superVisor = superVisor;
    }

    public String getId() {
        return _id;
    }

    public Kullanici setId(String id) {
        this._id = id;
        return this;
    }

    public String getIsim() {
        return isim;
    }

    public Kullanici setIsim(String isim) {
        this.isim = isim;
        return this;
    }

    public String getSoyIsim() {
        return soyIsim;
    }

    public Kullanici setSoyIsim(String soyIsim) {
        this.soyIsim = soyIsim;
        return this;
    }

    public String getePosta() {
        return ePosta;
    }

    public Kullanici setePosta(String ePosta) {
        this.ePosta = ePosta;
        return this;
    }

    public String getAdres() {
        return adres;
    }

    public Kullanici setAdres(String adres) {
        this.adres = adres;
        return this;
    }

    public String getTelefon() {
        return telefon;
    }

    public Kullanici setTelefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public String getSehir() {
        return sehir;
    }

    public Kullanici setSehir(String sehir) {
        this.sehir = sehir;
        return this;
    }

    public String getResim() {
        return resim;
    }

    public Kullanici setResim(String resim) {
        this.resim = resim;
        return this;
    }

    public String getDepartman() {
        return departman;
    }

    public Kullanici setDepartman(String departman) {
        this.departman = departman;
        return this;
    }

    public String getUnvan() {
        return unvan;
    }

    public Kullanici setUnvan(String unvan) {
        this.unvan = unvan;
        return this;
    }

    public boolean isTalepAcabilir() {
        return talepAcabilir;
    }

    public Kullanici setTalepAcabilir(boolean talepAcabilir) {
        this.talepAcabilir = talepAcabilir;
        return this;
    }

    public boolean isProjeAcabilir() {
        return projeAcabilir;
    }

    public Kullanici setProjeAcabilir(boolean projeAcabilir) {
        this.projeAcabilir = projeAcabilir;
        return this;
    }

    public boolean isSorumluEkleyebilir() {
        return sorumluEkleyebilir;
    }

    public Kullanici setSorumluEkleyebilir(boolean sorumluEkleyebilir) {
        this.sorumluEkleyebilir = sorumluEkleyebilir;
        return this;
    }

    public boolean isGorevliEkleyebilir() {
        return gorevliEkleyebilir;
    }

    public Kullanici setGorevliEkleyebilir(boolean gorevliEkleyebilir) {
        this.gorevliEkleyebilir = gorevliEkleyebilir;
        return this;
    }

    public boolean isGorevEkleyebilir() {
        return gorevEkleyebilir;
    }

    public Kullanici setGorevEkleyebilir(boolean gorevEkleyebilir) {
        this.gorevEkleyebilir = gorevEkleyebilir;
        return this;
    }

    public boolean isTalepSilebilir() {
        return talepSilebilir;
    }

    public Kullanici setTalepSilebilir(boolean talepSilebilir) {
        this.talepSilebilir = talepSilebilir;
        return this;
    }

    public boolean isProjeSilebilir() {
        return projeSilebilir;
    }

    public Kullanici setProjeSilebilir(boolean projeSilebilir) {
        this.projeSilebilir = projeSilebilir;
        return this;
    }

    public boolean isSorumluSilebilir() {
        return sorumluSilebilir;
    }

    public Kullanici setSorumluSilebilir(boolean sorumluSilebilir) {
        this.sorumluSilebilir = sorumluSilebilir;
        return this;
    }

    public boolean isGorevliSilebilir() {
        return gorevliSilebilir;
    }

    public Kullanici setGorevliSilebilir(boolean gorevliSilebilir) {
        this.gorevliSilebilir = gorevliSilebilir;
        return this;
    }

    public boolean isGorevSilebilir() {
        return gorevSilebilir;
    }

    public Kullanici setGorevSilebilir(boolean gorevSilebilir) {
        this.gorevSilebilir = gorevSilebilir;
        return this;
    }

    public boolean isKullaniciEkleyebilir() {
        return kullaniciEkleyebilir;
    }

    public Kullanici setKullaniciEkleyebilir(boolean kullaniciEkleyebilir) {
        this.kullaniciEkleyebilir = kullaniciEkleyebilir;
        return this;
    }

    public boolean isKullaniciSilebilir() {
        return kullaniciSilebilir;
    }

    public Kullanici setKullaniciSilebilir(boolean kullaniciSilebilir) {
        this.kullaniciSilebilir = kullaniciSilebilir;
        return this;
    }

    public boolean isKullaniciDuzenleyebilir() {
        return kullaniciDuzenleyebilir;
    }

    public Kullanici setKullaniciDuzenleyebilir(boolean kullaniciDuzenleyebilir) {
        this.kullaniciDuzenleyebilir = kullaniciDuzenleyebilir;
        return this;
    }

    public boolean isDepartmanEkleyebilir() {
        return departmanEkleyebilir;
    }

    public Kullanici setDepartmanEkleyebilir(boolean departmanEkleyebilir) {
        this.departmanEkleyebilir = departmanEkleyebilir;
        return this;
    }

    public boolean isDepartmanSilebilir() {
        return departmanSilebilir;
    }

    public Kullanici setDepartmanSilebilir(boolean departmanSilebilir) {
        this.departmanSilebilir = departmanSilebilir;
        return this;

    }

    public boolean isSuperVisor() {
        return superVisor;
    }

    public Kullanici setSuperVisor(boolean superVisor) {
        this.superVisor = superVisor;
        return this;
    }

    @Override
    public String toString() {
       return  new Gson().toJson(this);
    }
}

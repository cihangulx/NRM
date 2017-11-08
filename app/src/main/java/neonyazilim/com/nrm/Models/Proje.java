package neonyazilim.com.nrm.Models;

import java.util.Date;
import java.util.List;

/**
 * Created by cihan on 26.10.2017.
 */

public class Proje {

    private String _id;
    private String baslik;
    private String aciklama;
    private String gonderen;// Gönderen id
    private String [] gorevler;
    private String [] gorevliler;
    private List<Kullanici> sorumluList;
    private Date tarih;
    private String [] sorumlular;
    private String token;
    private int progress;
    private String [] departmanlar;



    public Proje() {
    }

    public Proje(String id, String baslik, String aciklama, String gonderen, String[] gorevler, String[] gorevliler, Date tarih, String[] sorumlular, String token, int progress, String[] departmanlar) {
        this._id = id;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.gonderen = gonderen;
        this.gorevler = gorevler;
        this.gorevliler = gorevliler;
        this.tarih = tarih;
        this.sorumlular = sorumlular;
        this.token = token;
        this.progress = progress;
        this.departmanlar = departmanlar;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getGonderen() {
        return gonderen;
    }

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public String[] getGorevler() {
        return gorevler;
    }

    public void setGorevler(String[] gorevler) {
        this.gorevler = gorevler;
    }

    public String[] getGorevliler() {
        return gorevliler;
    }

    public void setGorevliler(String[] gorevliler) {
        this.gorevliler = gorevliler;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public String[] getSorumlular() {
        return sorumlular;
    }

    public void setSorumlular(String[] sorumlular) {
        this.sorumlular = sorumlular;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String[] getDepartmanlar() {
        return departmanlar;
    }

    public void setDepartmanlar(String[] departmanlar) {
        this.departmanlar = departmanlar;
    }

    public List<Kullanici> getSorumluList() {
        return sorumluList;
    }

    public void setSorumluList(List<Kullanici> sorumluList) {
        this.sorumluList = sorumluList;
    }
}

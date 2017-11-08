package neonyazilim.com.nrm.Models;

import java.util.List;

/**
 * Created by tuzlabim on 3.11.2017.
 */

public class Gorev {

    private String _id;
    private String baslik;
    private String aciklama;
    private String gonderen;// Gönderen id
    private String [] gorevli;
    private String tarih;
    private String token;
    private List<Adim> adimlar;
    private String proje;
    private int progress;

    public Gorev() {
    }
    public Gorev(String id, String baslik, String aciklama, String gonderen, String[] gorevli, String tarih, String token, int progress) {
        this._id = id;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.gonderen = gonderen;
        this.gorevli = gorevli;
        this.tarih = tarih;
        this.token = token;
        this.progress = progress;
    }


    public String getProje() {
        return proje;
    }

    public void setProje(String proje) {
        this.proje = proje;
    }

    public List<Adim> getAdimlar() {
        return adimlar;
    }

    public void setAdimlar(List<Adim> adimlar) {
        this.adimlar = adimlar;
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

    public String[] getGorevli() {
        return gorevli;
    }

    public void setGorevli(String[] gorevli) {
        this.gorevli = gorevli;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
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
}

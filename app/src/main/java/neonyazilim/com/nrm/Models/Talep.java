package neonyazilim.com.nrm.Models;

import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

/**
 * Created by cihan on 26.10.2017.
 */


public class Talep {
    private String _id;
    private String baslik;
    private String aciklama;
    private String gonderen;// Gönderen id
    private String alici; //alıcı id
    private Date tarih;
    private String durum;
    private String departman;
    private List<Islem> islemler;

    /**
     *
     * @param baslik
     * @param aciklama
     * @param gonderen
     * @param alici
     * @param tarih
     */
    public Talep(String baslik, String aciklama, String gonderen, String alici, Date tarih) {
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.gonderen = gonderen;
        this.alici = alici;
        this.tarih = tarih;
    }

    public Talep() {
    }

    public List<Islem> getIslemler() {
        return islemler;
    }

    public void setIslemler(List<Islem> islemler) {
        this.islemler = islemler;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
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

    public String getAlici() {
        return alici;
    }

    public void setAlici(String alici) {
        this.alici = alici;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

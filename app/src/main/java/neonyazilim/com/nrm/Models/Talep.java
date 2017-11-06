package neonyazilim.com.nrm.Models;

import java.util.Date;

/**
 * Created by cihan on 26.10.2017.
 */


public class Talep {
    private String id;
    private String baslik;
    private String aciklama;
    private String gonderen;// Gönderen id
    private String alici; //alıcı id
    private Date tarih;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}

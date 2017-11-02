package neonyazilim.com.nrm.Models;

/**
 * Created by cihan on 30.10.2017.
 */

public class Departman {

    private String _id;
    private String baslik;
    private String aciklama;

    public Departman() {
    }

    public Departman(String baslik, String aciklama) {
        this.baslik = baslik;
        this.aciklama = aciklama;
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
}

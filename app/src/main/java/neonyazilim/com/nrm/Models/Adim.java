package neonyazilim.com.nrm.Models;

/**
 * Created by tuzlabim on 3.11.2017.
 */

public class Adim {
    private String _id;
    private String baslik;
    private boolean bitti;

    public Adim(String _id, String baslik, boolean bitti) {
        this._id = _id;
        this.baslik = baslik;
        this.bitti = bitti;

    }

    public Adim() {
    }

    public Adim(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public boolean isBitti() {
        return bitti;
    }

    public void setBitti(boolean bitti) {
        this.bitti = bitti;
    }
}

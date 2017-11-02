package neonyazilim.com.nrm.Models;

/**
 * Created by tuzlabim on 27.10.2017.
 */

public class UserRequest {
    private String ePosta;
    private String sifre;

    public UserRequest(String ePosta, String sifre) {
        this.ePosta = ePosta;
        this.sifre = sifre;
    }

    public String getePosta() {
        return ePosta;
    }

    public void setePosta(String ePosta) {
        this.ePosta = ePosta;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}

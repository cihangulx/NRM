package neonyazilim.com.nrm.Models;

import neonyazilim.com.nrm.S;

/**
 * Created by cihan on 20.11.2017.
 */

public class TalepIzin {

    private String talepId; //İzinlerin geçerli olduğu talep

    private String goruntule; // Talebi ve işlemleri görüntüleme izni
    private String sonuclandir; // Talebi sonuçlandırma izni
    private String reddet; // talebi reddetme izni
    private String duzenlemeIste; //Talep gonerene düzeltme talebinde bulunma izni
    private String talebiSil; // talebi silme izni



    public TalepIzin(String talepId, String goruntule, String sonuclandir, String reddet, String duzenlemeIste, String talebiSil) {
        this.talepId = talepId;
        this.goruntule = goruntule;
        this.sonuclandir = sonuclandir;
        this.reddet = reddet;
        this.duzenlemeIste = duzenlemeIste;
        this.talebiSil = talebiSil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TalepIzin)) return false;

        TalepIzin talepIzin = (TalepIzin) o;

        if (!getTalepId().equals(talepIzin.getTalepId())) return false;
        if (getGoruntule() != null ? !getGoruntule().equals(talepIzin.getGoruntule()) : talepIzin.getGoruntule() != null)
            return false;
        if (getSonuclandir() != null ? !getSonuclandir().equals(talepIzin.getSonuclandir()) : talepIzin.getSonuclandir() != null)
            return false;
        if (getReddet() != null ? !getReddet().equals(talepIzin.getReddet()) : talepIzin.getReddet() != null)
            return false;
        if (getDuzenlemeIste() != null ? !getDuzenlemeIste().equals(talepIzin.getDuzenlemeIste()) : talepIzin.getDuzenlemeIste() != null)
            return false;
        return getTalebiSil() != null ? getTalebiSil().equals(talepIzin.getTalebiSil()) : talepIzin.getTalebiSil() == null;

    }

    @Override
    public int hashCode() {
        int result = getTalepId().hashCode();
        result = 31 * result + (getGoruntule() != null ? getGoruntule().hashCode() : 0);
        result = 31 * result + (getSonuclandir() != null ? getSonuclandir().hashCode() : 0);
        result = 31 * result + (getReddet() != null ? getReddet().hashCode() : 0);
        result = 31 * result + (getDuzenlemeIste() != null ? getDuzenlemeIste().hashCode() : 0);
        result = 31 * result + (getTalebiSil() != null ? getTalebiSil().hashCode() : 0);
        return result;
    }

    public String getTalepId() {
        return talepId;
    }

    public void setTalepId(String talepId) {
        this.talepId = talepId;
    }

    public String getGoruntule() {
        return goruntule;
    }

    public void setGoruntule(String goruntule) {
        this.goruntule = goruntule;
    }

    public String getSonuclandir() {
        return sonuclandir;
    }

    public void setSonuclandir(String sonuclandir) {
        this.sonuclandir = sonuclandir;
    }

    public String getReddet() {
        return reddet;
    }

    public void setReddet(String reddet) {
        this.reddet = reddet;
    }

    public String getDuzenlemeIste() {
        return duzenlemeIste;
    }

    public void setDuzenlemeIste(String duzenlemeIste) {
        this.duzenlemeIste = duzenlemeIste;
    }

    public String getTalebiSil() {
        return talebiSil;
    }

    public void setTalebiSil(String talebiSil) {
        this.talebiSil = talebiSil;
    }
}

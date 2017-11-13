package neonyazilim.com.nrm.Models;

import java.util.Date;

/**
 * Created by cihan on 13.11.2017.
 */

public class Islem {


    private String islem;
    private String islemiYapan;
    private String islemOncesiDurum;
    private Date islemTarihi;
    private String islemAciklamasi;

    public Islem() {
    }

    public Islem(String islem) {
        this.islem = islem;
    }

    public String getIslemAciklamasi() {
        return islemAciklamasi;
    }

    public void setIslemAciklamasi(String islemAciklamasi) {
        this.islemAciklamasi = islemAciklamasi;
    }

    public String getIslem() {
        return islem;
    }

    public void setIslem(String islem) {
        this.islem = islem;
    }

    public String getIslemiYapan() {
        return islemiYapan;
    }

    public void setIslemiYapan(String islemiYapan) {
        this.islemiYapan = islemiYapan;
    }

    public String getIslemOncesiDurum() {
        return islemOncesiDurum;
    }

    public void setIslemOncesiDurum(String islemOncesiDurum) {
        this.islemOncesiDurum = islemOncesiDurum;
    }

    public Date getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(Date islemTarihi) {
        this.islemTarihi = islemTarihi;
    }
}

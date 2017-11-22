package neonyazilim.com.nrm.Models;

import java.util.List;

/**
 * Created by cihan on 26.10.2017.
 */

public class RequestBody {
    private String _id;
    private String token;
    private String durum;
    private String [] filtreler;
    private List<Islem> islem;
    private Izin izin;
    private ProjeIzin projeIzin;

    private String [] departmanList;

    public RequestBody(String userId, String token) {
        this._id = userId;
        this.token = token;
    }

    public RequestBody() {
    }

    public String[] getFiltreler() {
        return filtreler;
    }

    public ProjeIzin getProjeIzin() {
        return projeIzin;
    }

    public void setProjeIzin(ProjeIzin projeIzin) {
        this.projeIzin = projeIzin;
    }

    public void setFiltreler(String[] filtreler) {
        this.filtreler = filtreler;
    }

    public List<Islem> getIslem() {
        return islem;
    }

    public void setIslem(List<Islem> islem) {
        this.islem = islem;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getUserId() {
        return _id;
    }

    public void setUserId(String userId) {
        this._id = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String[] getDepartmanList() {
        return departmanList;
    }

    public void setDepartmanList(String[] departmanList) {
        this.departmanList = departmanList;
    }

    public Izin getIzin() {
        return izin;
    }

    public void setIzin(Izin izin) {
        this.izin = izin;
    }
}

package neonyazilim.com.nrm.Models;

import java.util.List;

/**
 * Created by cihan on 26.10.2017.
 */

public class RequestBody {
    private String _id;
    private String token;
    private String durum;

    private List<Islem> islem;

    private String [] departmanList;

    public RequestBody(String userId, String token) {
        this._id = userId;
        this.token = token;
    }

    public RequestBody() {
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
}

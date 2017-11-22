package neonyazilim.com.nrm.Models;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cihan on 20.11.2017.
 */

public class Izin {
    private String _id; //izin idsi
    private String userId; // izin sahibinin id si
    private List<ProjeIzin> projeIzinList;
    private List<TalepIzin> talepIzinList;

    String [] departmanList; // Talepleri görüntülenecek departmanların idleri

    private boolean editor;
    private boolean yonetici;
    private boolean superVisor;

    public Izin(String _id, String userId, List<ProjeIzin> projeIzinList, List<TalepIzin> talepIzinList, String[] departmanList, boolean editor, boolean yonetici, boolean superVisor) {
        this._id = _id;
        this.userId = userId;
        this.projeIzinList = projeIzinList;
        this.talepIzinList = talepIzinList;
        this.departmanList = departmanList;
        this.editor = editor;
        this.yonetici = yonetici;
        this.superVisor = superVisor;
    }

    public Izin() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Izin)) return false;

        Izin izin = (Izin) o;

        if (isEditor() != izin.isEditor()) return false;
        if (isYonetici() != izin.isYonetici()) return false;
        if (isSuperVisor() != izin.isSuperVisor()) return false;
        if (!get_id().equals(izin.get_id())) return false;
        if (!getUserId().equals(izin.getUserId())) return false;
        if (getProjeIzinList() != null ? !getProjeIzinList().equals(izin.getProjeIzinList()) : izin.getProjeIzinList() != null)
            return false;
        if (getTalepIzinList() != null ? !getTalepIzinList().equals(izin.getTalepIzinList()) : izin.getTalepIzinList() != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getDepartmanList(), izin.getDepartmanList());

    }

    @Override
    public int hashCode() {
        int result = get_id().hashCode();
        result = 31 * result + getUserId().hashCode();
        result = 31 * result + (getProjeIzinList() != null ? getProjeIzinList().hashCode() : 0);
        result = 31 * result + (getTalepIzinList() != null ? getTalepIzinList().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getDepartmanList());
        result = 31 * result + (isEditor() ? 1 : 0);
        result = 31 * result + (isYonetici() ? 1 : 0);
        result = 31 * result + (isSuperVisor() ? 1 : 0);
        return result;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProjeIzin> getProjeIzinList() {
        return projeIzinList;
    }

    public void setProjeIzinList(List<ProjeIzin> projeIzinList) {
        this.projeIzinList = projeIzinList;
    }

    public List<TalepIzin> getTalepIzinList() {
        return talepIzinList;
    }

    public void setTalepIzinList(List<TalepIzin> talepIzinList) {
        this.talepIzinList = talepIzinList;
    }

    public String[] getDepartmanList() {
        return departmanList;
    }

    public void setDepartmanList(String[] departmanList) {
        this.departmanList = departmanList;
    }

    public boolean isEditor() {
        return editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public boolean isYonetici() {
        return yonetici;
    }

    public void setYonetici(boolean yonetici) {
        this.yonetici = yonetici;
    }

    public boolean isSuperVisor() {
        return superVisor;
    }

    public void setSuperVisor(boolean superVisor) {
        this.superVisor = superVisor;
    }

    @Override
    public String toString() {
        return  new Gson().toJson(this);
    }

}

package neonyazilim.com.nrm.Models;

/**
 * Created by cihan on 20.11.2017.
 */




public class ProjeIzin {

    private String projeId; /// izinlerin geçerli olduğu proje id si
    private String userId;

    private boolean goruntule; /// projeyi görüntüleme izni
    private boolean projeyiDuzenle; // Proje başlığı ve açıklamasını değiştirme izni

    private boolean departmanEkle; /// projeye departman ekleme izni
    private boolean departmanSil; /// projede var olan departmanı kaldırma izni

    private boolean gorevEkle;   /// projeye gorev ekleme izni
    private boolean gorevSil;   /// projede var olan gorevi silme yetkisi

    private boolean sorumluEkle;
    private boolean sorumluSil;




    public ProjeIzin() {
    }

    public ProjeIzin(String projeId, boolean goruntule, boolean projeyiDuzenle, boolean departmanEkle, boolean departmanSil, boolean gorevEkle, boolean gorevSil) {
        this.projeId = projeId;
        this.goruntule = goruntule;
        this.projeyiDuzenle = projeyiDuzenle;
        this.departmanEkle = departmanEkle;
        this.departmanSil = departmanSil;
        this.gorevEkle = gorevEkle;
        this.gorevSil = gorevSil;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isSorumluEkle() {
        return sorumluEkle;
    }

    public void setSorumluEkle(boolean sorumluEkle) {
        this.sorumluEkle = sorumluEkle;
    }

    public boolean isSorumluSil() {
        return sorumluSil;
    }

    public void setSorumluSil(boolean sorumluSil) {
        this.sorumluSil = sorumluSil;
    }

    public String getProjeId() {
        return projeId;
    }

    public void setProjeId(String projeId) {
        this.projeId = projeId;
    }

    public boolean isGoruntule() {
        return goruntule;
    }

    public void setGoruntule(boolean goruntule) {
        this.goruntule = goruntule;
    }

    public boolean isProjeyiDuzenle() {
        return projeyiDuzenle;
    }

    public void setProjeyiDuzenle(boolean projeyiDuzenle) {
        this.projeyiDuzenle = projeyiDuzenle;
    }

    public boolean isDepartmanEkle() {
        return departmanEkle;
    }

    public void setDepartmanEkle(boolean departmanEkle) {
        this.departmanEkle = departmanEkle;
    }

    public boolean isDepartmanSil() {
        return departmanSil;
    }

    public void setDepartmanSil(boolean departmanSil) {
        this.departmanSil = departmanSil;
    }

    public boolean isGorevEkle() {
        return gorevEkle;
    }

    public void setGorevEkle(boolean gorevEkle) {
        this.gorevEkle = gorevEkle;
    }

    public boolean isGorevSil() {
        return gorevSil;
    }

    public void setGorevSil(boolean gorevSil) {
        this.gorevSil = gorevSil;
    }
}

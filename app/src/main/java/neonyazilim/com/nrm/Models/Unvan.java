package neonyazilim.com.nrm.Models;

/**
 * Created by cihan on 30.10.2017.
 */

public class Unvan {

    private String baslik;
    private String aciklama;

    private boolean talepAcabilir;//
    private boolean projeAcabilir;//
    private boolean sorumluEkleyebilir;//
    private boolean gorevliEkleyebilir;
    private boolean gorevEkleyebilir;//

    private boolean talepSilebilir; //
    private boolean projeSilebilir;//
    private boolean sorumluSilebilir;//
    private boolean gorevliSilebilir;
    private boolean gorevSilebilir;//

    private boolean kullaniciEkleyebilir;
    private boolean kullaniciSilebilir;
    private boolean kullaniciDuzenleyebilir;

    private boolean departmanEkleyebilir;
    private boolean departmanSilebilir;

    private boolean superVisor;

    public Unvan(String baslik, String aciklama,
                 boolean talepAcabilir,
                 boolean projeAcabilir,
                 boolean sorumluEkleyebilir,
                 boolean gorevliEkleyebilir,
                 boolean gorevEkleyebilir,
                 boolean talepSilebilir,
                 boolean projeSilebilir,
                 boolean sorumluSilebilir,
                 boolean gorevliSilebilir,
                 boolean gorevSilebilir) {
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.talepAcabilir = talepAcabilir;
        this.projeAcabilir = projeAcabilir;
        this.sorumluEkleyebilir = sorumluEkleyebilir;
        this.gorevliEkleyebilir = gorevliEkleyebilir;
        this.gorevEkleyebilir = gorevEkleyebilir;
        this.talepSilebilir = talepSilebilir;
        this.projeSilebilir = projeSilebilir;
        this.sorumluSilebilir = sorumluSilebilir;
        this.gorevliSilebilir = gorevliSilebilir;
        this.gorevSilebilir = gorevSilebilir;
    }

    public String getBaslik() {
        return baslik;
    }

    public Unvan setBaslik(String baslik) {
        this.baslik = baslik;
        return this;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Unvan setAciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public boolean isTalepAcabilir() {
        return talepAcabilir;
    }

    public Unvan setTalepAcabilir(boolean talepAcabilir) {
        this.talepAcabilir = talepAcabilir;
        return this;
    }

    public boolean isProjeAcabilir() {
        return projeAcabilir;
    }

    public Unvan setProjeAcabilir(boolean projeAcabilir) {
        this.projeAcabilir = projeAcabilir;
        return this;
    }

    public boolean isSorumluEkleyebilir() {
        return sorumluEkleyebilir;
    }

    public Unvan setSorumluEkleyebilir(boolean sorumluEkleyebilir) {
        this.sorumluEkleyebilir = sorumluEkleyebilir;
        return this;
    }

    public boolean isGorevliEkleyebilir() {
        return gorevliEkleyebilir;
    }

    public Unvan setGorevliEkleyebilir(boolean gorevliEkleyebilir) {
        this.gorevliEkleyebilir = gorevliEkleyebilir;
        return this;
    }

    public boolean isGorevEkleyebilir() {
        return gorevEkleyebilir;
    }

    public Unvan setGorevEkleyebilir(boolean gorevEkleyebilir) {
        this.gorevEkleyebilir = gorevEkleyebilir;
        return this;
    }

    public boolean isTalepSilebilir() {
        return talepSilebilir;
    }

    public Unvan setTalepSilebilir(boolean talepSilebilir) {
        this.talepSilebilir = talepSilebilir;
        return this;
    }

    public boolean isProjeSilebilir() {
        return projeSilebilir;
    }

    public Unvan setProjeSilebilir(boolean projeSilebilir) {
        this.projeSilebilir = projeSilebilir;
        return this;
    }

    public boolean isSorumluSilebilir() {
        return sorumluSilebilir;
    }

    public Unvan setSorumluSilebilir(boolean sorumluSilebilir) {
        this.sorumluSilebilir = sorumluSilebilir;
        return this;
    }

    public boolean isGorevliSilebilir() {
        return gorevliSilebilir;
    }

    public Unvan setGorevliSilebilir(boolean gorevliSilebilir) {
        this.gorevliSilebilir = gorevliSilebilir;
        return this;
    }

    public boolean isGorevSilebilir() {
        return gorevSilebilir;
    }

    public Unvan setGorevSilebilir(boolean gorevSilebilir) {
        this.gorevSilebilir = gorevSilebilir;
        return this;
    }

    public boolean isKullaniciEkleyebilir() {
        return kullaniciEkleyebilir;
    }

    public Unvan setKullaniciEkleyebilir(boolean kullaniciEkleyebilir) {
        this.kullaniciEkleyebilir = kullaniciEkleyebilir;
        return this;
    }

    public boolean isKullaniciSilebilir() {
        return kullaniciSilebilir;
    }

    public Unvan setKullaniciSilebilir(boolean kullaniciSilebilir) {
        this.kullaniciSilebilir = kullaniciSilebilir;
        return this;
    }

    public boolean isKullaniciDuzenleyebilir() {
        return kullaniciDuzenleyebilir;
    }

    public Unvan setKullaniciDuzenleyebilir(boolean kullaniciDuzenleyebilir) {
        this.kullaniciDuzenleyebilir = kullaniciDuzenleyebilir;
        return this;
    }

    public boolean isDepartmanEkleyebilir() {
        return departmanEkleyebilir;
    }

    public Unvan setDepartmanEkleyebilir(boolean departmanEkleyebilir) {
        this.departmanEkleyebilir = departmanEkleyebilir;
        return this;
    }

    public boolean isDepartmanSilebilir() {
        return departmanSilebilir;
    }

    public Unvan setDepartmanSilebilir(boolean departmanSilebilir) {
        this.departmanSilebilir = departmanSilebilir;
        return this;
    }

    public boolean isSuperVisor() {
        return superVisor;
    }

    public Unvan setSuperVisor(boolean superVisor) {
        this.superVisor = superVisor;
        return this;
    }
}

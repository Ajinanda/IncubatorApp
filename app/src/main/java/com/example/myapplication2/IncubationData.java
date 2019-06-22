package com.example.myapplication2;

/**
 * <h1>Incubation Data</h1>
 * class ini dibuat untuk mengambil dan mengirim data inkubasi yang sedang berjalan dari/ke database firebase
 *
 * @author Ajinanda Nugraha Putra
 * @version 0.1 (alpha)
 * @since 2019-6-22
 */
public class IncubationData {
    public String namaInkubasi;
    public String tanggalInkubasi;
    public String profilUnggas;
    public int jumlahTelur;

    public IncubationData(String namaInkubasi, String tanggalInkubasi, String profilUnggas, int jumlahTelur) {
        this.namaInkubasi = namaInkubasi;
        this.tanggalInkubasi = tanggalInkubasi;
        this.profilUnggas = profilUnggas;
        this.jumlahTelur = jumlahTelur;
    }

    public String getNamaInkubasi() {
        return namaInkubasi;
    }

    public void setNamaInkubasi(String namaInkubasi) {
        this.namaInkubasi = namaInkubasi;
    }

    public String getTanggalInkubasi() {
        return tanggalInkubasi;
    }

    public void setTanggalInkubasi(String tanggalInkubasi) {
        this.tanggalInkubasi = tanggalInkubasi;
    }

    public String getProfilUnggas() {
        return profilUnggas;
    }

    public void setProfilUnggas(String profilUnggas) {
        this.profilUnggas = profilUnggas;
    }

    public int getJumlahTelur() {
        return jumlahTelur;
    }

    public void setJumlahTelur(int jumlahTelur) {
        this.jumlahTelur = jumlahTelur;
    }

    public IncubationData() {
    }
}

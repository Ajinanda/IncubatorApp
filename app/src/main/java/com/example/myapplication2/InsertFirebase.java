package com.example.myapplication2;

public class InsertFirebase {
    public String namaInkubasi;
    public String jenisUnggas;
    public String tanggalMulaiInkubasi;
    public String tanggalAkhirInkubasi;
    public String jumlahTelur;
    public String menetas;
    public String gagal;
    public String masaInkubasi;

    public InsertFirebase(String namaInkubasi, String jenisUnggas, String tanggalMulaiInkubasi, String tanggalAkhirInkubasi, String jumlahTelur, String masaInkubasi) {
        this.namaInkubasi = namaInkubasi;
        this.jenisUnggas = jenisUnggas;
        this.tanggalMulaiInkubasi = tanggalMulaiInkubasi;
        this.tanggalAkhirInkubasi = tanggalAkhirInkubasi;
        this.jumlahTelur = jumlahTelur;
        this.masaInkubasi = masaInkubasi;
    }

    public String getNamaInkubasi() {
        return namaInkubasi;
    }

    public void setNamaInkubasi(String namaInkubasi) {
        this.namaInkubasi = namaInkubasi;
    }

    public String getJenisUnggas() {
        return jenisUnggas;
    }

    public void setJenisUnggas(String jenisUnggas) {
        this.jenisUnggas = jenisUnggas;
    }

    public String getTanggalMulaiInkubasi() {
        return tanggalMulaiInkubasi;
    }

    public void setTanggalMulaiInkubasi(String tanggalMulaiInkubasi) {
        this.tanggalMulaiInkubasi = tanggalMulaiInkubasi;
    }

    public String getTanggalAkhirInkubasi() {
        return tanggalAkhirInkubasi;
    }

    public void setTanggalAkhirInkubasi(String tanggalAkhirInkubasi) {
        this.tanggalAkhirInkubasi = tanggalAkhirInkubasi;
    }

    public String getJumlahTelur() {
        return jumlahTelur;
    }

    public void setJumlahTelur(String jumlahTelur) {
        this.jumlahTelur = jumlahTelur;
    }

    public String getMenetas() {
        return menetas;
    }

    public void setMenetas(String menetas) {
        this.menetas = menetas;
    }

    public String getGagal() {
        return gagal;
    }

    public void setGagal(String gagal) {
        this.gagal = gagal;
    }

    public String getMasaInkubasi() {
        return masaInkubasi;
    }

    public void setMasaInkubasi(String masaInkubasi) {
        this.masaInkubasi = masaInkubasi;
    }

    public InsertFirebase(){

    }
}

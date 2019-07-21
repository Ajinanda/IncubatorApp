package com.example.myapplication2;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Incubation Data</h1>
 * class ini dibuat untuk mengambil dan mengirim data inkubasi yang sedang berjalan dari/ke database firebase
 *
 * @author Ajinanda Nugraha Putra
 * @version 0.1 (alpha)
 * @since 2019-6-22
 */
public class IncubationData {
    public String namaInkubasi, jenisUnggas, tanggalInkubasi;
    public int jumlahTelur, masaInkubasi, masaMembalikTelur, siklusPembalikanTelur,
            minTemp, maxTemp, moist;
    public int[][] jadwal = new int[3][2];
    public int[][] tanggalPembalikan = new int[2][3];

    public String getTanggalInkubasi() {
        return tanggalInkubasi;
    }

    public void setTanggalInkubasi(String tanggalInkubasi) {
        this.tanggalInkubasi = tanggalInkubasi;
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

    public int getJumlahTelur() {
        return jumlahTelur;
    }

    public void setJumlahTelur(int jumlahTelur) {
        this.jumlahTelur = jumlahTelur;
    }

    public int getMasaInkubasi() {
        return masaInkubasi;
    }

    public void setMasaInkubasi(int masaInkubasi) {
        this.masaInkubasi = masaInkubasi;
    }

    public int getMasaMembalikTelur() {
        return masaMembalikTelur;
    }

    public void setMasaMembalikTelur(int masaMembalikTelur) {
        this.masaMembalikTelur = masaMembalikTelur;
    }

    public int getSiklusPembalikanTelur() {
        return siklusPembalikanTelur;
    }

    public void setSiklusPembalikanTelur(int siklusPembalikanTelur) {
        this.siklusPembalikanTelur = siklusPembalikanTelur;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMoist() {
        return moist;
    }

    public void setMoist(int moist) {
        this.moist = moist;
    }

    public int[][] getJadwal() {
        return jadwal;
    }

    public void setJadwal(int[][] jadwal) {
        this.jadwal = jadwal;
    }

    public int[][] getTanggalPembalikan() {
        return tanggalPembalikan;
    }

    public void setTanggalPembalikan(int[][] tanggalPembalikan) {
        this.tanggalPembalikan = tanggalPembalikan;
    }

    /**
     * <h1>Fungsi Profile Data</h1>
     * Constructor ini digunakan untuk menentukan data inkubasi apa yang akan
     * dikirim ke database.
     *
     *
     */
    public IncubationData(String namaInkubasi, String jenisUnggas, int jumlahTelur, int masaInkubasi, int masaMembalikTelur, int siklusPembalikanTelur, int minTemp, int maxTemp, int moist, int[][] jadwal, int[][] tanggalPembalikan) {
        this.namaInkubasi = namaInkubasi;
        this.jenisUnggas = jenisUnggas;
        this.jumlahTelur = jumlahTelur;
        this.masaInkubasi = masaInkubasi;
        this.masaMembalikTelur = masaMembalikTelur;
        this.siklusPembalikanTelur = siklusPembalikanTelur;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.moist = moist;
        this.jadwal = jadwal;
        this.tanggalPembalikan = tanggalPembalikan;
    }

    public Map<String, Object> inkubasiMap(){
        HashMap<String, Object> inkubasi = new HashMap<>();
        inkubasi.put("jumlahTelur", jumlahTelur);
        inkubasi.put("namaInkubasi", namaInkubasi);
        inkubasi.put("rotationCycle", siklusPembalikanTelur);
        inkubasi.put("timeIncubation", masaInkubasi);
        inkubasi.put("timeRotation", masaMembalikTelur);
        inkubasi.put("unggas", jenisUnggas);
        return inkubasi;
    }

    public Map<String, Object> atursuhuMap(){
        HashMap<String, Object> atursuhu = new HashMap<>();
        atursuhu.put("maxsuhu", maxTemp);
        atursuhu.put("minlembab", moist);
        atursuhu.put("minsuhu", minTemp);
        return atursuhu;
    }

    public Map<String, Object> rtcMap(){
        HashMap<String, Object> rtc = new HashMap();
        rtc.put("/jadwal1/jam",jadwal[0][0]);
        rtc.put("/jadwal1/menit",jadwal[0][1]);
        rtc.put("/jadwal2/jam",jadwal[1][0]);
        rtc.put("/jadwal2/menit",jadwal[1][1]);
        rtc.put("/jadwal3/jam",jadwal[2][0]);
        rtc.put("/jadwal3/menit",jadwal[2][1]);
        rtc.put("/tgl1/tanggal",tanggalPembalikan[0][0]);
        rtc.put("/tgl1/bulan",tanggalPembalikan[0][1]);
        rtc.put("/tgl1/tahun",tanggalPembalikan[0][2]);
        rtc.put("/tgl2/tanggal",tanggalPembalikan[1][0]);
        rtc.put("/tgl2/bulan",tanggalPembalikan[1][1]);
        rtc.put("/tgl2/tahun",tanggalPembalikan[1][2]);
        return rtc;
    }




    public IncubationData() {
    }
}

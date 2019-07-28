package com.example.myapplication2;

import android.util.Log;

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
    public long jumlahTelur, masaInkubasi, masaMembalikTelur, siklusPembalikanTelur,
            minTemp, maxTemp, moist;
    public long[][] jadwal = new long[3][2];
    public long[][] tanggalPembalikan = new long[2][3];

    public IncubationData(String namaInkubasi, String jenisUnggas, long jumlahTelur, long masaInkubasi, long masaMembalikTelur, long siklusPembalikanTelur, long minTemp, long maxTemp, long moist, long[][] jadwal, long[][] tanggalPembalikan) {
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

    public String getTanggalInkubasi() {
        return tanggalInkubasi;
    }

    public void setTanggalInkubasi(String tanggalInkubasi) {
        this.tanggalInkubasi = tanggalInkubasi;
    }

    public long getJumlahTelur() {
        return jumlahTelur;
    }

    public void setJumlahTelur(long jumlahTelur) {
        this.jumlahTelur = jumlahTelur;
    }

    public long getMasaInkubasi() {
        return masaInkubasi;
    }

    public void setMasaInkubasi(long masaInkubasi) {
        this.masaInkubasi = masaInkubasi;
    }

    public long getMasaMembalikTelur() {
        return masaMembalikTelur;
    }

    public void setMasaMembalikTelur(long masaMembalikTelur) {
        this.masaMembalikTelur = masaMembalikTelur;
    }

    public long getSiklusPembalikanTelur() {
        return siklusPembalikanTelur;
    }

    public void setSiklusPembalikanTelur(long siklusPembalikanTelur) {
        this.siklusPembalikanTelur = siklusPembalikanTelur;
    }

    public long getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(long minTemp) {
        this.minTemp = minTemp;
    }

    public long getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(long maxTemp) {
        this.maxTemp = maxTemp;
    }

    public long getMoist() {
        return moist;
    }

    public void setMoist(long moist) {
        this.moist = moist;
    }

    public long[][] getJadwal() {
        return jadwal;
    }

    public void setJadwal(long[][] jadwal) {
        this.jadwal = jadwal;
    }

    public long[][] getTanggalPembalikan() {
        return tanggalPembalikan;
    }

    public void setTanggalPembalikan(long[][] tanggalPembalikan) {
        this.tanggalPembalikan = tanggalPembalikan;
    }

    public IncubationData(String namaInkubasi, String jenisUnggas, long jumlahTelur, long masaInkubasi, long masaMembalikTelur, long minTemp, long maxTemp, long moist, long[][] jadwal, long[][] tanggalPembalikan) {
        this.namaInkubasi = namaInkubasi;
        this.jenisUnggas = jenisUnggas;
        this.jumlahTelur = jumlahTelur;
        this.masaInkubasi = masaInkubasi;
        this.masaMembalikTelur = masaMembalikTelur;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.moist = moist;
        this.jadwal = jadwal;
        this.tanggalPembalikan = tanggalPembalikan;
    }

    /**
     * <h1>Fungsi Profile Data</h1>
     * Constructor ini digunakan untuk menentukan data inkubasi apa yang akan
     * dikirim ke database.
     *
     *
     */



    public Map<String, Object> inkubasiMap(){
        HashMap<String, Object> inkubasi = new HashMap<>();
        inkubasi.put("jumlahTelur", jumlahTelur);
        inkubasi.put("namaInkubasi", namaInkubasi);
        inkubasi.put("timeIncubation", masaInkubasi);
        inkubasi.put("timeRotation", masaMembalikTelur);
        inkubasi.put("unggas", jenisUnggas);
        Log.i("InkubasiMap", "InkubasiMap: Test Map");
        return inkubasi;
    }

    public Map<String, Object> atursuhuMap(){
        HashMap<String, Object> atursuhu = new HashMap<>();
        atursuhu.put("maxsuhu", maxTemp);
        atursuhu.put("minlembab", moist);
        atursuhu.put("minsuhu", minTemp);
        Log.i("InkubasiMap", "AtursuhuMap: Test Map");
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
        Log.i("InkubasiMap", "rtcMap: Test Map");
        return rtc;
    }




    public IncubationData() {
    }
}

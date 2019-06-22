package com.example.myapplication2;

/**
 *<h1>Data Profile</h1>
 * class ini dibuat untuk mengambil dan mengirim data profile unggas dari/ke database firebase
 *
 * @author Ajinanda Nugraha Putra
 * @version 0.1 (alpha)
 * @since 2019-6-21
 */

public class ProfileData {

    public String name;
    public int minTemp;
    public int maxTemp;
    public int minMoist;
    public int timeIncubation;
    public int timeRotation;
    public int rotationCycle;

    /**
     * <h1>Fungsi Profile Data</h1>
     * Constructor ini digunakan untuk menentukan data profil apa yang akan
     * dikirim ke database.
     *
     * @param name - nama unggas yang profilnya akan dimasukkan ke database.
     * @param minTemp - minimum temperatur yang dibutuhkan untuk menginkubasi telur.
     * @param maxTemp - maximum temperatur yang dibutuhkan untuk menginkubasi telur.
     * @param minMoist - minimum kelembapan yang dibutuhkan untuk menginkubasi telur.
     * @param timeIncubation - waktu yang diperlukan untuk menginkubasi telur
     * @param timeRotation - menentukan jumlah hari dimana telur akan dibolak balik
     * @param rotationCycle - menentukan setiap berapa jam telur akan dibolak balik
     */
    public ProfileData(String name, int minTemp, int maxTemp, int minMoist, int timeIncubation, int timeRotation, int rotationCycle){
        this.name = name;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minMoist = minMoist;
        this.timeIncubation = timeIncubation;
        this.timeRotation = timeRotation;
        this.rotationCycle = rotationCycle;
    }


    public String getNama(){
        return name;
    }

    public int getMinTemp(){
        return minTemp;
    }

    public int getMaxTemp(){
        return maxTemp;
    }

    public int getMinMoist(){
        return minMoist;
    }

    public int getTimeIncubation(){
        return timeIncubation;
    }

    public int getTimeRotation(){
        return timeRotation;
    }

    public int getRotationCycle(){
        return rotationCycle;
    }

    public void setNama(String name) {
        this.name = name;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinMoist(int minMoist) {
        this.minMoist = minMoist;
    }

    public void setTimeIncubation(int timeIncubation) {
        this.timeIncubation = timeIncubation;
    }

    public void setTimeRotation(int timeRotation) {
        this.timeRotation = timeRotation;
    }

    public void setRotationCycle(int rotationCycle) {
        this.rotationCycle = rotationCycle;
    }

    public ProfileData(){

    }

}

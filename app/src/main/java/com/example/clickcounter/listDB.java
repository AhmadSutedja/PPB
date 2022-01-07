package com.example.clickcounter;

public class listDB {
    long waktuTotal;
    int jumlahklik,gambarjari;
    String namajari,key;

    public listDB(long waktuTotal, int jumlahklik, int gambarjari, String namajari,String key) {
        this.key = key;
        this.waktuTotal = waktuTotal;
        this.jumlahklik = jumlahklik;
        this.gambarjari = gambarjari;
        this.namajari = namajari;
    }
    public listDB(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getWaktuTotal() {
        return waktuTotal;
    }

    public void setWaktuTotal(long waktuTotal) {
        this.waktuTotal = waktuTotal;
    }

    public int getJumlahklik() {
        return jumlahklik;
    }

    public void setJumlahklik(int jumlahklik) {
        this.jumlahklik = jumlahklik;
    }

    public int getGambarjari() {
        return gambarjari;
    }

    public void setGambarjari(int gambarjari) {
        this.gambarjari = gambarjari;
    }

    public String getNamajari() {
        return namajari;
    }

    public void setNamajari(String namajari) {
        this.namajari = namajari;
    }
}

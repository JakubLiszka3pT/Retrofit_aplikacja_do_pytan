package com.example.wprowadzenieretrofit;

import com.google.gson.annotations.SerializedName;

public class Pytanie {
    @SerializedName("tresc") // tego trzeba uzyc jak w jsonie jest podana inna nazwa :D
    private String trescPytania;
    private String odpa;
    private String odpb;
    private String odpc;
    private int poprawna;

    public Pytanie(String trescPytania, String odpa, String odpb, int poprawna, String odpc) {
        this.trescPytania = trescPytania;
        this.odpa = odpa;
        this.odpb = odpb;
        this.poprawna = poprawna;
        this.odpc = odpc;
    }

    public String getTrescPytania() {
        return trescPytania;
    }

    public void setTrescPytania(String trescPytania) {
        this.trescPytania = trescPytania;
    }

    public String getOdpa() {
        return odpa;
    }

    public void setOdpa(String odpa) {
        this.odpa = odpa;
    }

    public String getOdpc() {
        return odpc;
    }

    public void setOdpc(String odpc) {
        this.odpc = odpc;
    }

    public int getPoprawna() {
        return poprawna;
    }

    public void setPoprawna(int poprawna) {
        this.poprawna = poprawna;
    }

    public String getOdpb() {
        return odpb;
    }

    public void setOdpb(String odpb) {
        this.odpb = odpb;
    }
}

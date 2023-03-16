/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.objects;

import org.jyotisa.meta.base.MetaTheme;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaObject extends MetaTheme {
    private static final long serialVersionUID = 9171470605395207803L;

    protected Integer rasi;
    protected Integer bhava;
    protected Integer vakri;

    protected String deg;
    protected String degr;
    protected Float vdegr;
    protected String lat;
    protected String lon;
    protected String npada;

    protected Integer naksatra;
    protected Integer pada;
    protected Integer dignity;
    protected Integer navamsa;
    protected Integer karaka;

    public Integer rasi() {
        return rasi;
    }

    public void rasi(Integer rasi) {
        this.rasi = rasi;
    }

    public Integer bhava() {
        return bhava;
    }

    public void bhava(Integer bhava) {
        this.bhava = bhava;
    }

    public Integer vakri() {
        return vakri;
    }

    public void vakri(Integer vakri) {
        this.vakri = vakri;
    }

    public String deg() {
        return deg;
    }

    public void deg(String deg) {
        this.deg = deg;
    }

    public String degr() {
        return degr;
    }

    public void degr(String degr) {
        this.degr = degr;
    }

    public String lat() {
        return lat;
    }

    public void lat(String latitude) {
        this.lat = latitude;
    }

    public String lon() {
        return lon;
    }

    public void lon(String longitude) {
        this.lon = longitude;
    }

    public Float vdegr() {
        return vdegr;
    }

    public void vdegr(Float vdegr) {
        this.vdegr = vdegr;
    }

    public String npada() {
        return npada;
    }

    public void npada(String npada) {
        this.npada = npada;
    }

    public Integer naksatra() {
        return naksatra;
    }

    public void naksatra(Integer naksatra) {
        this.naksatra = naksatra;
    }

    public Integer pada() {
        return pada;
    }

    public void pada(Integer pada) {
        this.pada = pada;
    }

    public Integer karaka() {
        return karaka;
    }

    public void karaka(Integer karaka) {
        this.karaka = karaka;
    }

    public Integer dignity() {
        return dignity;
    }

    public void dignity(Integer dignity) {
        this.dignity = dignity;
    }

    public Integer navamsa() {
        return navamsa;
    }

    public void navamsa(Integer navamsa) {
        this.navamsa = navamsa;
    }
}

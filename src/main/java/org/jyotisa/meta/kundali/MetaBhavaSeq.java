/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaJyotisaPojo;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaBhavaSeq implements IMetaJyotisaPojo {
    protected Integer bhava;
    protected Integer rasi;

    protected Object bhavaShape;
    protected Object rasiShape;
    protected Object grahaShape;

    public Integer bhava() {
        return bhava;
    }

    public void bhava(Integer bhava) {
        this.bhava = bhava;
    }

    public Integer rasi() {
        return rasi;
    }

    public void rasi(Integer rasi) {
        this.rasi = rasi;
    }

    public Object bhavaShape() {
        return bhavaShape;
    }

    public void bhavaShape(Object bhavaShape) {
        this.bhavaShape = bhavaShape;
    }

    public Object rasiShape() {
        return rasiShape;
    }

    public void rasiShape(Object rasiShape) {
        this.rasiShape = rasiShape;
    }

    public Object grahaShape() {
        return grahaShape;
    }

    public void grahaShape(Object grahaShape) {
        this.grahaShape = grahaShape;
    }
}

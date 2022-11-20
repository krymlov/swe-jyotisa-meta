/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.base;

import org.jyotisa.meta.api.IMetaJyotisaPojo;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaCode implements IMetaJyotisaPojo {
    private static final long serialVersionUID = -7421094986181581536L;

    protected Integer fid;
    protected String code;

    public Integer fid() {
        return fid;
    }

    public void fid(Integer fid) {
        this.fid = fid;
    }

    public String code() {
        return code;
    }

    public void code(String code) {
        this.code = code;
    }
}

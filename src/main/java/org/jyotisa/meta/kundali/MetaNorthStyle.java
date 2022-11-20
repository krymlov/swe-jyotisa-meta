/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaJyotisaPojo;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.varga.EVarga;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaNorthStyle implements IMetaJyotisaPojo {
    private static final long serialVersionUID = -7513839883761888120L;

    protected final List<Integer> infoBox = new ArrayList<>(4);
    protected final List<MetaBhavaSeq> viewBox = new ArrayList<>(ERasi.values().length);
    protected final Map<String, List<MetaBhavaSeq>> objects = new LinkedHashMap<>(EVarga.values().length);

    public List<Integer> infoBox() {
        return infoBox;
    }

    public List<MetaBhavaSeq> viewBox() {
        return viewBox;
    }

    public Map<String, List<MetaBhavaSeq>> objects() {
        return objects;
    }
}

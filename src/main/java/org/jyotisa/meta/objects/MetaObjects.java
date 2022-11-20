/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.objects;

import org.jyotisa.meta.api.IMetaJyotisaPojo;
import org.jyotisa.upagraha.EUpagraha;

import java.util.ArrayList;
import java.util.List;

import static org.swisseph.api.ISweObjects.OBJECTS_COUNT;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaObjects implements IMetaJyotisaPojo {
    private static final long serialVersionUID = 280858418715809866L;

    protected final List<MetaObject> grahas = new ArrayList<>(OBJECTS_COUNT);
    protected final List<MetaObject> arudhas = new ArrayList<>(0);
    protected final List<MetaObject> upagrahas = new ArrayList<>(EUpagraha.values().length);

    public List<MetaObject> grahas() {
        return grahas;
    }

    public List<MetaObject> arudhas() {
        return arudhas;
    }

    public List<MetaObject> upagrahas() {
        return upagrahas;
    }
}

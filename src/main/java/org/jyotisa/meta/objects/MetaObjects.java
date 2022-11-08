/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.objects;

import org.jyotisa.meta.api.IMetaJyotisaPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaObjects implements IMetaJyotisaPojo {
    protected final List<MetaObject> grahas = new ArrayList<>();
    protected final List<MetaObject> arudhas = new ArrayList<>();
    protected final List<MetaObject> upagrahas = new ArrayList<>();

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

/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.options;

import org.jyotisa.meta.api.IMetaJyotisaPojo;
import org.jyotisa.meta.base.MetaTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaOptions implements IMetaJyotisaPojo {
    protected final List<MetaTheme> groups = new ArrayList<>();
    protected final List<MetaOption> items = new ArrayList<>();
    protected final List<MetaOptionView> views = new ArrayList<>();

    public List<MetaTheme> groups() {
        return groups;
    }

    public List<MetaOption> items() {
        return items;
    }

    public List<MetaOptionView> views() {
        return views;
    }
}

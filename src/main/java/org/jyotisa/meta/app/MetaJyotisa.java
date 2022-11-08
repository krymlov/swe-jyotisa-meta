/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.app;

import org.jyotisa.bhava.EBhava;
import org.jyotisa.dignity.EDignity;
import org.jyotisa.karaka.ECharaKaraka;
import org.jyotisa.meta.api.IMetaJyotisa;
import org.jyotisa.meta.base.MetaTheme;
import org.jyotisa.meta.event.MetaEvent;
import org.jyotisa.meta.kundali.*;
import org.jyotisa.meta.objects.MetaObjects;
import org.jyotisa.meta.options.MetaOptions;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.rasi.ERasi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaJyotisa extends MetaTheme implements IMetaJyotisa {
    protected String metaVersion = "2.10.03";
    protected String appVersion = "2.10.03i";
    protected String appName = "swe-jyotisa";

    protected final MetaEvent event = new MetaEvent();
    protected final MetaOptions options = new MetaOptions();
    protected final MetaKundali kundali = new MetaKundali();

    protected final Map<String, MetaObjects> objects = new LinkedHashMap<>();

    protected final List<MetaDignity> dignity = new ArrayList<>(EDignity.values().length);
    protected final List<MetaNaksatra> naksatra = new ArrayList<>(ENaksatra.values().length);
    protected final List<MetaKaraka> karaka = new ArrayList<>(ECharaKaraka.values().length);
    protected final List<MetaBhava> bhava = new ArrayList<>(EBhava.values().length);
    protected final List<MetaRasi> rasi = new ArrayList<>(ERasi.values().length);

    @Override
    public String appVersion() {
        return appVersion;
    }

    @Override
    public void appVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String appName() {
        return appName;
    }

    @Override
    public void appName(String appName) {
        this.appName = appName;
    }

    @Override
    public String metaVersion() {
        return metaVersion;
    }

    @Override
    public void metaVersion(String metaVersion) {
        this.metaVersion = metaVersion;
    }

    @Override
    public MetaEvent event() {
        return event;
    }

    @Override
    public MetaOptions options() {
        return options;
    }

    @Override
    public MetaKundali kundali() {
        return kundali;
    }

    @Override
    public Map<String, MetaObjects> objects() {
        return objects;
    }

    @Override
    public List<MetaDignity> dignity() {
        return dignity;
    }

    @Override
    public List<MetaNaksatra> naksatra() {
        return naksatra;
    }

    @Override
    public List<MetaKaraka> karaka() {
        return karaka;
    }

    @Override
    public List<MetaBhava> bhava() {
        return bhava;
    }

    @Override
    public List<MetaRasi> rasi() {
        return rasi;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.api;

import org.jyotisa.meta.event.MetaEvent;
import org.jyotisa.meta.kundali.*;
import org.jyotisa.meta.objects.MetaObjects;
import org.jyotisa.meta.options.MetaOptions;

import java.util.List;
import java.util.Map;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public interface IMetaJyotisa extends IMetaPojo {
    String appVersion();
    void appVersion(String appVersion);

    String appName();
    void appName(String appName);

    String metaVersion();
    void metaVersion(String metaVersion);

    MetaEvent event();
    MetaOptions options();
    MetaKundali kundali();
    MetaChalita chalita();
    MetaPanchanga panchanga();
    MetaAshtakavarga ashtakavarga();

    /**
     * The Vimsottari dasha, to the depth {@code IMetaJyotisaConfig.confMetaVimsottariLevels()}
     * asks for. Left empty when that is 0.
     */
    MetaVimsottari vimsottari();

    Map<String, MetaObjects> objects();

    List<MetaDignity> dignity();
    List<MetaNaksatra> naksatra();
    List<MetaKaraka> karaka();
    /** the nine Vimsottari lords, indexed by the {@code fid} every period carries */
    List<MetaDasa> dasa();
    List<MetaBhava> bhava();
    List<MetaRasi> rasi();
}

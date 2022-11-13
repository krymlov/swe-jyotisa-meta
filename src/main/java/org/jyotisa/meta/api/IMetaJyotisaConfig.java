package org.jyotisa.meta.api;

import org.apache.commons.lang3.ArrayUtils;
import org.jyotisa.api.varga.IVargaEnum;
import org.jyotisa.meta.options.MetaView;
import org.jyotisa.varga.EVarga;
import org.swisseph.api.ISweEnumIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.api.varga.IVarga.D09_CD;

public interface IMetaJyotisaConfig {
    MetaViewStyle[] DEFAULT_STYLES = MetaViewStyle.values();

    default MetaViewStyle[] confMetaStyles() {
        return DEFAULT_STYLES;
    }

    default boolean confMetaStyle(final MetaViewStyle style) {
        return ArrayUtils.contains(confMetaStyles(), style);
    }

    default Iterator<MetaView> confMetaViews() {
        final MetaViewStyle[] styles = confMetaStyles();

        if (null == styles || styles.length == 0) {
            throw new IllegalArgumentException("At least one style is required");
        }

        final List<MetaView> viewList = new ArrayList<>();
        for (MetaViewStyle style : styles) viewList.add(new MetaView(style, D01_CD));
        if (styles.length == 1) viewList.add(new MetaView(styles[0], D09_CD));

        return viewList.iterator();
    }

    default ISweEnumIterator<IVargaEnum> confMetaVargas() {
        return EVarga.iterator();
    }
}

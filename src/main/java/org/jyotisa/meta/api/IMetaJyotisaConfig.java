package org.jyotisa.meta.api;

import org.jyotisa.meta.options.MetaOptionView;

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

    default Iterator<MetaOptionView> confMetaViews() {
        final MetaViewStyle[] styles = confMetaStyles();

        if (null == styles || styles.length == 0) {
            throw new IllegalArgumentException("At least one style is required");
        }

        final List<MetaOptionView> viewList = new ArrayList<>();
        for (MetaViewStyle style : styles) viewList.add(new MetaOptionView(style, D01_CD));
        if (styles.length == 1) viewList.add(new MetaOptionView(styles[0], D09_CD));

        return viewList.iterator();
    }
}

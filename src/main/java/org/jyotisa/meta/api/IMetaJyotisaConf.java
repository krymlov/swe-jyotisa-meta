package org.jyotisa.meta.api;

import org.jyotisa.meta.options.MetaOptionView;

import java.util.Iterator;

import static java.util.Arrays.asList;
import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.meta.api.MetaViewStyle.north;
import static org.jyotisa.meta.api.MetaViewStyle.south;

public interface IMetaJyotisaConf {
    MetaOptionView[] DEFAULT_OPTIONS_VIEWS = new MetaOptionView[]{
            new MetaOptionView(south, D01_CD), new MetaOptionView(north, D01_CD)
    };

    default Iterator<MetaOptionView> optionsViews() {
        return asList(DEFAULT_OPTIONS_VIEWS).iterator();
    }
}

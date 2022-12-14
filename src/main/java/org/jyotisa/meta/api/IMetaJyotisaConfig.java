package org.jyotisa.meta.api;

import org.apache.commons.lang3.ArrayUtils;
import org.jyotisa.api.bhava.IBhavaEnum;
import org.jyotisa.api.dignity.IDignityEnum;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.graha.IGrahaEnum;
import org.jyotisa.api.karaka.ICharaKaraka;
import org.jyotisa.api.naksatra.INaksatraEnum;
import org.jyotisa.api.rasi.IRasiEnum;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.api.upagraha.IUpagrahaEnum;
import org.jyotisa.api.varga.IVargaEnum;
import org.jyotisa.bhava.EBhava;
import org.jyotisa.dignity.EDignity;
import org.jyotisa.graha.EGraha;
import org.jyotisa.karaka.ECharaKaraka;
import org.jyotisa.meta.options.MetaView;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.upagraha.EUpagraha;
import org.jyotisa.varga.EVarga;
import org.swisseph.api.ISweEnumIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.api.varga.IVarga.D09_CD;
import static org.jyotisa.graha.EGraha.KETU;
import static org.jyotisa.graha.EGraha.LAGNA;
import static org.jyotisa.upagraha.EUpagraha.DHUMA;
import static org.jyotisa.upagraha.EUpagraha.UPAKETU;

public interface IMetaJyotisaConfig {
    ViewStyle[] DEFAULT_STYLES = ViewStyle.values();

    default ViewStyle[] confMetaStyles() {
        return DEFAULT_STYLES;
    }

    default boolean confMetaStyle(final ViewStyle style) {
        return ArrayUtils.contains(confMetaStyles(), style);
    }

    default Iterator<MetaView> confMetaViews() {
        final ViewStyle[] styles = confMetaStyles();

        if (null == styles || styles.length == 0) {
            throw new IllegalArgumentException("At least one style is required");
        }

        final List<MetaView> viewList = new ArrayList<>();
        for (ViewStyle style : styles) viewList.add(new MetaView(style, D01_CD));
        if (styles.length == 1) viewList.add(new MetaView(styles[0], D09_CD));

        return viewList.iterator();
    }

    default ISweEnumIterator<ICharaKaraka> confMetaCharaKarakas() {
        return ECharaKaraka.iterator();
    }

    default ISweEnumIterator<INaksatraEnum> confMetaNaksatras() {
        return ENaksatra.iterator();
    }

    default ISweEnumIterator<IDignityEnum> confMetaDignities() {
        return EDignity.iterator();
    }

    default ISweEnumIterator<IVargaEnum> confMetaVargas() {
        return EVarga.iterator();
    }

    default ISweEnumIterator<IBhavaEnum> confMetaBhavas() {
        return EBhava.iterator();
    }

    default ISweEnumIterator<IGrahaEnum> confMetaGrahas() {
        return EGraha.iterator(LAGNA, KETU);
    }

    default ISweEnumIterator<IRasiEnum> confMetaRasis() {
        return ERasi.iterator();
    }

    default List<IGrahaEntity> confMetaGrahasFilter(IGrahaEntity[] all) {
        final ISweEnumIterator<IGrahaEnum> iterator = confMetaGrahas();
        final List<IGrahaEntity> grahas = new ArrayList<>(all.length);

        while (iterator.hasNext()) {
            final int guid = iterator.next().graha().uid();
            for (IGrahaEntity ge : all) {
                if (ge != null && ge.entityEnum().uid() == guid) {
                    grahas.add(ge);
                    break;
                }
            }
        }

        return grahas;
    }

    default ISweEnumIterator<IUpagrahaEnum> confMetaUpagrahas() {
        return EUpagraha.iterator(DHUMA, UPAKETU);
    }

    default List<IUpagrahaEntity> confMetaUpagrahasFilter(IUpagrahaEntity[] all) {
        final ISweEnumIterator<IUpagrahaEnum> iterator = confMetaUpagrahas();
        final List<IUpagrahaEntity> upagrahas = new ArrayList<>(all.length);

        while (iterator.hasNext()) {
            final int uuid = iterator.next().upagraha().uid();
            for (IUpagrahaEntity ue : all) {
                if (ue != null && ue.entityEnum().uid() == uuid) {
                    upagrahas.add(ue);
                    break;
                }
            }
        }

        return upagrahas;
    }

    default IMetaNorthStyleCalc confMetaStyleNorthCalc(IMetaJyotisa jyotisa) {
        return null;
    }
}

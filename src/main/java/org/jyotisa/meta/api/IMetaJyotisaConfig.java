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
import org.jyotisa.api.vimsottari.IVimsottariDasaEnum;
import org.jyotisa.vimsottari.EVimsottariDasa;
import org.jyotisa.meta.options.MetaView;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.upagraha.EUpagraha;
import org.jyotisa.varga.EVarga;
import org.swisseph.api.ISweEnumIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * How deep the Vimsottari dasha is exported: 1 mahadasha, 2 antardasha, 3 pratyantardasha,
     * 4 sookshma, 5 prana. <b>0 leaves it out of the document entirely.</b>
     * <p>
     * Two by default - mahadasha and antardasha - because that is what a reader following a chart
     * normally wants, and because the cost is nine-fold per level: 9, 81, 729, 6561, 59049 periods.
     * A consumer that only ever shows mahadashas should say so, and one that wants pratyantardasha
     * or deeper asks for it.
     */
    default int confMetaVimsottariLevels() {
        return 2;
    }

    /** the nine Vimsottari lords for the document's reference table */
    default ISweEnumIterator<IVimsottariDasaEnum> confMetaDasas() {
        return EVimsottariDasa.iterator();
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

    /**
     * The grahas {@link #confMetaGrahas()} names, as the set of {@code ISweObjects} indices.
     * <p>
     * For the places that hold plain {@link org.jyotisa.api.graha.IGraha}s rather than entities and
     * so cannot use {@link #confMetaGrahasFilter} - Bhava Chalita is one. It exists so that those
     * places follow the <b>same</b> setting as the D1 objects rather than carrying a second one:
     * two knobs for "which grahas are shown" would only ever be a pair to keep in sync.
     */
    default Set<Integer> confMetaGrahaUids() {
        final ISweEnumIterator<IGrahaEnum> iterator = confMetaGrahas();
        final Set<Integer> uids = new HashSet<>();

        while (iterator.hasNext()) uids.add(iterator.next().graha().uid());
        return uids;
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

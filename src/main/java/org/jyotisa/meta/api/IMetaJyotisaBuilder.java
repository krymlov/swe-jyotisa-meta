/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.api;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.bhava.IBhavaEnum;
import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.dignity.IDignityEnum;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.graha.IGrahas;
import org.jyotisa.api.karaka.ICharaKaraka;
import org.jyotisa.api.naksatra.INaksatraEnum;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.rasi.IRasiEnum;
import org.jyotisa.api.upagraha.IUpagraha;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.api.varga.IVargaEnum;
import org.jyotisa.bhava.EBhava;
import org.jyotisa.meta.app.MetaJyotisa;
import org.jyotisa.meta.base.MetaTheme;
import org.jyotisa.meta.kundali.*;
import org.jyotisa.meta.objects.MetaObject;
import org.jyotisa.meta.objects.MetaObjects;
import org.jyotisa.meta.options.MetaOption;
import org.jyotisa.meta.options.MetaView;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.upagraha.EUpagraha;
import org.jyotisa.varga.EVarga;
import org.swisseph.api.ISweEnumEntity;
import org.swisseph.api.ISweEnumIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Character.toLowerCase;
import static java.util.Comparator.comparingDouble;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;
import static org.jyotisa.api.graha.IGraha.KE_CD;
import static org.jyotisa.api.graha.IGraha.RA_CD;
import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.meta.kundali.MetaBhava.NIL_BHAVA;
import static org.jyotisa.meta.kundali.MetaDignity.NIL_DIGNITY;
import static org.jyotisa.meta.kundali.MetaKaraka.NIL_KARAKA;
import static org.jyotisa.meta.kundali.MetaNaksatra.NIL_NAKSATRA;
import static org.jyotisa.meta.kundali.MetaRasi.NIL_RASI;
import static org.jyotisa.varga.EVarga.RASI;
import static org.swisseph.api.ISweConstants.RASI_LENGTH;
import static org.swisseph.utils.IDegreeUtils.toDMSms;
import static swisseph.SweConst.ODEGREE_CHAR;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public interface IMetaJyotisaBuilder extends IMetaJyotisaConfig, IMetaJyotisaTheme {
    String[] DIGNITY_SYMBOLS = new String[]{EMPTY, "↓", "↙", EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, "↖", "↑"};
    String[] PADA_DIGITS = new String[]{EMPTY, "¹", "²", "³", "⁴"};

    default IMetaJyotisa buildMetaJyotisa(IKundali kundali) {
        final MetaJyotisa jyotisa = new MetaJyotisa();

        addMetaOptionsGroups(jyotisa);
        addMetaOptionsItems(jyotisa);
        addMetaOptionsViews(jyotisa);

        addMetaKundaliMainBox(jyotisa);
        addMetaSouthStyleInfoBox(jyotisa);

        addMetaSouthStyleViewBox(jyotisa);
        addMetaNorthStyleViewBox(jyotisa);

        addMetaSouthStyleObjects(jyotisa, kundali);
        addMetaNorthStyleObjects(jyotisa, kundali);

        addMetaCharaKarakaEnum(jyotisa);
        addMetaNaksatraEnum(jyotisa);
        addMetaDignityEnum(jyotisa);
        addMetaBhavaEnums(jyotisa);
        addMetaRasiEnums(jyotisa);

        addMetaEventInfo(jyotisa, kundali);
        addMetaVargaGrahas(jyotisa, kundali);
        addMetaRasiUpagrahas(jyotisa, kundali);

        return jyotisa;
    }

    default void addMetaOptionsGroups(IMetaJyotisa jyotisa) {
        final MetaTheme vargaGroup = new MetaTheme();
        vargaGroup.code(EVarga.class.getSimpleName());
        jyotisa.options().groups().add(vargaGroup);
        vargaGroup.name("VARGA CHAKRA");
    }

    default void addMetaOptionsItems(IMetaJyotisa jyotisa) {
        final ISweEnumIterator<IVargaEnum> iterator = confMetaVargas();
        final List<MetaOption> items = jyotisa.options().items();

        while (iterator.hasNext()) {
            final IVargaEnum vargaEnum = iterator.next();
            final MetaOption option = new MetaOption();
            option.group(EVarga.class.getSimpleName());
            option.code(vargaEnum.code());
            option.name(vargaEnum.code() + SPACE
                    + capitalizeFully(vargaEnum.name()));
            items.add(option);
        }
    }

    default void addMetaOptionsViews(IMetaJyotisa jyotisa) {
        Iterator<MetaView> iterator = confMetaViews();
        List<MetaView> viewList = jyotisa.options().views();
        while (iterator.hasNext()) viewList.add(iterator.next());
    }

    default void addMetaKundaliMainBox(IMetaJyotisa jyotisa) {
    }

    default void addMetaSouthStyleInfoBox(IMetaJyotisa jyotisa) {
        if (!confMetaStyle(ViewStyle.south)) return;

        List<Integer> mainBox = jyotisa.kundali().mainBox();
        List<Integer> infoBox = jyotisa.kundali().southStyle().infoBox();

        final int width = Math.round(mainBox.get(2) / 4f);
        final int height = Math.round(mainBox.get(3) / 4f);

        infoBox.add(width);
        infoBox.add(height);
        infoBox.add(width * 2);
        infoBox.add(height * 2);
    }

    default void addMetaSouthStyleViewBox(IMetaJyotisa jyotisa) {
    }

    default void addMetaSouthStyleObjects(IMetaJyotisa jyotisa, IKundali kundali) {
        if (!confMetaStyle(ViewStyle.south)) return;

        final Map<String, List<MetaRasiSeq>> mapVargaRasiSeqs = jyotisa.kundali().southStyle().objects();
        final Iterator<IVargaEnum> iterator = confMetaVargas();
        final IGrahaEntity lagna = kundali.grahas().lagna();

        while (iterator.hasNext()) {
            final IVarga varga = iterator.next().varga();
            final List<MetaRasiSeq> rasiSeqs = new ArrayList<>(ERasi.values().length);
            final ISweEnumIterator<IRasiEnum> rasiIterator = confMetaRasis();
            final int lgRasiFid = varga.rasi(lagna.longitude()).fid();
            mapVargaRasiSeqs.put(varga.code(), rasiSeqs);

            while (rasiIterator.hasNext()) {
                rasiSeqs.add(buildMetaRasiSeq(rasiIterator.next(), lgRasiFid));
            }
        }
    }

    default MetaRasiSeq buildMetaRasiSeq(IRasiEnum rasiEnum, int lagnaRasiFid) {
        MetaRasiSeq sequence = new MetaRasiSeq();
        sequence.rasi(rasiEnum.fid());
        sequence.bhava((sequence.rasi() + 12 - lagnaRasiFid) % 12 + 1);
        return sequence;
    }

    default void addMetaNorthStyleViewBox(IMetaJyotisa jyotisa) {
        if (!confMetaStyle(ViewStyle.north)) return;

        final IMetaNorthStyleCalc coordsCalc = confMetaStyleNorthCalc(jyotisa);
        if (null == coordsCalc) return;

        final ISweEnumIterator<IBhavaEnum> bhavaIterator = confMetaBhavas();
        final List<MetaBhavaSeq> viewBox = jyotisa.kundali().northStyle().viewBox();
        while (bhavaIterator.hasNext()) viewBox.add(buildMetaRasiSeq(bhavaIterator.next(), coordsCalc));
    }

    default MetaBhavaSeq buildMetaRasiSeq(IBhavaEnum bhavaEnum, IMetaNorthStyleCalc coordsCalc) {
        final MetaBhavaSeq sequence = new MetaBhavaSeq();
        sequence.bhava(bhavaEnum.fid());

        if (null != coordsCalc) {
            sequence.bhavaShape(coordsCalc.calc(bhavaEnum.fid()));
            sequence.rasiShape(coordsCalc.calcRasiCords(bhavaEnum.fid()));
            sequence.grahaShape(coordsCalc.calcPlanetBlockCords(bhavaEnum.fid()));
        }

        return sequence;
    }

    default void addMetaNorthStyleObjects(IMetaJyotisa jyotisa, IKundali kundali) {
        if (!confMetaStyle(ViewStyle.north)) return;

        final Map<String, List<MetaBhavaSeq>> mapVargaBhavaSeqs = jyotisa.kundali().northStyle().objects();
        final Iterator<IVargaEnum> iterator = confMetaVargas();
        final IGrahaEntity lagna = kundali.grahas().lagna();

        while (iterator.hasNext()) {
            final IVarga varga = iterator.next().varga();
            final List<MetaBhavaSeq> bhavaSeqs = new ArrayList<>(EBhava.values().length);
            final ISweEnumIterator<IBhavaEnum> bhavaIterator = confMetaBhavas();
            final int lgRasiFid = varga.rasi(lagna.longitude()).fid();
            IRasiEnum rasiEnum = ERasi.values()[lgRasiFid];
            mapVargaBhavaSeqs.put(varga.code(), bhavaSeqs);

            while (bhavaIterator.hasNext()) {
                bhavaSeqs.add(buildMetaRasiSeq(bhavaIterator.next(), rasiEnum));
                rasiEnum = rasiEnum.following();
            }
        }
    }

    default MetaBhavaSeq buildMetaRasiSeq(IBhavaEnum bhavaEnum, IRasiEnum rasiEnum) {
        final MetaBhavaSeq sequence = new MetaBhavaSeq();
        sequence.bhava(bhavaEnum.fid());
        sequence.rasi(rasiEnum.fid());
        return sequence;
    }

    default void addMetaRasiEnums(IMetaJyotisa jyotisa) {
        final List<MetaRasi> list = jyotisa.rasi();
        list.add(NIL_RASI); // #0
        ISweEnumIterator<IRasiEnum> iterator = confMetaRasis();
        while (iterator.hasNext()) list.add(buildMetaRasi(iterator.next()));
    }

    default MetaRasi buildMetaRasi(IRasiEnum rasiEnum) {
        MetaRasi entity = new MetaRasi();
        entity.fid(rasiEnum.fid());
        entity.code(rasiEnum.code());
        entity.name(capitalizeFully(rasiEnum.rasi().all()[1].name()));
        entity.text(capitalizeFully(rasiEnum.name()));
        entity.lord(rasiEnum.rasi().lord().fid());
        entity.start(rasiEnum.segment().start());
        entity.close(rasiEnum.segment().close());
        return entity;
    }

    default void addMetaBhavaEnums(IMetaJyotisa jyotisa) {
        final List<MetaBhava> list = jyotisa.bhava();
        list.add(NIL_BHAVA); // #0
        ISweEnumIterator<IBhavaEnum> iterator = confMetaBhavas();
        while (iterator.hasNext()) list.add(buildMetaBhava(iterator.next()));
    }

    default MetaBhava buildMetaBhava(IBhavaEnum bhavaEnum) {
        MetaBhava entity = new MetaBhava();
        entity.fid(bhavaEnum.fid());
        entity.code(bhavaEnum.code());
        entity.text(capitalizeFully(bhavaEnum.bhava().all()[1].name()));
        entity.desc(capitalizeFully(bhavaEnum.name()));
        entity.name(String.valueOf(bhavaEnum.fid()));
        return entity;
    }

    default void addMetaCharaKarakaEnum(IMetaJyotisa jyotisa) {
        final List<MetaKaraka> list = jyotisa.karaka();
        list.add(NIL_KARAKA); // #0
        ISweEnumIterator<ICharaKaraka> iterator = confMetaCharaKarakas();
        while (iterator.hasNext()) list.add(buildMetaCharaKaraka(iterator.next()));
    }

    default MetaKaraka buildMetaCharaKaraka(ICharaKaraka karaka) {
        MetaKaraka entity = new MetaKaraka();
        entity.fid(karaka.fid());
        entity.code(karaka.code());
        entity.name(karaka.code());
        entity.text(capitalizeFully(substringBefore(karaka.name(), "_")));
        return entity;
    }

    default void addMetaDignityEnum(IMetaJyotisa jyotisa) {
        final List<MetaDignity> list = jyotisa.dignity();
        list.add(NIL_DIGNITY); // #0
        ISweEnumIterator<IDignityEnum> iterator = confMetaDignities();
        while (iterator.hasNext()) list.add(buildMetaDignity(iterator.next()));
    }

    default MetaDignity buildMetaDignity(IDignityEnum dignityEnum) {
        MetaDignity entity = new MetaDignity();
        entity.fid(dignityEnum.fid());
        entity.code(dignityEnum.code());
        entity.text(capitalizeFully(dignityEnum.name()));
        entity.name(capitalizeFully(dignityEnum.dignity().all()[1].name()));
        return entity;
    }

    default void addMetaNaksatraEnum(IMetaJyotisa jyotisa) {
        final List<MetaNaksatra> list = jyotisa.naksatra();
        list.add(NIL_NAKSATRA); // #0
        ISweEnumIterator<INaksatraEnum> iterator = confMetaNaksatras();
        while (iterator.hasNext()) list.add(buildMetaNaksatra(iterator.next()));
    }

    default MetaNaksatra buildMetaNaksatra(INaksatraEnum naksatraEnum) {
        MetaNaksatra entity = new MetaNaksatra();
        entity.fid(naksatraEnum.fid());
        entity.code(naksatraEnum.code());
        entity.name(capitalizeFully(naksatraEnum.naksatra().all()[1].name()));
        entity.text(capitalizeFully(replaceChars(naksatraEnum.name(), '_', '-')));
        return entity;
    }

    void addMetaEventInfo(IMetaJyotisa jyotisa, IKundali kundali);

    default void addMetaVargaGrahas(IMetaJyotisa jyotisa, IKundali kundali) {
        final IGrahas grahas = kundali.grahas();
        final IGrahaEntity lagna = grahas.lagna();
        final Iterator<IVargaEnum> iterator = confMetaVargas();
        final List<IGrahaEntity> filteredGrahas = confMetaGrahasFilter(grahas.all());

        while (iterator.hasNext()) {
            final IVarga varga = iterator.next().varga();
            final MetaObjects objects = new MetaObjects();
            final List<MetaObject> metaGrahas = objects.grahas();
            final int lgRasiFid = varga.rasi(lagna.longitude()).fid();

            jyotisa.objects().put(varga.code(), objects);

            for (IGrahaEntity graha : filteredGrahas) {
                metaGrahas.add(buildMetaVargaGraha(varga, lgRasiFid, graha));
            }

            if (!metaGrahas.isEmpty()) metaGrahas.sort(comparingDouble(MetaObject::vdegr));
            if (themeMetaGrahas()) themeMetaGrahas(metaGrahas);
        }
    }

    default MetaObject buildMetaVargaGraha(IVarga varga, int lagnaRasiFid, IGrahaEntity grahaEntity) {
        final MetaObject obj = new MetaObject();
        final double vargaRasiLongitude = varga.rasiLongitude(grahaEntity.longitude());
        final String degr = toDMSms(vargaRasiLongitude).toString();

        obj.rasi(varga.rasi(grahaEntity.longitude()).fid());
        obj.vdegr((float) (((obj.rasi() - 1) * RASI_LENGTH) + vargaRasiLongitude));
        obj.deg(degr.substring(0, degr.indexOf(ODEGREE_CHAR) + 1));
        obj.name(buildMetaGrahaName(varga, grahaEntity));
        obj.text(buildMetaGrahaText(varga, grahaEntity));
        obj.code(grahaEntity.entityEnum().code());
        if (grahaEntity.vakri()) obj.vakri(1);
        obj.degr(degr);

        if (varga.code().equals(D01_CD)) {
            final INaksatraPada pada = grahaEntity.pada();
            obj.lon(toDMSms(grahaEntity.longitude()).toString());
            obj.npada(buildMetaNaksatraPadaName(varga, grahaEntity));
            obj.bhava(grahaEntity.bhava().fid());
            obj.naksatra(pada.naksatra().fid());
            obj.navamsa(pada.navamsa().fid());
            obj.pada(pada.pada());
        } else {
            obj.bhava(((obj.rasi() + 12 - lagnaRasiFid) % 12 + 1));
        }

        final IDignity dignity = grahaEntity.dignity(varga);
        if (null != dignity) {
            obj.dignity(dignity.fid());
            writeMetaVargaGrahaDignity(obj, dignity);
        }

        return obj;
    }

    default String buildMetaGrahaName(IVarga varga, IGrahaEntity grahaEntity) {
        final IGraha graha = grahaEntity.entityEnum();
        final String name = graha.all()[1].name();
        final StringBuilder builder = new StringBuilder(name.length());

        final boolean printVakri = grahaEntity.vakri()
                && !name.equalsIgnoreCase(RA_CD)
                && !name.equalsIgnoreCase(KE_CD);

        if (printVakri) builder.append('(');
        builder.append(name.charAt(0));

        for (int i = 1; i < name.length(); i++) {
            builder.append(toLowerCase(name.charAt(i)));
        }

        if (printVakri) builder.append(')');
        return builder.toString();
    }

    default String buildMetaGrahaText(IVarga varga, IGrahaEntity grahaEntity) {
        return capitalizeFully(grahaEntity.entityEnum().all()[2].name());
    }

    default String buildMetaNaksatraPadaName(IVarga varga, IGrahaEntity grahaEntity) {
        final INaksatraPada pada = grahaEntity.pada();
        return pada.naksatra().following().name() + PADA_DIGITS[pada.pada()];
    }

    default void addMetaRasiUpagrahas(IMetaJyotisa jyotisa, IKundali kundali) {
        final MetaObjects objects = jyotisa.objects().get(RASI.varga().code());
        if (null == objects) return;

        final List<IUpagrahaEntity> upagrahas = confMetaUpagrahasFilter(kundali.upagrahas().all());
        if (null == upagrahas || upagrahas.isEmpty()) return;

        final List<MetaObject> metaUpagrahas = objects.upagrahas();
        upagrahas.sort(comparingDouble(ISweEnumEntity::longitude));

        for (final IUpagrahaEntity upagrahaEntity : upagrahas) {
            metaUpagrahas.add(buildMetaRasiUpagraha(upagrahaEntity));
        }
    }

    default MetaObject buildMetaRasiUpagraha(IUpagrahaEntity upagrahaEntity) {
        final String degr = toDMSms(rasiDegree(upagrahaEntity.longitude())).toString();
        final IUpagraha upagraha = upagrahaEntity.entityEnum();
        final INaksatraPada pada = upagrahaEntity.pada();
        final MetaObject obj = new MetaObject();

        obj.lon(toDMSms(upagrahaEntity.longitude()).toString());
        obj.deg(degr.substring(0, degr.indexOf(ODEGREE_CHAR) + 1));
        obj.name(buildMetaUpagrahaName(upagrahaEntity));
        obj.text(buildMetaUpagrahaText(upagrahaEntity));
        obj.bhava(upagrahaEntity.bhava().fid());
        obj.naksatra(pada.naksatra().fid());
        obj.navamsa(pada.navamsa().fid());
        obj.rasi(pada.rasi().fid());
        obj.code(upagraha.code());
        obj.pada(pada.pada());
        obj.degr(degr);

        return obj;
    }

    default String buildMetaUpagrahaName(IUpagrahaEntity upagrahaEntity) {
        return capitalizeFully(upagrahaEntity.entityEnum().name());
    }

    default String buildMetaUpagrahaText(IUpagrahaEntity upagrahaEntity) {
        return capitalizeFully(EUpagraha.values()[upagrahaEntity.entityEnum().fid()].name());
    }

    default void writeMetaVargaGrahaDignity(MetaObject metaObject, IDignity dignity) {
        if (null == dignity) return;
        String ds = DIGNITY_SYMBOLS[dignity.uid()];
        if (!EMPTY.equals(ds)) metaObject.deg(ds + metaObject.deg());
    }

}

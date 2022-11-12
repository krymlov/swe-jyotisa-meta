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
import org.jyotisa.api.karaka.ICharaKaraka;
import org.jyotisa.api.naksatra.INaksatraEnum;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.rasi.IRasiEnum;
import org.jyotisa.api.upagraha.IUpagraha;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.api.varga.IVargaEnum;
import org.jyotisa.bhava.EBhava;
import org.jyotisa.dignity.EDignity;
import org.jyotisa.karaka.ECharaKaraka;
import org.jyotisa.meta.app.MetaJyotisa;
import org.jyotisa.meta.app.MetaNorthCalc;
import org.jyotisa.meta.base.MetaTheme;
import org.jyotisa.meta.kundali.*;
import org.jyotisa.meta.objects.MetaObject;
import org.jyotisa.meta.objects.MetaObjects;
import org.jyotisa.meta.options.MetaOption;
import org.jyotisa.meta.options.MetaOptionView;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.upagraha.EUpagraha;
import org.jyotisa.varga.EVarga;
import org.swisseph.api.ISweEnumEntity;
import org.swisseph.api.ISweEnumIterator;

import java.util.*;

import static java.lang.Character.toLowerCase;
import static java.util.Comparator.comparingDouble;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;
import static org.jyotisa.api.graha.IGraha.KE_CD;
import static org.jyotisa.api.graha.IGraha.RA_CD;
import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.meta.kundali.MetaBhava.NIL_BHAVA;
import static org.jyotisa.meta.kundali.MetaDignity.NIL_DIGNITY;
import static org.jyotisa.meta.kundali.MetaKaraka.NIL_KARAKA;
import static org.jyotisa.meta.kundali.MetaRasi.NIL_RASI;
import static org.jyotisa.varga.EVarga.HORA;
import static org.jyotisa.varga.EVarga.RASI;
import static org.swisseph.api.ISweConstants.RASI_LENGTH;
import static org.swisseph.utils.IDegreeUtils.toDMS;
import static swisseph.SweConst.ODEGREE_CHAR;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public interface IMetaJyotisaBuilder extends IMetaJyotisaConfig {

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

        addMetaRasiGrahas(jyotisa, kundali);
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
        final ISweEnumIterator<IVargaEnum> iterator = EVarga.iterator();
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
        Iterator<MetaOptionView> iterator = confMetaViews();
        List<MetaOptionView> viewList = jyotisa.options().views();
        while (iterator.hasNext()) viewList.add(iterator.next());
    }

    default void addMetaKundaliMainBox(IMetaJyotisa jyotisa) {
    }

    default void addMetaSouthStyleInfoBox(IMetaJyotisa jyotisa) {
        if (!confMetaStyle(MetaViewStyle.south)) return;

        List<Integer> mainBox = jyotisa.kundali().mainBox();
        Integer width = mainBox.get(2), height = mainBox.get(3);
        List<Integer> infoBox = jyotisa.kundali().southStyle().infoBox();
        infoBox.add(width / 4);
        infoBox.add(height / 4);
        infoBox.add(width / 2);
        infoBox.add(height / 2);
    }

    default void addMetaSouthStyleViewBox(IMetaJyotisa jyotisa) {
        if (!confMetaStyle(MetaViewStyle.south)) return;

        final List<MetaRasiSeq> viewBox = jyotisa.kundali().southStyle().viewBox();
        final List<Integer> mainBox = jyotisa.kundali().mainBox();

        final int rasiSquareSize = mainBox.get(2) / 4;
        final int w = rasiSquareSize, h = mainBox.get(3) / 4;

        IRasiEnum rasiEnum = ERasi.MESHA;
        int x = rasiSquareSize, y = 0;

        for (int i = 0; i < 12; i++) {
            viewBox.add(buildMetaRasiSeq(x, y, w, h, rasiEnum));
            rasiEnum = rasiEnum.following();
            if (i < 2) x += w;
            if (i >= 2 && i < 5) y += h;
            if (i >= 5 && i < 8) x -= w;
            if (i >= 8) y -= h;
        }
    }

    default MetaRasiSeq buildMetaRasiSeq(int x, int y, int w, int h, IRasiEnum rasiEnum) {
        final MetaRasiSeq sequence = new MetaRasiSeq();
        sequence.shape(new ArrayList<>(4));
        sequence.rasi(rasiEnum.fid());
        sequence.shape().add(x);
        sequence.shape().add(y);
        sequence.shape().add(w);
        sequence.shape().add(h);
        return sequence;
    }

    default void addMetaSouthStyleObjects(IMetaJyotisa jyotisa, IKundali kundali) {
        if (!confMetaStyle(MetaViewStyle.south)) return;

        final Map<String, List<MetaRasiSeq>> mapVargaRasiSeqs = jyotisa.kundali().southStyle().objects();
        final Iterator<IVargaEnum> iterator = EVarga.iteratorFrom(RASI);
        final IGrahaEntity lagna = kundali.grahas().lagna();

        while (iterator.hasNext()) {
            final IVarga varga = iterator.next().varga();
            final List<MetaRasiSeq> rasiSeqs = new ArrayList<>(12);
            final int lagnaRasiFid = varga.rasi(lagna.longitude()).fid();
            mapVargaRasiSeqs.put(varga.code(), rasiSeqs);

            IRasiEnum rasiEnum = ERasi.MESHA;
            for (int i = 0; i < 12; i++) {
                rasiSeqs.add(buildMetaRasiSeq(rasiEnum, lagnaRasiFid));
                rasiEnum = rasiEnum.following();
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
        if (!confMetaStyle(MetaViewStyle.north)) return;

        final List<Integer> mainBox = jyotisa.kundali().mainBox();
        final List<MetaBhavaSeq> viewBox = jyotisa.kundali().northStyle().viewBox();
        final MetaNorthCalc coordsCalc = new MetaNorthCalc(mainBox.get(2), mainBox.get(3));
        IBhavaEnum bhavaEnum = EBhava.TANU;

        for (int i = 0; i < 12; i++) {
            viewBox.add(buildMetaRasiSeq(bhavaEnum, coordsCalc));
            bhavaEnum = bhavaEnum.following();
        }
    }

    default MetaBhavaSeq buildMetaRasiSeq(IBhavaEnum bhavaEnum, MetaNorthCalc coordsCalc) {
        final MetaBhavaSeq sequence = new MetaBhavaSeq();
        sequence.bhavaShape(coordsCalc.calc(bhavaEnum.fid()));
        sequence.rasiShape(coordsCalc.calcRasiCords(bhavaEnum.fid()));
        sequence.grahaShape(coordsCalc.calcPlanetBlockCords(bhavaEnum.fid()));
        sequence.bhava(bhavaEnum.fid());
        return sequence;
    }

    default void addMetaNorthStyleObjects(IMetaJyotisa jyotisa, IKundali kundali) {
        if (!confMetaStyle(MetaViewStyle.north)) return;

        final Map<String, List<MetaBhavaSeq>> mapVargaBhavaSeqs = jyotisa.kundali().northStyle().objects();
        final Iterator<IVargaEnum> iterator = EVarga.iteratorFrom(RASI);
        final IGrahaEntity lagna = kundali.grahas().lagna();

        while (iterator.hasNext()) {
            final IVarga varga = iterator.next().varga();
            final List<MetaBhavaSeq> bhavaSeqs = new ArrayList<>(12);
            final int lagnaRasiFid = varga.rasi(lagna.longitude()).fid();
            mapVargaBhavaSeqs.put(varga.code(), bhavaSeqs);

            IRasiEnum rasiEnum = ERasi.values()[lagnaRasiFid];
            IBhavaEnum bhavaEnum = EBhava.TANU;

            for (int i = 0; i < 12; i++) {
                bhavaSeqs.add(buildMetaRasiSeq(bhavaEnum, rasiEnum));
                bhavaEnum = bhavaEnum.following();
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
        ISweEnumIterator<IRasiEnum> iterator = ERasi.iterator();
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
        ISweEnumIterator<IBhavaEnum> iterator = EBhava.iterator();
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
        ISweEnumIterator<ICharaKaraka> iterator = ECharaKaraka.iterator();
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
        ISweEnumIterator<IDignityEnum> iterator = EDignity.iterator();
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
        list.add(new MetaNaksatra()); // #0
        ISweEnumIterator<INaksatraEnum> iterator = ENaksatra.iterator();
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

    default void addMetaRasiGrahas(IMetaJyotisa jyotisa, IKundali kundali) {
        final int[] sweObjects = kundali.sweObjects().sweSequence().objects();
        final IGrahaEntity[] grahas = kundali.grahas().all();

        final MetaObjects objects = new MetaObjects();
        final List<MetaObject> metaGrahas = objects.grahas();
        jyotisa.objects().put(EVarga.RASI.varga().name(), objects);

        for (int i = 0; i < sweObjects.length && i < grahas.length; i++) {
            metaGrahas.add(buildMetaRasiGraha(grahas[sweObjects[i]]));
        }
    }

    default MetaObject buildMetaRasiGraha(IGrahaEntity grahaEntity) {
        final String degr = toDMS(rasiDegree(grahaEntity.longitude())).toString();
        final INaksatraPada pada = grahaEntity.pada();
        final IGraha graha = grahaEntity.entityEnum();
        final MetaObject obj = new MetaObject();

        IDignity dignity = grahaEntity.dignity();
        if (null != dignity) obj.dignity(dignity.fid());

        obj.name(buildMetaGrahaName(graha, grahaEntity.vakri()));
        obj.deg(degr.substring(0, degr.indexOf(ODEGREE_CHAR) + 1));
        obj.lgtd(toDMS(grahaEntity.longitude()).toString());
        obj.npada(buildMetaNaksatraPadaName(grahaEntity));
        if (grahaEntity.vakri()) obj.vakri(1);
        obj.bhava(grahaEntity.bhava().fid());
        obj.naksatra(pada.naksatra().fid());
        obj.text(buildMetaGrahaText(graha));
        obj.navamsa(pada.navamsa().fid());
        obj.rasi(pada.rasi().fid());
        obj.pada(pada.pada());
        obj.code(graha.code());
        obj.degr(degr);

        return obj;
    }

    default String buildMetaGrahaName(IGraha graha, boolean vakri) {
        final String name = graha.all()[1].name();
        final StringBuilder builder = new StringBuilder(name.length());

        final boolean printVakri = vakri &&
                !name.equalsIgnoreCase(RA_CD) &&
                !name.equalsIgnoreCase(KE_CD);

        if (printVakri) builder.append('(');
        builder.append(name.charAt(0));

        for (int i = 1; i < name.length(); i++) {
            builder.append(toLowerCase(name.charAt(i)));
        }

        if (printVakri) builder.append(')');
        return builder.toString();
    }

    default String buildMetaGrahaText(IGraha graha) {
        return capitalizeFully(graha.all()[2].name());
    }

    default String buildMetaNaksatraPadaName(IGrahaEntity grahaEntity) {
        return grahaEntity.pada().naksatra().following().name() + grahaEntity.pada().pada();
    }

    default void addMetaRasiUpagrahas(IMetaJyotisa jyotisa, IKundali kundali) {
        final MetaObjects objects = jyotisa.objects().get(EVarga.RASI.varga().name());
        if (null == objects) return;

        final List<MetaObject> metaUpagrahas = objects.upagrahas();
        final ArrayList<IUpagrahaEntity> upagrahas = new ArrayList<>(
                Arrays.asList(kundali.upagrahas().all()));

        upagrahas.remove(0);
        upagrahas.sort(comparingDouble(ISweEnumEntity::longitude));

        for (final IUpagrahaEntity upagrahaEntity : upagrahas) {
            metaUpagrahas.add(buildMetaRasiUpagraha(upagrahaEntity));
        }
    }

    default MetaObject buildMetaRasiUpagraha(IUpagrahaEntity upagrahaEntity) {
        final String degr = toDMS(rasiDegree(upagrahaEntity.longitude())).toString();
        final IUpagraha upagraha = upagrahaEntity.entityEnum();
        final INaksatraPada pada = upagrahaEntity.pada();
        final MetaObject obj = new MetaObject();

        obj.lgtd(toDMS(upagrahaEntity.longitude()).toString());
        obj.deg(degr.substring(0, degr.indexOf(ODEGREE_CHAR) + 1));
        obj.bhava(upagrahaEntity.bhava().fid());
        obj.name(buildMetaUpagrahaName(upagraha));
        obj.text(buildMetaUpagrahaText(upagraha));
        obj.naksatra(pada.naksatra().fid());
        obj.navamsa(pada.navamsa().fid());
        obj.rasi(pada.rasi().fid());
        obj.code(upagraha.code());
        obj.pada(pada.pada());
        obj.degr(degr);

        return obj;
    }

    default String buildMetaUpagrahaName(IUpagraha upagraha) {
        return capitalizeFully(upagraha.name());
    }

    default String buildMetaUpagrahaText(IUpagraha upagraha) {
        return capitalizeFully(EUpagraha.values()[upagraha.fid()].name());
    }

    default void addMetaVargaGrahas(IMetaJyotisa jyotisa, IKundali kundali) {
        final Iterator<IVargaEnum> iterator = EVarga.iteratorFrom(HORA);
        final IGrahaEntity[] grahas = kundali.grahas().all();
        final IGrahaEntity lagna = kundali.grahas().lagna();

        while (iterator.hasNext()) {
            final IVarga varga = iterator.next().varga();
            final MetaObjects objects = new MetaObjects();
            final List<MetaObject> metaGrahas = objects.grahas();
            final int lagnaRasiFid = varga.rasi(lagna.longitude()).fid();
            jyotisa.objects().put(varga.code(), objects);

            for (IGrahaEntity graha : grahas) {
                metaGrahas.add(buildMetaVargaGraha(varga, lagnaRasiFid, graha));
            }

            if (!metaGrahas.isEmpty()) metaGrahas.sort(comparingDouble(MetaObject::vdegr));
            if (!objects.upagrahas().isEmpty()) objects.upagrahas().sort(comparingDouble(MetaObject::vdegr));
        }
    }

    default MetaObject buildMetaVargaGraha(IVarga varga, int lagnaRasiFid, IGrahaEntity grahaEntity) {
        final IGraha graha = grahaEntity.entityEnum();
        final MetaObject obj = new MetaObject();
        final double vargaRasiLongitude = varga.rasiLongitude(grahaEntity.longitude());
        final String degr = toDMS(vargaRasiLongitude).toString();

        IDignity dignity = grahaEntity.dignity(varga);
        if (null != dignity) obj.dignity(dignity.fid());

        obj.rasi(varga.rasi(grahaEntity.longitude()).fid());
        obj.vdegr((float) (((obj.rasi() - 1) * RASI_LENGTH) + vargaRasiLongitude));
        obj.deg(degr.substring(0, degr.indexOf(ODEGREE_CHAR) + 1));
        obj.bhava(((obj.rasi() + 12 - lagnaRasiFid) % 12 + 1));
        obj.name(buildMetaGrahaName(graha, grahaEntity.vakri()));
        if (grahaEntity.vakri()) obj.vakri(1);
        obj.text(buildMetaGrahaText(graha));
        obj.code(graha.code());
        obj.degr(degr);

        return obj;
    }
}

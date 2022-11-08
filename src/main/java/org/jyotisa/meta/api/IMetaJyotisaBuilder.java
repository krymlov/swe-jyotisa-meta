/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.api;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.bhava.IBhavaEnum;
import org.jyotisa.api.dignity.IDignityEnum;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.karaka.ICharaKaraka;
import org.jyotisa.api.naksatra.INaksatraEnum;
import org.jyotisa.api.rasi.IRasiEnum;
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
import org.jyotisa.meta.options.MetaOption;
import org.jyotisa.meta.options.MetaOptionView;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.varga.EVarga;
import org.swisseph.api.ISweEnumIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.meta.api.MetaViewStyle.south;
import static org.jyotisa.meta.kundali.MetaBhava.NIL_BHAVA;
import static org.jyotisa.meta.kundali.MetaDignity.NIL_DIGNITY;
import static org.jyotisa.meta.kundali.MetaKaraka.NIL_KARAKA;
import static org.jyotisa.meta.kundali.MetaRasi.NIL_RASI;
import static org.jyotisa.varga.EVarga.RASI;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public interface IMetaJyotisaBuilder {
    String GROUP_GRAHA = "GRAHA";
    String GROUP_VARGA = "VARGA";

    default IMetaJyotisa buildMetaJyotisa(IKundali kundali) {
        final MetaJyotisa jyotisa = new MetaJyotisa();

        addOptionsGroups(jyotisa);
        addOptionsItems(jyotisa);
        addOptionsViews(jyotisa);

        addKundaliMainBox(jyotisa);
        addSouthStyleInfoBox(jyotisa);

        addSouthStyleViewBox(jyotisa);
        addNorthStyleViewBox(jyotisa);

        addSouthStyleObjects(jyotisa, kundali);
        addNorthStyleObjects(jyotisa, kundali);

        addCharaKarakaEnum(jyotisa);
        addNaksatraEnum(jyotisa);
        addDignityEnum(jyotisa);
        addBhavaEnums(jyotisa);
        addRasiEnums(jyotisa);

        addEventInfo(jyotisa, kundali);

        addRasiGrahas(jyotisa, kundali);
        addVargaGrahas(jyotisa, kundali);
        addRasiUpagrahas(jyotisa, kundali);

        return jyotisa;
    }

    default void addOptionsGroups(IMetaJyotisa jyotisa) {
        final MetaTheme grahaGroup = new MetaTheme();
        jyotisa.options().groups().add(grahaGroup);
        grahaGroup.code(GROUP_GRAHA);
        grahaGroup.name("Graha");

        final MetaTheme vargaGroup = new MetaTheme();
        jyotisa.options().groups().add(vargaGroup);
        vargaGroup.code(GROUP_VARGA);
        vargaGroup.name("Varga");
    }

    default void addOptionsItems(IMetaJyotisa jyotisa) {
        ISweEnumIterator<IVargaEnum> iterator = EVarga.iterator();
        while (iterator.hasNext()) {
            IVargaEnum ee = iterator.next();
            MetaOption option = new MetaOption();
            jyotisa.options().items().add(option);
            option.group(GROUP_VARGA);
            option.code(ee.code());
            option.name(ee.code() + " " + ee.name());
        }
    }

    default void addOptionsViews(IMetaJyotisa jyotisa) {
        jyotisa.options().views().add(new MetaOptionView(south, D01_CD));
    }

    default void addKundaliMainBox(IMetaJyotisa jyotisa) {
        List<Integer> mainBox = jyotisa.kundali().mainBox();
        mainBox.add(0);
        mainBox.add(0);
        mainBox.add(1000);
        mainBox.add(400);
    }

    default void addSouthStyleInfoBox(IMetaJyotisa jyotisa) {
        List<Integer> mainBox = jyotisa.kundali().mainBox();
        final Integer width = mainBox.get(2);
        final Integer height = mainBox.get(3);

        List<Integer> infoBox = jyotisa.kundali().southStyle().infoBox();
        infoBox.add(width / 4);
        infoBox.add(height / 4);
        infoBox.add(width / 2);
        infoBox.add(height / 2);
    }

    default void addSouthStyleViewBox(IMetaJyotisa jyotisa) {
        final List<MetaRasiSeq> viewBox = jyotisa.kundali().southStyle().viewBox();
        final List<Integer> mainBox = jyotisa.kundali().mainBox();

        final int rasiSquareSize = mainBox.get(2) / 4;
        final int w = rasiSquareSize, h = mainBox.get(3) / 4;

        IRasiEnum rasiEnum = ERasi.MESHA;
        int x = rasiSquareSize, y = 0;

        for (int i = 0; i < 12; i++) {
            viewBox.add(buildMetaRasiSeq(x,y,w,h,rasiEnum));
            rasiEnum = rasiEnum.following();
            if (i < 2) x += w;
            if (i >= 2 && i < 5) y += h;
            if (i >= 5 && i < 8) x -= w;
            if (i >= 8) y -= h;
        }
    }

    default MetaRasiSeq buildMetaRasiSeq(int x, int y, int w, int h, IRasiEnum rasiEnum) {
        final MetaRasiSeq sequence = new MetaRasiSeq();
        sequence.rasi(rasiEnum.fid());
        sequence.shape().add(x);
        sequence.shape().add(y);
        sequence.shape().add(w);
        sequence.shape().add(h);
        return sequence;
    }

    default void addSouthStyleObjects(IMetaJyotisa jyotisa, IKundali kundali) {
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

    default void addNorthStyleViewBox(IMetaJyotisa jyotisa) {
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

    default void addNorthStyleObjects(IMetaJyotisa jyotisa, IKundali kundali) {
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

    default void addRasiEnums(IMetaJyotisa jyotisa) {
        final List<MetaRasi> list = jyotisa.rasi();
        list.add(NIL_RASI); // #0
        ISweEnumIterator<IRasiEnum> iterator = ERasi.iterator();
        while (iterator.hasNext()) list.add(buildMetaRasi(iterator.next()));
    }

    default MetaRasi buildMetaRasi(IRasiEnum rasiEnum) {
        MetaRasi entity = new MetaRasi();
        entity.fid(rasiEnum.fid());
        entity.code(rasiEnum.code());
        entity.name(rasiEnum.rasi().all()[1].name());
        entity.text(rasiEnum.name());
        entity.desc(rasiEnum.name());
        entity.lord(rasiEnum.rasi().lord().fid());
        entity.start(rasiEnum.segment().start());
        entity.close(rasiEnum.segment().close());
        return entity;
    }

    default void addBhavaEnums(IMetaJyotisa jyotisa) {
        final List<MetaBhava> list = jyotisa.bhava();
        list.add(NIL_BHAVA); // #0
        ISweEnumIterator<IBhavaEnum> iterator = EBhava.iterator();
        while (iterator.hasNext()) list.add(buildMetaBhava(iterator.next()));
    }

    default MetaBhava buildMetaBhava(IBhavaEnum bhavaEnum) {
        MetaBhava entity = new MetaBhava();
        entity.fid(bhavaEnum.fid());
        entity.code(bhavaEnum.code());
        entity.text(bhavaEnum.bhava().all()[1].name());
        entity.desc(bhavaEnum.name());
        entity.name(String.valueOf(bhavaEnum.fid()));
        return entity;
    }

    default void addCharaKarakaEnum(IMetaJyotisa jyotisa) {
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
        entity.text(karaka.name());
        entity.desc(karaka.name());
        return entity;
    }

    default void addDignityEnum(IMetaJyotisa jyotisa) {
        final List<MetaDignity> list = jyotisa.dignity();
        list.add(NIL_DIGNITY); // #0
        ISweEnumIterator<IDignityEnum> iterator = EDignity.iterator();
        while (iterator.hasNext()) list.add(buildMetaDignity(iterator.next()));
    }

    default MetaDignity buildMetaDignity(IDignityEnum dignityEnum) {
        MetaDignity entity = new MetaDignity();
        entity.fid(dignityEnum.fid());
        entity.code(dignityEnum.code());
        entity.text(dignityEnum.name());
        entity.desc(dignityEnum.name());
        entity.name(dignityEnum.dignity().all()[1].name());
        return entity;
    }

    default void addNaksatraEnum(IMetaJyotisa jyotisa) {
        final List<MetaNaksatra> list = jyotisa.naksatra();
        list.add(new MetaNaksatra()); // #0
        ISweEnumIterator<INaksatraEnum> iterator = ENaksatra.iterator();
        while (iterator.hasNext()) list.add(buildMetaNaksatra(iterator.next()));
    }

    default MetaNaksatra buildMetaNaksatra(INaksatraEnum naksatraEnum) {
        MetaNaksatra entity = new MetaNaksatra();
        entity.fid(naksatraEnum.fid());
        entity.code(naksatraEnum.code());
        entity.text(naksatraEnum.name());
        entity.desc(naksatraEnum.name());
        entity.name(naksatraEnum.naksatra().all()[1].name());
        return entity;
    }

    void addEventInfo(IMetaJyotisa jyotisa, IKundali kundali);

    void addRasiGrahas(IMetaJyotisa jyotisa, IKundali kundali);
    MetaObject buildRasiGraha(IGrahaEntity entity);

    void addRasiUpagrahas(IMetaJyotisa jyotisa, IKundali kundali);
    MetaObject buildRasiUpagraha(IUpagrahaEntity entity);

    void addVargaGrahas(IMetaJyotisa jyotisa, IKundali kundali);
    MetaObject buildVargaGraha(IVarga varga, int lagnaRasiFid, IGrahaEntity entity);
}

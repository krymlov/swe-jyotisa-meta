/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.api;

import org.jyotisa.api.IKundali;
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.api.IKundaliFields;
import org.jyotisa.api.karana.IKarana;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.nityayoga.INityaYoga;
import org.jyotisa.api.tithi.ITithi;
import org.jyotisa.app.KundaliFields;
import org.swisseph.api.ISweJulianDate;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.jyotisa.api.bhava.IBhava;
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
import org.jyotisa.api.vimsottari.IVimsottariDasaEnum;
import org.jyotisa.api.vimsottari.IVimsottariDasas;
import org.jyotisa.api.vimsottari.IVimsottariPeriod;
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
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.api.IKundaliFields;
import org.jyotisa.api.karana.IKarana;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.jyotisa.api.nityayoga.INityaYoga;
import org.jyotisa.api.tithi.ITithi;
import org.jyotisa.app.KundaliFields;
import org.swisseph.api.ISweJulianDate;
import org.swisseph.api.ISweObjects;
import org.swisseph.app.SweJulianDate;
import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.bhava.IBhavaChalita;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.rasi.IRasi;
import org.jyotisa.api.varga.IAshtakavarga;
import org.jyotisa.bhava.EBhava;
import org.jyotisa.meta.kundali.MetaAshtakavarga;
import org.jyotisa.meta.kundali.MetaChalita;
import org.jyotisa.meta.kundali.MetaBhinna;
import org.jyotisa.meta.kundali.MetaChalitaBhava;
import org.jyotisa.meta.kundali.MetaLimb;
import org.jyotisa.meta.kundali.MetaPanchanga;
import org.jyotisa.meta.kundali.MetaSarva;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.karana.EKarana;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.nityayoga.ENityaYoga;
import org.jyotisa.tithi.ETithi;
import org.jyotisa.vaara.EVaara;
import org.jyotisa.varga.EVarga;
import org.jyotisa.api.naksatra.INaksatra;
import org.swisseph.api.ISweEnumSequence;
import org.swisseph.api.ISweEnum;
import org.swisseph.api.ISweEnumEntity;
import org.swisseph.api.ISweEnumIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map;

import static java.lang.Character.toLowerCase;
import static java.util.Comparator.comparingDouble;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;
import static org.jyotisa.api.graha.IGraha.KE_CD;
import static org.jyotisa.api.graha.IGraha.RA_CD;
import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;
import static org.swisseph.api.ISweObjects.LG;
import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.meta.kundali.MetaBhava.NIL_BHAVA;
import static org.jyotisa.meta.kundali.MetaDasa.NIL_DASA;
import static org.jyotisa.meta.kundali.MetaDignity.NIL_DIGNITY;
import static org.jyotisa.meta.kundali.MetaKaraka.NIL_KARAKA;
import static org.jyotisa.meta.kundali.MetaNaksatra.NIL_NAKSATRA;
import static org.jyotisa.meta.kundali.MetaRasi.NIL_RASI;
import static org.jyotisa.varga.EVarga.RASI;
import static org.swisseph.api.ISweConstants.RASI_LENGTH;
import static org.swisseph.utils.IDateUtils.F4Y_2M_2D_2H_2M_2S;
import static org.swisseph.utils.IDateUtils.format;
import static org.swisseph.utils.IDegreeUtils.toDMS;
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
        addMetaVimsottariDasaEnum(jyotisa);
        addMetaNaksatraEnum(jyotisa);
        addMetaDignityEnum(jyotisa);
        addMetaBhavaEnums(jyotisa);
        addMetaRasiEnums(jyotisa);

        addMetaEventInfo(jyotisa, kundali);
        addMetaVargaGrahas(jyotisa, kundali);
        addMetaRasiUpagrahas(jyotisa, kundali);

        addMetaPanchanga(jyotisa, kundali);
        addMetaBhavaChalita(jyotisa, kundali);
        addMetaAshtakavarga(jyotisa, kundali);
        addMetaVimsottari(jyotisa, kundali);

        return jyotisa;
    }

    /**
     * Bhava Chalita - Porphyry cusps read as Sripati bhavas, i.e. the cusp is the bhava's
     * <b>middle</b> and the lagna sits halfway along the first one.
     * <p>
     * Each longitude is split into a rasi and a degree-within-it exactly as
     * {@link #buildMetaVargaGraha} splits a graha's, so a consumer renders it against the
     * document's own {@code rasi} table without parsing anything.
     * <p>
     * A chart built without an ascendant reports {@code calculated = false} and no bhavas at all,
     * rather than twelve counted from zero.
     * <p>
     * <b>The grahas listed are the ones {@link #confMetaGrahas()} names</b>, not everything the
     * chalita placed. {@code IBhavaChalita} places every calculated body, so without that filter a
     * feed configured for the ten traditional grahas still showed Uranus, Neptune and Pluto here -
     * and only here, which is the shape of inconsistency a single shared setting prevents.
     */
    default void addMetaBhavaChalita(IMetaJyotisa jyotisa, IKundali kundali) {
        final IBhavaChalita chalita = kundali.bhavaChalita();
        final MetaChalita meta = jyotisa.chalita();

        meta.calculated(chalita.isCalculated());
        if (!chalita.isCalculated()) return;

        final ISweEnumIterator<IBhavaEnum> bhavas = EBhava.iterator();
        // the same grahas the D1 objects carry, so the two cannot disagree about what is shown -
        // IBhavaChalita places every calculated body, the outer three included
        final Set<Integer> shown = confMetaGrahaUids();

        while (bhavas.hasNext()) {
            final IBhava bhava = bhavas.next().bhava();
            final MetaChalitaBhava metaBhava = new MetaChalitaBhava();

            metaBhava.bhava(bhava.fid());
            buildMetaChalitaPoint(chalita.start(bhava), metaBhava::startRasi, metaBhava::start);
            buildMetaChalitaPoint(chalita.madhya(bhava), metaBhava::rasi, metaBhava::madhya);
            buildMetaChalitaPoint(chalita.close(bhava), metaBhava::closeRasi, metaBhava::close);

            for (IGraha graha : chalita.grahas(bhava)) {
                if (shown.contains(graha.uid())) metaBhava.grahas().add(graha.code());
            }

            meta.bhavas().add(metaBhava);
        }
    }

    /** one chalita longitude, as the sign it is in and the position inside that sign */
    default void buildMetaChalitaPoint(double longitude,
                                       java.util.function.Consumer<Integer> rasi,
                                       java.util.function.Consumer<String> degr) {
        rasi.accept(rasiFid0(longitude) + 1);
        degr.accept(toDMSms(rasiDegree(longitude)).toString());
    }

    /**
     * Sarvashtakavarga, one total per rasi, each carrying the bhava it falls in so the table
     * stands on its own without the chart beside it.
     * <p>
     * The eight Bhinnashtakavarga rows go with it, each indexed by rasi in the same order, so the
     * classical table can be rendered without a second pass - but see {@link MetaAshtakavarga}:
     * the total is the seven grahas, and Lagna's row does not belong in it. {@code complete} is
     * false when a point was missing, and then every total is short by that point's contribution.
     */
    default void addMetaAshtakavarga(IMetaJyotisa jyotisa, IKundali kundali) {
        final IAshtakavarga ashtakavarga = kundali.ashtakavarga();
        final MetaAshtakavarga meta = jyotisa.ashtakavarga();

        meta.complete(ashtakavarga.isComplete());

        final int lagnaRasiFid = kundali.sweObjects().signs()[LG];

        final ISweEnumIterator<IRasiEnum> rasis = ERasi.iterator();

        while (rasis.hasNext()) {
            final IRasi rasi = rasis.next().rasi();
            final MetaSarva sarva = new MetaSarva();

            sarva.rasi(rasi.fid());
            sarva.sarva(ashtakavarga.sarva(rasi));

            // the whole-sign distance from the lagna, the convention every bhava column here uses
            if (lagnaRasiFid >= 1) sarva.bhava((rasi.fid() + 12 - lagnaRasiFid) % 12 + 1);

            meta.sarva().add(sarva);
        }

        for (IGraha point : ashtakavarga.points()) {
            final MetaBhinna bhinna = new MetaBhinna();
            final int[] bindu = new int[12];

            final ISweEnumIterator<IRasiEnum> byRasi = ERasi.iterator();
            for (int i = 0; byRasi.hasNext(); i++) bindu[i] = ashtakavarga.bindu(point, byRasi.next().rasi());

            bhinna.graha(point.code());
            bhinna.bindu(bindu);

            meta.bhinna().add(bhinna);
        }
    }

    /**
     * The panchanga, plus the frame the whole document is computed in: the ayanamsa, sidereal time
     * and the sun's own rising and setting.
     * <p>
     * None of it can be derived from the longitudes the feed already carries, which is why it is
     * exported rather than left to the consumer. The percentages are what make the five limbs
     * useful rather than decorative - a tithi at 96% is about to turn.
     * <p>
     * <b>Times are local to the chart's own place</b>, matching the event block rather than the
     * UTC that {@code KundaliFields.toString()} prints, and snapped to whole seconds through
     * {@code KundaliFields.atWholeSecond} so a value never renders one second short.
     */
    default void addMetaPanchanga(IMetaJyotisa jyotisa, IKundali kundali) {
        final MetaPanchanga meta = jyotisa.panchanga();
        final IPanchanga panchanga = kundali.panchanga();
        final IKundaliFields fields = kundali.fields();
        final ISweObjects sweObjects = kundali.sweObjects();

        final INaksatra naksatra = panchanga.pada().naksatra();

        buildMetaLimb(meta.vaara(), EVaara.byVaara(panchanga.vaara()), panchanga.vaara(), null);
        buildMetaLimb(meta.tithi(), ETithi.byTithi(panchanga.tithi()), panchanga.tithi(),
                ITithi.progress(panchanga));
        // INaksatra, not INaksatraPada: the row is the naksatra, and the pada function measures
        // the quarter (3 deg 20') rather than the whole 13 deg 20'. For pada 2 the two read almost
        // like complements - 60.6% of the pada is 40.1% of the naksatra - which is how the wrong
        // one looked "inverted" rather than simply wrong. Kundali.toString() has always used this.
        buildMetaLimb(meta.naksatra(), ENaksatra.byNaksatra(naksatra), naksatra,
                INaksatra.progress(panchanga));
        buildMetaPada(meta.pada(), panchanga);
        buildMetaLimb(meta.nityaYoga(), ENityaYoga.byYoga(panchanga.yoga()), panchanga.yoga(),
                INityaYoga.progress(panchanga));
        buildMetaLimb(meta.karana(), EKarana.byKarana(panchanga.karana()), panchanga.karana(),
                IKarana.progress(panchanga));

        meta.ayanamsa(sweObjects.sweOptions().ayanamsa().name());
        meta.ayanamsaDegr(toDMSms(sweObjects.ayanamsa()).toString());
        meta.siderealTime(toDMS(fields.siderealTime(), true).toString());

        meta.sunrise(localTime(kundali, fields.sunrise()));
        meta.sunset(localTime(kundali, fields.sunset()));
        meta.moonrise(localTime(kundali, fields.moonrise()));
        meta.moonset(localTime(kundali, fields.moonset()));
    }

    /**
     * Filled exactly as {@link #buildMetaBhava} fills a bhava, which is the reference entry that
     * uses all four fields: {@code name} the ordinal, {@code text} the short alias off the leaf,
     * {@code desc} the registry constant's own full name. So {@code Vaara 1 / Syvr / Surya} reads
     * like {@code Bhava 1 / Tan / Tanu}.
     */
    default <E extends ISweEnumSequence<E>> void buildMetaLimb(
            MetaLimb limb, ISweEnum entry, ISweEnumSequence<E> leaf, Double progress) {
        limb.fid(entry.fid());
        limb.code(entry.code());
        limb.name(String.valueOf(entry.fid()));
        limb.text(capitalizeFully(leaf.all()[1].name()));
        // the underscore matters: a bhava is TANU, but a vaara is SURYA_VAARA and a tithi
        // KRISHNA_PANCHAMI, and capitalizeFully alone leaves those as "Surya_vaara"
        limb.desc(capitalizeFully(entry.name().replace('_', ' ')));
        if (null != progress) limb.progress(progress.floatValue());
    }

    /**
     * The naksatra pada, filled by hand rather than through {@link #buildMetaLimb}: the family is
     * computed rather than declared, so it has no registry constant with an alias list to read a
     * short and a long name from. {@code fid} is the composite the family defines - naksatra fid
     * and pada digit, 19 and 2 giving 192 - and {@code name} the pada digit alone, which is the
     * ordinal a bhava puts there.
     */
    default void buildMetaPada(MetaLimb limb, IPanchanga panchanga) {
        final INaksatraPada pada = panchanga.pada();
        final INaksatra naksatra = pada.naksatra();

        limb.fid(pada.fid());
        limb.code(pada.code());
        limb.name(String.valueOf(pada.pada()));
        limb.text(capitalizeFully(naksatra.label()) + SPACE + pada.pada());
        limb.desc(capitalizeFully(ENaksatra.byNaksatra(naksatra).name()) + SPACE + pada.pada());
        limb.progress((float) INaksatraPada.progress(panchanga));
    }

    /** a julian day as local wall-clock time at the chart's own place, to the whole second */
    default String localTime(IKundali kundali, double julianDay) {
        final ISweJulianDate date = kundali.sweObjects().sweJulianDate();
        return format(kundali.sweObjects().swissEph().initDateTime(new SweJulianDate(
                KundaliFields.atWholeSecond(julianDay), date.timeZone())),
                F4Y_2M_2D_2H_2M_2S).toString();
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

    /**
     * The interior of the South grid - the four cells the chart does not use, which carry the
     * event text and the varga selector.
     * <p>
     * <b>Both edges are derived, never stepped.</b> The near edge is the first quarter and the far
     * edge is {@code size - quarter}, which is how the grid's own lines are placed; taking
     * {@code 2 * quarter} for the span instead puts the far edge on {@code 3 * quarter}, and the
     * two agree only when the size is a multiple of four. They did in portrait (396: 297 either
     * way) and did not in landscape (302: 226 against 228, and 439: 329 against 330), so the box
     * overhung the grid by two pixels down and one across - drawn as a second line a hair outside
     * the real one.
     */
    default void addMetaSouthStyleInfoBox(IMetaJyotisa jyotisa) {
        if (!confMetaStyle(ViewStyle.south)) return;

        List<Integer> mainBox = jyotisa.kundali().mainBox();
        List<Integer> infoBox = jyotisa.kundali().southStyle().infoBox();

        final int nearX = Math.round(mainBox.get(2) / 4f);
        final int nearY = Math.round(mainBox.get(3) / 4f);

        infoBox.add(nearX);
        infoBox.add(nearY);
        infoBox.add(mainBox.get(2) - nearX - nearX);
        infoBox.add(mainBox.get(3) - nearY - nearY);
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

    /**
     * The Vimsottari dasha, to {@code confMetaVimsottariLevels()} deep.
     * <p>
     * Only starting moments are written, and only the ninth mahadasha's close - the periods of any
     * level tile their parent exactly, so every other end is the next sibling's start. See
     * {@link MetaVimsottari} for the rule and for why the alternative was not worth its size.
     * <p>
     * <b>Times are local to the chart's own place</b>, like the panchanga block and unlike the UTC
     * that {@code Kundali.toString()} prints, because this is what a reader is shown.
     * <p>
     * The chart is asked for exactly the requested depth, but may answer with a deeper tree it
     * already had, so the recursion stops on the depth asked for rather than on running out of
     * sub-periods.
     */
    default void addMetaVimsottari(IMetaJyotisa jyotisa, IKundali kundali) {
        final int levels = confMetaVimsottariLevels();
        if (levels < 1) return;

        final MetaVimsottari meta = jyotisa.vimsottari();
        final IVimsottariDasas dasas = kundali.vimsottari(levels);
        final List<IVimsottariPeriod> mahadashas = dasas.periods();

        meta.levels(levels);
        meta.year(dasas.year().name());
        meta.to(localTime(kundali, mahadashas.get(mahadashas.size() - 1).close()));

        for (final IVimsottariPeriod period : mahadashas) {
            meta.periods().add(buildMetaPeriod(kundali, period, levels));
        }
    }

    /** one period and, unless this is the deepest level asked for, its nine sub-periods */
    default MetaPeriod buildMetaPeriod(IKundali kundali, IVimsottariPeriod period, int levels) {
        final MetaPeriod meta = new MetaPeriod();
        meta.fid(period.dasa().fid());
        meta.from(localTime(kundali, period.start()));

        if (period.level() < levels) {
            final List<MetaPeriod> subs = new ArrayList<>(9);
            for (final IVimsottariPeriod sub : period.periods()) {
                subs.add(buildMetaPeriod(kundali, sub, levels));
            }
            meta.periods(subs);
        }

        return meta;
    }

    default void addMetaVimsottariDasaEnum(IMetaJyotisa jyotisa) {
        final List<MetaDasa> list = jyotisa.dasa();
        list.add(NIL_DASA); // #0
        final ISweEnumIterator<IVimsottariDasaEnum> iterator = confMetaDasas();
        while (iterator.hasNext()) list.add(buildMetaDasa(iterator.next()));
    }

    /**
     * {@code text} comes from the ruling graha rather than from the dasha's own alias list, whose
     * second constant is the code again - see {@link MetaDasa}.
     */
    default MetaDasa buildMetaDasa(IVimsottariDasaEnum entry) {
        final MetaDasa meta = new MetaDasa();
        meta.fid(entry.fid());
        meta.code(entry.code());
        meta.name(String.valueOf(entry.fid()));
        meta.text(entry.dasa().lord().label());
        meta.desc(capitalizeFully(entry.name().replace('_', ' ')));
        meta.years((int) entry.dasa().length());
        return meta;
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
        final Iterator<IVargaEnum> confVargas = confMetaVargas();
        final List<IGrahaEntity> filteredGrahas = confMetaGrahasFilter(grahas.all());

        while (confVargas.hasNext()) {
            final IVarga varga = confVargas.next().varga();
            final MetaObjects objects = new MetaObjects();
            final List<MetaObject> metaGrahas = objects.grahas();
            final int lgRasiFid = varga.rasi(lagna.longitude()).fid();

            jyotisa.objects().put(varga.code(), objects);

            for (IGrahaEntity graha : filteredGrahas) {
                MetaObject metaObject = buildMetaVargaGraha(varga, lgRasiFid, graha);
                if (null != metaObject) metaGrahas.add(metaObject);
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
            final IBhava bhava = grahaEntity.bhava();
            final INaksatraPada pada = grahaEntity.pada();
            obj.lon(toDMSms(grahaEntity.longitude()).toString());
            obj.npada(buildMetaNaksatraPadaName(varga, grahaEntity));
            obj.bhava(null != bhava ? bhava.fid() : 0);
            obj.naksatra(pada.naksatra().fid());
            obj.navamsa(pada.navamsa().fid());
            obj.pada(pada.pada());

            // the chara karaka belongs to the graha in the natal chart, not to a division of it,
            // so it is written here beside the other D-1-only fields rather than in every varga.
            // MetaObject has declared this field since the model was written and nothing filled
            // it, so the karaka reference table was being emitted with nothing to point at it.
            final ICharaKaraka karaka = grahaEntity.charaKaraka();
            if (null != karaka) obj.karaka(karaka.fid());
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
        return pada.naksatra().label() + PADA_DIGITS[pada.pada()];
    }

    default void addMetaRasiUpagrahas(IMetaJyotisa jyotisa, IKundali kundali) {
        final MetaObjects objects = jyotisa.objects().get(RASI.varga().code());
        if (null == objects) return;

        final List<IUpagrahaEntity> upagrahas = confMetaUpagrahasFilter(kundali.upagrahas().all());
        if (null == upagrahas || upagrahas.isEmpty()) return;

        final List<MetaObject> metaUpagrahas = objects.upagrahas();
        upagrahas.sort(comparingDouble(ISweEnumEntity::longitude));

        for (final IUpagrahaEntity upagrahaEntity : upagrahas) {
            MetaObject metaObject = buildMetaRasiUpagraha(upagrahaEntity);
            if (null != metaObject) metaUpagrahas.add(metaObject);
        }
    }

    default MetaObject buildMetaRasiUpagraha(IUpagrahaEntity upagrahaEntity) {
        final String degr = toDMSms(rasiDegree(upagrahaEntity.longitude())).toString();
        final IUpagraha upagraha = upagrahaEntity.entityEnum();
        final INaksatraPada pada = upagrahaEntity.pada();
        final IBhava bhava = upagrahaEntity.bhava();
        final MetaObject obj = new MetaObject();

        obj.lon(toDMSms(upagrahaEntity.longitude()).toString());
        obj.deg(degr.substring(0, degr.indexOf(ODEGREE_CHAR) + 1));
        obj.name(buildMetaUpagrahaName(upagrahaEntity));
        obj.text(buildMetaUpagrahaText(upagrahaEntity));
        obj.bhava(null != bhava ? bhava.fid() : 0);
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

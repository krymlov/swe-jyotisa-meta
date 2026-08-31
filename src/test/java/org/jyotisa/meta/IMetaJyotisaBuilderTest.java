package org.jyotisa.meta;

import org.junit.jupiter.api.Test;
import org.jyotisa.api.IKundali;
import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.api.varga.IVarga;
import org.jyotisa.meta.api.IMetaJyotisa;
import org.jyotisa.meta.api.IMetaJyotisaBuilder;
import org.jyotisa.meta.api.IMetaNorthStyleCalc;
import org.jyotisa.meta.api.ViewStyle;
import org.jyotisa.meta.app.MetaJyotisa;
import org.jyotisa.api.graha.IGrahaEnum;
import org.jyotisa.graha.EGraha;
import org.jyotisa.meta.kundali.MetaBhavaSeq;
import org.jyotisa.meta.kundali.MetaChalitaBhava;
import org.swisseph.api.ISweEnumIterator;
import org.jyotisa.meta.objects.MetaObject;
import org.jyotisa.meta.objects.MetaObjects;
import org.jyotisa.varga.EVarga;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static java.util.TimeZone.getTimeZone;
import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.api.varga.IVarga.D09_CD;
import static org.jyotisa.meta.api.ViewStyle.north;
import static org.jyotisa.meta.api.ViewStyle.south;
import static org.swisseph.utils.IDegreeUtils.toDMSms;

/**
 * Integration coverage for {@link IMetaJyotisaBuilder#buildMetaJyotisa(IKundali)}: builds a
 * real chart (same fixture date as {@code ChennaiJsonTest}/{@code KyivJsonTest}) and checks
 * that the produced {@link IMetaJyotisa} faithfully transcribes the underlying, already
 * validated {@link IKundali} data - rather than re-deriving Jyotisha math (that is
 * swe-jyotisa-lib's own responsibility, covered there), this pins that the presentation
 * layer does not drop, mislabel or misalign a field on its way into the DTO.
 */
class IMetaJyotisaBuilderTest extends AbstractTest implements IMetaJyotisaBuilder {

    private static final IMetaNorthStyleCalc STUB_NORTH_CALC = new IMetaNorthStyleCalc() {
        @Override
        public int[][] calc(int bhava) {
            return new int[][]{{bhava, 0}};
        }

        @Override
        public int[] calcRasiCords(int rasi) {
            return new int[]{rasi, 1};
        }

        @Override
        public int[] calcPlanetBlockCords(int block) {
            return new int[]{block, 2};
        }
    };

    @Override
    public void addMetaEventInfo(IMetaJyotisa jyotisa, IKundali kundali) {
        jyotisa.event().entity().name("unit-test-event");
    }

    private IKundali fixedChennaiKundali() {
        Calendar calendar = newCalendar(getTimeZone(ASIA_CALCUTTA));
        calendar.set(1962, Calendar.FEBRUARY, 4, 8, 30, 0);
        return newChennaiKundali(getSwephExp(), calendar);
    }

    private static MetaObject byCode(List<MetaObject> list, String code) {
        for (MetaObject obj : list) if (code.equals(obj.code())) return obj;
        throw new AssertionError("no object with code " + code + " in " + list);
    }

    /**
     * Bhava Chalita lists the grahas {@code confMetaGrahas()} names, and no others.
     * <p>
     * {@code IBhavaChalita} places every calculated body, the outer three included, so a feed
     * configured for the ten traditional grahas used to show Uranus, Neptune and Pluto in this one
     * block and nowhere else. Reported from a real chart. Both halves are asserted - that the outer
     * three are gone, and that the ten that should be there still are - because a filter that
     * dropped everything would satisfy the first alone.
     */
    @Test
    void buildMetaJyotisa_chalitaListsOnlyTheConfiguredGrahas() {
        final IMetaJyotisa jyotisa = buildMetaJyotisa(fixedChennaiKundali());
        final List<String> placed = new java.util.ArrayList<>();

        for (MetaChalitaBhava bhava : jyotisa.chalita().bhavas()) placed.addAll(bhava.grahas());

        assertFalse(placed.isEmpty(), "the chalita should place the chart's grahas somewhere");
        for (String outer : new String[]{"SW", "SM", "TE"}) {
            assertFalse(placed.contains(outer),
                    outer + " is not among the configured grahas, so the chalita must not list it: "
                            + placed);
        }
        for (String graha : new String[]{"LG", "SY", "CH", "MA", "BU", "GU", "SK", "SA", "RA", "KE"}) {
            assertTrue(placed.contains(graha),
                    graha + " is configured and calculated, so the chalita must list it: " + placed);
        }
    }

    /**
     * And it follows the setting rather than a list of its own: narrowing the configured grahas
     * narrows the chalita with it, which is the whole reason there is no second switch for this.
     */
    @Test
    void buildMetaJyotisa_chalitaFollowsANarrowedGrahaConfiguration() {
        final IMetaJyotisaBuilder narrowed = new IMetaJyotisaBuilder() {
            @Override
            public void addMetaEventInfo(IMetaJyotisa jyotisa, IKundali kundali) {
                // nothing this test reads
            }

            @Override
            public ISweEnumIterator<IGrahaEnum> confMetaGrahas() {
                return EGraha.iterator(EGraha.SURYA, EGraha.CHANDRA);
            }
        };

        final List<String> placed = new java.util.ArrayList<>();
        for (MetaChalitaBhava bhava : narrowed.buildMetaJyotisa(fixedChennaiKundali())
                .chalita().bhavas()) placed.addAll(bhava.grahas());

        assertEquals(2, placed.size(), "only the two configured grahas should be placed: " + placed);
        assertTrue(placed.contains("SY") && placed.contains("CH"), "expected SY and CH: " + placed);
    }

    /**
     * A chart whose Moon could not be placed carries no dasha, and the feed leaves the block out
     * rather than writing a closing moment with nothing inside it.
     * <p>
     * The NaN is how a caller says "indeterminable" - an event with a date but no time has to,
     * since the Moon crosses half a sign in a day - and it reached this builder as an
     * {@code IndexOutOfBoundsException} on the last mahadasha of an empty list.
     */
    @Test
    void buildMetaJyotisa_omitsTheDashaWhenTheMoonCannotBePlaced() {
        final IKundali kundali = fixedChennaiKundali();
        kundali.sweObjects().longitudes()[org.swisseph.api.ISweObjects.CH] = Double.NaN;

        final IMetaJyotisa jyotisa = assertDoesNotThrow(() -> buildMetaJyotisa(kundali),
                "an indeterminable Moon is a state to report, not one to throw on");

        assertTrue(jyotisa.vimsottari().periods().isEmpty(),
                "there is no dasha to compute without the Moon's naksatra");
        assertNull(jyotisa.vimsottari().to(),
                "and no closing moment either - the block is left out, not half written");
    }

    @Test
    void buildMetaJyotisa_populatesEveryTopLevelSection() {
        IMetaJyotisa jyotisa = buildMetaJyotisa(fixedChennaiKundali());

        assertNotNull(jyotisa.event());
        assertNotNull(jyotisa.options());
        assertNotNull(jyotisa.kundali());
        assertNotNull(jyotisa.objects());
        assertFalse(jyotisa.objects().isEmpty());

        assertEquals("unit-test-event", jyotisa.event().entity().name());
        assertEquals("2.10.03j6f", jyotisa.appVersion());
    }

    @Test
    void buildMetaJyotisa_objectsMapHasExactlyOneEntryPerConfiguredVarga() {
        IMetaJyotisa jyotisa = buildMetaJyotisa(fixedChennaiKundali());
        Map<String, MetaObjects> objects = jyotisa.objects();

        assertEquals(23, objects.size());
        assertTrue(objects.containsKey(D01_CD));
        assertTrue(objects.containsKey(D09_CD));
        assertTrue(objects.containsKey("D144"));
    }

    @Test
    void buildMetaJyotisa_enumReferenceLists_startWithNilThenOnePerConfiguredMember() {
        IMetaJyotisa jyotisa = buildMetaJyotisa(fixedChennaiKundali());

        assertEquals(28, jyotisa.naksatra().size()); // NIL + 27
        assertNull(jyotisa.naksatra().get(0).fid());

        assertEquals(12, jyotisa.dignity().size()); // NIL + 11
        assertNull(jyotisa.dignity().get(0).fid());

        assertEquals(9, jyotisa.karaka().size()); // NIL + 8
        assertNull(jyotisa.karaka().get(0).fid());

        assertEquals(13, jyotisa.bhava().size()); // NIL + 12
        assertNull(jyotisa.bhava().get(0).fid());

        assertEquals(13, jyotisa.rasi().size()); // NIL + 12
        assertNull(jyotisa.rasi().get(0).fid());
    }

    @Test
    void buildMetaJyotisa_d1Grahas_containsExactlyTheTenTraditionalBodiesSortedByDegree() {
        IMetaJyotisa jyotisa = buildMetaJyotisa(fixedChennaiKundali());
        List<MetaObject> grahas = jyotisa.objects().get(D01_CD).grahas();

        assertEquals(10, grahas.size());
        for (int i = 1; i < grahas.size(); i++) {
            assertTrue(grahas.get(i - 1).vdegr() <= grahas.get(i).vdegr(),
                    "grahas must be sorted ascending by vdegr");
        }
    }

    @Test
    void buildMetaJyotisa_d1Grahas_faithfullyTranscribeTheUnderlyingGrahaEntities() {
        IKundali kundali = fixedChennaiKundali();
        IMetaJyotisa jyotisa = buildMetaJyotisa(kundali);
        List<MetaObject> grahas = jyotisa.objects().get(D01_CD).grahas();
        IVarga d1 = EVarga.RASI.varga();

        IGrahaEntity[] traditional = {
                kundali.grahas().lagna(), kundali.grahas().surya(), kundali.grahas().chandra(),
                kundali.grahas().mangala(), kundali.grahas().budha(), kundali.grahas().guru(),
                kundali.grahas().shukra(), kundali.grahas().shani(), kundali.grahas().rahu(),
                kundali.grahas().ketu()
        };

        for (IGrahaEntity entity : traditional) {
            MetaObject obj = byCode(grahas, entity.entityEnum().code());

            int expectedRasi = d1.rasi(entity.longitude()).fid();
            assertEquals(expectedRasi, obj.rasi(), "rasi mismatch for " + entity.entityEnum().code());

            assertEquals(entity.pada().naksatra().fid(), obj.naksatra(),
                    "naksatra mismatch for " + entity.entityEnum().code());
            assertEquals(entity.pada().navamsa().fid(), obj.navamsa(),
                    "navamsa mismatch for " + entity.entityEnum().code());
            assertEquals(entity.pada().pada(), obj.pada(),
                    "pada mismatch for " + entity.entityEnum().code());

            assertNotNull(entity.bhava(), "fixture must always resolve a bhava");
            assertEquals(entity.bhava().fid(), obj.bhava(), "bhava mismatch for " + entity.entityEnum().code());

            assertEquals(toDMSms(entity.longitude()).toString(), obj.lon(),
                    "lon mismatch for " + entity.entityEnum().code());

            if (entity.vakri()) {
                assertEquals(1, obj.vakri(), "vakri flag mismatch for " + entity.entityEnum().code());
            } else {
                assertNull(obj.vakri(), "non-vakri graha must leave vakri unset, not 0, for "
                        + entity.entityEnum().code());
            }

            IDignity dignity = entity.dignity(d1);
            if (null == dignity) {
                assertNull(obj.dignity(), "dignity mismatch for " + entity.entityEnum().code());
            } else {
                assertEquals(dignity.fid(), obj.dignity(), "dignity mismatch for " + entity.entityEnum().code());
            }
        }
    }

    @Test
    void buildMetaJyotisa_nonD1Varga_bhavaComesFromSignDistanceToTheVargaLagna_notFromRealCusps() {
        IKundali kundali = fixedChennaiKundali();
        IMetaJyotisa jyotisa = buildMetaJyotisa(kundali);
        List<MetaObject> d9Grahas = jyotisa.objects().get(D09_CD).grahas();
        IVarga d9 = EVarga.NAVAMSA.varga();
        int lagnaRasiInD9 = d9.rasi(kundali.grahas().lagna().longitude()).fid();

        for (MetaObject obj : d9Grahas) {
            int expectedBhava = (obj.rasi() + 12 - lagnaRasiInD9) % 12 + 1;
            assertEquals(expectedBhava, obj.bhava(), "D9 bhava mismatch for " + obj.code());
            assertNull(obj.naksatra(), "naksatra/pada/navamsa are only meaningful in D1");
            assertNull(obj.pada());
            assertNull(obj.navamsa());
            assertNull(obj.lon());
        }
    }

    @Test
    void buildMetaJyotisa_upagrahas_onlyTheFiveImplementedOnesSortedByLongitude() {
        IKundali kundali = fixedChennaiKundali();
        IMetaJyotisa jyotisa = buildMetaJyotisa(kundali);
        List<MetaObject> upagrahas = jyotisa.objects().get(D01_CD).upagrahas();

        assertEquals(5, upagrahas.size());

        double previous = -1;
        for (MetaObject obj : upagrahas) {
            IUpagrahaEntity entity = findUpagrahaByCode(kundali, obj.code());
            assertNotNull(entity, "no underlying upagraha entity for code " + obj.code());
            assertTrue(entity.longitude() >= previous, "upagrahas must be sorted ascending by longitude");
            previous = entity.longitude();

            assertEquals(entity.pada().rasi().fid(), obj.rasi());
            assertEquals(entity.pada().naksatra().fid(), obj.naksatra());
            assertEquals(entity.pada().navamsa().fid(), obj.navamsa());
            assertEquals(entity.pada().pada(), obj.pada());
            assertEquals(entity.bhava().fid(), obj.bhava());
        }
    }

    private static IUpagrahaEntity findUpagrahaByCode(IKundali kundali, String code) {
        for (IUpagrahaEntity e : kundali.upagrahas().all()) {
            if (null != e && e.entityEnum().code().equals(code)) return e;
        }
        return null;
    }

    @Test
    void buildMetaJyotisa_southOnlyConfig_leavesNorthStyleEmpty() {
        IMetaJyotisaBuilder southOnly = new IMetaJyotisaBuilder() {
            @Override
            public ViewStyle[] confMetaStyles() {
                return new ViewStyle[]{south};
            }

            @Override
            public void addMetaEventInfo(IMetaJyotisa jyotisa, IKundali kundali) {
            }
        };

        IMetaJyotisa jyotisa = southOnly.buildMetaJyotisa(fixedChennaiKundali());

        assertFalse(jyotisa.kundali().southStyle().infoBox().isEmpty());
        assertFalse(jyotisa.kundali().southStyle().objects().isEmpty());

        assertTrue(jyotisa.kundali().northStyle().infoBox().isEmpty());
        assertTrue(jyotisa.kundali().northStyle().viewBox().isEmpty());
        assertTrue(jyotisa.kundali().northStyle().objects().isEmpty());
    }

    @Test
    void buildMetaJyotisa_northOnlyConfigWithoutCoordsCalc_populatesObjectsButNoViewBoxShapes() {
        IMetaJyotisaBuilder northOnly = new IMetaJyotisaBuilder() {
            @Override
            public ViewStyle[] confMetaStyles() {
                return new ViewStyle[]{north};
            }

            @Override
            public void addMetaEventInfo(IMetaJyotisa jyotisa, IKundali kundali) {
            }
        };

        IMetaJyotisa jyotisa = northOnly.buildMetaJyotisa(fixedChennaiKundali());

        assertFalse(jyotisa.kundali().northStyle().objects().isEmpty());
        assertTrue(jyotisa.kundali().northStyle().viewBox().isEmpty(),
                "without a confMetaStyleNorthCalc override no view box shapes can be computed");
    }

    @Test
    void buildMetaJyotisa_northOnlyConfigWithCoordsCalc_populatesViewBoxShapes() {
        IMetaJyotisaBuilder northWithCalc = new IMetaJyotisaBuilder() {
            @Override
            public ViewStyle[] confMetaStyles() {
                return new ViewStyle[]{north};
            }

            @Override
            public IMetaNorthStyleCalc confMetaStyleNorthCalc(IMetaJyotisa jyotisa) {
                return STUB_NORTH_CALC;
            }

            @Override
            public void addMetaEventInfo(IMetaJyotisa jyotisa, IKundali kundali) {
            }
        };

        IMetaJyotisa jyotisa = northWithCalc.buildMetaJyotisa(fixedChennaiKundali());
        List<MetaBhavaSeq> viewBox = jyotisa.kundali().northStyle().viewBox();

        assertEquals(12, viewBox.size());
        for (MetaBhavaSeq seq : viewBox) {
            assertArrayEquals(new int[][]{{seq.bhava(), 0}}, seq.bhavaShape());
            assertArrayEquals(new int[]{seq.bhava(), 1}, seq.rasiShape());
            assertArrayEquals(new int[]{seq.bhava(), 2}, seq.grahaShape());
        }
    }

    @Test
    void buildMetaJyotisa_isDeterministic_rebuildingTheSameKundaliProducesAnEqualDocument() {
        IKundali kundali = fixedChennaiKundali();
        IMetaJyotisa first = buildMetaJyotisa(kundali);
        IMetaJyotisa second = buildMetaJyotisa(kundali);

        assertMetaDiff(first, second);
    }

    @Test
    void buildMetaJyotisa_survivesAJacksonRoundTripUnchanged() throws Exception {
        IMetaJyotisa jyotisa = buildMetaJyotisa(fixedChennaiKundali());
        String json = printJyotisa(jyotisa);

        MetaJyotisa roundTripped = OBJECT_MAPPER.readValue(json, MetaJyotisa.class);

        assertMetaDiff(jyotisa, roundTripped);
    }
}

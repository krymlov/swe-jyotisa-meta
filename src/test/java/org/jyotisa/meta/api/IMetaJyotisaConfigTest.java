package org.jyotisa.meta.api;

import org.junit.jupiter.api.Test;
import org.jyotisa.api.bhava.IBhavaEnum;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.graha.IGrahaEnum;
import org.jyotisa.api.upagraha.IUpagrahaEntity;
import org.jyotisa.api.upagraha.IUpagrahaEnum;
import org.jyotisa.graha.EGraha;
import org.jyotisa.meta.options.MetaView;
import org.jyotisa.upagraha.EUpagraha;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.api.varga.IVarga.D09_CD;
import static org.jyotisa.meta.api.ViewStyle.north;
import static org.jyotisa.meta.api.ViewStyle.south;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercises every default method on {@link IMetaJyotisaConfig} directly, independent
 * of a real chart, plus the configuration overrides used by the two existing
 * integration fixtures ({@code ChennaiJsonTest}: defaults, {@code KyivJsonTest}: north-only).
 */
class IMetaJyotisaConfigTest {

    private final IMetaJyotisaConfig defaultConfig = new IMetaJyotisaConfig() {
    };

    @Test
    void confMetaStyles_defaultsToBothSouthAndNorth() {
        assertArrayEquals(new ViewStyle[]{south, north}, defaultConfig.confMetaStyles());
    }

    @Test
    void confMetaStyle_matchesOnlyStylesInTheConfiguredArray() {
        assertTrue(defaultConfig.confMetaStyle(south));
        assertTrue(defaultConfig.confMetaStyle(north));

        IMetaJyotisaConfig northOnly = new IMetaJyotisaConfig() {
            @Override
            public ViewStyle[] confMetaStyles() {
                return new ViewStyle[]{north};
            }
        };
        assertTrue(northOnly.confMetaStyle(north));
        assertFalse(northOnly.confMetaStyle(south));
    }

    @Test
    void confMetaViews_withTwoStylesYieldsOneD1ViewPerStyleAndNoD9() {
        List<MetaView> views = collect(defaultConfig.confMetaViews());

        assertEquals(2, views.size());
        assertEquals(south, views.get(0).style());
        assertEquals(D01_CD, views.get(0).view());
        assertEquals(north, views.get(1).style());
        assertEquals(D01_CD, views.get(1).view());
    }

    @Test
    void confMetaViews_withASingleStyleAlsoAddsAD9ViewForThatStyle() {
        IMetaJyotisaConfig northOnly = new IMetaJyotisaConfig() {
            @Override
            public ViewStyle[] confMetaStyles() {
                return new ViewStyle[]{north};
            }
        };

        List<MetaView> views = collect(northOnly.confMetaViews());

        assertEquals(2, views.size());
        assertEquals(north, views.get(0).style());
        assertEquals(D01_CD, views.get(0).view());
        assertEquals(north, views.get(1).style());
        assertEquals(D09_CD, views.get(1).view());
    }

    @Test
    void confMetaViews_rejectsAnEmptyStyleArray() {
        IMetaJyotisaConfig noStyles = new IMetaJyotisaConfig() {
            @Override
            public ViewStyle[] confMetaStyles() {
                return new ViewStyle[0];
            }
        };
        assertThrows(IllegalArgumentException.class, noStyles::confMetaViews);
    }

    @Test
    void confMetaViews_rejectsANullStyleArray() {
        IMetaJyotisaConfig nullStyles = new IMetaJyotisaConfig() {
            @Override
            public ViewStyle[] confMetaStyles() {
                return null;
            }
        };
        assertThrows(IllegalArgumentException.class, nullStyles::confMetaViews);
    }

    @Test
    void confMetaCharaKarakas_iteratesAllEightKarakas() {
        assertEquals(8, count(defaultConfig.confMetaCharaKarakas()));
    }

    @Test
    void confMetaNaksatras_iteratesAllTwentySevenNaksatras() {
        assertEquals(27, count(defaultConfig.confMetaNaksatras()));
    }

    @Test
    void confMetaDignities_iteratesAllElevenDignities() {
        assertEquals(11, count(defaultConfig.confMetaDignities()));
    }

    @Test
    void confMetaVargas_iteratesAllTwentyThreeVargas() {
        assertEquals(23, count(defaultConfig.confMetaVargas()));
    }

    @Test
    void confMetaBhavas_iteratesAllTwelveBhavas() {
        assertEquals(12, count(defaultConfig.confMetaBhavas()));
    }

    @Test
    void confMetaRasis_iteratesAllTwelveRasis() {
        assertEquals(12, count(defaultConfig.confMetaRasis()));
    }

    @Test
    void confMetaGrahas_iteratesLagnaThroughKetu_tenTraditionalBodies() {
        List<String> codes = new ArrayList<>();
        Iterator<IGrahaEnum> it = defaultConfig.confMetaGrahas();
        while (it.hasNext()) codes.add(it.next().graha().code());

        assertEquals(10, codes.size());
        assertEquals("LG", codes.get(0));
        assertEquals("KE", codes.get(codes.size() - 1));
        assertFalse(codes.contains("SW"), "Uranus is not a traditional graha");
        assertFalse(codes.contains("SM"), "Neptune is not a traditional graha");
        assertFalse(codes.contains("TE"), "Pluto is not a traditional graha");
    }

    @Test
    void confMetaUpagrahas_iteratesDhumaThroughUpaketu_theFiveImplementedOnes() {
        List<String> codes = new ArrayList<>();
        Iterator<IUpagrahaEnum> it = defaultConfig.confMetaUpagrahas();
        while (it.hasNext()) codes.add(it.next().upagraha().code());

        assertEquals(5, codes.size());
        assertEquals("UG1", codes.get(0));
        assertEquals("UG5", codes.get(codes.size() - 1));
        assertFalse(codes.contains("UG10"), "Gulika is not implemented downstream yet");
        assertFalse(codes.contains("UG11"), "Maandi is not implemented downstream yet");
    }

    @Test
    void confMetaStyleNorthCalc_defaultsToNull() {
        assertNull(defaultConfig.confMetaStyleNorthCalc(null));
    }

    @Test
    void confMetaGrahasFilter_keepsOnlyConfiguredGrahasInEncounterOrder_skipsNullsAndUnmatched() {
        // deliberately shuffled and sparse, mirroring what a real IGrahaEntity[] all() looks like
        IGrahaEntity[] all = new IGrahaEntity[EGraha.values().length];
        all[EGraha.KETU.uid()] = stubGraha(EGraha.KETU);
        all[EGraha.SURYA.uid()] = stubGraha(EGraha.SURYA);
        all[EGraha.LAGNA.uid()] = stubGraha(EGraha.LAGNA);
        // SWETA/SYAMA/TEEVRA intentionally present but must be filtered out
        all[EGraha.SWETA.uid()] = stubGraha(EGraha.SWETA);

        List<IGrahaEntity> filtered = defaultConfig.confMetaGrahasFilter(all);

        assertEquals(3, filtered.size());
        assertEquals("LG", filtered.get(0).entityEnum().code());
        assertEquals("SY", filtered.get(1).entityEnum().code());
        assertEquals("KE", filtered.get(2).entityEnum().code());
    }

    @Test
    void confMetaUpagrahasFilter_keepsOnlyConfiguredUpagrahasInEncounterOrder() {
        IUpagrahaEntity[] all = new IUpagrahaEntity[EUpagraha.values().length];
        all[EUpagraha.UPAKETU.uid()] = stubUpagraha(EUpagraha.UPAKETU);
        all[EUpagraha.DHUMA.uid()] = stubUpagraha(EUpagraha.DHUMA);
        // KAALA is not among the 5 implemented upagrahas and must be filtered out
        all[EUpagraha.KAALA.uid()] = stubUpagraha(EUpagraha.KAALA);

        List<IUpagrahaEntity> filtered = defaultConfig.confMetaUpagrahasFilter(all);

        assertEquals(2, filtered.size());
        assertEquals("UG1", filtered.get(0).entityEnum().code());
        assertEquals("UG5", filtered.get(1).entityEnum().code());
    }

    private static List<MetaView> collect(Iterator<MetaView> iterator) {
        List<MetaView> list = new ArrayList<>();
        while (iterator.hasNext()) list.add(iterator.next());
        return list;
    }

    private static int count(Iterator<?> iterator) {
        int n = 0;
        while (iterator.hasNext()) {
            iterator.next();
            n++;
        }
        return n;
    }

    private static IGrahaEntity stubGraha(EGraha graha) {
        return (IGrahaEntity) java.lang.reflect.Proxy.newProxyInstance(
                IGrahaEntity.class.getClassLoader(), new Class[]{IGrahaEntity.class},
                (proxy, method, args) -> {
                    if ("entityEnum".equals(method.getName())) return graha.graha();
                    return defaultValue(method.getReturnType());
                });
    }

    private static IUpagrahaEntity stubUpagraha(EUpagraha upagraha) {
        return (IUpagrahaEntity) java.lang.reflect.Proxy.newProxyInstance(
                IUpagrahaEntity.class.getClassLoader(), new Class[]{IUpagrahaEntity.class},
                (proxy, method, args) -> {
                    if ("entityEnum".equals(method.getName())) return upagraha.upagraha();
                    return defaultValue(method.getReturnType());
                });
    }

    private static Object defaultValue(Class<?> type) {
        if (!type.isPrimitive()) return null;
        if (type == boolean.class) return false;
        if (type == double.class) return 0d;
        if (type == float.class) return 0f;
        if (type == long.class) return 0L;
        return 0;
    }
}

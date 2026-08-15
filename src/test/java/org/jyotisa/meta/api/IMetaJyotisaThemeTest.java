package org.jyotisa.meta.api;

import org.junit.jupiter.api.Test;
import org.jyotisa.meta.objects.MetaObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link IMetaJyotisaTheme#themeMetaGrahas(List)} shrinks the font size of every graha
 * crowded into the same rasi, down to a configured floor. These tests pin the exact
 * arithmetic, including the case that used to undershoot that floor.
 */
class IMetaJyotisaThemeTest {

    private final IMetaJyotisaTheme theme = new IMetaJyotisaTheme() {
    };

    private static MetaObject graha(int rasi, boolean vakri) {
        MetaObject obj = new MetaObject();
        obj.rasi(rasi);
        if (vakri) obj.vakri(1);
        return obj;
    }

    @Test
    void themeMetaGrahas_ignoresNullOrEmptyLists() {
        assertDoesNotThrow(() -> theme.themeMetaGrahas(null));
        assertDoesNotThrow(() -> theme.themeMetaGrahas(Collections.emptyList()));
    }

    @Test
    void themeMetaGrahas_defaultTuningConstants() {
        assertEquals(26, theme.themeMetaGrahasBaseSize());
        assertEquals(14, theme.themeMetaGrahasBaseSizeMinVal());
        assertEquals(2, theme.themeMetaGrahasBaseSizeDecVal());
        assertEquals(1, theme.themeMetaGrahasBaseSizeDecValVakri());
        assertFalse(theme.themeMetaGrahas());
    }

    @Test
    void themeMetaGrahas_oneGrahaAloneInARasiKeepsBaseSizeMinusOneStep() {
        MetaObject g = graha(1, false);
        theme.themeMetaGrahas(new ArrayList<>(Collections.singletonList(g)));
        assertEquals("24", g.size());
    }

    @Test
    void themeMetaGrahas_vakriGrahaGetsAnExtraDecrement() {
        MetaObject g = graha(1, true);
        theme.themeMetaGrahas(new ArrayList<>(Collections.singletonList(g)));
        assertEquals("23", g.size());
    }

    @Test
    void themeMetaGrahas_sizeIsSharedByEveryGrahaInTheSameRasi() {
        MetaObject g1 = graha(3, false);
        MetaObject g2 = graha(3, true);
        List<MetaObject> list = new ArrayList<>(java.util.Arrays.asList(g1, g2));

        theme.themeMetaGrahas(list);

        // base(26) -2 (g1) -2 (g2) -1 (g2 vakri) = 21, applied to both
        assertEquals("21", g1.size());
        assertEquals("21", g2.size());
    }

    @Test
    void themeMetaGrahas_differentRasisAreSizedIndependently() {
        MetaObject inRasi1 = graha(1, false);
        MetaObject inRasi2 = graha(2, false);
        MetaObject anotherInRasi1 = graha(1, false);

        theme.themeMetaGrahas(new ArrayList<>(java.util.Arrays.asList(inRasi1, inRasi2, anotherInRasi1)));

        assertEquals("22", inRasi1.size());       // 26 - 2 - 2
        assertEquals("22", anotherInRasi1.size());
        assertEquals("24", inRasi2.size());        // 26 - 2, alone
    }

    @Test
    void themeMetaGrahas_neverShrinksBelowTheConfiguredMinimum_manyGrahasClamp() {
        // enough grahas in one rasi to run the plain 26 -> 14 ladder past its floor
        List<MetaObject> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) list.add(graha(7, false));

        theme.themeMetaGrahas(list);

        for (MetaObject g : list) assertEquals("14", g.size());
    }

    @Test
    void themeMetaGrahas_neverUndershootsTheMinimumWhenVakriParityShiftsItOdd() {
        // Regression: base(26) -2(non-vakri) -> 24; -2-1(vakri) -> 21 (odd from here on);
        // four more plain -2 steps used to walk 21 -> 19 -> 17 -> 15 -> 13, one below the
        // documented floor of 14, because the old guard only checked the size *before*
        // subtracting, not that the result would still respect the floor.
        List<MetaObject> list = new ArrayList<>();
        list.add(graha(9, false));
        list.add(graha(9, true));
        for (int i = 0; i < 4; i++) list.add(graha(9, false));

        theme.themeMetaGrahas(list);

        for (MetaObject g : list) {
            int size = Integer.parseInt(g.size());
            assertTrue(size >= theme.themeMetaGrahasBaseSizeMinVal(),
                    "size " + size + " must never be below the configured minimum "
                            + theme.themeMetaGrahasBaseSizeMinVal());
        }
        assertEquals("14", list.get(0).size());
    }

    @Test
    void themeMetaGrahas_vakriDecrementAlsoRespectsTheMinimumOnceAtTheFloor() {
        // Drive the rasi's size to exactly the floor with plain decrements, then add
        // one more vakri graha: the extra -1 must not push it under the floor either.
        List<MetaObject> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) list.add(graha(4, false)); // 26 -> 14 exactly
        list.add(graha(4, true));

        theme.themeMetaGrahas(list);

        for (MetaObject g : list) assertEquals("14", g.size());
    }
}

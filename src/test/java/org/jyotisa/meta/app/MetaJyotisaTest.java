package org.jyotisa.meta.app;

import org.junit.jupiter.api.Test;
import org.jyotisa.bhava.EBhava;
import org.jyotisa.dignity.EDignity;
import org.jyotisa.karaka.ECharaKaraka;
import org.jyotisa.meta.api.IMetaJyotisa;
import org.jyotisa.naksatra.ENaksatra;
import org.jyotisa.rasi.ERasi;
import org.jyotisa.varga.EVarga;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The plain-POJO contract of {@link MetaJyotisa}: sensible, stable defaults and
 * that every top-level section required by {@link IMetaJyotisa} is reachable
 * without the builder ever having touched it.
 */
class MetaJyotisaTest {

    @Test
    void defaultVersionsMatchTheCurrentlyDependedOnSwissephVersion() {
        MetaJyotisa jyotisa = new MetaJyotisa();

        // Kept in sync with pom.xml's <version> and its swe-jyotisa dependency version.
        // A stale value here (e.g. an old "j3d") silently ships wrong metadata in every
        // produced document even though the library underneath was upgraded.
        assertEquals("2.10.03j6f", jyotisa.appVersion());
        assertEquals("2.10.03j6f", jyotisa.metaVersion());
        assertEquals("swe-jyotisa", jyotisa.appName());
    }

    @Test
    void versionsAreMutable() {
        MetaJyotisa jyotisa = new MetaJyotisa();
        jyotisa.appVersion("9.9.9");
        jyotisa.metaVersion("9.9.9");
        jyotisa.appName("custom-app");

        assertEquals("9.9.9", jyotisa.appVersion());
        assertEquals("9.9.9", jyotisa.metaVersion());
        assertEquals("custom-app", jyotisa.appName());
    }

    @Test
    void topLevelSectionsAreNeverNullAndStable() {
        MetaJyotisa jyotisa = new MetaJyotisa();

        assertNotNull(jyotisa.event());
        assertNotNull(jyotisa.options());
        assertNotNull(jyotisa.kundali());
        assertNotNull(jyotisa.objects());

        assertSame(jyotisa.event(), jyotisa.event());
        assertSame(jyotisa.options(), jyotisa.options());
        assertSame(jyotisa.kundali(), jyotisa.kundali());
        assertSame(jyotisa.objects(), jyotisa.objects());
    }

    @Test
    void enumReferenceListsStartEmptyButAreMutable() {
        MetaJyotisa jyotisa = new MetaJyotisa();

        assertTrue(jyotisa.dignity().isEmpty());
        assertTrue(jyotisa.naksatra().isEmpty());
        assertTrue(jyotisa.karaka().isEmpty());
        assertTrue(jyotisa.bhava().isEmpty());
        assertTrue(jyotisa.rasi().isEmpty());

        // capacity hints only, must not truncate anything appended beyond them
        for (int i = 0; i < EDignity.values().length + 5; i++) jyotisa.dignity().add(null);
        for (int i = 0; i < ENaksatra.values().length + 5; i++) jyotisa.naksatra().add(null);
        for (int i = 0; i < ECharaKaraka.values().length + 5; i++) jyotisa.karaka().add(null);
        for (int i = 0; i < EBhava.values().length + 5; i++) jyotisa.bhava().add(null);
        for (int i = 0; i < ERasi.values().length + 5; i++) jyotisa.rasi().add(null);

        assertEquals(EDignity.values().length + 5, jyotisa.dignity().size());
        assertEquals(ENaksatra.values().length + 5, jyotisa.naksatra().size());
        assertEquals(ECharaKaraka.values().length + 5, jyotisa.karaka().size());
        assertEquals(EBhava.values().length + 5, jyotisa.bhava().size());
        assertEquals(ERasi.values().length + 5, jyotisa.rasi().size());
    }

    @Test
    void objectsMapStartsEmptyAndAcceptsAnyVargaCode() {
        MetaJyotisa jyotisa = new MetaJyotisa();
        assertTrue(jyotisa.objects().isEmpty());

        for (org.jyotisa.api.varga.IVargaEnum vargaEnum : EVarga.values()) {
            if (0 == vargaEnum.fid()) continue; // NIL
            jyotisa.objects().put(vargaEnum.code(), new org.jyotisa.meta.objects.MetaObjects());
        }

        assertEquals(23, jyotisa.objects().size());
    }
}

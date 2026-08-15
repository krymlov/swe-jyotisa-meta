package org.jyotisa.meta.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetaObjectsTest {

    @Test
    void metaObjects_listsAreNeverNullAndStartEmpty() {
        MetaObjects objects = new MetaObjects();
        assertNotNull(objects.grahas());
        assertNotNull(objects.arudhas());
        assertNotNull(objects.upagrahas());
        assertTrue(objects.grahas().isEmpty());
        assertTrue(objects.arudhas().isEmpty());
        assertTrue(objects.upagrahas().isEmpty());
    }

    @Test
    void metaObjects_listsAreMutableAndStable() {
        MetaObjects objects = new MetaObjects();
        MetaObject graha = new MetaObject();
        graha.code("SY");
        objects.grahas().add(graha);

        assertEquals(1, objects.grahas().size());
        assertSame(graha, objects.grahas().get(0));
        assertSame(objects.grahas(), objects.grahas());
    }

    @Test
    void metaObject_allFieldsRoundTrip() {
        MetaObject obj = new MetaObject();
        obj.rasi(5);
        obj.bhava(8);
        obj.vakri(1);
        obj.deg("18°");
        obj.degr("18°11'40.99\"");
        obj.vdegr(18.19472f);
        obj.lat("00°00'00\"");
        obj.lon("18°11'40.99\"");
        obj.npada("BHA²");
        obj.naksatra(2);
        obj.pada(2);
        obj.dignity(6);
        obj.navamsa(6);
        obj.karaka(1);

        assertEquals(5, obj.rasi());
        assertEquals(8, obj.bhava());
        assertEquals(1, obj.vakri());
        assertEquals("18°", obj.deg());
        assertEquals("18°11'40.99\"", obj.degr());
        assertEquals(18.19472f, obj.vdegr());
        assertEquals("00°00'00\"", obj.lat());
        assertEquals("18°11'40.99\"", obj.lon());
        assertEquals("BHA²", obj.npada());
        assertEquals(2, obj.naksatra());
        assertEquals(2, obj.pada());
        assertEquals(6, obj.dignity());
        assertEquals(6, obj.navamsa());
        assertEquals(1, obj.karaka());
    }

    @Test
    void metaObject_allFieldsDefaultToNull() {
        MetaObject obj = new MetaObject();
        assertNull(obj.rasi());
        assertNull(obj.bhava());
        assertNull(obj.vakri());
        assertNull(obj.deg());
        assertNull(obj.degr());
        assertNull(obj.vdegr());
        assertNull(obj.lat());
        assertNull(obj.lon());
        assertNull(obj.npada());
        assertNull(obj.naksatra());
        assertNull(obj.pada());
        assertNull(obj.dignity());
        assertNull(obj.navamsa());
        assertNull(obj.karaka());
    }

    @Test
    void metaObject_inheritsCodeThemeFields() {
        MetaObject obj = new MetaObject();
        obj.code("RA");
        obj.name("Ra");
        obj.text("Rahu");
        obj.size("23");

        assertEquals("RA", obj.code());
        assertEquals("Ra", obj.name());
        assertEquals("Rahu", obj.text());
        assertEquals("23", obj.size());
    }
}

package org.jyotisa.meta.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Covers the base POJO inheritance chain shared by (almost) every meta DTO:
 * MetaCode -&gt; MetaName -&gt; MetaText -&gt; MetaDesc -&gt; MetaTheme -&gt; MetaGroup.
 * Each class only adds one field over its parent; these tests pin that every
 * accessor along the whole chain is reachable and round-trips independently.
 */
class MetaBaseTest {

    @Test
    void metaCode_roundTripsFidAndCode() {
        MetaCode code = new MetaCode();
        assertNull(code.fid());
        assertNull(code.code());

        code.fid(7);
        code.code("D7");

        assertEquals(7, code.fid());
        assertEquals("D7", code.code());
    }

    @Test
    void metaName_addsNameOverCode() {
        MetaName name = new MetaName();
        name.fid(1);
        name.code("D1");
        name.name("Rasi");

        assertEquals(1, name.fid());
        assertEquals("D1", name.code());
        assertEquals("Rasi", name.name());
    }

    @Test
    void metaText_addsTextOverName() {
        MetaText text = new MetaText();
        text.fid(9);
        text.code("D9");
        text.name("Nav");
        text.text("Navamsa");

        assertEquals(9, text.fid());
        assertEquals("D9", text.code());
        assertEquals("Nav", text.name());
        assertEquals("Navamsa", text.text());
    }

    @Test
    void metaDesc_addsDescOverText() {
        MetaDesc desc = new MetaDesc();
        desc.code("B1");
        desc.name("1");
        desc.text("Tn");
        desc.desc("Tanu");

        assertEquals("B1", desc.code());
        assertEquals("1", desc.name());
        assertEquals("Tn", desc.text());
        assertEquals("Tanu", desc.desc());
    }

    @Test
    void metaTheme_addsStylingFieldsOverDesc() {
        MetaTheme theme = new MetaTheme();
        theme.code("D1");
        theme.weight("bold");
        theme.style("italic");
        theme.size("20");
        theme.color("red");
        theme.family("serif");

        assertEquals("D1", theme.code());
        assertEquals("bold", theme.weight());
        assertEquals("italic", theme.style());
        assertEquals("20", theme.size());
        assertEquals("red", theme.color());
        assertEquals("serif", theme.family());
    }

    @Test
    void metaTheme_stylingFieldsDefaultToNull() {
        MetaTheme theme = new MetaTheme();
        assertNull(theme.weight());
        assertNull(theme.style());
        assertNull(theme.size());
        assertNull(theme.color());
        assertNull(theme.family());
    }

    @Test
    void metaGroup_addsGroupOverTheme() {
        MetaGroup group = new MetaGroup();
        group.code("D1");
        group.name("D1 Rasi");
        group.group("EVarga");

        assertEquals("D1", group.code());
        assertEquals("D1 Rasi", group.name());
        assertEquals("EVarga", group.group());
    }

    @Test
    void metaGroup_groupDefaultsToNull() {
        assertNull(new MetaGroup().group());
    }
}

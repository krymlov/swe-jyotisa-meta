package org.jyotisa.meta.options;

import org.junit.jupiter.api.Test;
import org.jyotisa.meta.api.ViewStyle;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.api.varga.IVarga.D09_CD;
import static org.jyotisa.meta.api.ViewStyle.north;
import static org.jyotisa.meta.api.ViewStyle.south;

class MetaOptionsTest {

    @Test
    void metaOptions_listsAreNeverNullAndStartEmpty() {
        MetaOptions options = new MetaOptions();
        assertNotNull(options.groups());
        assertNotNull(options.items());
        assertNotNull(options.views());
        assertTrue(options.groups().isEmpty());
        assertTrue(options.items().isEmpty());
        assertTrue(options.views().isEmpty());
    }

    @Test
    void metaOptions_accessorsReturnTheSameListEveryCall() {
        MetaOptions options = new MetaOptions();
        assertSame(options.groups(), options.groups());
        assertSame(options.items(), options.items());
        assertSame(options.views(), options.views());
    }

    @Test
    void metaOption_inheritsGroupAndTheme() {
        MetaOption option = new MetaOption();
        option.group("EVarga");
        option.code("D1");
        option.name("D1 Rasi");

        assertEquals("EVarga", option.group());
        assertEquals("D1", option.code());
        assertEquals("D1 Rasi", option.name());
    }

    @Test
    void metaView_defaultConstructorIsD1South() {
        MetaView view = new MetaView();
        assertEquals(D01_CD, view.view());
        assertEquals(south, view.style());
    }

    @Test
    void metaView_parameterizedConstructorSetsStyleAndView() {
        MetaView view = new MetaView(north, D09_CD);
        assertEquals(D09_CD, view.view());
        assertEquals(north, view.style());
    }

    @Test
    void metaView_settersOverrideConstructorValues() {
        MetaView view = new MetaView(south, D01_CD);
        view.view(D09_CD);
        view.style(north);

        assertEquals(D09_CD, view.view());
        assertEquals(north, view.style());
    }

    @Test
    void viewStyle_hasExactlySouthAndNorth() {
        assertArrayEquals(new ViewStyle[]{south, north}, ViewStyle.values());
    }
}

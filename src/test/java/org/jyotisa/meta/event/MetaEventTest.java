package org.jyotisa.meta.event;

import org.junit.jupiter.api.Test;
import org.jyotisa.meta.api.EventType;

import static org.junit.jupiter.api.Assertions.*;

class MetaEventTest {

    @Test
    void metaEvent_defaultsToKundaliType() {
        MetaEvent event = new MetaEvent();
        assertEquals(EventType.kundali, event.type());
    }

    @Test
    void metaEvent_typeIsMutable() {
        MetaEvent event = new MetaEvent();
        event.type(EventType.prasna);
        assertEquals(EventType.prasna, event.type());
    }

    @Test
    void metaEvent_nestedSectionsAreNeverNull() {
        MetaEvent event = new MetaEvent();
        assertNotNull(event.entity());
        assertNotNull(event.location());
        assertNotNull(event.datetime());
    }

    @Test
    void metaEvent_nestedAccessorsReturnTheSameInstanceEveryCall() {
        MetaEvent event = new MetaEvent();
        assertSame(event.entity(), event.entity());
        assertSame(event.location(), event.location());
        assertSame(event.datetime(), event.datetime());
    }

    @Test
    void metaEntity_roundTripsThemeAndLink() {
        MetaEntity entity = new MetaEntity();
        entity.name("Kyiv 2022");
        entity.text("Kundali of Kyiv 2022");
        entity.link("https://example.org");

        assertEquals("Kyiv 2022", entity.name());
        assertEquals("Kundali of Kyiv 2022", entity.text());
        assertEquals("https://example.org", entity.link());
        assertNull(new MetaEntity().link());
    }

    @Test
    void metaLocation_roundTripsLatLon() {
        MetaLocation location = new MetaLocation();
        location.lat("50°26'00\"N");
        location.lon("30°31'00\"E");

        assertEquals("50°26'00\"N", location.lat());
        assertEquals("30°31'00\"E", location.lon());
    }

    @Test
    void metaDateTime_roundTripsDateTimeZone() {
        MetaDateTime dateTime = new MetaDateTime();
        dateTime.date("2022–11–21");
        dateTime.time("12:00:00");
        dateTime.zone("(UTC+2.0)");

        assertEquals("2022–11–21", dateTime.date());
        assertEquals("12:00:00", dateTime.time());
        assertEquals("(UTC+2.0)", dateTime.zone());
    }
}

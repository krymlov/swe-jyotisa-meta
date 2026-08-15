package org.jyotisa.meta.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class EventTypeTest {

    @Test
    void eventType_hasExactlyPrasnaAndKundaliInThatOrder() {
        assertArrayEquals(new EventType[]{EventType.prasna, EventType.kundali}, EventType.values());
    }
}

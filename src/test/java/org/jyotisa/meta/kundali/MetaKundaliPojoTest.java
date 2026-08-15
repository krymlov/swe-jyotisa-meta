package org.jyotisa.meta.kundali;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetaKundaliPojoTest {

    @Test
    void metaKundali_defaultMainBoxIsA640SquareAtOrigin() {
        MetaKundali kundali = new MetaKundali();
        assertEquals(Arrays.asList(0, 0, 640, 640), kundali.mainBox());
    }

    @Test
    void metaKundali_mainBoxIsMutable() {
        MetaKundali kundali = new MetaKundali();
        kundali.mainBox().set(2, 800);
        assertEquals(800, kundali.mainBox().get(2));
    }

    @Test
    void metaKundali_styleSectionsAreNeverNullAndStable() {
        MetaKundali kundali = new MetaKundali();
        assertNotNull(kundali.southStyle());
        assertNotNull(kundali.northStyle());
        assertSame(kundali.southStyle(), kundali.southStyle());
        assertSame(kundali.northStyle(), kundali.northStyle());
    }

    @Test
    void metaSouthStyle_collectionsStartEmptyAndAreStable() {
        MetaSouthStyle style = new MetaSouthStyle();
        assertTrue(style.infoBox().isEmpty());
        assertTrue(style.viewBox().isEmpty());
        assertTrue(style.objects().isEmpty());
        assertSame(style.infoBox(), style.infoBox());
        assertSame(style.objects(), style.objects());
    }

    @Test
    void metaNorthStyle_collectionsStartEmptyAndAreStable() {
        MetaNorthStyle style = new MetaNorthStyle();
        assertTrue(style.infoBox().isEmpty());
        assertTrue(style.viewBox().isEmpty());
        assertTrue(style.objects().isEmpty());
        assertSame(style.infoBox(), style.infoBox());
        assertSame(style.objects(), style.objects());
    }

    @Test
    void metaRasiSeq_roundTripsRasiBhavaShape() {
        MetaRasiSeq seq = new MetaRasiSeq();
        seq.rasi(5);
        seq.bhava(8);
        List<Integer> shape = Arrays.asList(1, 2, 3, 4);
        seq.shape(shape);

        assertEquals(5, seq.rasi());
        assertEquals(8, seq.bhava());
        assertEquals(shape, seq.shape());
    }

    @Test
    void metaBhavaSeq_roundTripsBhavaRasiAndShapes() {
        MetaBhavaSeq seq = new MetaBhavaSeq();
        seq.bhava(3);
        seq.rasi(7);
        int[][] bhavaShape = {{0, 0}, {1, 1}};
        int[] rasiShape = {5, 6};
        int[] grahaShape = {7, 8};
        seq.bhavaShape(bhavaShape);
        seq.rasiShape(rasiShape);
        seq.grahaShape(grahaShape);

        assertEquals(3, seq.bhava());
        assertEquals(7, seq.rasi());
        assertSame(bhavaShape, seq.bhavaShape());
        assertSame(rasiShape, seq.rasiShape());
        assertSame(grahaShape, seq.grahaShape());
    }

    @Test
    void metaRasi_hasANilSentinelWithNoFidOrCode() {
        assertNotNull(MetaRasi.NIL_RASI);
        assertNull(MetaRasi.NIL_RASI.fid());
        assertNull(MetaRasi.NIL_RASI.code());
    }

    @Test
    void metaRasi_roundTripsLordAndSegment() {
        MetaRasi rasi = new MetaRasi();
        rasi.fid(1);
        rasi.code("R1");
        rasi.lord(9);
        rasi.start(0d);
        rasi.close(30d);

        assertEquals(1, rasi.fid());
        assertEquals("R1", rasi.code());
        assertEquals(9, rasi.lord());
        assertEquals(0d, rasi.start());
        assertEquals(30d, rasi.close());
    }

    @Test
    void metaBhava_hasANilSentinel() {
        assertNotNull(MetaBhava.NIL_BHAVA);
        assertNull(MetaBhava.NIL_BHAVA.fid());
    }

    @Test
    void metaDignity_hasANilSentinel() {
        assertNotNull(MetaDignity.NIL_DIGNITY);
        assertNull(MetaDignity.NIL_DIGNITY.fid());
    }

    @Test
    void metaKaraka_hasANilSentinel() {
        assertNotNull(MetaKaraka.NIL_KARAKA);
        assertNull(MetaKaraka.NIL_KARAKA.fid());
    }

    @Test
    void metaNaksatra_hasANilSentinelAndRoundTripsLord() {
        assertNotNull(MetaNaksatra.NIL_NAKSATRA);
        assertNull(MetaNaksatra.NIL_NAKSATRA.fid());

        MetaNaksatra naksatra = new MetaNaksatra();
        naksatra.lord(4);
        assertEquals(4, naksatra.lord());
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-01
 */

package org.jyotisa.meta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.jyotisa.api.IKundali;
import org.jyotisa.app.Kundali;
import org.jyotisa.meta.api.IMetaJyotisa;
import org.jyotisa.meta.api.IMetaJyotisaPojo;
import org.swisseph.ISwissEph;
import org.swisseph.SwephNative;
import org.swisseph.api.ISweGeoLocation;
import org.swisseph.app.SweGeoLocation;
import org.swisseph.app.SweJulianDate;
import org.swisseph.app.SweObjects;

import java.util.Calendar;
import java.util.TimeZone;

import static java.util.TimeZone.getTimeZone;
import static org.jyotisa.app.KundaliOptions.KUNDALI_7_KARAKAS;
import static org.jyotisa.app.KundaliOptions.KUNDALI_8_KARAKAS;
import static org.swisseph.api.ISweConstants.EPHE_PATH;
import static org.swisseph.app.SweObjectsOptions.LAHIRI_CITRAPAKSA;
import static org.swisseph.app.SweObjectsOptions.LAHIRI_TRADITIONAL;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-11
 */
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public abstract class AbstractTest {
    /**
     * Place : Chennai, Tamil Nadu, India<br>
     * Location : 13.09, 80.28 Time Zone : IST (+05:30)
     */
    public static final ISweGeoLocation GEO_CHENNAI = new SweGeoLocation(80 + (16 / 60.), 13 + (5 / 60.), 6.7);

    /**
     * Place : Kyiv, Ukraine<br>
     * Location : 50°27'N, 30°31'E. Time Zone : (+02:00)
     */
    public static final ISweGeoLocation GEO_KYIV = new SweGeoLocation(30 + (31 / 60.), 50 + (26 / 60.), 180);


    protected static final ThreadLocal<ISwissEph> SWEPH_EXPS = new ThreadLocal<>();
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected static final String ASIA_CALCUTTA = "Asia/Calcutta";
    protected static final String EUROPE_KIEV = "Europe/Kiev";

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    protected static SwephNative newSwephExp() {
        return new SwephNative(EPHE_PATH);
    }

    public static ISwissEph getSwephExp() {
        ISwissEph swissEph = SWEPH_EXPS.get();

        if (null == swissEph) {
            swissEph = newSwephExp();
            SWEPH_EXPS.set(swissEph);
        }

        return swissEph;
    }

    public static void closeSwephExp() {
        try (ISwissEph swissEph = SWEPH_EXPS.get()) {
            if (null == swissEph) return;
            SWEPH_EXPS.remove();
        }
    }

    protected Calendar newCalendar(TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    protected IKundali newChennaiKundali(ISwissEph swissEph) {
        return new Kundali(KUNDALI_7_KARAKAS, new SweObjects(swissEph, new SweJulianDate(
                newCalendar(getTimeZone(ASIA_CALCUTTA))), GEO_CHENNAI, LAHIRI_TRADITIONAL).completeBuild());
    }

    protected IKundali newKyivKundali(ISwissEph swissEph) {
        return new Kundali(KUNDALI_8_KARAKAS, new SweObjects(swissEph, new SweJulianDate(
                newCalendar(getTimeZone(EUROPE_KIEV))), GEO_KYIV, LAHIRI_CITRAPAKSA).completeBuild());
    }

    @BeforeAll
    protected void callBeforeAll() {
    }

    @BeforeEach
    protected void callBeforeEach() {
    }

    @AfterEach
    protected void callAfterEach() {
        closeSwephExp();
    }

    @AfterAll
    protected void callAfterAll() {
    }

    protected String printJyotisa(IMetaJyotisa jyotisa) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(jyotisa);
    }

    protected String printPrettyJyotisa(IMetaJyotisa jyotisa) throws JsonProcessingException {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jyotisa);
    }

    protected void assertMetaDiff(IMetaJyotisaPojo pojo1, IMetaJyotisaPojo pojo2) {
        Javers javers = JaversBuilder.javers().build();
        Diff diff = javers.compare(pojo1, pojo2);
        Assertions.assertFalse(diff.hasChanges());
    }
}

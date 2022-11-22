package org.jyotisa.meta;

import org.junit.jupiter.api.Test;
import org.jyotisa.api.IKundali;
import org.jyotisa.meta.api.EventType;
import org.jyotisa.meta.api.IMetaJyotisa;
import org.jyotisa.meta.api.IMetaJyotisaBuilder;
import org.jyotisa.meta.app.MetaJyotisa;
import org.swisseph.api.ISweJulianDate;
import org.swisseph.utils.IDateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.TimeZone.getTimeZone;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.swisseph.utils.IDateUtils.F2H_2M_2S;
import static org.swisseph.utils.IDateUtils.F4Y_2M_2D;
import static org.swisseph.utils.IDegreeUtils.toLAT;
import static org.swisseph.utils.IDegreeUtils.toLON;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class ChennaiJsonTest extends AbstractTest implements IMetaJyotisaBuilder {

    @Override
    public void addMetaEventInfo(IMetaJyotisa jyotisa, IKundali kundali) {
        final ISweJulianDate date = kundali.sweJulianDate();
        final int year = date.year();

        jyotisa.event().entity().name("Chennai " + year);
        jyotisa.event().entity().text("Kundali of " + jyotisa.event().entity().name());

        jyotisa.event().location().name("Chennai, India");
        jyotisa.event().location().text("Chennai " + year + ", India");
        jyotisa.event().location().lttd(toLAT(kundali.sweLocation().latitude()).toString());
        jyotisa.event().location().lgtd(toLON(kundali.sweLocation().longitude()).toString());

        jyotisa.event().datetime().name(IDateUtils.format6(date).toString());
        jyotisa.event().datetime().date(IDateUtils.format(date, F4Y_2M_2D).toString());
        jyotisa.event().datetime().time(IDateUtils.format(date, F2H_2M_2S).toString());

        String tmzsign = date.timeZone() >= 0 ? "+" : "-";
        jyotisa.event().datetime().zone("(" + tmzsign + date.timeZone() + ")");
    }

    @Test
    void testChennaiNow() throws IOException {
        IKundali kundali = newChennaiKundali(getSwephExp());
        ISweJulianDate date = kundali.sweJulianDate();
        int year = date.year();

        System.out.println(kundali);

        IMetaJyotisa metaJyotisa = buildMetaJyotisa(kundali);
        metaJyotisa.event().type(EventType.prasna);

        File file = new File("Chennai." + year + ".json");
        writeStringToFile(file, printPrettyJyotisa(metaJyotisa), UTF_8);

        assertMetaDiff(metaJyotisa, OBJECT_MAPPER.readValue(file, MetaJyotisa.class));
    }

    @Test
    void testChennai1962() throws IOException {
        Calendar calendar = newCalendar(getTimeZone(ASIA_CALCUTTA));
        calendar.set(1962, Calendar.FEBRUARY, 4, 8, 30, 0);
        IKundali kundali = newChennaiKundali(getSwephExp(), calendar);
        ISweJulianDate date = kundali.sweJulianDate();
        int year = date.year();

        System.out.println(kundali);

        IMetaJyotisa metaJyotisa = buildMetaJyotisa(kundali);
        File file = new File("Chennai." + year + ".json");
        writeStringToFile(file, printPrettyJyotisa(metaJyotisa), UTF_8);

        assertMetaDiff(metaJyotisa, OBJECT_MAPPER.readValue(file, MetaJyotisa.class));
    }

}

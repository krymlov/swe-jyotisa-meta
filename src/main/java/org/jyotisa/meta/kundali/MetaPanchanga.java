/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaPojo;

/**
 * The panchanga - the five limbs - together with the frame the chart was computed in.
 * <p>
 * The five limbs answer "what day is this in the lunar calendar"; the ayanamsa, sidereal time and
 * the sun's own rising and setting answer "against what frame", which is what makes the rest of the
 * document reproducible. They are carried here rather than left to the consumer because none of
 * them can be derived from the object longitudes the feed already ships.
 * <p>
 * Every time is **local to the chart's own place**, matching the event block, and rendered to whole
 * seconds. The ayanamsa is split the way a graha's longitude is - a name and a formatted degree -
 * so a consumer never parses either.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaPanchanga implements IMetaPojo {
    private static final long serialVersionUID = 7735024612290166195L;

    protected final MetaLimb vaara = new MetaLimb();
    protected final MetaLimb tithi = new MetaLimb();
    protected final MetaLimb naksatra = new MetaLimb();
    protected final MetaLimb nityaYoga = new MetaLimb();
    protected final MetaLimb karana = new MetaLimb();

    /** which ayanamsa the whole document is sidereal against, e.g. {@code TRUE_CITRA} */
    protected String ayanamsa;
    protected String ayanamsaDegr;

    protected String siderealTime;

    protected String sunrise;
    protected String sunset;
    protected String moonrise;
    protected String moonset;

    public MetaLimb vaara() {
        return vaara;
    }

    public MetaLimb tithi() {
        return tithi;
    }

    public MetaLimb naksatra() {
        return naksatra;
    }

    public MetaLimb nityaYoga() {
        return nityaYoga;
    }

    public MetaLimb karana() {
        return karana;
    }

    public String ayanamsa() {
        return ayanamsa;
    }

    public void ayanamsa(String ayanamsa) {
        this.ayanamsa = ayanamsa;
    }

    public String ayanamsaDegr() {
        return ayanamsaDegr;
    }

    public void ayanamsaDegr(String ayanamsaDegr) {
        this.ayanamsaDegr = ayanamsaDegr;
    }

    public String siderealTime() {
        return siderealTime;
    }

    public void siderealTime(String siderealTime) {
        this.siderealTime = siderealTime;
    }

    public String sunrise() {
        return sunrise;
    }

    public void sunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String sunset() {
        return sunset;
    }

    public void sunset(String sunset) {
        this.sunset = sunset;
    }

    public String moonrise() {
        return moonrise;
    }

    public void moonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String moonset() {
        return moonset;
    }

    public void moonset(String moonset) {
        this.moonset = moonset;
    }
}

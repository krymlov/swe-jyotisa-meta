/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.event;

import org.jyotisa.meta.api.IMetaPojo;
import org.jyotisa.meta.api.EventType;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaEvent implements IMetaPojo {
    private static final long serialVersionUID = 5686129421155291413L;

    protected EventType type = EventType.kundali;

    protected final MetaEntity entity = new MetaEntity();
    protected final MetaLocation location = new MetaLocation();
    protected final MetaDateTime datetime = new MetaDateTime();

    public EventType type() {
        return type;
    }

    public void type(EventType type) {
        this.type = type;
    }

    public MetaEntity entity() {
        return entity;
    }

    public MetaLocation location() {
        return location;
    }

    public MetaDateTime datetime() {
        return datetime;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.event;

import org.jyotisa.meta.api.IMetaJyotisaPojo;
import org.jyotisa.meta.api.MetaEventType;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaEvent implements IMetaJyotisaPojo {
    protected MetaEventType type = MetaEventType.kundali;

    protected final MetaEntity entity = new MetaEntity();
    protected final MetaLocation location = new MetaLocation();
    protected final MetaDateTime datetime = new MetaDateTime();

    public MetaEventType type() {
        return type;
    }

    public void type(MetaEventType type) {
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

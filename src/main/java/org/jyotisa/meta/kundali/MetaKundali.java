/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaJyotisaPojo;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaKundali implements IMetaJyotisaPojo {
    protected final List<Integer> mainBox = Arrays.asList(0, 0, 640, 640);
    protected final MetaSouthStyle southStyle = new MetaSouthStyle();
    protected final MetaNorthStyle northStyle = new MetaNorthStyle();

    public List<Integer> mainBox() {
        return mainBox;
    }

    public MetaSouthStyle southStyle() {
        return southStyle;
    }

    public MetaNorthStyle northStyle() {
        return northStyle;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaJyotisaPojo;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaKundali implements IMetaJyotisaPojo {
    private static final long serialVersionUID = 522753150992452647L;

    protected final List<Integer> mainBox = new ArrayList<>(asList(0, 0, 640, 640));
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

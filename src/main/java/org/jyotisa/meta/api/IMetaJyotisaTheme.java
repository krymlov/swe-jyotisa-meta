package org.jyotisa.meta.api;

import org.jyotisa.meta.objects.MetaObject;
import org.jyotisa.rasi.ERasi;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.toBoolean;

public interface IMetaJyotisaTheme {
    Integer VAKRI_FALSE = Integer.valueOf(0);
    Integer VAKRI_TRUE = Integer.valueOf(1);

    default boolean themeMetaGrahas() {
        return false;
    }

    default int themeMetaGrahasBaseSize() {
        return 26;
    }

    default int themeMetaGrahasBaseSizeMinVal() {
        return 14;
    }

    default int themeMetaGrahasBaseSizeDecVal() {
        return 2;
    }

    default int themeMetaGrahasBaseSizeDecValVakri() {
        return 1;
    }

    default void themeMetaGrahas(List<MetaObject> grahas) {
        if (null == grahas || grahas.isEmpty()) return;

        final int decValVakri = themeMetaGrahasBaseSizeDecValVakri();
        final int decVal = themeMetaGrahasBaseSizeDecVal();
        final int minVal = themeMetaGrahasBaseSizeMinVal();
        final int[] sizes = new int[ERasi.values().length];
        Arrays.fill(sizes, themeMetaGrahasBaseSize());

        grahas.forEach(it -> {
            final int idx = it.rasi() - 1;
            if (sizes[idx] > minVal) sizes[idx] -= decVal;
            if (null != it.vakri() && toBoolean(it.vakri(), VAKRI_TRUE, VAKRI_FALSE)) {
                if (sizes[idx] > minVal) sizes[idx] -= decValVakri;
            }
        });

        grahas.forEach(it -> it.size(String.valueOf(sizes[it.rasi() - 1])));
    }
}

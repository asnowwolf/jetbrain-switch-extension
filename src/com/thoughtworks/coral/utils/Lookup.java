package com.thoughtworks.coral.utils;

import java.util.List;

public class Lookup {
    public static int findSuffix(String text, List<String> suffixes) {
        for (int i = 0; i < suffixes.size(); ++i) {
            String suffix = suffixes.get(i);
            if (text.endsWith(suffix)) {
                return i;
            }
        }
        return -1;
    }
}

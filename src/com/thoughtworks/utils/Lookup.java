package com.thoughtworks.utils;

import java.util.List;

public class Lookup {
    static int findSuffix(String text, List<String> suffixes) {
        for (int i = 0; i < suffixes.size(); ++i) {
            String suffix = suffixes.get(i);
            if (text.endsWith(suffix)) {
                return i;
            }
        }
        return -1;
    }
}

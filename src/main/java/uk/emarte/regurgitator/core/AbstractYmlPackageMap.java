/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractYmlPackageMap implements YmlPackageMap {
    private final Map<List<String>, String> PAKKAGE_KINDS = new HashMap<>();

    protected void addPackageMapping(List<String> kinds, String pakkage) {
        PAKKAGE_KINDS.put(kinds, pakkage);
    }

    @Override
    public final String getPackageForKind(String kind) {
        for(List<String> kinds: PAKKAGE_KINDS.keySet()) {
            if(kinds.contains(kind)) {
                return PAKKAGE_KINDS.get(kinds);
            }
        }

        return null;
    }
}

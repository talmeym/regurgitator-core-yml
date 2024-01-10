/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.ServiceLoader;

class YmlPackageLookup {
    private static final ServiceLoader<YmlPackageMap> PACKAGE_MAPS = ServiceLoader.load(YmlPackageMap.class);

    static String getPackageForKind(String kind) throws RegurgitatorException {
        for(YmlPackageMap set: PACKAGE_MAPS) {
            String pakkage = set.getPackageForKind(kind);

            if(pakkage != null) {
                return pakkage;
            }
        }

        throw new RegurgitatorException("Package not found for kind: " + kind);
    }
}

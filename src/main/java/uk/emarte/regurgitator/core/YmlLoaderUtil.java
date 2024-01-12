/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import static uk.emarte.regurgitator.core.StringUtil.dashesToCamelCase;
import static uk.emarte.regurgitator.core.YmlPackageLookup.getPackageForKind;

public class YmlLoaderUtil<TYPE extends Loader<?, ?>> extends LoaderUtil<Yaml, TYPE> {

    @Override
    public TYPE deriveLoader(Yaml yaml) throws RegurgitatorException {
        return buildFromClass(deriveClass(yaml));
    }

    @Override
    String deriveClass(Yaml yaml) throws RegurgitatorException {
        String kind = yaml.getKind();
        return deriveClass(getPackageForKind(kind), dashesToCamelCase(kind));
    }

    @Override
    String deriveClass(String packageName, String className) throws RegurgitatorException {
        if (packageName == null) {
            throw new RegurgitatorException("Package not found for class: " + className);
        }

        return packageName + "." + className + "YmlLoader";
    }
}

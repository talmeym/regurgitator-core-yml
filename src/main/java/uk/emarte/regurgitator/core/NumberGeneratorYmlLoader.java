/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.MAX;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadOptionalInt;

public class NumberGeneratorYmlLoader implements YmlLoader<ValueGenerator> {
    private static final Log log = getLog(NumberGeneratorYmlLoader.class);

    @Override
    public NumberGenerator load(Yaml yaml, Set<Object> allIds) {
        log.debug("Loaded number generator");
        return new NumberGenerator(loadOptionalInt(yaml, MAX));
    }
}

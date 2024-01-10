/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.AS_INDEX;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadOptionalBool;

public class SizeProcessorYmlLoader implements YmlLoader<SizeProcessor> {
    private static final Log log = getLog(SizeProcessorYmlLoader.class);

    @Override
    public SizeProcessor load(Yaml yaml, Set<Object> allIds) {
        boolean lastIndex = loadOptionalBool(yaml, AS_INDEX);
        log.debug("Loaded size processor");
        return new SizeProcessor(lastIndex);
    }
}

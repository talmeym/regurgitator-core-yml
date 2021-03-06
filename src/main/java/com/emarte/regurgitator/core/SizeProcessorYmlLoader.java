/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.core;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.AS_INDEX;
import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.YmlConfigUtil.loadOptionalBool;

public class SizeProcessorYmlLoader implements YmlLoader<SizeProcessor> {
    private static final Log log = getLog(SizeProcessorYmlLoader.class);

    @Override
    public SizeProcessor load(Yaml yaml, Set<Object> allIds) throws RegurgitatorException {
        boolean lastIndex = loadOptionalBool(yaml, AS_INDEX);
        log.debug("Loaded size processor");
        return new SizeProcessor(lastIndex);
    }
}

/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.*;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadOptionalBool;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadOptionalStr;

public class IndexOfProcessorYmlLoader implements YmlLoader<IndexOfProcessor> {
    private static final Log log = getLog(IndexOfProcessorYmlLoader.class);

    @Override
    public IndexOfProcessor load(Yaml yaml, Set<Object> allIds) {
        String source = loadOptionalStr(yaml, SOURCE);
        String value = loadOptionalStr(yaml, VALUE);
        boolean backwards = loadOptionalBool(yaml, LAST);
        log.debug("Loaded index of processor");
        return new IndexOfProcessor(new ValueSource(source != null ? new ContextLocation(source) : null, value), backwards);
    }
}

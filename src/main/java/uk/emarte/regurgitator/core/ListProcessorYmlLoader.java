/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.List;
import java.util.Set;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadMandatoryValueProcessors;

public class ListProcessorYmlLoader implements YmlLoader<ListProcessor> {
    private static final Log log = getLog(ListProcessorYmlLoader.class);

    @Override
    public ListProcessor load(Yaml yaml, Set<Object> allIds) throws RegurgitatorException {
        List<ValueProcessor> processors = loadMandatoryValueProcessors(yaml, allIds);
        log.debug("Loaded list processor");
        return new ListProcessor(processors);
    }
}

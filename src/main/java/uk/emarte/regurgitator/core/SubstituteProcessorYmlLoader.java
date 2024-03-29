/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.REPLACEMENT;
import static uk.emarte.regurgitator.core.CoreConfigConstants.TOKEN;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadMandatoryStr;

public class SubstituteProcessorYmlLoader implements YmlLoader<SubstituteProcessor> {
    private static final Log log = getLog(SubstituteProcessorYmlLoader.class);

    @Override
    public SubstituteProcessor load(Yaml yaml, Set<Object> allIds) throws RegurgitatorException {
        String token = loadMandatoryStr(yaml, TOKEN);
        String replacement = loadMandatoryStr(yaml, REPLACEMENT);
        log.debug("Loaded substitute processor");
        return new SubstituteProcessor(token, replacement);
    }
}

/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.BUILDER;
import static uk.emarte.regurgitator.core.EntityLookup.valueBuilder;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.YmlConfigUtil.*;

public class BuildParameterYmlLoader implements YmlLoader<Step> {
    private static final Log log = getLog(BuildParameterYmlLoader.class);
    private static final YmlLoaderUtil<YmlLoader<ValueBuilder>> builderLoaderUtil = new YmlLoaderUtil<>();

    @Override
    public Step load(Yaml yaml, Set<Object> allIds) throws RegurgitatorException {
        Object builderObj = loadMandatory(yaml, BUILDER);
        ValueBuilder builder;

        if(builderObj instanceof String) {
            builder = valueBuilder((String) builderObj);
        } else {
            Yaml builderYaml = new Yaml((Map<?, ?>) builderObj);
            builder = builderLoaderUtil.deriveLoader(builderYaml).load(builderYaml, allIds);
        }

        List<ValueProcessor> processors = loadOptionalValueProcessors(yaml, allIds);

        String id = loadId(yaml, allIds);
        log.debug("Loaded build parameter '{}'", id);
        return new BuildParameter(id, loadPrototype(yaml), loadContext(yaml), builder, processors);
    }
}

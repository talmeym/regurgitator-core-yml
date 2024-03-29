/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.ISOLATE;
import static uk.emarte.regurgitator.core.CoreConfigConstants.STEPS;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadId;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadOptionalStr;

public class SequenceYmlLoader implements YmlLoader<Sequence> {
    private static final Log log = getLog(SequenceYmlLoader.class);
    private static final YmlLoaderUtil<YmlLoader<Step>> loaderUtil = new YmlLoaderUtil<>();

    @Override
    public Sequence load(Yaml yaml, Set<Object> allIds) throws RegurgitatorException {
        List<Step> steps = new ArrayList<>();
        List<?> stepYamls = (List<?>) yaml.get(STEPS);

        if(stepYamls != null) {
            for (Object obj : stepYamls) {
                Yaml stepYaml = new Yaml((Map<?, ?>) obj);
                steps.add(loaderUtil.deriveLoader(stepYaml).load(stepYaml, allIds));
            }
        }

        if(steps.isEmpty()) {
            throw new RegurgitatorException("No steps defined");
        }

        String id = loadId(yaml, allIds);
        log.debug("Loaded sequence '{}'", id);
        return new Sequence(id, steps, loadIsolate(yaml));
    }

    private Isolate loadIsolate(Yaml yaml) {
        String isolateStr = loadOptionalStr(yaml, ISOLATE);
        return isolateStr != null ? new Isolate(isolateStr.contains("session"), isolateStr.contains("param")) : null;
    }
}

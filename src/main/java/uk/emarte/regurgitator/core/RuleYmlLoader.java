/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.*;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadId;
import static uk.emarte.regurgitator.core.YmlConfigUtil.loadMandatoryStr;

class RuleYmlLoader {
    private static final Log log = getLog(RuleYmlLoader.class);

    static Rule loadRule(Yaml yaml, Set<Object> stepIds, Set<Object> allIds) throws RegurgitatorException {
        List<Condition> conditions = new ArrayList<>();
        List<?> conditionYamls = (List<?>) yaml.get(CONDITIONS);

        if(conditionYamls != null) {
            for (Object obj : conditionYamls) {
                Yaml conditionYaml = new Yaml(CONDITION, (Map<?, ?>) obj);
                conditions.add(ConditionYmlLoader.load(conditionYaml, allIds));
            }
        }

        String stepId = loadMandatoryStr(yaml, STEP);

        if(!stepIds.contains(stepId)) {
            throw new RegurgitatorException("Error with configuration: rule step not found: " + stepId);
        }

        String id = loadId(yaml, RULE, allIds);
        log.debug("Loaded rule '{}'", id);
        return new Rule(id, conditions, stepId);
    }
}

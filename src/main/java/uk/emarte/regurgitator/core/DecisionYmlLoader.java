/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.*;

import static uk.emarte.regurgitator.core.CoreConfigConstants.*;
import static uk.emarte.regurgitator.core.EntityLookup.rulesBehaviour;
import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.RuleYmlLoader.loadRule;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static uk.emarte.regurgitator.core.YmlConfigUtil.*;

public class DecisionYmlLoader implements YmlLoader<Step> {
    private static final Log log = getLog(DecisionYmlLoader.class);

    private static final YmlLoaderUtil<YmlLoader<Step>> stepLoaderUtil = new YmlLoaderUtil<>();
    private static final YmlLoaderUtil<YmlLoader<RulesBehaviour>> rulesBehaviourLoaderUtil = new YmlLoaderUtil<>();

    @Override
    public Step load(Yaml yaml, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(yaml, allIds);
        List<Step> steps = loadSteps(yaml, allIds);
        Set<Object> stepIds = stepIds(steps);
        List<Rule> rules = loadRules(yaml, stepIds, allIds);
        Object behaviourObj = loadOptional(yaml, BEHAVIOUR);
        RulesBehaviour behaviour;

        if(behaviourObj == null) {
            behaviour = new FirstMatchBehaviour();
        } else if (behaviourObj instanceof String) {
            behaviour = rulesBehaviour(stringify(behaviourObj));
        } else {
            Yaml behaviourYaml = new Yaml((Map<?, ?>) behaviourObj);
            behaviour = rulesBehaviourLoaderUtil.deriveLoader(behaviourYaml).load(behaviourYaml, allIds);
        }

        log.debug("Loaded decision '{}'", id);
        return new Decision(id, steps, rules, behaviour, checkDefaultStepId(loadOptionalStr(yaml, DEFAULT_STEP), stepIds));
    }

    private String checkDefaultStepId(String defaultStepId, Set<Object> stepIds) {
        if (defaultStepId != null && !stepIds.contains(defaultStepId)) {
            throw new IllegalArgumentException("Error with configuration: decision default step not found: " + defaultStepId);
        }

        return defaultStepId;
    }

    private List<Rule> loadRules(Yaml yaml, Set<Object> stepIds, Set<Object> allIds) throws RegurgitatorException {
        List<Rule> rules = new ArrayList<>();

        List<?> ruleYamls = (List<?>) yaml.get(RULES);

        if(ruleYamls != null) {
            for (Object obj : ruleYamls) {
                Yaml ruleYaml = new Yaml(RULE, (Map<?, ?>) obj);
                rules.add(loadRule(ruleYaml, stepIds, allIds));
            }
        }

        return rules;
    }

    private List<Step> loadSteps(Yaml yaml, Set<Object> allIds) throws RegurgitatorException {
        List<Step> steps = new ArrayList<>();
        List<?> stepYamls = (List<?>) yaml.get(STEPS);

        if(stepYamls != null) {
            for (Object obj : stepYamls) {
                Yaml stepYaml = new Yaml((Map<?, ?>) obj);
                steps.add(stepLoaderUtil.deriveLoader(stepYaml).load(stepYaml, allIds));
            }
        }

        return steps;
    }

    private Set<Object> stepIds(List<Step> steps) {
        Set<Object> stepIds = new HashSet<>(steps.size());

        for (Step step : steps) {
            stepIds.add(step.getId());
        }

        return stepIds;
    }
}

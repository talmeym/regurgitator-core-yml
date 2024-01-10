/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.core;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class Yaml {
    private final String kind;
    private final Map<?, ?> values;

    public Yaml(Map<?, ?> map) {
        kind = (String) map.keySet().iterator().next();
        values = map.get(kind) instanceof Map ? unmodifiableMap((Map<?, ?>) map.get(kind)) : new HashMap<>();
    }

    public Yaml(String kind, Map<?, ?> values) {
        this.kind = kind;
        this.values = values;
    }

    public String getKind() {
        return kind;
    }

    public Map getValueMap() {
        return values;
    }

    public boolean contains(String key) {
        return values.containsKey(key);
    }

    public Object get(String key) {
        return values.get(key);
    }
}

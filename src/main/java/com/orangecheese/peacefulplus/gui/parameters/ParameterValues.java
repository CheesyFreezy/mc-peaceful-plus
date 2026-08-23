package com.orangecheese.peacefulplus.gui.parameters;

import java.util.Map;

public record ParameterValues(Map<String, Object> values) {
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) values.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(values.get(key));
    }
}
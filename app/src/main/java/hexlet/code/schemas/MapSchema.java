package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<String, Object>> {

    public MapSchema required() {
        addCheck(value -> value != null);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        addCheck(value -> {
            if (value == null) return true;
            for (var entry : schemas.entrySet()) {
                Object val = value.get(entry.getKey());
                if (!entry.getValue().isValid(val)) return false;
            }
            return true;
        });
        return this;
    }
}

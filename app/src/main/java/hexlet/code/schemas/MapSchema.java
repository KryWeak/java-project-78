package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<String, Object>> {

    public MapSchema required() {
        addCheck(value -> value != null);
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck(map -> map != null && map.size() == size);
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        addCheck(value -> {
            if (value == null) {
                return true;
            }
            for (var entry : schemas.entrySet()) {
                Object val = value.get(entry.getKey());
                BaseSchema<Object> schema = (BaseSchema<Object>) entry.getValue();
                if (!schema.isValid(val)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}

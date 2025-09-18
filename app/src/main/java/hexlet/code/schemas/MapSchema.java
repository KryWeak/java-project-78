package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema<Map> {
    private Map<String, BaseSchema<Object>> shapeSchemas = new HashMap<>();

    public MapSchema required() {
        super.required();
        return this;
    }

    public MapSchema sizeof(int size) {
        checks.add((Predicate<Map>) map -> map.size() == size);
        return this;
    }

    @SuppressWarnings("unchecked")
    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        this.shapeSchemas = new HashMap<>();
        for (Map.Entry<String, ? extends BaseSchema<?>> entry : schemas.entrySet()) {
            this.shapeSchemas.put(entry.getKey(), (BaseSchema<Object>) entry.getValue());
        }
        checks.add((Predicate<Map>) map -> {
            for (Map.Entry<String, BaseSchema<Object>> entry : shapeSchemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<Object> schema = entry.getValue();
                Object value = map.get(key);
                if (!map.containsKey(key) || !schema.isValid(value)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}

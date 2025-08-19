package hexlet.code.schemas;

import java.util.Map;

public class MapSchema {

    private boolean required = false;
    private Integer expectedSize = null;
    private Map<String, Object> shapeSchemas = null;

    public MapSchema required() {
        this.required = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        this.expectedSize = size;
        return this;
    }

    public MapSchema shape(Map<String, ?> schemas) {
        this.shapeSchemas = (Map<String, Object>) schemas;
        return this;
    }

    public boolean isValid(Map<?, ?> value) {
        if (value == null) {
            return !required;
        }
        if (expectedSize != null && value.size() != expectedSize) {
            return false;
        }
        if (shapeSchemas != null) {
            for (String key : shapeSchemas.keySet()) {
                Object schema = shapeSchemas.get(key);
                Object fieldValue = value.get(key);

                if (schema instanceof StringSchema stringSchema) {
                    if (!stringSchema.isValid((String) fieldValue)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }
}

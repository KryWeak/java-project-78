package hexlet.code.schemas;

import java.util.Map;

public class MapSchema {

    private boolean isRequired = false;
    private Integer expectedSize = null;

    public MapSchema required() {
        this.isRequired = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        this.expectedSize = size;
        return this;
    }

    public boolean isValid(Object value) {
        if (value == null) {
            return !isRequired;
        }

        if (!(value instanceof Map)) {
            return false;
        }

        Map<?, ?> mapValue = (Map<?, ?>) value;

        if (expectedSize != null && mapValue.size() != expectedSize) {
            return false;
        }

        return true;
    }
}

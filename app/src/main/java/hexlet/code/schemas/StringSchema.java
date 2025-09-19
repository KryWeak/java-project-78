package hexlet.code.schemas;

import java.util.ArrayList;

public final class StringSchema extends BaseSchema<String> {
    private Integer minLength = null;

    public StringSchema() {
        checks = new ArrayList<>();
        checks.add(value -> value == null || value.isEmpty() ? !isRequired : true);
    }

    public StringSchema required() {
        super.required();
        return this;
    }

    public StringSchema minLength(int length) {
        if (minLength != null) {
            checks.removeIf(check -> check.toString().contains("minLength"));
        }
        minLength = length;
        checks.add(value -> value == null || value.length() >= minLength);
        return this;
    }

    public StringSchema contains(String substring) {
        checks.add(value -> value == null || value.contains(substring));
        return this;
    }
}

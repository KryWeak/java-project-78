package hexlet.code.schemas;

import java.util.Objects;

public class StringSchema {

    private boolean required = false;
    private Integer minLength = null;
    private String requiredSubstring = null;

    public StringSchema() {
    }

    public StringSchema required() {
        this.required = true;
        return this;
    }

    public StringSchema minLength(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("minLength must be non-negative");
        }
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        Objects.requireNonNull(substring);
        this.requiredSubstring = substring;
        return this;
    }

    public boolean isValid(Object value) {
        if (value == null) {
            return !required;
        }

        if (!(value instanceof String)) {
            return false;
        }

        String s = (String) value;

        if (required && s.isEmpty()) {
            return false;
        }

        if (minLength != null && s.length() < minLength) {
            return false;
        }

        if (requiredSubstring != null && !s.contains(requiredSubstring)) {
            return false;
        }

        return true;
    }
}

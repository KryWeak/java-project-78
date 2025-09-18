package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    private boolean required = false;

    public StringSchema required() {
        required = true;
        return this;
    }

    public StringSchema minLength(int length) {
        addCheck(v -> v == null || v.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck(v -> v == null || v.contains(substring));
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return !required;
        }
        if (!(value instanceof String)) {
            return false;
        }
        String str = (String) value;
        if (required && str.isEmpty()) {
            return false;
        }
        return super.isValid(str);
    }
}

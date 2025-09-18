package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    private boolean isRequired = false;

    @Override
    public boolean isValid(Object value) {
        if (isRequired && (value == null || !(value instanceof String) || ((String) value).isEmpty())) {
            return false;
        }
        return super.isValid(value);
    }

    public StringSchema required() {
        this.isRequired = true;
        return this;
    }

    public StringSchema minLength(int length) {
        addCheck(value -> value == null || value.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck(value -> value == null || value.contains(substring));
        return this;
    }
}

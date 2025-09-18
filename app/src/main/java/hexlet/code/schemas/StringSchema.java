package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    public StringSchema() {
        checks.add(value -> value == null || value.isEmpty() ? !isRequired : true);
    }

    public StringSchema required() {
        super.required();
        return this;
    }

    public StringSchema minLength(int length) {
        checks.add(value -> value.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        checks.add(value -> value.contains(substring));
        return this;
    }
}

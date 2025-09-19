package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.function.Predicate;

public class StringSchema extends BaseSchema<String> {
    private Predicate<String> minLengthCheck = null;

    public StringSchema() {
        checks = new ArrayList<>();
        checks.add(value -> value == null || value.isEmpty() ? !isRequired : true);
    }

    public StringSchema required() {
        super.required();
        return this;
    }

    public StringSchema minLength(int length) {
        minLengthCheck = value -> value == null || value.length() >= length;
        checks.removeIf(check -> check == minLengthCheck);
        checks.add(minLengthCheck);
        return this;
    }

    public StringSchema contains(String substring) {
        checks.add(value -> value == null || value.contains(substring));
        return this;
    }
}

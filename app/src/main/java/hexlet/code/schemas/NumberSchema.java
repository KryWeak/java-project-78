package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class NumberSchema {

    private boolean isRequired = false;
    private final List<Predicate<Number>> checks = new ArrayList<>();

    public NumberSchema required() {
        isRequired = true;
        return this;
    }

    public NumberSchema positive() {
        checks.add(num -> num.doubleValue() > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        checks.add(num -> num.doubleValue() >= min && num.doubleValue() <= max);
        return this;
    }

    public boolean isValid(Object value) {
        if (value == null) {
            return !isRequired;
        }

        if (!(value instanceof Number)) {
            return false;
        }

        Number num = (Number) value;
        for (Predicate<Number> check : checks) {
            if (!check.test(num)) {
                return false;
            }
        }
        return true;
    }
}

package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected boolean isRequired = false;
    protected List<Predicate<T>> checks = new ArrayList<>();

    public BaseSchema<T> required() {
        this.isRequired = true;
        return this;
    }

    public boolean isValid(T value) {
        if (value == null) {
            return !isRequired;
        }
        return checks.stream().allMatch(check -> check.test(value));
    }
}

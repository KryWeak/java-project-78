package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected boolean isRequired = false;
    protected List<Predicate<T>> checks = new ArrayList<>();

    /**
     * Marks the schema as required, meaning null values are invalid.
     * @return this schema instance for method chaining
     */
    public BaseSchema<T> required() {
        this.isRequired = true;
        return this;
    }

    /**
     * Validates the given value against all checks in the schema.
     * @param value the value to validate
     * @return true if the value is valid, false otherwise
     */
    public boolean isValid(T value) {
        if (value == null) {
            return !isRequired;
        }
        return checks.stream().allMatch(check -> check.test(value));
    }
}

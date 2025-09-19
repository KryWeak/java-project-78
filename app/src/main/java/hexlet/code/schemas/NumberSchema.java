package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Number> {
    public NumberSchema required() {
        super.required();
        return this;
    }

    public NumberSchema positive() {
        checks.add(value -> value == null || value.doubleValue() > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        checks.add(value -> value == null || (value.doubleValue() >= min && value.doubleValue() <= max));
        return this;
    }
}

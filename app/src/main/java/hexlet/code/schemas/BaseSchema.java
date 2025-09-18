package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BaseSchema<T> {
    private final List<Predicate<T>> checks = new ArrayList<>();

    public void addCheck(Predicate<T> check) {
        checks.add(check);
    }

    @SuppressWarnings("unchecked")
    public boolean isValid(Object value) {
        for (Predicate<T> check : checks) {
            try {
                if (!check.test((T) value)) {
                    return false;
                }
            } catch (ClassCastException e) {
                return false;
            }
        }
        return true;
    }
}

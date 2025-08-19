package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BaseSchema<T> {

    private final List<Predicate<T>> checks = new ArrayList<>();

    public void addCheck(Predicate<T> check) {
        checks.add(check);
    }

    public boolean isValid(Object value) {
        for (Predicate<T> check : checks) {
            //noinspection unchecked
            if (!check.test((T) value)) {
                return false;
            }
        }
        return true;
    }
}

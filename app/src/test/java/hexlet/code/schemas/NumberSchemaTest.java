package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class NumberSchemaTest {

    @Test
    void testDefaultBehaviourAllowsNull() {
        var v = new Validator();
        var schema = v.number();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(5));
    }

    @Test
    void testRequired() {
        var v = new Validator();
        var schema = v.number().required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(10));
    }

    @Test
    void testPositive() {
        var v = new Validator();
        var schema = v.number().positive();

        assertTrue(schema.isValid(null)); // null пока допустим
        assertFalse(schema.isValid(-1));
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(5));

        schema.required();
        assertFalse(schema.isValid(null));
    }

    @Test
    void testRange() {
        var v = new Validator();
        var schema = v.number().range(5, 10);

        assertTrue(schema.isValid(null)); // до required
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(11));

        schema.required();
        assertFalse(schema.isValid(null));
    }
}

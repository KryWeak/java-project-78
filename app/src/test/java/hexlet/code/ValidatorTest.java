package hexlet.code;

import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    void testStringSchema() {
        StringSchema schema = new StringSchema();

        // required
        schema.required();
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));

        // minLength
        schema.minLength(5);
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("hey"));

        // contains
        schema.contains("ell");
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("world"));
    }

    @Test
    void testNumberSchema() {
        NumberSchema schema = new NumberSchema();

        // required
        schema.required();
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(null));

        // positive
        schema.positive();
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(-3));

        // range
        schema.range(1, 10);
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(11));
    }

    @Test
    void testMapSchema() {
        MapSchema schema = new MapSchema();

        // required
        schema.required();
        assertTrue(schema.isValid(Map.of("key", "value")));
        assertFalse(schema.isValid(null));

        // shape
        schema.shape(Map.of(
                "name", new StringSchema().required(),
                "age", new NumberSchema().positive()
        ));

        assertTrue(schema.isValid(Map.of("name", "Alice", "age", 30)));
        assertFalse(schema.isValid(Map.of("name", "", "age", 30)));
        assertFalse(schema.isValid(Map.of("name", "Bob", "age", -1)));
    }
}

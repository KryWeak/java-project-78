package hexlet.code;

import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidatorTest {

    @Test
    void testStringSchema() {
        StringSchema schema = new StringSchema();

        schema.required();
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));

        schema.minLength(5);
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("hey"));

        schema.contains("ell");
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("world"));
    }

    @Test
    void testNumberSchema() {
        NumberSchema schema = new NumberSchema();

        schema.required();
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(null));

        schema.positive();
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(-3));

        schema.range(1, 10);
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(11));
    }

    @Test
    void testMapSchema() {
        MapSchema schema = new MapSchema();

        schema.required();
        assertTrue(schema.isValid(Map.of("key", "value")));
        assertFalse(schema.isValid(null));

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", new StringSchema().required());
        schemas.put("age", new NumberSchema().positive());
        schema.shape(schemas);

        assertTrue(schema.isValid(Map.of("name", "Alice", "age", 30)));
        assertFalse(schema.isValid(Map.of("name", "", "age", 30)));
        assertFalse(schema.isValid(Map.of("name", "Bob", "age", -1)));

        schema.sizeof(2); // новый метод вместо addCheck
        assertTrue(schema.isValid(Map.of("name", "Tom", "age", 20)));
        assertFalse(schema.isValid(Map.of("name", "Tom")));
    }
}

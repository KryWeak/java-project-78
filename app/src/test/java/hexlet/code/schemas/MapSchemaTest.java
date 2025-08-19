package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MapSchemaTest {
    private Validator v;
    private MapSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        schema = v.map();
    }

    @Test
    void testRequired() {
        schema.required();
        assertTrue(schema.isValid(Map.of("key", "value")));
        assertFalse(schema.isValid(null));
    }

    @Test
    void testShape() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> validData = Map.of("name", "Alice", "age", 25);
        Map<String, Object> invalidData = Map.of("name", "", "age", -5);

        assertTrue(schema.isValid(validData));
        assertFalse(schema.isValid(invalidData));
    }
}

package hexlet.code.schemas;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MapSchemaTest {

    @Test
    void testDefaultAllowsNull() {
        var schema = new MapSchema();
        assertTrue(schema.isValid(null));
    }

    @Test
    void testRequired() {
        var schema = new MapSchema().required();

        assertFalse(schema.isValid(null));

        Map<String, String> map = new HashMap<>();
        assertTrue(schema.isValid(map));

        map.put("key", "value");
        assertTrue(schema.isValid(map));
    }

    @Test
    void testSizeof() {
        var schema = new MapSchema().required();

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");

        schema.sizeof(2);
        assertFalse(schema.isValid(map));

        map.put("key2", "value2");
        assertTrue(schema.isValid(map));
    }

    @Test
    void testNotMapValue() {
        var schema = new MapSchema().required();
        assertFalse(schema.isValid("not a map"));
        assertFalse(schema.isValid(123));
    }
}

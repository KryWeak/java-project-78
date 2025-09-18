package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ValidatorTest {

    @Test
    void testStringSchema() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        System.out.println("Testing StringSchema...");

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));

        schema.required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("hexlet"));

        schema.minLength(5);
        assertFalse(schema.isValid("hex"));
        assertTrue(schema.isValid("hexlet"));

        schema.contains("ex");
        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid("hello"));

        System.out.println("StringSchema tests passed!");
    }

    @Test
    void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        System.out.println("Testing NumberSchema...");

        assertTrue(schema.isValid(null));

        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(10));

        schema.positive();
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(-10));
        assertFalse(schema.isValid(0));

        schema.range(5, 10);
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(11));

        System.out.println("NumberSchema tests passed!");
    }

    @Test
    void testMapSchemaSimple() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        System.out.println("Testing MapSchema simple...");

        assertTrue(schema.isValid(null));

        schema.required();
        assertFalse(schema.isValid(null));

        Map<String, Object> map1 = new HashMap<>();
        assertTrue(schema.isValid(map1));

        schema.sizeof(2);
        map1.put("key1", "value1");
        map1.put("key2", "value2");
        assertTrue(schema.isValid(map1));

        map1.put("key3", "extra");
        assertFalse(schema.isValid(map1));

        System.out.println("MapSchema simple tests passed!");
    }

    @Test
    void testMapSchemaShape() {
        Validator v = new Validator();

        System.out.println("Testing MapSchema shape...");

        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> shapeMap = new HashMap<>();
        shapeMap.put("name", v.string().required());
        shapeMap.put("surname", v.string().required().minLength(3));

        schema.shape(shapeMap);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "John");
        human1.put("surname", "Smith");
        assertTrue(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Tom");
        human2.put("surname", "Li");
        assertFalse(schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", null);
        human3.put("surname", "Brown");
        assertFalse(schema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Alice");
        human4.put("surname", "Smith");
        assertTrue(schema.isValid(human4));

        System.out.println("MapSchema shape tests passed!");
    }
}

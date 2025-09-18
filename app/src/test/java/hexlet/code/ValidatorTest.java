package hexlet.code;

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

        System.out.println("DEBUG: StringSchema null = " + schema.isValid(null));
        assertTrue(schema.isValid(null));

        System.out.println("DEBUG: StringSchema empty = " + schema.isValid(""));
        assertTrue(schema.isValid(""));

        schema.required();
        System.out.println("DEBUG: StringSchema required null = " + schema.isValid(null));
        assertFalse(schema.isValid(null));

        System.out.println("DEBUG: StringSchema required empty = " + schema.isValid(""));
        assertFalse(schema.isValid(""));

        System.out.println("DEBUG: StringSchema required 'hexlet' = " + schema.isValid("hexlet"));
        assertTrue(schema.isValid("hexlet"));

        schema.minLength(5);
        System.out.println("DEBUG: StringSchema minLength(5) 'hex' = " + schema.isValid("hex"));
        assertFalse(schema.isValid("hex"));

        System.out.println("DEBUG: StringSchema minLength(5) 'hexlet' = " + schema.isValid("hexlet"));
        assertTrue(schema.isValid("hexlet"));

        schema.contains("ex");
        System.out.println("DEBUG: StringSchema contains('ex') 'hexlet' = " + schema.isValid("hexlet"));
        assertTrue(schema.isValid("hexlet"));

        System.out.println("DEBUG: StringSchema contains('ex') 'hello' = " + schema.isValid("hello"));
        assertFalse(schema.isValid("hello"));
    }

    @Test
    void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        System.out.println("DEBUG: NumberSchema null = " + schema.isValid(null));
        assertTrue(schema.isValid(null));

        schema.required();
        System.out.println("DEBUG: NumberSchema required null = " + schema.isValid(null));
        assertFalse(schema.isValid(null));

        System.out.println("DEBUG: NumberSchema required '5' = " + schema.isValid("5"));
        assertFalse(schema.isValid("5")); // не Integer

        System.out.println("DEBUG: NumberSchema required 10 = " + schema.isValid(10));
        assertTrue(schema.isValid(10));

        schema.positive();
        System.out.println("DEBUG: NumberSchema positive 5 = " + schema.isValid(5));
        assertTrue(schema.isValid(5));

        System.out.println("DEBUG: NumberSchema positive -10 = " + schema.isValid(-10));
        assertFalse(schema.isValid(-10));

        System.out.println("DEBUG: NumberSchema positive 0 = " + schema.isValid(0));
        assertFalse(schema.isValid(0));

        schema.range(5, 10);
        System.out.println("DEBUG: NumberSchema range(5,10) 5 = " + schema.isValid(5));
        assertTrue(schema.isValid(5));

        System.out.println("DEBUG: NumberSchema range(5,10) 10 = " + schema.isValid(10));
        assertTrue(schema.isValid(10));

        System.out.println("DEBUG: NumberSchema range(5,10) 4 = " + schema.isValid(4));
        assertFalse(schema.isValid(4));

        System.out.println("DEBUG: NumberSchema range(5,10) 11 = " + schema.isValid(11));
        assertFalse(schema.isValid(11));
    }

    @Test
    void testMapSchemaSimple() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        System.out.println("DEBUG: MapSchema null = " + schema.isValid(null));
        assertTrue(schema.isValid(null));

        schema.required();
        System.out.println("DEBUG: MapSchema required null = " + schema.isValid(null));
        assertFalse(schema.isValid(null));

        Map<String, Object> map1 = new HashMap<>();
        System.out.println("DEBUG: MapSchema empty map = " + schema.isValid(map1));
        assertTrue(schema.isValid(map1));

        schema.sizeof(2);
        map1.put("key1", "value1");
        map1.put("key2", "value2");
        System.out.println("DEBUG: MapSchema sizeof(2) with 2 keys = " + schema.isValid(map1));
        assertTrue(schema.isValid(map1));

        map1.put("key3", "extra");
        System.out.println("DEBUG: MapSchema sizeof(2) with 3 keys = " + schema.isValid(map1));
        assertFalse(schema.isValid(map1));
    }

    @Test
    void testMapSchemaShape() {
        Validator v = new Validator();

        MapSchema schema = v.map();

        Map<String, StringSchema> shape = new HashMap<>();
        shape.put("name", v.string().required());
        shape.put("surname", v.string().required().minLength(3));

        schema.shape(new HashMap<>(shape));

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "John");
        human1.put("surname", "Smith");
        System.out.println("DEBUG: MapSchema shape valid human1 = " + schema.isValid(human1));
        assertTrue(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Tom");
        human2.put("surname", "Li"); // слишком короткое
        System.out.println("DEBUG: MapSchema shape invalid surname = " + schema.isValid(human2));
        assertFalse(schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", null);
        human3.put("surname", "Brown");
        System.out.println("DEBUG: MapSchema shape invalid name=null = " + schema.isValid(human3));
        assertFalse(schema.isValid(human3));
    }
}

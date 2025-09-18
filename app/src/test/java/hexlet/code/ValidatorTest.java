package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {
    private Validator v;
    private StringSchema stringSchema;
    private NumberSchema numberSchema;
    private MapSchema mapSchema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        stringSchema = v.string();
        numberSchema = v.number();
        mapSchema = v.map();
    }

    @Test
    void testStringSchemaEmptyAndNullBeforeRequired() {
        assertThat(stringSchema.isValid("")).isTrue();
        assertThat(stringSchema.isValid(null)).isTrue();
    }

    @Test
    void testStringSchemaRequired() {
        stringSchema.required();
        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
        assertThat(stringSchema.isValid("hexlet")).isTrue();
    }

    @Test
    void testStringSchemaMinLength() {
        stringSchema.minLength(5);
        assertThat(stringSchema.isValid("hex")).isFalse();
        assertThat(stringSchema.isValid("hexlet")).isTrue();
    }

    @Test
    void testStringSchemaContains() {
        stringSchema.contains("wh");
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
        stringSchema = v.string().contains("what");
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
        stringSchema = v.string().contains("whatthe");
        assertThat(stringSchema.isValid("what does the fox say")).isFalse();
    }

    @Test
    void testStringSchemaCombined() {
        stringSchema.required().minLength(5).contains("hex");
        assertThat(stringSchema.isValid("hexlet")).isTrue();
        assertThat(stringSchema.isValid("hello")).isFalse();
        assertThat(stringSchema.isValid("hex")).isFalse();
    }

    @Test
    void testNumberSchemaNullBeforeRequired() {
        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(5)).isTrue();
    }

    @Test
    void testNumberSchemaRequired() {
        numberSchema.required();
        assertThat(numberSchema.isValid(null)).isFalse();
        assertThat(numberSchema.isValid(10)).isTrue();
    }

    @Test
    void testNumberSchemaPositive() {
        numberSchema.positive();
        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isFalse();
        assertThat(numberSchema.isValid(0)).isFalse();
    }

    @Test
    void testNumberSchemaRange() {
        numberSchema.range(5, 10);
        assertThat(numberSchema.isValid(5)).isTrue();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(4)).isFalse();
        assertThat(numberSchema.isValid(11)).isFalse();
    }

    @Test
    void testNumberSchemaCombined() {
        numberSchema.required().positive().range(5, 10);
        assertThat(numberSchema.isValid(5)).isTrue();
        assertThat(numberSchema.isValid(0)).isFalse();
        assertThat(numberSchema.isValid(null)).isFalse();
        assertThat(numberSchema.isValid(11)).isFalse();
    }

    @Test
    void testMapSchemaNullBeforeRequired() {
        assertThat(mapSchema.isValid(null)).isTrue();
    }

    @Test
    void testMapSchemaRequired() {
        mapSchema.required();
        assertThat(mapSchema.isValid(null)).isFalse();
        assertThat(mapSchema.isValid(new HashMap<>())).isTrue();
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(mapSchema.isValid(data)).isTrue();
    }

    @Test
    void testMapSchemaSizeOf() {
        mapSchema.sizeof(2);
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(mapSchema.isValid(data)).isFalse();
        data.put("key2", "value2");
        assertThat(mapSchema.isValid(data)).isTrue();
    }

    @Test
    void testMapSchemaCombined() {
        mapSchema.required().sizeof(2);
        assertThat(mapSchema.isValid(null)).isFalse();
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(mapSchema.isValid(data)).isFalse();
        data.put("key2", "value2");
        assertThat(mapSchema.isValid(data)).isTrue();
    }

    @Test
    void testMapSchemaShape() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));
        mapSchema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertThat(mapSchema.isValid(human1)).isTrue();

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertThat(mapSchema.isValid(human2)).isFalse();

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertThat(mapSchema.isValid(human3)).isFalse();

        Map<String, String> human4 = new HashMap<>();
        human4.put("firstName", "");
        human4.put("lastName", "Johnson");
        assertThat(mapSchema.isValid(human4)).isFalse();
    }
}

package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MapSchemaTest {

    @Test
    void testShapeWithoutBaseSchema() {
        var v = new Validator();
        var schema = v.map();

        Map<String, Object> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");

        // создаем схемы для каждого поля
        StringSchema firstNameSchema = v.string().required();
        StringSchema lastNameSchema = v.string().required().minLength(2);

        // shape с проверкой каждого поля через лямбду
        schema.shape(Map.of(
                "firstName", firstNameSchema,
                "lastName", lastNameSchema
        ));

        assertTrue(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3));
    }
}

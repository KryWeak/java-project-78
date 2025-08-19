package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;



public class StringSchemaTest {

    @Test
    void testDefaultBehaviourAllowsNullAndEmpty() {
        var v = new Validator();
        var schema = v.string();

        assertTrue(schema.isValid(null), "null should be valid by default");
        assertTrue(schema.isValid(""), "empty string should be valid by default");
    }

    @Test
    void testRequired() {
        var v = new Validator();
        var schema = v.string().required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("hello"));
    }

    @Test
    void testMinLength() {
        var v = new Validator();
        var schema = v.string().minLength(5);

        assertFalse(schema.isValid("1234"));
        assertTrue(schema.isValid("12345"));
        // minLength is overwritten by last call
        schema.minLength(3);
        assertTrue(schema.isValid("1234"));
    }

    @Test
    void testContains() {
        var v = new Validator();
        var schema = v.string();

        schema.contains("hex");
        assertTrue(schema.isValid("hexlet"));
        assertTrue(schema.isValid("lethex"));

        var schema2 = v.string().contains("what").contains("fox");
        assertTrue(schema2.isValid("what does the fox say"));
        assertFalse(schema2.isValid("what does the cat say"));
    }

    @Test
    void testCombination() {
        var v = new Validator();
        var schema = v.string().required().minLength(5).contains("hex");

        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid("hex"));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
    }
}

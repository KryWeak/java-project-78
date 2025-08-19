package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringSchemaTest {
    private Validator v;
    private StringSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        schema = v.string();
    }

    @Test
    void testRequired() {
        schema.required();
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
    }

    @Test
    void testMinLength() {
        schema.minLength(5);
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("hi"));
    }

    @Test
    void testContains() {
        schema.contains("ell");
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("hi"));
    }
}

package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberSchemaTest {
    private Validator v;
    private NumberSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        schema = v.number();
    }

    @Test
    void testPositive() {
        schema.positive();
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(-3));
    }

    @Test
    void testRange() {
        schema.range(2, 10);
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(1));
        assertFalse(schema.isValid(12));
    }
}

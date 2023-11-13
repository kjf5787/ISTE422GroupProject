import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeTableTest {
    EdgeTable table;

    @Before
    public void testInstance() {
        table = new EdgeTable("1|tableName");
    }

    // constructor test
    // given correct delimeter string, should initialize properly
    @Test
    public void givenCorrectDelim_whenEdgeTableConstructor_thenEquals() {
        String expected = "|";
        String actual = EdgeConvertFileParser.DELIM;

        assertEquals("FAIL - Delimiter character invalid", expected, actual);
    }

    @Test
    public void givenStringParam_whenEdgeTableConstructor_thenEqual() {
        String invalidString = "im a string";
        String[] invalidStringArr = { "im", "a", "string", "array" };
        int invalidInt = 1;
        int[] invalidIntArr = { 0, 1, 2 };
        double invalidDouble = 1.1;
        double[] invalidDoubleArr = { 1.1, 1.2, 1.3 };

        String actual = "im a string";
        String isStringType = temp.instanceOf(String) ? true : false;

        // given string parameter passed, should iniitalize correctly
        assertTrue("Parameter passed into constructor is a String type",
                temp.instanceOf(String));
        // given incorrect data type passed, should throw error
        assertFalse("Parameter passed into constructor has to be of String type",
                !temp.instanceOf(String));
        fail("FAIL - Parameter must be of string type");
    }

    // getter tests
    // given property exists, should return its correct value
    @Test
    public void givenNameExists_whenGetProperty_thenEqual() {
        String expected = "tableName";
        String actual = table.getName();
        assertEquals("FAIL - Name invalid", expected, actual);
    }

    @Test
    public void givenNumFigureExists_whenGetProperty_thenEqual() {
        int expected = 1;
        int actual = table.getNumFigure();
        assertEquals("FAIL - Figure number invalid", expected, actual);
    }

    @Test
    public void givenRelatedTableExists_whenGetProperty_thenEqual() {
        table.addRelatedTable(1);

        int[] expected = { 1 };
        int[] actual = table.getRelatedTablesArray();

        assertArrayEquals(expected, actual);

        if (actual.length == 0) {
            fail("FAIL - Related Tables is empty");
        } else {
            fail("FAIL - Related Table invalid");
        }
    }

    // @Test
    public void givenRelatedFieldsArrayExists_whenGetProperty_thenEqual() {
        table.setRelatedField(0, 1);
        table.setRelatedField(1, 2);

        int[] expected = { 1, 2 };
        int[] actual = table.getRelatedFieldsArray();

        assertArrayEquals(expected, actual);
        fail("FAIL - Related Fields Array invalid");
    }

    @Test
    public void givenNativeFieldsArrayExists_whenGetProperty_thenEqual() {
        table.addNativeField(1);
        table.addNativeField(2);

        int[] expected = { 1, 2 };
        int[] actual = table.getNativeFieldsArray();

        assertArrayEquals(expected, actual);
        fail("FAIL - Native Fields Array invalid");
    }

    // setter tests
    // setter should set the property of the class
    @Test
    public void givenCorrectParams_whenSetRelatedField_thenEqual() {
        table.setRelatedField(0, 1);

        int[] expected = { 1 };
        int[] actual = table.getRelatedFieldsArray();

        assertArrayEquals(expected, actual);
        fail("FAIL - Invalid parameters");
    }
    // wrong data type, throw error

    // addNativeField test
    // string paramter passed, alNativeFielsd array list count+1
    @Test
    public void givenCorrectParams_whenAddNativeField_thenEqual() {
        table.addNativeField(1);
        int length = table.getNativeFieldsArray().length;

        int expected = 1;
        int actual = table.getNativeFieldsArray()[length - 1];

        assertEquals("FAIL - Could not add to NativeField array", expected, actual);
    }

    // moveFileUp test
    // integer paramter, object inside nativeFields should have an index 1 less than
    // ebfore
    @Test
    public void givenArraysExist_whenMoveFieldUp_valueMovesUp() {
        table.addNativeField(1);
        table.addNativeField(2);
        table.addNativeField(3);

        table.addRelatedTable(1);
        table.addRelatedTable(2);
        table.addRelatedTable(3);

        // move 2nd item in array up
        table.moveFieldUp(1);

        // first value of each array should be 2
        int nativeVal = table.getNativeFieldsArray()[0];
        int relatedVal = table.getRelatedFieldsArray()[0];

        int[] expected = { 2, 2 };
        int[] actual = { nativeVal, relatedVal };
        assertArrayEquals(expected, actual);

        if (nativeVal != 2) {
            fail("FAIL - NativeFields array invalid");
        } else {
            fail("FAIL - RelatedFields array invalid");
        }
    }
    // incorrect data type passed, throw error

    @Test
    public void givenArraysEmpty_whenMovieFieldUp_thenNotNull() {
        boolean nativeArrIsEmpty = table.getNativeFieldsArray().length > 0 ? false : true;
        boolean relatedArrIsEmpty = table.getRelatedFieldsArray().length > 0 ? false : true;

        boolean[] expected = { false, false };
        boolean[] actual = { nativeArrIsEmpty, relatedArrIsEmpty };

        assertArrayEquals(expected, actual);

        if (nativeArrIsEmpty) {
            fail("FAIL - NativeFields array is empty");
        } else {
            fail("FAIL - RelatedFields array is empty");
        }
    }

    // moveFileDown test
    // integer paramter, object inside nativeFields should have an index 1 less than
    // before
    @Test
    public void givenArraysExist_whenMoveFieldDown_valueMovesDown() {
        table.addNativeField(1);
        table.addNativeField(2);
        table.addNativeField(3);

        table.addRelatedTable(1);
        table.addRelatedTable(2);
        table.addRelatedTable(3);

        // move 2nd item in array up
        table.moveFieldDown(0);

        // first value of each array should be 1
        int nativeVal = table.getNativeFieldsArray()[1];
        int relatedVal = table.getRelatedFieldsArray()[1];

        int[] expected = { 1, 1 };
        int[] actual = { nativeVal, relatedVal };
        assertArrayEquals(expected, actual);

        if (nativeVal != 1) {
            fail("FAIL - NativeFields array invalid");
        } else {
            fail("FAIL - RelatedFields array invalid");
        }
    }
    // incorrect data type passed, throw error

    @Test
    public void givenArraysEmpty_whenMovieFieldDown_thenNotNull() {
        boolean nativeArrIsEmpty = table.getNativeFieldsArray().length > 0 ? false : true;
        boolean relatedArrIsEmpty = table.getRelatedFieldsArray().length > 0 ? false : true;

        boolean[] expected = { false, false };
        boolean[] actual = { nativeArrIsEmpty, relatedArrIsEmpty };

        assertArrayEquals(expected, actual);

        if (nativeArrIsEmpty) {
            fail("FAIL - NativeFields array is empty");
        } else {
            fail("FAIL - RelatedFields array is empty");
        }
    }

    // makeArrays test
    // no paramters passed, relatedFields created and filled with
    // values...nativeFields, relatedTables and relatedField are each an array of
    // integers
    // parameter passed, throw error
    @Test
    public void givenParams_whenMakeArrays_thenIncorrect() {
        table.addNativeField(0);
        table.addRelatedTable(0);
        table.makeArrays();

        int nativeFieldsArray = table.getNativeFieldsArray().length;
        int relatedTablesArray = table.getRelatedTablesArray().length;
        int relatedFieldsArray = table.getRelatedFieldsArray().length;

        assertTrue("NativeFieldsArray cannot be empty", nativeFieldsArray == 0);
        assertTrue("RelatedTablesArray cannot be empty", relatedTablesArray == 0);
        assertTrue("RelatedFieldsArray cannot be empty", relatedFieldsArray == 0);
    }

    // toString test
    // no parameters passed, string should be returned
    @Test
    public void givenNoParams_whenToString_thenCorrect() {
        char[] expected = { 'T', 'a', 'b', 'l', 'e' };
        char[] actual = table.toString().substring(0, 5).toCharArray(); // get first 5 letters and compare

        assertArrayEquals(expected, actual);
    }
    // parameters passed, throw erorr
}
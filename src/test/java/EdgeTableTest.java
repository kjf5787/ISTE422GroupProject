/* author: Thea Arias */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeTableTest {
    EdgeTable table;

    @Before
    public void setUp() {
        //instantiate new EdgeTable object to test with
        table = new EdgeTable("1|tableName");
    }

    @Test // works
    public void testGetNumFigure() {
        int expected = 1;
        int actual = table.getNumFigure();

        assertEquals("NumFigure initialized as and must be 1", expected, actual);
    }

    @Test // works
    public void testGetName() {
        String expected = "tableName";
        String actual = table.getName();

        assertEquals("Name initialized as and must be 'tableName'", expected, actual);
    }

    @Test //works
    public void testAddRelatedTable() {
        table.addRelatedTable(1); 

        assertFalse("alRelatedTables cannot be empty",table.getAlRelatedTables().isEmpty());
    }

    @Test //works
    public void testGetRelatedTablesArray() {
        int expected = 1;
        table.addRelatedTable(1); 
        int lastIndex = table.getAlRelatedTables().size()-1;
        int actual = (int) table.getAlRelatedTables().get(lastIndex);

        assertEquals("Last index value is initialized as and should be 1", expected, actual);
    }

    @Test //works
    public void testGetRelatedFieldsArray() {
        int[] temp = {1,2};
        table.setRelatedFieldsArray(temp);

        assertFalse("relatedFieldsArray cannot be empty",table.getRelatedFieldsArray().length==0);
    }

    @Test //works
    public void testSetRelatedField() {
        //create non-empty relatedFieldsArray
        int[] temp = {1,2};
        table.setRelatedFieldsArray(temp);
        table.setRelatedField(0, 2);

        int expected = 2;
        int actual = table.getRelatedFieldsArray()[0];

        assertEquals("Value initalized as and should be 2",expected, actual);
    }

    @Test //works
    public void testGetNativeFieldsArray() {
        int[] temp = {1,2};
        table.setNativeFieldsArray(temp);

        assertFalse("relatedFieldsArray cannot be empty",table.getNativeFieldsArray().length==0);
    }

    @Test //works
    public void testAddNativeField() {
        //create non-empty alNativeFields array
        table.addNativeField(1);

        assertFalse("alNativeFields array must not be empty",table.getAlNativeFieldsArray().isEmpty());
    }

    @Test //works
    public void testMoveFileUp() {
        int[] temp = {1,2,3};
        table.setNativeFieldsArray(temp);

        int[] temp2 = {1,2,3};
        table.setRelatedFieldsArray(temp2);

        // move 2nd item in array up
        table.moveFieldUp(1);

        // first value of each array should be 2
        int expectedValue = 2;
        int actualNativeVal = table.getNativeFieldsArray()[0];
        int actualRelatedVal = table.getRelatedFieldsArray()[0];

        assertEquals("Previous value of index is initialized as and should be 2",expectedValue, actualNativeVal);
        assertEquals("Previous value of index is initialized as and should be 2",expectedValue, actualRelatedVal);
    }

    @Test //works
    public void testMoveFileDown() {
        int[] temp = {1,2,3};
        table.setNativeFieldsArray(temp);

        int[] temp2 = {1,2,3};
        table.setRelatedFieldsArray(temp2);

        // move 2nd item in array up
        table.moveFieldDown(0);

        // first value of each array should be 2
        int expectedValue = 1;
        int actualNativeVal = table.getNativeFieldsArray()[1];
        int actualRelatedVal = table.getRelatedFieldsArray()[1];

        assertEquals("Previous value of index is initialized as and should be 2",expectedValue, actualNativeVal);
        assertEquals("Previous value of index is initialized as and should be 2",expectedValue, actualRelatedVal);
    }

    @Test //works
    public void testMakeArrays() {
        assertNotNull("alNativeFields array cannot be null", table.getAlNativeFieldsArray());
        assertNotNull("alRelatedTables array cannot be null", table.getAlRelatedTables());

        //create non-empty instance of alNativeFields
        table.addNativeField(1);
        table.addNativeField(2);

        //create non-empty instance of alRelatedTables
        table.addRelatedTable(1);
        table.addRelatedTable(2);

        table.makeArrays();
        
        assertFalse("nativeFields array cannot be empty",table.getNativeFieldsArray().length==0);
        assertFalse("relatedTables array cannot be empty",table.getRelatedTablesArray().length==0);
        assertFalse("relatedFields array cannot be empty",table.getRelatedFieldsArray().length==0);
    }

    @Test //works
    public void testToString() {
        //create non-empty instance of alNativeFields
        table.addNativeField(1);
        table.addNativeField(2);

        //create non-empty instance of alRelatedTables
        table.addRelatedTable(1);
        table.addRelatedTable(2);

        table.makeArrays();

        assertNotNull("numFigure cannot be null", table.getNumFigure());
        assertNotNull("name cannot be null", table.getName());
        assertNotNull("nativeFields array cannot be null", table.getNativeFieldsArray());
        assertNotNull("relatedTables array cannot be null", table.getRelatedTablesArray());
        assertNotNull("relatedFields array cannot be null", table.getRelatedFieldsArray());
    }
}
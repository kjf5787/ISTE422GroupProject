import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class EdgeFieldTest {
    EdgeField testObj;
    EdgeField testObj2;

    @Before
	public void setUp() throws Exception {
		testObj = new EdgeField("1|MyField");
	}

    @Test
    public void testConstructor_givenCorrectInput(){
        testObj2 = new EdgeField("1|MyField");

        assertEquals("EdgeField was initialized with an input string of 1|MyField so numFigure should be 1", 1, testObj2.getNumFigure());
        assertEquals("EdgeField was initialized with an input string of 1|MyField so name should be MyField", "MyField", testObj2.getName());
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default tableID should be 0", 0, testObj2.getTableID());
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default tableBound should be 0", 0, testObj2.getTableBound());
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default fieldBound should be 0", 0, testObj2.getFieldBound());
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default disallowNull should be false", false, testObj2.getDisallowNull());
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default isPrimaryKey should be false", false, testObj2.getIsPrimaryKey());
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default defaultValue should be empty", "", testObj2.getDefaultValue());
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default varcharValue should be 1", 1, testObj2.getVarcharValue());
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default dataType should be 0", 0, testObj2.getDataType());
    }   

    @Test(expected = NoSuchElementException.class)
    public void testConstructor_givenNumFigureOnly(){
        testObj2 = new EdgeField("1");
    }

    @Test(expected = NumberFormatException.class)
    public void testConstructor_givenNameOnly(){
        testObj2 = new EdgeField("MyField");
    }

    @Test(expected = Exception.class)
    public void testConstructor_givenIncorrectDelim(){
        testObj2 = new EdgeField("1,MyField");
    }

    @Test(expected = NumberFormatException.class)
    public void testConstructor_givenNoIntegers(){
        testObj2 = new EdgeField("One|MyField");
    }

    @Test
    public void testConstructor_givenOnlyIntegers(){
        testObj2 = new EdgeField("1|2");

        assertEquals("EdgeField was initialized with an input string of 1|2 so numFigure should be 1", 1, testObj2.getNumFigure());
        assertEquals("EdgeField was initialized with an input string of 1|2 so name should be 2", "2", testObj2.getName());
        assertEquals("EdgeField was initialized with an input string of 1|2, by default tableID should be 0", 0, testObj2.getTableID());
        assertEquals("EdgeField was initialized with an input string of 1|2, by default tableBound should be 0", 0, testObj2.getTableBound());
        assertEquals("EdgeField was initialized with an input string of 1|2, by default fieldBound should be 0", 0, testObj2.getFieldBound());
        assertEquals("EdgeField was initialized with an input string of 1|2, by default disallowNull should be false", false, testObj2.getDisallowNull());
        assertEquals("EdgeField was initialized with an input string of 1|2, by default isPrimaryKey should be false", false, testObj2.getIsPrimaryKey());
        assertEquals("EdgeField was initialized with an input string of 1|2, by default defaultValue should be empty", "", testObj2.getDefaultValue());
        assertEquals("EdgeField was initialized with an input string of 1|2, by default varcharValue should be 1", 1, testObj2.getVarcharValue());
        assertEquals("EdgeField was initialized with an input string of 1|2, by default dataType should be 0", 0, testObj2.getDataType());
    }
    
    @Test
    public void testGetNumFigure(){
        assertEquals("EdgeField was initialized with an input string of 1|MyField so numFigure should be 1", 1, testObj.getNumFigure());
    }

    @Test
    public void testGetName(){
        assertEquals("EdgeField was initialized with an input string of 1|MyField so name should be MyField", "MyField", testObj.getName());
    }

    @Test
    public void testGetTableID(){
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default tableID should be 0", 0, testObj.getTableID());
    }

    @Test
    public void testGetTableBound(){
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default tableBound should be 0", 0, testObj.getTableBound());
    }

    @Test
    public void testGetFieldBound(){
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default fieldBound should be 0", 0, testObj.getFieldBound());
    }

    @Test
    public void testGetDisallowNull(){
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default disallowNull should be false", false, testObj.getDisallowNull());
    }

    @Test
    public void testGetIsPrimaryKey(){
        assertEquals("EdgeField was initialized with an input string of 1|2, by default isPrimaryKey should be false", false, testObj.getIsPrimaryKey());
    }

    @Test
    public void testGetDefaultValue(){
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default defaultValue should be empty", "", testObj.getDefaultValue());
    }

    @Test
    public void testGetVarcharValue(){
        assertEquals("EdgeField was initialized with an input string of 1|2, by default varcharValue should be 1", 1, testObj.getVarcharValue());
    }

    @Test
    public void testGetDataType(){
        assertEquals("EdgeField was initialized with an input string of 1|MyField, by default dataType should be 0", 0, testObj.getDataType());
    }

    @Test
    public void testGetStrDataType(){
        String[] strDataType = {"Varchar", "Boolean", "Integer", "Double"};

        assertArrayEquals("EdgeField was initialized with an input string of 1|MyField, by default dataType should be 0", strDataType, testObj.getStrDataType());
    }

    @Test
    public void testSetTableID(){
        testObj.setTableID(3);

        assertEquals("tableID was set to 3, so it should equal 3", 3, testObj.getTableID());
    }

    @Test
    public void testSetTableID_givenNegativeInt(){
        testObj.setTableID(-5);

        assertEquals("tableID was set to -5, so it should equal -5", -5, testObj.getTableID());
    }

    @Test
    public void testSetTableID_givenMultipleCalls(){
        testObj.setTableID(10);
        testObj.setTableID(8);
        testObj.setTableID(3);

        assertEquals("tableID was set to 3 at the end of 3 consecutive calls to the setter, so it should equal 3", 3, testObj.getTableID());
    }

    @Test
    public void testSetTableBound(){
        testObj.setTableBound(3);

        assertEquals("tableBound was set to 3, so it should equal 3", 3, testObj.getTableBound());
    }

    @Test
    public void testSetTableBound_givenNegativeInt(){
        testObj.setTableBound(-5);

        assertEquals("tableBound was set to -5, so it should equal -5", -5, testObj.getTableBound());
    }

    @Test
    public void testSetTableBound_givenMultipleCalls(){
        testObj.setTableBound(10);
        testObj.setTableBound(8);
        testObj.setTableBound(3);

        assertEquals("tableBound was set to 3 at the end of 3 consecutive calls to the setter, so it should equal 3", 3, testObj.getTableBound());
    }

    @Test
    public void testSetFieldBound(){
        testObj.setFieldBound(3);

        assertEquals("fieldBound was set to 3, so it should equal 3", 3, testObj.getFieldBound());
    }

    @Test
    public void testSetFieldBound_givenNegativeInt(){
        testObj.setFieldBound(-5);

        assertEquals("fieldBound was set to -5, so it should equal -5", -5, testObj.getFieldBound());
    }

    @Test
    public void testSetFieldBound_givenMultipleCalls(){
        testObj.setFieldBound(10);
        testObj.setFieldBound(8);
        testObj.setFieldBound(3);

        assertEquals("fieldBound was set to 3 at the end of 3 consecutive calls to the setter, so it should equal 3", 3, testObj.getFieldBound());
    }
    
    @Test
    public void testSetDisallowNull(){
        testObj.setDisallowNull(true);

        assertEquals("disallowNull was set to true, so it should equal true", true, testObj.getDisallowNull());
    }

    @Test
    public void testSetDisallowNull_givenMultipleCalls(){
        testObj.setDisallowNull(true);
        testObj.setDisallowNull(false);
        testObj.setDisallowNull(true);

        assertEquals("disallowNull was set to true at the end of 3 consecutive calls to the setter, so it should equal true", true, testObj.getDisallowNull());
    }

    @Test
    public void testSetIsPrimaryKey(){
        testObj.setIsPrimaryKey(true);

        assertEquals("isPrimaryKey was set to true, so it should equal true", true, testObj.getIsPrimaryKey());
    }

    @Test
    public void testSetIsPrimaryKey_givenMultipleCalls(){
        testObj.setIsPrimaryKey(true);
        testObj.setIsPrimaryKey(false);
        testObj.setIsPrimaryKey(true);

        assertEquals("isPrimaryKey was set to true at the end of 3 consecutive calls to the setter, so it should equal true", true, testObj.getIsPrimaryKey());
    }

    @Test
    public void testSetDefaultValue(){
        testObj.setDefaultValue("string");

        assertEquals("defaultValue was set to string, so it should equal string", "string", testObj.getDefaultValue());
    }

    @Test
    public void testSetDefaultValue_givenNull(){
        testObj.setDefaultValue(null);

        assertEquals("defaultValue was set to null, so it should equal null", null, testObj.getDefaultValue());
    }

    @Test
    public void testSetDefaultValue_givenMultipleCalls(){
        testObj.setDefaultValue("string1");
        testObj.setDefaultValue("string2");
        testObj.setDefaultValue("string");

        assertEquals("defaultValue was set to string at the end of 3 consecutive calls to the setter, so it should equal string", "string", testObj.getDefaultValue());
    }

    @Test
    public void testSetVarcharValue(){
        testObj.setVarcharValue(3);

        assertEquals("varcharValue was set to 3, so it should equal 3", 3, testObj.getVarcharValue());
    }

    @Test
    public void testSetVarcharValue_giveOutOfBoundsParam(){
        testObj.setVarcharValue(-5);

        assertEquals("varcharValue was set to -5 which is out of bounds, so it should equal the default value 1", 1, testObj.getVarcharValue());
    }

    @Test
    public void testSetVarcharValue_givenMultipleCalls(){
        testObj.setVarcharValue(2);
        testObj.setVarcharValue(1);
        testObj.setVarcharValue(3);

        assertEquals("varcharValue was set to 3 at the end of 3 consecutive calls to the setter, so it should equal 3", 3, testObj.getVarcharValue());
    }

    @Test
    public void testSetDataType(){
        testObj.setDataType(3);

        assertEquals("dataType was set to 3, so it should equal 3", 3, testObj.getDataType());
    }

    @Test
    public void testSetDataType_giveOutOfBoundsParam(){
        int upperBound = 4;
        testObj.setDataType(upperBound + 5);

        assertEquals("dataType was set to an int above the bounds, so it should equal the default value 0", 0, testObj.getDataType());
    }

    @Test
    public void testSetDataType_givenMultipleCalls(){
        testObj.setDataType(2);
        testObj.setDataType(1);
        testObj.setDataType(3);

        assertEquals("dataType was set to 3 at the end of 3 consecutive calls to the setter, so it should equal 3", 3, testObj.getDataType());
    }

    @Test
    public void testToString(){
        String expected = testObj.getNumFigure() + EdgeConvertFileParser.DELIM +
        testObj.getName() + EdgeConvertFileParser.DELIM +
        testObj.getTableID() + EdgeConvertFileParser.DELIM +
        testObj.getTableBound() + EdgeConvertFileParser.DELIM +
        testObj.getFieldBound() + EdgeConvertFileParser.DELIM +
        testObj.getDataType() + EdgeConvertFileParser.DELIM +
        testObj.getVarcharValue() + EdgeConvertFileParser.DELIM +
        testObj.getIsPrimaryKey() + EdgeConvertFileParser.DELIM +
        testObj.getDisallowNull() + EdgeConvertFileParser.DELIM +
        testObj.getDefaultValue();

        assertEquals("EdgeField was initialized with an input string of 1|MyField, so the toString should print: " + expected, expected, testObj.toString());
    }
}

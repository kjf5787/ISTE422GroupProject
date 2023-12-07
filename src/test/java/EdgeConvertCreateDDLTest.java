//package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// public class EdgeConvertCreateDDLTest extends EdgeConvertCreateDDL {
    // EdgeConvertCreateDDLTest testObj1;
    // EdgeTable[] tables;
    // EdgeField[] fields;

    // public EdgeConvertCreateDDLTest(EdgeTable[] tables, EdgeField[] fields) {
    //     this.fields = fields;
    //     this.tables = tables;
    // }

    // public EdgeConvertCreateDDLTest() {}

    // public String getDatabaseName() {
    //     return null;
    // }

    // public String getProductName() {
    //     return null;
    // }

    // public String getSQLString() {
    //     return null;
    // }

    // public void createDDL() {
    // }

public class EdgeConvertCreateDDLTest {
    protected EdgeConvertCreateDDL testObj1;

    @Before
    public void setUp() throws Exception {
        EdgeTable[] tempTable1 = { new EdgeTable("1|table1"), new EdgeTable("1|table2") };
        EdgeField[] tempField1 = { new EdgeField("1|field1"), new EdgeField("1|field2") };

        // this.tables = tempTable1;
        // this.fields = tempField1;
      
        testObj1 = new EdgeConvertCreateDDL(tempTable1, tempField1) {
            public String getDatabaseName() {
                return null;
            }  

            public String getProductName() {
              return null;
            }

            public String getSQLString() { 
              return null;
            }
  
            public void createDDL() {}
        };

      //TODO: test initialize() method
    }

    // @Test
    // public void testInitialize() {
    //     testObj1.initialize();
    //     assertEquals(testObj1.numBoundsTable, tables.length);
    // }

    // @Test
    // public void testGetTable() {
    //     EdgeTable result = null;
    //     int numFigure = 4;
    //     for (int tIndex = 0; tIndex < tables.length; tIndex++) {
    //         if (numFigure == tables[tIndex].getNumFigure()) {
    //             result = tables[tIndex];
    //         }
    //     }
    //     assertEquals("GetTable was found", result, testObj1.getTable(numFigure));
    // }

    // @Test
    // public void testGetTableNull() {
    //     EdgeTable result = null;
    //     int numFigure = -1;
    //     for (int tIndex = 0; tIndex < tables.length; tIndex++) {
    //         if (numFigure == tables[tIndex].getNumFigure()) {
    //             result = tables[tIndex];
    //         }
    //     }
    //     assertEquals("GetTable was not found", result, testObj1.getTable(numFigure));

    // }

    // @Test
    // public void testGetField() {
    //     EdgeField result = null;
    //     int numFigure = 4;
    //     for (int fIndex = 0; fIndex < fields.length; fIndex++) {
    //         if (numFigure == fields[fIndex].getNumFigure()) {
    //             result = fields[fIndex];
    //         }
    //     }
    //     assertEquals("GetField was found", result, testObj1.getField(numFigure));
    // }

    // @Test
    // public void testGetDatabaseName() {
    //     assertEquals("DatabaseName was found", this.testObj1.getDatabaseName(), testObj1.getDatabaseName());

    // }
}
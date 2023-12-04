//package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeConvertCreateDDLTest extends EdgeConvertCreateDDL {
    EdgeConvertCreateDDLTest testObj1;

    public String getDatabaseName() {
        return null;
    }

    public String getProductName() {
        return null;
    }

    public String getSQLString() {
        return null;
    }

    public void createDDL() {
        
    }

    @Before
    public void setUp() throws Exception {
        this.testObj1 = new EdgeConvertCreateDDLTest();
    }

    @Test
    public void testInitialize() {
        testObj1.initialize();

        assertEquals(testObj1.numBoundsTable, tables.length);

    }

    @Test
    public void testGetTable() {
        EdgeTable result;
        int numFigure = 4;
        for (int tIndex = 0; tIndex < tables.length; tIndex++) {
            if (numFigure == tables[tIndex].getNumFigure()) {
                result = tables[tIndex];
            }
        }
        assertEquals("GetTable was found", result, testObj1.getTable(numFigure));
    }

    @Test
    public void testGetTableNull() {
        EdgeTable result;
        int numFigure = -1;
        for (int tIndex = 0; tIndex < tables.length; tIndex++) {
            if (numFigure == tables[tIndex].getNumFigure()) {
                result = tables[tIndex];
            }
        }
        assertEquals("GetTable was not found", result, testObj1.getTable(numFigure));

    }

    @Test
    public void testGetField() {
        EdgeField result;
        int numFigure = 4;
        for (int fIndex = 0; fIndex < fields.length; fIndex++) {
            if (numFigure == fields[fIndex].getNumFigure()) {
                result = fields[fIndex];
            }
        }
        assertEquals("GetField was found", result, testObj1.getField(numFigure));

    }

    @Test
    public void testGetDatabaseName() {
        assertEquals("DatabaseName was found", testObj1.DatabaseName, testObj1.getDatabaseName());

    }
}
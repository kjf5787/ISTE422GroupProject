package test.java;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
public class EdgeConvertCreateDDLExample extends EdgeConvertCreateDDL {
    
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
        return null;
    }
}
public class EdgeConvertCreateDDLTest {
    EdgeConvertCreateDDLExample testObj1;
    EdgeConvertCreateDDLExample testObj2;


    @Before
    public void setUp() throws Exception {
        this.testObj1 = new EdgeConvertCreateDDLExample();
    }

    @Test 
    public void testInitialize() {
        testObj1.initialize();

        assertEquals(testObj1.numBoundsTable, [tables.length]);

    }

    @Test
    public void testGetTable() {
        EdgeTable result;
        int numFigure = 4;
        for (int tIndex = 0; tIndex < tables.length; tIndex++) {
            if (numFigure == tables[tIndex].getNumFigure()) {
               result =  tables[tIndex];
            }
         }
        assertEquals("GetTable was found",result,testObj1.getTable(numFigure));
    }

    
}

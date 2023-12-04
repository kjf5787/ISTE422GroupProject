//package test.java;

import static org.junit.Assert.*;

import java.beans.Transient;

import org.junit.Before;
import org.junit.Test;

public class CreateDDLMySQLTest {
    CreateDDLMySQL testObj1;
    CreateDDLMySQL testObj2;

    @Before
    public void setUp() throws Exception {
        EdgeTable[] table1 = {new EdgeTable("table1"), new EdgeTable("table2")};
        EdgeField[] field1 = {new EdgeField("field1"), new EdgeField("field2")};
        EdgeTable[] table2 = {new EdgeTable("table1"), new EdgeTable("table2")};
        EdgeField[] field2 = {new EdgeField("field1"), new EdgeField("field2")};
        testObj1 = new CreateDDLMySQL(table1, field1);
        testObj2 = new CreateDDLMySQL(table2, field2);
    }

    @Test 
    public void testcreateDDL() {
        testObj1.createDDL();
        testObj2.createDDL();

        assertEquals(testObj1, testObj2);
    }

    @Test 
    public void testconvertStrBooleanToInt() {
        int outcome = testObj1.convertStrBooleanToInt("true");

        assertEquals("Testing convert boolean to int (True)", 1, outcome);
    }

    @Test 
    public void testgenerateDatabaseName() {
        String defaultName = "MySQLDB";

        assertEquals(defaultName, testObj1.generateDatabaseName());
    }

    @Test 
    public void testgetDatabaseName() {
        String databaseName = "MySQLDB";

        assertEquals(databaseName, testObj1.getDatabaseName());
    }

    @Test 
    public void testgetProductName() {
        assertEquals("Testing product name: ", "MySQL", testObj1.getProductName());
    }

    @Test 
    public void testgetSQLString() {
        String sqlString1 = testObj1.getSQLString();
        String sqlString2 = testObj2.getSQLString();

        assertEquals(sqlString1, sqlString2);
    }
}

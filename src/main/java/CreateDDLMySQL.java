import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateDDLMySQL extends EdgeConvertCreateDDL {

   private static Logger logger = LogManager.getLogger(CreateDDLMySQL.class.getName());
   protected String databaseName;
   //this array is for determining how MySQL refers to datatypes
   protected String[] strDataType = {"VARCHAR", "BOOL", "INT", "DOUBLE"};
   protected StringBuffer sb;

   public CreateDDLMySQL(EdgeTable[] inputTables, EdgeField[] inputFields) {
      super(inputTables, inputFields);
      sb = new StringBuffer();
   } //CreateDDLMySQL(EdgeTable[], EdgeField[])
   
   public CreateDDLMySQL() { //default constructor with empty arg list for to allow output dir to be set before there are table and field objects
      
   }
   
   public void createDDL() {
      EdgeConvertGUI.setReadSuccess(true);
      databaseName = generateDatabaseName();
      logger.debug("Creating database...");
      sb.append("CREATE DATABASE " + databaseName + ";\r\n");
      sb.append("USE " + databaseName + ";\r\n");
      for (int boundCount = 0; boundCount <= maxBound; boundCount++) { //process tables in order from least dependent (least number of bound tables) to most dependent
         for (int tableCount = 0; tableCount < numBoundTables.length; tableCount++) { //step through list of tables
            if (numBoundTables[tableCount] == boundCount) { //
               logger.debug("Creating table...");
               sb.append("CREATE TABLE " + tables[tableCount].getName() + " (\r\n");
               int[] nativeFields = tables[tableCount].getNativeFieldsArray();
               int[] relatedFields = tables[tableCount].getRelatedFieldsArray();
               boolean[] primaryKey = new boolean[nativeFields.length];
               int numPrimaryKey = 0;
               int numForeignKey = 0;
               for (int nativeFieldCount = 0; nativeFieldCount < nativeFields.length; nativeFieldCount++) { //print out the fields
                  EdgeField currentField = getField(nativeFields[nativeFieldCount]);
                  sb.append("\t" + currentField.getName() + " " + strDataType[currentField.getDataType()]);
                  if (currentField.getDataType() == 0) { //varchar
                     logger.debug("Appending varchar length...");
                     sb.append("(" + currentField.getVarcharValue() + ")"); //append varchar length in () if data type is varchar
                  }
                  if (currentField.getDisallowNull()) {
                     logger.warning("Not Null");
                     sb.append(" NOT NULL");
                  }
                  if (!currentField.getDefaultValue().equals("")) {
                     if (currentField.getDataType() == 1) { //boolean data type
                        logger.debug("Default...");
                        sb.append(" DEFAULT " + convertStrBooleanToInt(currentField.getDefaultValue()));
                     } else { //any other data type
                        sb.append(" DEFAULT " + currentField.getDefaultValue());
                     }
                  }
                  if (currentField.getIsPrimaryKey()) {
                     primaryKey[nativeFieldCount] = true;
                     logger.debug("Primary key: " + primaryKey[nativeFieldCount]);
                     numPrimaryKey++;
                  } else {
                     primaryKey[nativeFieldCount] = false;
                  }
                  if (currentField.getFieldBound() != 0) {
                     numForeignKey++;
                  }
                  logger.info("End of field.");
                  sb.append(",\r\n"); //end of field
               }
               if (numPrimaryKey > 0) { //table has primary key(s)
                  sb.append("CONSTRAINT " + tables[tableCount].getName() + "_PK PRIMARY KEY (");
                  logger.debug("Constraint: " + tables[tableCount].getName());
                  for (int i = 0; i < primaryKey.length; i++) {
                     if (primaryKey[i]) {
                        logger.debug("Appending Primary Key...");
                        sb.append(getField(nativeFields[i]).getName());
                        numPrimaryKey--;
                        if (numPrimaryKey > 0) {
                           sb.append(", ");
                        }
                     }
                  }
                  sb.append(")");
                  if (numForeignKey > 0) {
                     sb.append(",");
                  }
                  sb.append("\r\n");
               }
               if (numForeignKey > 0) { //table has foreign keys
                  int currentFK = 1;
                  for (int i = 0; i < relatedFields.length; i++) {
                     if (relatedFields[i] != 0) {
                        sb.append("CONSTRAINT " + tables[tableCount].getName() + "_FK" + currentFK +
                                  " FOREIGN KEY(" + getField(nativeFields[i]).getName() + ") REFERENCES " +
                                  getTable(getField(nativeFields[i]).getTableBound()).getName() + "(" + getField(relatedFields[i]).getName() + ")");
                        logger.debug("Constraint: " + tables[tableCount].getName());
                        logger.debug("FK: " + currentFK);
                        logger.debug("Foreign Key: " + getField(nativeFields[i]).getName());
                        logger.debug("References: " + getTable(getField(nativeFields[i]).getTableBound()).getName());
                        if (currentFK < numForeignKey) {
                           sb.append(",\r\n");
                        }
                        currentFK++;
                     }
                  }
                  sb.append("\r\n");
               }
               logger.info("End of table...");
               sb.append(");\r\n\r\n"); //end of table
            }
         }
      }
   }

   protected int convertStrBooleanToInt(String input) { //MySQL uses '1' and '0' for boolean types
      if (input.equals("true")) {
         logger.info("True");
         return 1;
      } else {
         logger.info("False");
         return 0;
      }
   }
   
   public String generateDatabaseName() { //prompts user for database name
      String dbNameDefault = "MySQLDB";
      //String databaseName = "";

      do {
         databaseName = (String)JOptionPane.showInputDialog(
                       null,
                       "Enter the database name:",
                       "Database Name",
                       JOptionPane.PLAIN_MESSAGE,
                       null,
                       null,
                       dbNameDefault);
         if (databaseName == null) {
            logger.info("Database name is Null");
            EdgeConvertGUI.setReadSuccess(false);
            return "";
         }
         if (databaseName.equals("")) {
            logger.warning("Name is required for your database");
            JOptionPane.showMessageDialog(null, "You must select a name for your database.");
         }
      } while (databaseName.equals(""));
      return databaseName;
   }
   
   public String getDatabaseName() {
      logger.debug("Getting database name: " + databaseName);
      return databaseName;
   }
   
   public String getProductName() {
      logger.debug("Getting product name: " + "MySQL");
      return "MySQL";
   }

   public String getSQLString() {
      createDDL();
      logger.debug("Getting SQL String: " + sb.toString());
      return sb.toString();
   }
   
}//EdgeConvertCreateDDL

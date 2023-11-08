import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeField { 
   private static Logger logger = LogManager.getLogger(EdgeField.class.getName());

   private int numFigure, tableID, tableBound, fieldBound, dataType, varcharValue;
   private String name, defaultValue;
   private boolean disallowNull, isPrimaryKey;
   private static String[] strDataType = {"Varchar", "Boolean", "Integer", "Double"};
   public static final int VARCHAR_DEFAULT_LENGTH = 1;
   
   public EdgeField(String inputString) {
      logger.info("creating new EdgeField instance");
      logger.debug("creating new EdgeField instance using " + inputString);
      
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numFigure = Integer.parseInt(st.nextToken());
      logger.debug("setting defaults for EdgeField");
      name = st.nextToken();
      tableID = 0;
      tableBound = 0;
      fieldBound = 0;
      disallowNull = false;
      isPrimaryKey = false;
      defaultValue = "";
      varcharValue = VARCHAR_DEFAULT_LENGTH;
      dataType = 0;
   }
   
   public int getNumFigure() {
      logger.debug("getting numFigure");
      return numFigure;
   }
   
   public String getName() {
      logger.debug("getting name");
      return name;
   }
   
   public int getTableID() {
      logger.debug("getting tableID");
      return tableID;
   }
   
   public void setTableID(int value) {
      logger.debug("setting tableID to " + value);
      tableID = value;
   }
   
   public int getTableBound() {
      logger.debug("getting tableBound");
      return tableBound;
   }
   
   public void setTableBound(int value) {
      logger.debug("setting tableBound to " + value);
      tableBound = value;
   }

   public int getFieldBound() {
      logger.debug("getting fieldBound");
      return fieldBound;
   }
   
   public void setFieldBound(int value) {
      logger.debug("setting fieldBound to " + value);
      fieldBound = value;
   }

   public boolean getDisallowNull() {
      logger.debug("getting disallowNull");
      return disallowNull;
   }
   
   public void setDisallowNull(boolean value) {
      logger.debug("setting disallowNull to " + value);
      disallowNull = value;
   }
   
   public boolean getIsPrimaryKey() {
      logger.debug("getting whether isPrimaryKey");
      return isPrimaryKey;
   }
   
   public void setIsPrimaryKey(boolean value) {
      logger.debug("setting isPrimaryKey to " + value);
      isPrimaryKey = value;
   }
   
   public String getDefaultValue() {
      logger.debug("getting defaultValue");
      return defaultValue;
   }
   
   public void setDefaultValue(String value) {
      logger.debug("setting defaultValue to " + value);
      defaultValue = value;
   }
   
   public int getVarcharValue() {
      logger.debug("getting varcharValue");
      return varcharValue;
   }
   
   public void setVarcharValue(int value) {
      logger.debug("setting varcharValue to " + value);
      if (value > 0) {
         varcharValue = value;
      }
   }
   public int getDataType() {
      logger.debug("getting dataType");
      return dataType;
   }
   
   public void setDataType(int value) {
      logger.debug("setting dataType to " + value);
      if (value >= 0 && value < strDataType.length) {
         dataType = value;
      }
   }
   
   public static String[] getStrDataType() {
      logger.debug("getting strDataType");
      return strDataType;
   }
   
   public String toString() {
      logger.debug("printing tostring");
      return numFigure + EdgeConvertFileParser.DELIM +
      name + EdgeConvertFileParser.DELIM +
      tableID + EdgeConvertFileParser.DELIM +
      tableBound + EdgeConvertFileParser.DELIM +
      fieldBound + EdgeConvertFileParser.DELIM +
      dataType + EdgeConvertFileParser.DELIM +
      varcharValue + EdgeConvertFileParser.DELIM +
      isPrimaryKey + EdgeConvertFileParser.DELIM +
      disallowNull + EdgeConvertFileParser.DELIM +
      defaultValue;
   }
}

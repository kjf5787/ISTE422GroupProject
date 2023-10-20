import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeTable {
   private static Logger logger = LogManager.getLogger(EdgeTable.class.getName());

   private int numFigure;
   private String name;
   private ArrayList alRelatedTables, alNativeFields;
   private int[] relatedTables, relatedFields, nativeFields;

   public EdgeTable(String inputString) {
      logger.info("creating new EdgeTable instance");
      logger.debug("creating new EdgeTable instance using " + inputString);

      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numFigure = Integer.parseInt(st.nextToken());
      name = st.nextToken();
      alRelatedTables = new ArrayList();
      alNativeFields = new ArrayList();
   }

   public int getNumFigure() {
      logger.debug("getting numFigure: " + numFigure);
      return numFigure;
   }

   public String getName() {
      logger.debug("getting name: " + name);
      return name;
   }

   public void addRelatedTable(int relatedTable) {
      logger.debug("adding " + relatedTable + " to alRelatedTables");
      alRelatedTables.add(new Integer(relatedTable));
   }

   public int[] getRelatedTablesArray() {
      logger.debug("getting relatedTables array: " + relatedTables);
      return relatedTables;
   }

   public int[] getRelatedFieldsArray() {
      logger.debug("getting relatedFields array: " + relatedFields);
      return relatedFields;
   }

   public void setRelatedField(int index, int relatedValue) {
      logger.debug("setting relatedFields[" + index + "] to " + relatedValue);
      relatedFields[index] = relatedValue;
   }

   public int[] getNativeFieldsArray() {
      logger.debug("getting nativeFields array: " + nativeFields);
      return nativeFields;
   }

   public void addNativeField(int value) {
      logger.debug("adding " + value + " to alNativeFields");
      alNativeFields.add(new Integer(value));
   }

   public void moveFieldUp(int index) { // move the field closer to the beginning of the list
      logger.info("move the field closer to the beginning of the list");
      if (index == 0) {
         return;
      }
      logger.debug("switching native fields with index of " + index + " and " + (index - 1));
      int tempNative = nativeFields[index - 1]; // save element at destination index
      nativeFields[index - 1] = nativeFields[index]; // copy target element to destination
      nativeFields[index] = tempNative; // copy saved element to target's original location
      logger.debug("nativeFields[" + index + "]: " + nativeFields[index]);
      logger.debug("nativeFields[" + (index - 1) + "]: " + nativeFields[(index - 1)]);

      logger.debug("switching related fields with index of " + index + " and " + (index - 1));
      int tempRelated = relatedFields[index - 1]; // save element at destination index
      relatedFields[index - 1] = relatedFields[index]; // copy target element to destination
      relatedFields[index] = tempRelated; // copy saved element to target's original location
      logger.debug("relatedFields[" + index + "]: " + relatedFields[index]);
      logger.debug("relatedFields[" + (index - 1) + "]: " + relatedFields[(index - 1)]);
   }

   public void moveFieldDown(int index) { // move the field closer to the end of the list
      logger.info("move the field closer to the end of the list");
      if (index == (nativeFields.length - 1)) {
         return;
      }
      logger.debug("switching native fields with index of " + index + " and " + (index + 1));
      int tempNative = nativeFields[index + 1]; // save element at destination index
      nativeFields[index + 1] = nativeFields[index]; // copy target element to destination
      nativeFields[index] = tempNative; // copy saved element to target's original location
      logger.debug("nativeFields[" + index + "]: " + nativeFields[index]);
      logger.debug("nativeFields[" + (index + 1) + "]: " + nativeFields[(index - 1)]);

      logger.debug("switching related fields with index of " + index + " and " + (index + 1));
      int tempRelated = relatedFields[index + 1]; // save element at destination index
      relatedFields[index + 1] = relatedFields[index]; // copy target element to destination
      relatedFields[index] = tempRelated; // copy saved element to target's original location
      logger.debug("relatedFields[" + index + "]: " + relatedFields[index]);
      logger.debug("relatedFields[" + (index - 1) + "]: " + relatedFields[(index + 1)]);
   }

   public void makeArrays() { // convert the ArrayLists into int[]
      logger.info("convert the ArrayList into int[]");
      Integer[] temp;
      temp = (Integer[]) alNativeFields.toArray(new Integer[alNativeFields.size()]);
      nativeFields = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         nativeFields[i] = temp[i].intValue();
      }
      logger.debug("nativeFields array: " + nativeFields);

      temp = (Integer[]) alRelatedTables.toArray(new Integer[alRelatedTables.size()]);
      relatedTables = new int[temp.length];
      for (int i = 0; i < temp.length; i++) {
         relatedTables[i] = temp[i].intValue();
      }
      logger.debug("relatedTables array: " + nativeFields);

      relatedFields = new int[nativeFields.length];
      for (int i = 0; i < relatedFields.length; i++) {
         relatedFields[i] = 0;
      }
      logger.debug("relatedFields array: " + nativeFields);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();

      logger.debug("numFigure: " + numFigure + ", name: " + name);
      sb.append("Table: " + numFigure + "\r\n");
      sb.append("{\r\n");
      sb.append("TableName: " + name + "\r\n");
      logger.debug(sb);

      sb.append("NativeFields: ");
      logger.debug("nativeFields.length: " + nativeFields.length);
      for (int i = 0; i < nativeFields.length; i++) {
         sb.append(nativeFields[i]);
         if (i < (nativeFields.length - 1)) {
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      logger.debug(sb);

      sb.append("\r\nRelatedTables: ");
      logger.debug("relatedTables.length: " + relatedTables.length);
      for (int i = 0; i < relatedTables.length; i++) {
         sb.append(relatedTables[i]);
         if (i < (relatedTables.length - 1)) {
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      logger.debug(sb);

      sb.append("\r\nRelatedFields: ");
      logger.debug("relatedFields.length: " + relatedFields.length);
      for (int i = 0; i < relatedFields.length; i++) {
         sb.append(relatedFields[i]);
         if (i < (relatedFields.length - 1)) {
            sb.append(EdgeConvertFileParser.DELIM);
         }
      }
      sb.append("\r\n}\r\n");
      logger.debug(sb);

      return sb.toString();
   }
}

import java.io.*;
import java.util.*;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeConvertFileParser {
   private static Logger logger = LogManager.getLogger(EdgeConvertFileParser.class.getName());

   // private String filename = "test.edg";
   private File parseFile;
   private FileReader fr;
   private BufferedReader br;
   private String currentLine;
   private ArrayList alTables, alFields, alConnectors;
   private EdgeTable[] tables;
   private EdgeField[] fields;
   private EdgeField tempField;
   private EdgeConnector[] connectors;
   private String style;
   private String text;
   private String tableName;
   private String fieldName;
   private boolean isEntity, isAttribute, isUnderlined = false;
   private int numFigure, numConnector, numFields, numTables, numNativeRelatedFields;
   private int endPoint1, endPoint2;
   private int numLine;
   private String endStyle1, endStyle2;
   public static final String EDGE_ID = "EDGE Diagram File"; // first line of .edg files should be this
   public static final String SAVE_ID = "EdgeConvert Save File"; // first line of save files should be this
   public static final String DELIM = "|";

   public EdgeConvertFileParser(File constructorFile) {
      logger.info("creating new EdgeConvertFileParser instance");

      numFigure = 0;
      numConnector = 0;
      alTables = new ArrayList();
      alFields = new ArrayList();
      alConnectors = new ArrayList();
      isEntity = false;
      isAttribute = false;
      parseFile = constructorFile;
      numLine = 0;
      this.openFile(parseFile);
   }

   public void parseEdgeFile() throws IOException {
      logger.info("parsing edge file");

      while ((currentLine = br.readLine()) != null) {
         currentLine = currentLine.trim();
         if (currentLine.startsWith("Figure ")) { // this is the start of a Figure entry
            logger.debug("starting Figure entry");

            numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); // get the Figure number
            logger.debug("Figure number: " + numFigure);

            currentLine = br.readLine().trim(); // this should be "{"
            currentLine = br.readLine().trim();
            if (!currentLine.startsWith("Style")) { // this is to weed out other Figures, like Labels
               logger.debug("starting Style entry");
               continue;
            } else {
               style = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); // get the
                                                                                                            // Style
                                                                                                            // parameter
               logger.debug("style parameters: " + style);

               if (style.startsWith("Relation")) { // presence of Relations implies lack of normalization
                  logger.error("found Relation entry- lacks normalization");
                  JOptionPane.showMessageDialog(null, "The Edge Diagrammer file\n" + parseFile
                        + "\ncontains relations.  Please resolve them and try again.");
                  EdgeConvertGUI.setReadSuccess(false);
                  break;
               }
               if (style.startsWith("Entity")) {
                  logger.debug("starting Entity entry");
                  isEntity = true;
               }
               if (style.startsWith("Attribute")) {
                  logger.debug("starting Attribute entry");
                  isAttribute = true;
               }
               if (!(isEntity || isAttribute)) { // these are the only Figures we're interested in
                  logger.warn("these Figures that are not Entities and Attributes");
                  continue;
               }
               currentLine = br.readLine().trim(); // this should be Text
               logger.debug("currentLine: " + currentLine);

               text = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\""))
                     .replaceAll(" ", ""); // get the Text parameter
               logger.debug("text: " + text);

               if (text.equals("")) {
                  logger.error("these Entities or Attributes are blank");
                  JOptionPane.showMessageDialog(null,
                        "There are entities or attributes with blank names in this diagram.\nPlease provide names for them and try again.");
                  EdgeConvertGUI.setReadSuccess(false);
                  break;
               }
               int escape = text.indexOf("\\");
               if (escape > 0) { // Edge denotes a line break as "\line", disregard anything after a backslash
                  text = text.substring(0, escape);
               }
               logger.debug("text: " + text);

               do { // advance to end of record, look for whether the text is underlined
                  logger.debug("advance to end of record");

                  currentLine = br.readLine().trim();
                  logger.debug("currentLine: " + currentLine);

                  if (currentLine.startsWith("TypeUnderl")) {
                     logger.debug("currentLine is underlined");
                     isUnderlined = true;
                  }
               } while (!currentLine.equals("}")); // this is the end of a Figure entry
               logger.debug("end of Figure entry");

               if (isEntity) { // create a new EdgeTable object and add it to the alTables ArrayList
                  logger.debug("this element is an Entity");

                  if (isTableDup(text)) {
                     logger.error("there are multiple tables called " + text + " in the diagram");
                     JOptionPane.showMessageDialog(null, "There are multiple tables called " + text
                           + " in this diagram.\nPlease rename all but one of them and try again.");
                     EdgeConvertGUI.setReadSuccess(false);
                     break;
                  }

                  logger.debug("creating new EdgeTable object");
                  logger.debug("adding new EdgeTable object to alTables ArrayList");
                  alTables.add(new EdgeTable(numFigure + DELIM + text));
               }
               if (isAttribute) { // create a new EdgeField object and add it to the alFields ArrayList
                  logger.debug("this element is an Attribute");

                  logger.debug("creating new EdgeTable object");
                  tempField = new EdgeField(numFigure + DELIM + text);
                  logger.debug("setting this new EdgeTable object as the primary key");
                  tempField.setIsPrimaryKey(isUnderlined);
                  logger.debug("adding new EdgeTable object to alTables ArrayList");
                  alFields.add(tempField);
               }
               // reset flags
               logger.debug("reseting isEntity, isAttribute and isUnderlined flags");
               isEntity = false;
               isAttribute = false;
               isUnderlined = false;
            }
         } // if("Figure")
         if (currentLine.startsWith("Connector ")) { // this is the start of a Connector entry
            logger.debug("starting Connector entry");

            numConnector = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); // get the Connector
                                                                                                  // number
            logger.debug("Figure number: " + numConnector);

            currentLine = br.readLine().trim(); // this should be "{"
            logger.debug("currentLine: " + currentLine + " - this should be '{'");

            currentLine = br.readLine().trim(); // not interested in Style
            currentLine = br.readLine().trim(); // Figure1
            endPoint1 = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));
            currentLine = br.readLine().trim(); // Figure2
            endPoint2 = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1));
            currentLine = br.readLine().trim(); // not interested in EndPoint1
            currentLine = br.readLine().trim(); // not interested in EndPoint2
            currentLine = br.readLine().trim(); // not interested in SuppressEnd1
            currentLine = br.readLine().trim(); // not interested in SuppressEnd2
            currentLine = br.readLine().trim(); // End1
            endStyle1 = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); // get the
                                                                                                             // End1
                                                                                                             // parameter
            logger.debug("End1 parameter: " + endStyle1);

            currentLine = br.readLine().trim(); // End2
            endStyle2 = currentLine.substring(currentLine.indexOf("\"") + 1, currentLine.lastIndexOf("\"")); // get the
                                                                                                             // End2
                                                                                                             // parameter
            logger.debug("End1 parameter: " + endStyle2);

            do { // advance to end of record
               logger.debug("advance to end of record");
               currentLine = br.readLine().trim();
            } while (!currentLine.equals("}")); // this is the end of a Connector entry

            logger.debug("adding new EdgeConnector to alConnectors");
            alConnectors.add(new EdgeConnector(
                  numConnector + DELIM + endPoint1 + DELIM + endPoint2 + DELIM + endStyle1 + DELIM + endStyle2));
         } // if("Connector")
      } // while()
   } // parseEdgeFile()

   private void resolveConnectors() { // Identify nature of Connector endpoints
      logger.info("identifying the nature of Connector endpoints");

      int endPoint1, endPoint2;
      int fieldIndex = 0, table1Index = 0, table2Index = 0;
      for (int cIndex = 0; cIndex < connectors.length; cIndex++) {
         endPoint1 = connectors[cIndex].getEndPoint1();
         endPoint2 = connectors[cIndex].getEndPoint2();
         fieldIndex = -1;

         logger.debug("search field array for endpoints");
         for (int fIndex = 0; fIndex < fields.length; fIndex++) { // search fields array for endpoints
            if (endPoint1 == fields[fIndex].getNumFigure()) { // found endPoint1 in fields array
               connectors[cIndex].setIsEP1Field(true); // set appropriate flag
               fieldIndex = fIndex; // identify which element of the fields array that endPoint1 was found in
               logger.debug("found endPoint1 in fields array in index " + fieldIndex);
            }
            if (endPoint2 == fields[fIndex].getNumFigure()) { // found endPoint2 in fields array
               connectors[cIndex].setIsEP2Field(true); // set appropriate flag
               fieldIndex = fIndex; // identify which element of the fields array that endPoint2 was found in
               logger.debug("found endPoint2 in fields array in index " + fieldIndex);
            }
         }

         logger.debug("search tables array for endpoints");
         for (int tIndex = 0; tIndex < tables.length; tIndex++) { // search tables array for endpoints
            if (endPoint1 == tables[tIndex].getNumFigure()) { // found endPoint1 in tables array
               connectors[cIndex].setIsEP1Table(true); // set appropriate flag
               table1Index = tIndex; // identify which element of the tables array that endPoint1 was found in
               logger.debug("found endPoint1 in tables array in index " + fieldIndex);
            }
            if (endPoint2 == tables[tIndex].getNumFigure()) { // found endPoint1 in tables array
               connectors[cIndex].setIsEP2Table(true); // set appropriate flag
               table2Index = tIndex; // identify which element of the tables array that endPoint2 was found in
               logger.debug("found endPoint2 in tables array in index " + fieldIndex);
            }
         }

         if (connectors[cIndex].getIsEP1Field() && connectors[cIndex].getIsEP2Field()) { // both endpoints are fields,
                                                                                         // implies lack of
                                                                                         // normalization
            logger.error("both endpoints are Fields- lack of normalization");
            JOptionPane.showMessageDialog(null, "The Edge Diagrammer file\n" + parseFile
                  + "\ncontains composite attributes. Please resolve them and try again.");
            EdgeConvertGUI.setReadSuccess(false); // this tells GUI not to populate JList components
            break; // stop processing list of Connectors
         }

         if (connectors[cIndex].getIsEP1Table() && connectors[cIndex].getIsEP2Table()) { // both endpoints are tables
            logger.debug("both endpoints are tables");

            if ((connectors[cIndex].getEndStyle1().indexOf("many") >= 0) &&
                  (connectors[cIndex].getEndStyle2().indexOf("many") >= 0)) { // the connector represents a many-many
                                                                              // relationship, implies lack of
                                                                              // normalization
               logger.error("there is a many to many relationship- lack of normalization");
               JOptionPane.showMessageDialog(null,
                     "There is a many-many relationship between tables\n\"" + tables[table1Index].getName()
                           + "\" and \"" + tables[table2Index].getName() + "\""
                           + "\nPlease resolve this and try again.");
               EdgeConvertGUI.setReadSuccess(false); // this tells GUI not to populate JList components
               break; // stop processing list of Connectors
            } else { // add Figure number to each table's list of related tables
               tables[table1Index].addRelatedTable(tables[table2Index].getNumFigure());
               logger.debug("adding " + tables[table2Index].getNumFigure() + " each table's list of related tables");

               tables[table2Index].addRelatedTable(tables[table1Index].getNumFigure());
               logger.debug("adding " + tables[table1Index].getNumFigure() + " each table's list of related tables");
               continue; // next Connector
            }
         }

         if (fieldIndex >= 0 && fields[fieldIndex].getTableID() == 0) { // field has not been assigned to a table yet
            logger.debug("field has not been assigned to a table yet");

            if (connectors[cIndex].getIsEP1Table()) { // endpoint1 is the table
               logger.debug("endpoint1 is the table- adding to table's field list");

               tables[table1Index].addNativeField(fields[fieldIndex].getNumFigure()); // add to the appropriate table's
                                                                                      // field list
               fields[fieldIndex].setTableID(tables[table1Index].getNumFigure()); // tell the field what table it
                                                                                  // belongs to
            } else { // endpoint2 is the table
               logger.debug("endpoint2 is the table- adding to table's field list");

               tables[table2Index].addNativeField(fields[fieldIndex].getNumFigure()); // add to the appropriate table's
                                                                                      // field list
               fields[fieldIndex].setTableID(tables[table2Index].getNumFigure()); // tell the field what table it
                                                                                  // belongs to
            }
         } else if (fieldIndex >= 0) { // field has already been assigned to a table
            logger.error("field has already been assigned to a table");
            JOptionPane.showMessageDialog(null, "The attribute " + fields[fieldIndex].getName()
                  + " is connected to multiple tables.\nPlease resolve this and try again.");
            EdgeConvertGUI.setReadSuccess(false); // this tells GUI not to populate JList components
            break; // stop processing list of Connectors
         }
      } // connectors for() loop
   } // resolveConnectors()

   public void parseSaveFile() throws IOException { // this method is unclear and confusing in places
      logger.info("parsing save file");

      StringTokenizer stTables, stNatFields, stRelFields, stNatRelFields, stField;
      EdgeTable tempTable;
      EdgeField tempField;
      currentLine = br.readLine();
      currentLine = br.readLine(); // this should be "Table: "
      logger.debug("currentLine: " + currentLine);

      while (currentLine.startsWith("Table: ")) {
         logger.debug("current line starts with 'Table'");

         numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); // get the Table number
         logger.debug("numFigure: " + numFigure);

         currentLine = br.readLine(); // this should be "{"
         logger.debug("currentLine: " + currentLine + " (should be '{')");

         currentLine = br.readLine(); // this should be "TableName"
         logger.debug("currentLine: " + currentLine + " (should be 'TableName')");

         tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
         tempTable = new EdgeTable(numFigure + DELIM + tableName);

         currentLine = br.readLine(); // this should be the NativeFields list
         logger.debug("currentLine: " + currentLine + " (should be the NativeFields list)");

         stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         numFields = stNatFields.countTokens();
         for (int i = 0; i < numFields; i++) {
            tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
         }

         currentLine = br.readLine(); // this should be the RelatedTables list
         logger.debug("currentLine: " + currentLine + " (should be the RelatedTables list)");

         stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         numTables = stTables.countTokens();
         for (int i = 0; i < numTables; i++) {
            tempTable.addRelatedTable(Integer.parseInt(stTables.nextToken()));
         }
         tempTable.makeArrays();

         currentLine = br.readLine(); // this should be the RelatedFields list
         logger.debug("currentLine: " + currentLine + " (should be the RelatedFields list)");

         stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
         numFields = stRelFields.countTokens();

         for (int i = 0; i < numFields; i++) {
            tempTable.setRelatedField(i, Integer.parseInt(stRelFields.nextToken()));
         }

         logger.debug("adding " + tempTable + " to alTables");
         alTables.add(tempTable);
         currentLine = br.readLine(); // this should be "}"
         logger.debug("currentLine: " + currentLine + " (should be '{')");

         currentLine = br.readLine(); // this should be "\n"
         logger.debug("currentLine: " + currentLine + " (should be '\n')");

         currentLine = br.readLine(); // this should be either the next "Table: ", #Fields#
         logger.debug("currentLine: " + currentLine + " (should either be the next 'Table' or '#Fields#')");
      }
      while ((currentLine = br.readLine()) != null) {
         logger.debug("--- parsing next line ---");
         stField = new StringTokenizer(currentLine, DELIM);
         numFigure = Integer.parseInt(stField.nextToken());
         fieldName = stField.nextToken();
         tempField = new EdgeField(numFigure + DELIM + fieldName);
         tempField.setTableID(Integer.parseInt(stField.nextToken()));
         tempField.setTableBound(Integer.parseInt(stField.nextToken()));
         tempField.setFieldBound(Integer.parseInt(stField.nextToken()));
         tempField.setDataType(Integer.parseInt(stField.nextToken()));
         tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));
         tempField.setIsPrimaryKey(Boolean.valueOf(stField.nextToken()).booleanValue());
         tempField.setDisallowNull(Boolean.valueOf(stField.nextToken()).booleanValue());
         if (stField.hasMoreTokens()) { // Default Value may not be defined
            logger.warn("default value may not be defined");
            tempField.setDefaultValue(stField.nextToken());
         }
         logger.debug("adding " + tempField + " to alFields");
         alFields.add(tempField);
      }
   } // parseSaveFile()

   private void makeArrays() { // convert ArrayList objects into arrays of the appropriate Class type
      logger.info("converting ArrayList objects into arrays of appropriate Class type");
      if (alTables != null) {
         logger.debug("converting alTables to array");
         tables = (EdgeTable[]) alTables.toArray(new EdgeTable[alTables.size()]);
      }
      if (alFields != null) {
         logger.debug("converting alFields to array");
         fields = (EdgeField[]) alFields.toArray(new EdgeField[alFields.size()]);
      }
      if (alConnectors != null) {
         logger.debug("converting alConnectors to array");
         connectors = (EdgeConnector[]) alConnectors.toArray(new EdgeConnector[alConnectors.size()]);
      }
   }

   private boolean isTableDup(String testTableName) {
      logger.info("checking to see if " + testTableName + " exists");
      for (int i = 0; i < alTables.size(); i++) {
         EdgeTable tempTable = (EdgeTable) alTables.get(i);
         if (tempTable.getName().equals(testTableName)) {
            logger.debug(testTableName + " exists");
            return true;
         }
      }
      logger.debug(testTableName + " does not exist");
      return false;
   }

   public EdgeTable[] getEdgeTables() {
      logger.debug("getting EdgeTable: " + tables);
      return tables;
   }

   public EdgeField[] getEdgeFields() {
      logger.debug("getting EdgeField: " + fields);
      return fields;
   }

   public void openFile(File inputFile) {
      logger.info("opening file " + inputFile.getName());
      try {
         fr = new FileReader(inputFile);
         br = new BufferedReader(fr);
         // test for what kind of file we have
         currentLine = br.readLine().trim();
         numLine++;
         if (currentLine.startsWith(EDGE_ID)) { // the file chosen is an Edge Diagrammer file
            logger.debug("the file is an Edge Diagrammer file");

            logger.debug("parsing this file");
            this.parseEdgeFile(); // parse the file

            logger.debug("closing this file");
            br.close();

            logger.debug("converting ArrayList objects into array of the appropriate Class type");
            this.makeArrays(); // convert ArrayList objects into arrays of the appropriate Class type

            logger.debug("identifying the nature of Connector endpoints");
            this.resolveConnectors(); // Identify nature of Connector endpoints
         } else {
            if (currentLine.startsWith(SAVE_ID)) { // the file chosen is a Save file created by this application
               logger.debug("the file is an Save file created by this application");

               logger.debug("parsing this file");
               this.parseSaveFile(); // parse the file

               logger.debug("closing this file");
               br.close();

               logger.debug("converting ArrayList objects into array of the appropriate Class type");
               this.makeArrays(); // convert ArrayList objects into arrays of the appropriate Class type
            } else { // the file chosen is something else
               logger.error("unrecognized file format");
               JOptionPane.showMessageDialog(null, "Unrecognized file format");
            }
         }
      } // try
      catch (FileNotFoundException fnfe) {
         logger.error("cannot find " + inputFile.getName());
         // System.out.println("Cannot find \"" + inputFile.getName() + "\".");
         System.exit(0);
      } // catch FileNotFoundException
      catch (IOException ioe) {
         logger.error("something went wrong");
         // System.out.println(ioe);
         System.exit(0);
      } // catch IOException
   } // openFile()
} // EdgeConvertFileHandler

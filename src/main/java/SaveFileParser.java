import java.io.*;
import java.util.*;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFileParser extends EdgeConvertFileParser {

   protected String tableName; 
   protected String fieldName;

   protected int numFields; 
   protected int numTables; 

    private static Logger logger = LogManager.getLogger(SaveFileParser.class.getName());

    /*
     * Constructor 
     */
    public SaveFileParser(File constructorFile){
        super(constructorFile);
        this.openFile(parseFile);
    }

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

     public void openFile(File inputFile) {
        logger.info("opening file " + inputFile.getName());
        try {
            fr = new FileReader(inputFile);
            br = new BufferedReader(fr);
            // test for what kind of file we have
            currentLine = br.readLine().trim();
            numLine++;
            if (currentLine.startsWith(SAVE_ID)) { // the file chosen is a Save file created by this application
                logger.debug("the file is an Save file created by this application");        
                logger.debug("parsing this file");
                this.parseSaveFile(); // parse the fil      
                logger.debug("closing this file");
                br.close();     
                logger.debug("converting ArrayList objects into array of the appropriate Class type");
                this.makeArrays(); // convert ArrayList objects into arrays of the appropriate Class type
            } else { // the file chosen is something else
                 logger.error("unrecognized file format");
                 JOptionPane.showMessageDialog(null, "Unrecognized file format");
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
}

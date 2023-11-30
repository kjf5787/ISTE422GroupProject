import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EdgeConvertFileParser {
   private static Logger logger = LogManager.getLogger(EdgeConvertFileParser.class.getName());

   // private String filename = "test.edg";
   // protected File parseFile; 
   protected FileReader fr; 
   protected BufferedReader br;
   protected String currentLine; 
   protected ArrayList alTables, alFields, alConnectors;
   protected EdgeTable[] tables; 
   protected EdgeField[] fields; 
   protected EdgeField tempField;
   protected EdgeConnector[] connectors;  
   protected int numFigure;

   protected int numConnector; 

   protected int numLine; 
   public static final String EDGE_ID = "EDGE Diagram File"; // first line of .edg files should be this
   public static final String SAVE_ID = "EdgeConvert Save File"; // first line of save files should be this
   public static final String DELIM = "|";

   public EdgeConvertFileParser() {
      logger.info("creating new EdgeConvertFileParser instance");

      numFigure = 0;
      numConnector = 0;
      alTables = new ArrayList();
      alFields = new ArrayList();
      alConnectors = new ArrayList();
      numLine = 0;
   }

   protected void makeArrays() { // convert ArrayList objects into arrays of the appropriate Class type
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

   public EdgeTable[] getEdgeTables() {
      logger.debug("getting EdgeTable: " + tables);
      return tables;
   }

   public EdgeField[] getEdgeFields() {
      logger.debug("getting EdgeField: " + fields);
      return fields;
   }
} // EdgeConvertFileHandler

import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EdgeConvertFileParser {
   private static Logger logger = LogManager.getLogger(EdgeConvertFileParser.class.getName());

   // private String filename = "test.edg";
   protected File parseFile; // used in both
   protected FileReader fr; // used in savefileparser
   protected BufferedReader br; // used in savefileparser
   protected String currentLine; // used in savefileparser
   protected ArrayList alTables, alFields, alConnectors; // used in savefileparser
   protected EdgeTable[] tables; // used in edgefileparser & here
   protected EdgeField[] fields; // used in edgefileparser & here
   protected EdgeField tempField; // used in edgefileparser
   protected EdgeConnector[] connectors; // used in edgefileparser & here
   protected String style; // used in edgefileparser
   protected String text; // used in edgefileparser
   protected String tableName; // used in savefileparser
   protected String fieldName; // used in savefileparser
   protected boolean isEntity, isAttribute, isUnderlined = false; // used in edgefileparser
   protected int numFigure; // used in savefileparser

   protected int numConnector; // used in edgefileparser

   protected int numFields; // used in savefileparser

   protected int numTables; // used in savefileparser

   private int numNativeRelatedFields;
   protected int endPoint1, endPoint2; // used in edgefileparser
   protected int numLine; // used in savefileparser
   protected String endStyle1, endStyle2; // used in edgefileparser
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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EdgeConvertCreateDDL {
   private static Logger logger = LogManager.getLogger(EdgeField.class.getName());

   static String[] products = {"MySQL"};
   protected EdgeTable[] tables; //master copy of EdgeTable objects
   protected EdgeField[] fields; //master copy of EdgeField objects
   protected int[] numBoundTables;
   protected int maxBound;
   protected StringBuffer sb;
   protected int selected;
   
   public EdgeConvertCreateDDL(EdgeTable[] tables, EdgeField[] fields) {
      logger.info("creating new DDL instance using " + tables + " and " + fields);
      this.tables = tables;
      this.fields = fields;
      initialize();
   } //EdgeConvertCreateDDL(EdgeTable[], EdgeField[]) 
   
   public EdgeConvertCreateDDL() { //default constructor with empty arg list for to allow output dir to be set before there are table and field objects
      logger.info("creating new DDL instance using default constructor");
   } //EdgeConvertCreateDDL()

   public void initialize() {
      numBoundTables = new int[tables.length];
      maxBound = 0;
      sb = new StringBuffer();
      logger.debug("Initializing edgeConvert...");

      for (int i = 0; i < tables.length; i++) { //step through list of tables
         logger.debug("Stepping through tables...");
         int numBound = 0; //initialize counter for number of bound tables
         int[] relatedFields = tables[i].getRelatedFieldsArray();
        
          System.out.println("1 - relatedFields arr: "+relatedFields);
        
         for (int j = 0; j < relatedFields.length; j++) { //step through related fields list
            logger.debug("Stepping through fields...");
            if (relatedFields[j] != 0) {
               numBound++; //count the number of non-zero related fields
            }
         }
         numBoundTables[i] = numBound;
         if (numBound > maxBound) {
            logger.info("reached maxBound and set new maxBound");
            maxBound = numBound;
         }
      }
      logger.info("initialize complete");
   }
   
   protected EdgeTable getTable(int numFigure) {
      logger.debug("getting tables that match " + numFigure);
      for (int tIndex = 0; tIndex < tables.length; tIndex++) {
         if (numFigure == tables[tIndex].getNumFigure()) {
            return tables[tIndex];
         }
      }
      logger.warn("no matches found");
      return null;
   }
   
   protected EdgeField getField(int numFigure) {
      logger.debug("getting fields that match " + numFigure);
      for (int fIndex = 0; fIndex < fields.length; fIndex++) {
         if (numFigure == fields[fIndex].getNumFigure()) {
            return fields[fIndex];
         }
      }
      logger.warn("no matches found");
      return null;
   }

   public abstract String getDatabaseName();

   public abstract String getProductName();
   
   public abstract String getSQLString();
   
   public abstract void createDDL();
   
}//EdgeConvertCreateDDL

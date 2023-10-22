import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeConnector {
   private static Logger logger = LogManager.getLogger(EdgeConnector.class.getName());

   private int numConnector, endPoint1, endPoint2;
   private String endStyle1, endStyle2;
   private boolean isEP1Field, isEP2Field, isEP1Table, isEP2Table;
      
   public EdgeConnector(String inputString) {
      logger.info("Creating new EdgeConnector instance");
      StringTokenizer st = new StringTokenizer(inputString, EdgeConvertFileParser.DELIM);
      numConnector = Integer.parseInt(st.nextToken());
      endPoint1 = Integer.parseInt(st.nextToken());
      endPoint2 = Integer.parseInt(st.nextToken());
      endStyle1 = st.nextToken();
      endStyle2 = st.nextToken();
      isEP1Field = false;
      isEP2Field = false;
      isEP1Table = false;
      isEP2Table = false;
   }
   
   public int getNumConnector() {
      logger.info("Get numConnector");
      return numConnector;
   }
   
   public int getEndPoint1() {
      logger.info("Get endPoint1");
      return endPoint1;
   }
   
   public int getEndPoint2() {
      logger.info("Get endPoint2");
      return endPoint2;
   }
   
   public String getEndStyle1() {
      logger.info("Get endStyle1");
      return endStyle1;
   }
   
   public String getEndStyle2() {
      logger.info("Get endStyle2");
      return endStyle2;
   }
   public boolean getIsEP1Field() {
      logger.info("Get isEP1Field");
      return isEP1Field;
   }
   
   public boolean getIsEP2Field() {
      logger.info("Get isEP2Field");
      return isEP2Field;
   }

   public boolean getIsEP1Table() {
      logger.info("Get isEP1Table");
      return isEP1Table;
   }

   public boolean getIsEP2Table() {
      logger.info("Get isEP2Table");
      return isEP2Table;
   }

   public void setIsEP1Field(boolean value) {
      logger.info("Set isEP1Field to {}", value);
      isEP1Field = value;
   }
   
   public void setIsEP2Field(boolean value) {
      logger.info("Set isEP2Field to {}", value);
      isEP2Field = value;
   }

   public void setIsEP1Table(boolean value) {
      logger.info("Set isEP1Table to {}", value);
      isEP1Table = value;
   }

   public void setIsEP2Table(boolean value) {
      logger.info("Set isEP2Table to {}", value);
      isEP2Table = value;
   }
}

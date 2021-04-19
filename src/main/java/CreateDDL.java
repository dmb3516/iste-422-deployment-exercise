import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CreateDDL extends EdgeConvertCreateDDL {
	public static Logger logger = LogManager.getLogger(CreateDDLMySQL.class.getName());

	protected String databaseName;
	protected String[] strDataType = {"VARCHAR", "BOOL", "INT", "DOUBLE"};
  protected StringBuffer sb;

	public CreateDDL(String databaseName){
			//super(inputTables, inputFields);
      sb = new StringBuffer();
			this.databaseName = databaseName;
	}

	public CreateDDL(){

	}

	public void createDDL(){

		//CALL initSB
		initSB();

		for (int boundCount = 0; boundCount <= maxBound; boundCount++) { //process tables in order from least dependent (least number of bound tables) to most dependent
			logger.debug("Current Table bound count: "+boundCount);
			for (int tableCount = 0; tableCount < numBoundTables.length; tableCount++) { //step through list of tables
				if (numBoundTables[tableCount] == boundCount) { //
					logger.info("Generating MySQL Script");
					logger.debug("Current Table is "+ tables[tableCount].getName());
					sb.append("CREATE TABLE " + tables[tableCount].getName() + " (\r\n");
					int[] nativeFields = tables[tableCount].getNativeFieldsArray();
					int[] relatedFields = tables[tableCount].getRelatedFieldsArray();
					boolean[] primaryKey = new boolean[nativeFields.length];
					int numPrimaryKey = 0;
					int numForeignKey = 0;
					for (int nativeFieldCount = 0; nativeFieldCount < nativeFields.length; nativeFieldCount++) { //print out the fields
					
						EdgeField currentField = getField(nativeFields[nativeFieldCount]);
						sb.append("\t" + currentField.getName() + " " + strDataType[currentField.getDataType()]);
						logger.debug("Current Field Name: "+ currentField.getName() +" Data Type: "+currentField.getDataType());
						//CALL setFieldAttributes Method
						setFieldAttributes(currentField, numPrimaryKey, numForeignKey, primaryKey, nativeFieldCount);
					}

					//CALL setKeyConstraints
					setKeyConstraints(nativeFields, nativeFields, numPrimaryKey, numForeignKey, primaryKey, tableCount);
					
					sb.append(");\r\n\r\n"); //end of table
				}
			}
		}
	}//End of CreateDDLforMySQL

	public void initSB(){
		logger.debug("Database name '"+databaseName+"'generated");

		sb.append("CREATE DATABASE " + databaseName + ";\r\n");
		sb.append("USE " + databaseName + ";\r\n");

	}

	public void setFieldAttributes(EdgeField currentField, int numPrimaryKey, int numForeignKey, boolean[] primaryKey, int nativeFieldCount){
		if (currentField.getDataType() == 0) { //varchar
			logger.debug("Datatype is varchar of length "+ currentField.getVarcharValue());
			sb.append("(" + currentField.getVarcharValue() + ")"); //append varchar length in () if data type is varchar
		}
		if (currentField.getDisallowNull()) {
			logger.debug("Current field "+ currentField.getName() +" is NOT NULL");
			sb.append(" NOT NULL");
		}
		if (!currentField.getDefaultValue().equals("")) {
			logger.debug("Datatype of current field "+ currentField.getName() +" is boolean.");
			if (currentField.getDataType() == 1) { //boolean data type
				sb.append(" DEFAULT " + convertStrBooleanToInt(currentField.getDefaultValue()));
			} else { //any other data type
				sb.append(" DEFAULT " + currentField.getDefaultValue());
			}
		}
		if (currentField.getIsPrimaryKey()) {
			logger.debug("Current field "+ currentField.getName() +" is a primary key");
			primaryKey[nativeFieldCount] = true;
			numPrimaryKey++;
		} else {
			logger.debug("Current field "+ currentField.getName() +" is NOT primary key");
			primaryKey[nativeFieldCount] = false;
		}
		if (currentField.getFieldBound() != 0) {
			numForeignKey++;
		}
		sb.append(",\r\n"); //end of field
		
	}//End of setFieldAttributes

	public void setKeyConstraints(int[] nativeFields, int[] relatedFields, int numPrimaryKey, int numForeignKey, boolean[] primaryKey, int tableCount){
		if (numPrimaryKey > 0) { //table has primary key(s)
			sb.append("CONSTRAINT " + tables[tableCount].getName() + "_PK PRIMARY KEY (");
			for (int i = 0; i < primaryKey.length; i++) {
					if (primaryKey[i]) {
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
			logger.debug("Current Table has "+ numForeignKey +" foreign keys");
			int currentFK = 1;
			for (int i = 0; i < relatedFields.length; i++) {
					if (relatedFields[i] != 0) {
						sb.append("CONSTRAINT " + tables[tableCount].getName() + "_FK" + currentFK +
											" FOREIGN KEY(" + getField(nativeFields[i]).getName() + ") REFERENCES " +
											getTable(getField(nativeFields[i]).getTableBound()).getName() + "(" + getField(relatedFields[i]).getName() + ")");
						if (currentFK < numForeignKey) {
								sb.append(",\r\n");
						}
						currentFK++;
					}
			}
			sb.append("\r\n");
		}
	}



	protected int convertStrBooleanToInt(String input) { //MySQL uses '1' and '0' for boolean types
      if (input.equals("true")) {
				logger.debug("Boolean value is TRUE so it is being set to 1");
         return 1;
      } else {
				logger.debug("Boolean value is FALSE so it is being set to 0");
         return 0;
      }
   }

	public String getSQLString(){
		
		return sb.toString();
		};
	
	public String getDatabaseName(){return "";};

  public String getProductName(){return "";};


}//end of class
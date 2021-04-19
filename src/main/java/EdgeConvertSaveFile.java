import java.io.*;
import java.util.*;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EdgeConvertSaveFile extends EdgeConvertFileParser {
	private FileReader fr;
  private BufferedReader br;

	public static Logger logger = LogManager.getLogger(EdgeConvertSaveFile.class.getName());

	public EdgeConvertSaveFile(File file) {
		super(file);
	}
  
	@Override
	public void parseFile(File inputFile) { //this method is unclear and confusing in places
    StringTokenizer stTables, stNatFields, stRelFields, stNatRelFields, stField;
    EdgeTable tempTable;
    EdgeField tempField;
    //BufferedReader br;
    String currentLine = "";
    int numFigure;
    final String DELIM = "|";

		try {
      fr = new FileReader(inputFile);
      br = new BufferedReader(fr);

			currentLine = br.readLine(); //this should be "Table: "

    	while (currentLine.startsWith("Table: ")) {
				//ArrayList alTables;
				String tableName;
				int numFields, numTables;
    	  numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Table number
    	  currentLine = br.readLine(); //this should be "{"
    	  currentLine = br.readLine(); //this should be "TableName"
    	  tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
    	  tempTable = new EdgeTable(numFigure + DELIM + tableName);
         
      	currentLine = br.readLine(); //this should be the NativeFields list
      	stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
      	numFields = stNatFields.countTokens();

      	for (int i = 0; i < numFields; i++) {
      		tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
      	}
         
      	currentLine = br.readLine(); //this should be the RelatedTables list
      	stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
      	numTables = stTables.countTokens();

      	for (int i = 0; i < numTables; i++) {
      	  tempTable.addRelatedTable(Integer.parseInt(stTables.nextToken()));
      	}

      	tempTable.makeArrays();
         
      	currentLine = br.readLine(); //this should be the RelatedFields list
      	stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
      	numFields = stRelFields.countTokens();

      	for (int i = 0; i < numFields; i++) {
      	  tempTable.setRelatedField(i, Integer.parseInt(stRelFields.nextToken()));
      	}

      	super.alTables.add(tempTable);
      	currentLine = br.readLine(); //this should be "}"
      	currentLine = br.readLine(); //this should be "\n"
      	currentLine = br.readLine(); //this should be either the next "Table: ", #Fields#
    	}

    	while ((currentLine = br.readLine()) != null) {
				//ArrayList alFields;
				String fieldName;
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

      	if (stField.hasMoreTokens()) { //Default Value may not be defined
      	  tempField.setDefaultValue(stField.nextToken());
      	}

      	super.alFields.add(tempField);
    	}

    	logger.debug("Save file has been parsed");
		}
		catch (FileNotFoundException fnfe) {
      System.out.println("Cannot find \"" + inputFile.getName() + "\".");
			logger.error(inputFile.getName() + " is not found.");
      System.exit(0);
    } // catch FileNotFoundException
  	catch (IOException ioe) {
      System.out.println(ioe);
	 		logger.error("IOException: " + ioe);
	 		System.exit(0);
    } // catch IOException
  } // parseFile

	@Override
	public void openFile(File inputFile) {
    try {
      fr = new FileReader(inputFile);
      br = new BufferedReader(fr);

      br.readLine().trim();
      //numLine++;

      this.parseFile(inputFile); //parse the file
      br.close();
      this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
      logger.info("The file has been successfully opened");
    } // try
    catch (FileNotFoundException fnfe) {
      System.out.println("Cannot find \"" + inputFile.getName() + "\".");
			logger.error(inputFile.getName() + " is not found.");
      System.exit(0);
    } // catch FileNotFoundException
  	catch (IOException ioe) {
      System.out.println(ioe);
	 		logger.error("IOException: " + ioe);
	 		System.exit(0);
    } // catch IOException
  } // openFile()
}//EdgeConvertSaveFile
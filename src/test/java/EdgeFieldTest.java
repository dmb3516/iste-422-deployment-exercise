import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EdgeFieldTest {
	EdgeField testObj2;

	@Before
	public void setUp() throws Exception {
		testObj2 = new EdgeField("1|2|3|4|5|6|7|Name|DefaultValue");
	}

	@Test
	public void testGetNumFigure() {
		// Example of how a value can be passed into a test
		String opt1Str = System.getProperty("optionone");
		final long opt1;
		if (opt1Str == null) {
			opt1 = 1;
		}
		else {
			opt1 = Long.parseLong(opt1Str);
		}
		assertEquals("numConnector was intialized to 1 so it should be 1",(long)opt1,testObj2.getNumFigure());
	}
	@Test
	public void testGetName() {
		assertEquals("Name was intialized to \"Name\"","Name",testObj2.getName());
	}

	@Test
	public void testGetTableID() {
		assertEquals("TableID was intialized to 2",2,testObj2.getTableID());
	}

	@Test
	public void testGetTableBound() {
		assertEquals("TableBound was intialized as 3",3,testObj2.getTableBound());
	}

	@Test
	public void testGetFieldBound() {
		assertEquals("FieldBound was intialized as 4",4,testObj2.getFieldBound());
	}

	@Test
	public void testGetDataType() {
		assertEquals("DataType was intialized as 5",5,testObj2.getFieldBound());
	}

	@Test
	public void testsetVarcharValue() {
		assertEquals("VarcharValue was intialized as 6",6,testObj2.setVarcharValue(value));
	}

	@Test
	public void testsetDataType() {
		assertEquals("DataType was intialized as 7",7,testObj2.setDataType(value));
	}

	@Test
	public void testGetDisallowNull() {
		assertEquals("GetDisallowNull should be false",false,testObj2.getDisallowNull());
	}
	


	@Test
	public void testGetIsPrimaryKey() {
		assertEquals("getIsPrimaryKey should be false",false,testObj2.getIsPrimaryKey());
	}

	@Test
	public void testSetDisallowNull() {
		testObj2.setDisallowNull(false);
		assertEquals("setDisallowNull should be what you set it to",false,testObj2.getDisallowNull());
	}

	@Test
	public void testSetIsPrimaryKey() {
		testObj2.setIsPrimaryKey(false);
		assertEquals("setIsPrimaryKey should be what you set it to",false,testObj2.getIsPrimaryKey());
	}

	@Test
	public void testGetDefaultValue() {
		assertEquals("DefaultValue was intialized to \"DefaultValue\"","DefaultValue",testObj2.getDefaultValue());
	}

	@Test
	public void testSetDefaultValue() {
		testObj2.setDefaultValue(" ");
		assertEquals("DefaultValue was intialized to \"DefaultValue\"","DefaultValue",testObj2.getDefaultValue());
	}
}

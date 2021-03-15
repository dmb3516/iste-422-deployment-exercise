import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EdgeConvertCreateDDLTest {
	EdgeConvertCreateDDL testObjEmpty;
	EdgeConvertCreateDDL testObj1;

	EdgeTable[] tables;
	EdgeField[] fields;

	public class EdgeConvertCreateDDLTest2 extends EdgeConvertCreateDDL {
		// implementations are required for the below methods because they are abstract methods in EdgeConvertCreateDDL
		@Override
		public String getDatabaseName() {
			return null;
		}

		public String getProductName() {
			return null;
		}

		public String getSQLString() {
			return null;
		}

 		public void createDDL() {}
	}

	@Before
	public void setUp() throws Exception {
		testObjEmpty = new EdgeConvertCreateDDLTest2();

		EdgeTable table1 = new EdgeTable("1|TableOne");
		EdgeTable table2 = new EdgeTable("2|TableTwo");
		table1.addNativeField(1);
		table1.addNativeField(3);
		table1.addNativeField(5);

		table2.addNativeField(2);
		table2.addNativeField(4);

		table1.makeArrays();
		table2.makeArrays();
		table1.setRelatedField(0, 1);
		table1.setRelatedField(1, 3);
		table1.setRelatedField(2, 5);

		table2.setRelatedField(0, 2);
		table2.setRelatedField(1, 4);

		tables = new EdgeTable[2];
		tables[0] = table1;
		tables[1] = table2;

		//EdgeField field1 = new EdgeField("1|FieldOne");
		//EdgeField field2 = new EdgeField("2|FieldTwo");
		fields = new EdgeField[5];
		fields[0] = new EdgeField("1|FieldOne");
		fields[1] = new EdgeField("2|FieldTwo");
		fields[2] = new EdgeField("3|FieldThree");
		fields[3] = new EdgeField("4|FieldFour");
		fields[4] = new EdgeField("5|FieldFour");

		//tables[0] = table1;
		//tables[1] = table2;

		//fields[0] = field1;
		//fields[1] = field2;
			
		testObj1 = new EdgeConvertCreateDDL(tables, fields) {
			// implementations are required for the below methods because they are abstract methods in EdgeConvertCreateDDL
			@Override
			public String getDatabaseName() {
				return null;
			}

			@Override
			public String getProductName() {
				return null;
			}

			@Override
			public String getSQLString() {
				return null;
			}

			@Override
 			public void createDDL() {}
		};
	}

	/*@Test
	public void testEmptyConstructorGetTable() {
		assertEquals("Empty constructor should not set tables attribute, so it should be null.", null, testObjEmpty.getTable(0));
	}

	@Test
	public void testEmptyConstructorGetField() {
		assertEquals("Empty constructor should not set fields attribute, so it should be null.", null, testObjEmpty.getField(0));
	}*/

	@Test
	public void testGetTableAfterInit() {
		assertEquals("The tables array was entered into the constructor as the required tables array, so getTable(0) should be the same as tables[0].", tables[0], testObj1.getTable(0));
	}

	@Test
	public void testGetFieldAfterInit() {
		assertEquals("The fields array was entered into the constructor as the required fields array, so getField(0) should be the same as fields[0].", fields[0], testObj1.getField(0));
	}

	@Test
	public void testInvalidGetTableAfterInit() {
		assertEquals("There is no table at index 9999 in the tables array, so null should be returned.", null, testObj1.getTable(9999));
	}

	@Test
	public void testInvalidGetFieldAfterInit() {
		assertEquals("There is no field at index 9999 in the fields array, so null should be returned.", null, testObj1.getField(9999));
	}

	@Test
	public void testForNumBoundTablesCorrectLength() {
		assertEquals("numBoundTables should contain the numBound values for every table. Therefore, it should be the same length as the tables array.", testObj1.tables.length, testObj1.numBoundTables.length);
	}

	@Test
	public void testForCorrectMaxBound() {
		assertEquals("Since table1 has the most related fields (3), maxBound should be equal to 3.", 3, testObj1.maxBound);
	}

	@Test
	public void testForNumBoundTablesContainingAllTables() {
		assertEquals("The numBoundTables array is supposed to contain a slot for each table. Therefore, its length should be 2.", 2, testObj1.numBoundTables.length);
	}

	@Test
	public void testForCorrectNumBoundValues() {
		assertEquals("Since table1 has 3 related fields, numBoundTables[0] should also be 3.", 3, testObj1.numBoundTables[0]);
		assertEquals("Since table2 has 2 related fields, numBoundTables[1] should also be 2.", 2, testObj1.numBoundTables[1]);
	}
}
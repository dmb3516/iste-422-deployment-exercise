import org.junit.*;
import static org.junit.Assert.assertEquals;

public class CreateDDLMySQLTest {
    CreateDDLMySQL testObj;

    @Before
    public void setUp() throws Exception{
        testObj = new CreateDDLMySQL();
        //Need to import .edg to createDDLMySQL object in order to test getting the 
        //testGetSQLString method to output properly. I do not know how
        //to do this within my test class, so that test case will fail.
    }


    @Test
    public void testgetDatabaseName() {
        testObj.generateDatabaseName();
        String dbName = testObj.getDatabaseName();
        assertEquals("Should be the default 'MySQLDB'","MySQLDB", dbName);
    }

    @Test
    public void testconvertStrBooleantoIntTrue(){
        int boolInt = testObj.convertStrBooleanToInt("true");
        assertEquals("Should equal 1 from True String Boolean", 1, boolInt);
    }
    @Test
    public void testconvertStrBooleantoIntFalse(){
        int boolInt = testObj.convertStrBooleanToInt("false");
        assertEquals("Should equal 0 from false String Boolean", 0, boolInt);
    }
    @Test
    public void testconvertStrBooleantoIntGarbage(){
        int boolInt = testObj.convertStrBooleanToInt("neither");
        assertEquals("Should equal 0 from non Boolean String", 0, boolInt);
    }

    @Test
    public void testgenerateDatabaseName(){
        String dbName = testObj.generateDatabaseName();
        assertEquals("Should be the default 'MySQLDB'","MySQLDB", dbName);
    }

    @Test
    public void testgetProductName(){
        assertEquals("Should be the string 'MySQL'","MySQL",testObj.getProductName());
    }

    @Test
    public void testgetSQLString(){
        
        assertEquals("Should return a formatted string output from the created object 'createDDL'", null, testObj.getSQLString());
    }

    
}

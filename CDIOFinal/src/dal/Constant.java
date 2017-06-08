package dal;

public class Constant {

	public static final String

	server					= "localhost",  		// 	database-serveren. Her enten localhost eller IP på den fremmede server.
	database				= "cdiofinal",   		//	Navnet på selve databasen.
	username				= "root", 				//	Username til at logge på databasen med .
	password				= "", 					// 	password til at logge på databasen med.
	testserver				= "localhost",			//	Addresse på testdatabase
	testdatabase			= "testcdiofinal",		//	Navnet på test databasen.
	testusername 			= "root",				//  Username brugt i testdatabasen
	testpassword			= "";					//	Password brugt i testdatabasen
	
	public static final int
		port				= 3306,					//	Porten der bruges til at tilgå databasen
		testport 			= 3306;					//	Porten der bruges til at tilgå testdatabasen
	
	

}

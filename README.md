## CSCI3170 Group Project

### Information ###
**Group Number:   39**

Group Members:  
- Bao Wenrui,      1155157220
- Yao Shihurong,  1155157155
- Zhang Juyuan,  1155160257

List of files:  
- `CSCI3170Proj.java`: The main program to communicate with MySQL server
- `mysql-jdbc.jar`: MySQL connector

              
### Compilation and Deployment ###
Methods of compilation and execution:

Before compilation:

- Modify `CSCI3170Proj.java` to work on other MySQL server environment
- In Line 9-11:
```
String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db39?autoReconnect=true&useSSL=false";
String dbUsername = "Group39";
String dbPassword = "CSCI3170";
```
- Change the above parameters to other environment

For compilation:
- The `mysql-jdbc.jar` file should be placed in the same directory

For execution:
- `java -classpath ./mysql-jdbc.jar:./ CSCI3170`

### Operations ###

1. Operations for administrator menu
	- [x] 1.1 Create all tables
	- [x] 1.2 Delete all tables
	- [x] 1.3 Load from datafile
	- [x] 1.4 Show the content of a specified table
	- [x] 1.5 Return to the main menu

2. Operations for salesperson menu
	- [x] 2.1 Search for Parts
	- [x] 2.2 Perform Transaction
	- [x] 2.3 Return to the main menu

3. Operations for Manager menu
	- [x] 3.1 List all salespersons in ascending or descending order of years of experience
	- [x] 3.2 Count the number of transaction records of each salesperson within a given range on years of experience
	- [x] 3.3 Sort and list the manufacturers in descending order of total sales value
	- [x] 3.4 Show the N most popular parts
	- [x] 3.5 Return to the main menu

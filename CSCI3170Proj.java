import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

class CSCI3170 {
    public static void main(String[] args) {
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db39?autoReconnect=true&useSSL=false";
        String dbUsername = "Group39";
        String dbPassword = "CSCI3170";

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        } catch (ClassNotFoundException e) {
            System.out.println("[Error]: Java MySQL DB Driver not found!!");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println("Welcome to sales system!")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n-----Main menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");
            System.out.print("Enter Your Choice: ");
            int choice = -1;

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("INVALID INPUT.");
                continue;
            }

            if(choice == 4) {
                break;
            }
            else if (choice == 1) {
                while(true) {
                    System.out.println();
                    System.out.println("-----Operations for administrator menu-----");
                    System.out.println("What kinds of operation would you like to perform?");
                    System.out.println("1. Create all tables");
                    System.out.println("2. Delete all tables");
                    System.out.println("3. Load from datafile");
                    System.out.println("4. Show content of a table");
                    System.out.println("5. Return to the main menu");
                    System.out.print("Enter Your Choice: ");
                    int choice1 = -1;

                    try {
                        choice1 = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("INVALID INPUT");
                        continue;
                    }

                    if(choice1 == 5) {
                        break;
                    } else if(choice1 == 1) {
                        System.out.print("Processing...");
                        try {
                            create_tables(con);
                        } catch (Exception e) {
                            System.out.println("Fail to create tables.");
                            continue;
                        }
                        System.out.println("Done. Database is initialized!");
                    } else if(choice1 == 2) {
                        System.out.print("Processing...");
                        try {
                            delet_tables(con);
                        } catch (Exception e) {
                            System.out.println("Fail to delete tables.");
                            continue;
                        }
                        System.out.println("Done. Database is removed!");
                    } else if(choice1 == 3) {
                        System.out.print("Type in the Source Data Folder Path: ");
                        scanner.nextLine();
                        String path = scanner.nextLine();
                        System.out.print("Processing...");
                        try {
                            load_data(con, path);
                        } catch (Exception e) {
                            System.out.println("Error: fail to load data.");
                            continue;
                        }
                        System.out.println("Done. Data is inputted to the database.");
                    } else if(choice1 == 4) {
                        System.out.print("Which table would you like to show: ");
                        scanner.nextLine();
                        String name_table = scanner.nextLine();
                        try {
                            show_table(con, name_table);
                        } catch (Exception e) {
                            System.out.println("Fail to show table.");
                            continue;
                        }
                    } else {
                        System.out.println("INVALID INPUT.");
                    }
                }
            } else if (choice == 2) {



            } else if (choice == 3) {


            } else {
                System.out.println("INVALID INPUT.");
            }
        }
        scanner.close();
    }
    
    public static void class create_tables(Connection con) throws Exception{
        Statement stmt = null;
        stmt = con.createStatement();

        String sql = "create table category (" +
                    "cID integer not null," +
                    "cName varchar(20) not null," +
                    "primary key (cID)," +
                    "check (cID > 0 and cID < 10)" +
                    ")";

        stmt.executeUpdate(sql);

        sql = "create table manufacturer (" +
            "mID integer not null," +
            "mName varchar(20) not null," +
            "mAddress varchar(50) not null," +
            "mPhoneNumber integer not null," +
            "primary key (mID)," +
            "check (mID > 0 and mID < 100 and mPhoneNumber > 9999999 and mPhoneNumber < 100000000)" +
            ")";
        
        stmt.executeUpdate(sql);

        sql = "create table part (" +
            "pID integer not null," +
            "pName varchar(20) not null," +
            "pPrice integer not null," +
            "mID integer not null," +
            "cID integer not null," +
            "pWarrantyPeriod integer not null," +
            "pAvailableQuantity integer not null," +
            "primary key (pID)," +
            "foreign key (cID) references category(cID)," +
            "foreign key (mID) references manufacturer(mID)," +
            "check (pID > 0 and pID < 1000 and pPrice > 0 and pPrice < 100000 and mID > 0 and mID < 100 and cID > 0 and cID < 10 and pWarrantyPeriod > 0 and pWarrantyPeriod < 100 and pAvailableQuantity >= 0 and pAvailableQuantity < 100)" +
            ")";
        stmt.executeUpdate(sql);

        sql = "create table salesperson (" +
            "sID integer not null," +
            "sName varchar(20) not null," +
            "sAddress varchar(50) not null," +
            "sPhoneNumber integer not null," +
            "sExperience integer not null," +
            "primary key (sID)," +
            "check (sID > 0 and sID < 100 and sPhoneNumber > 9999999 and sPhoneNumber < 100000000 and sExperience > 0 and sExperience < 10)" +
            ")";
        stmt.executeUpdate(sql);

        sql = "create table transaction (" +
            "tID integer not null," +
            "pID integer not null," +
            "sID integer not null," +
            "tDate data not null," +
            "primary key (tID)," +
            "foreign key (pID) references part(pID)," +
            "foreign key (sID) references salesperson(sID)," +
            "check (tID > 0 and tID < 10000 and pID > 0 and pID < 1000 and sID > 0 and sID < 100)" +
            ")";
        stmt.executeUpdate(sql);
    }

    public static void class delet_tables(Connection con) throws Exception {
        Statement stmt = null;
        stmt = con.createStatement();
        stmt.executeUpdate("drop table if exists category, manufacturer, part, salesperson, transaction");
    }

    public static void class load_data(Connection con, String path) throws Exception {
        Scanner file = null;
        Statement stmt = null;
        
        file = new Scanner(new File(path + "/category.txt"));
        while(file.hasNextLine()) {
            String line = file.nextLine();
            String[] attributes = line.split("\t");
            String temp = attributes[0] + ", '" + attributes[1] + "'";
            stmt = con.createStatement();
            stmt.executeUpdate("insert into category values (" + temp + ")");
        }

        file = new Scanner(new File(path + "/manufacturer.txt"));
        while(file.hasNextLine()) {
            String line = file.nextLine();
            String[] attributes = line.split("\t");
            String temp = attributes[0] + ", '" + attributes[1] + "', '" + attributes[2] + "', " + attributes[3];
            stmt = con.createStatement();
            stmt.executeUpdate("insert into manufacturer values (" + temp + ")");
        }

        file = new Scanner(new File(path + "/part.txt"));
        while(file.hasNextLine()) {
            String line = file.nextLine();
            String[] attributes = line.split("\t");
            String temp = attributes[0] + ", '" + attributes[1] + "', " + attributes[2] + ", " + attributes[3] + ", " + attributes[4] + ", " + attributes[5] + ", " + attributes[6];
            stmt = con.createStatement();
            stmt.executeUpdate("insert into part values (" + temp + ")");
        }

        file = new Scanner(new File(path + "/salespercon.txt"));
        while(file.hasNextLine()) {
            String line = file.nextLine();
            String[] attributes = line.split("\t");
            String temp = attributes[0] + ", '" + attributes[1] + "', '" + attributes[2] + "', " + attributes[3] + ", " + attributes[4];
            stmt = con.createStatement();
            stmt.executeUpdate("insert into salesperson values (" + temp + ")");
        }

        file = new Scanner(new File(path + "/transaction.txt"));
        while(file.hasNextLine()) {
            String line = file.nextLine();
            String[] attributes = line.split("\t");
            String temp = attributes[0] + ", " + attributes[1] + ", " + attributes[2] + ", '" + attributes[3] + "'";
            stmt = con.createStatement();
            stmt.executeUpdate("insert into part values (" + temp + ")");
        }
    }

    public static void show_table(Connection con, String name_table) throws Exception {
        Statement stmt = con.createStatement();
        String query = "SELECT * FROM " + name_table;
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int column_count = rsmd.getColumnCount();
        System.out.println("Content of table " + name_table + " :");

        if(name_table.equals("category")) {
            System.out.println("| cID | cName |");
            while(rs.next()) {
                int cID = rs.getInt("cID");
                String cName = rs.getString("cName");
                System.out.println("| " + cID + " | " + cName + " |");
            }
        } else if(name_table.equals("manufacturer")) {
            System.out.println("| mID | mName | mAddress | mPhoneNumber |");
            while(rs.next()) {
                int mID = rs.getInt("mID");
                String mName = rs.getString("mName");
                String mAddress = rs.getString("mAddress");
                int mPhoneNumber = rs.getInt("mPhoneNumber");
                System.out.println("| " + mID + " | " + mName + " | " + mAddress + " | " + mPhoneNumber + " |");
            }
        } else if(name_table.equals("part")) {
            System.out.println("| pID | pName | pPrice | mID | cID | pWarrantyPeriod | pAvailableQuantity |");
            while(rs.next()) {
                int pID = rs.getInt("pID");
                String pName = rs.getString("pName");
                int pPrice = rs.getInt("pPrice");
                int mID = rs.getInt("mID");
                int cID = rs.getInt("cID");
                int pWarrantyPeriod = rs.getInt("pWarrantyPeriod");
                int pAvailableQuantity = rs.getInt("pAvailableQuantuty");
                System.out.println("| " + pID + " | " + pName + " | " + pPrice + " | " + mID + " | " + cID + " | " + pWarrantyPeriod + " | " + pAvailableQuantity + " |");
            }
        } else if(name_table.equals("salesperson")) {
            System.out.println("| sID | sName | sAddress | sPhoneNumber | sExperience |");
            while(rs.next()) {
                int sID = rs.getInt("sID");
                String sName = rs.getString("sName");
                String sAddress = rs.getString("sAddress");
                int sPhoneNumber = rs.getInt("sPhoneNumber");
                int sExperience = rs.getInt("sExperience");
                System.out.println("| " + sID + " | " + sName + " | " + sAddress + " | " + sPhoneNumber + " | " + sExperience + " |");
            }
        } else if(name_table.equals("transaction")) {
            System.out.println("| tID | pID | sID | tDate |");
            while(rs.next()) {
                int tID = rs.getInt("tID");
                int pID = rs.getInt("pID");
                int sID = rs.getInt("sID");
                java.sql.Date tDate = rs.getDate("tDate");
                System.out.println("| " + tID + " | " + pID + " | " + sID + " | " + tDate.toString() + " |");
            }
        } else {
            System.out.println("INVALID INPUT.");
        }
    }
}

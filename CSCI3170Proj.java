import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.function.IntSupplier;

import javax.management.Query;

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

        System.out.println("Welcome to sales system!");
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
                // choice of input 2
            } else if (choice == 2) {
                while(true){
                    System.out.println();
                    System.out.println("-----Operations for salesperson menu-----");
                    System.out.println("What kinds of operation would you like to perform?");
                    System.out.println("1. Search for parts");
                    System.out.println("2. Sell a part");
                    System.out.println("3. Return to the main menu");
                    System.out.print("Enter Your Choice: ");
                    int choice2 = -1;
                    int choice21 = -1;
                    int choice22 = -1;
                    String str = new String();
                    Scanner sc = new Scanner(System.in);
                    try {
                        choice2 = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("INVALID INPUT");
                        continue;
                    }
                    if(choice2 == 3){
                        break;
                    }else if(choice2 == 1){
                        // the choice of search keywords code
                        System.out.println("Choose the Search criterion:");
                        System.out.println("1. Part Name");
                        System.out.println("2. Manufacturer Name");
                        try {
                            choice21 = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("INVALID INPUT");
                            continue;
                        }
                        System.out.println("Type in the Search Keyword: ");
                        try {
                            str = sc.nextLine();
                        } catch (Exception e) {
                            System.out.println("INVALID INPUT");
                            continue;
                        }

                        System.out.println("Choose ordering: ");
                        System.out.println("1. By price, asending order");
                        System.out.println("2. By price, desending order");

                        try {
                            choice22 = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("INVALID INPUT");
                            continue;
                        }

                        if(choice21 == 1){
                            if(choice22 == 1){
                                try {
                                    SearchKeyword(1, 1, con, str);
                                } catch (Exception e) {
                                    System.out.println("fail to search keywords.");
                                    continue;
                                }
                            }else if(choice22 == 2){
                                try {
                                    SearchKeyword(1, 2, con, str);
                                } catch (Exception e) {
                                    System.out.println("fail to search keywords.");
                                    continue;
                                }
                            }
                        }
                        if(choice21 == 2){
                            if(choice22 == 1){
                                try {
                                    SearchKeyword(2, 1, con, str);
                                } catch (Exception e) {
                                    System.out.println("fail to search kerywords.");
                                    continue;
                                }
                            }else if(choice22 ==2 ){
                                try {
                                    SearchKeyword(2, 2, con, str);
                                } catch (Exception e) {
                                    System.out.println("fail to search keywords.");
                                    continue;
                                }
                            }
                        }
                        System.out.println("End of Query");
                        // the choice of sell parts code
                    } else if (choice2 == 2){
                        System.out.println("Enter The Part ID: ");
                        try {
                            choice21 = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("INVALID INPUT");
                            continue;
                        }
                        System.out.println("Enter The Salesperson ID: ");
                        try {
                            choice22 = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("INVALID INPUT");
                            continue;
                        }
                        try {
                            Perform_Transaction(con, choice21, choice22);
                        } catch (Exception E){
                            System.out.println("Fail to Sell");
                            continue;
                        }
                    }
                }
            } else if (choice == 3) {
                while(true)
                {
                    System.out.println();
                    System.out.println("-----Operations for manager menu-----");
                    System.out.println("What kinds of operation would you like to perform?");
                    System.out.println("1. List all salespersons");
                    System.out.println("2. Count the no. of sales record of each salespersion under a specific range on years of experience");
                    System.out.println("3. Show the total sales value of each manufactuerer");
                    System.out.println("4. Show the N most popular part");
                    System.out.println("5. Return to the main menu");
                    System.out.print("Enter Your Choice: ");
                    
                    int choice3 = -1;
                    int choice31 = -1;
                    Scanner sc = new Scanner(System.in);
         
                    try {
                        choice3 = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("INVALID INPUT");
                        continue;
                    }
                    if(choice3 == 5)
                        break;
                    else if (choice3 == 1)
                    {
                        System.out.println("Choose ordering: ");
                        System.out.println("1. By asending order");
                        System.out.println("2. By desending order");
                        System.out.print("Choose the list ordering:");
                        try {
                            choice31 = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("INVALID INPUT");
                            continue;
                        }
                        try {
                            List_Salespersons(con, choice31);
                        } catch (Exception E){
                            System.out.println("Fail to List all salespersons");
                            continue;
                        }
                    } 
                    else if (choice3 == 2)
                    {
                        int lower, higher;
                        System.out.print("Type in the lower bound for years of experience: ");
                        lower = sc.nextInt();
                        System.out.print("Type in the upper bound for years of experience: ");
                        higher = sc.nextInt();
                        try {
                            Count_the_Transaction(con, lower, higher);
                        } catch (Exception e) {
                            System.out.println("Fail to count transaction.");
                            continue;
                        }
                        System.out.println("End of Query");
                    }
                    else if (choice3 == 3) {
                        try {
                            Sort_and_List(con);
                        } catch (Exception e) {
                            System.out.println("Fail to sort and list.");
                            continue;
                        }
                        System.out.println("End of Query");
                    }
                    else if (choice3 == 4)
                    {
                        int N;
                        System.out.print("Type in the number of parts: ");
                        N = sc.nextInt();
                        try {
                            Show_Popular_Parts(con, N);
                        } catch (Exception e) {
                            System.out.println("Fail to show popular part.");
                            continue;
                        }
                        System.out.println("End of Query");
                    }
                }
            } else {
                System.out.println("INVALID INPUT.");
            }
        }
        scanner.close();
    }

    public static void create_tables(Connection con)throws Exception
    {
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
                    "check (pID > 0 and pID < 1000 and pPrice > 0 and pPrice < 100000 and mID > 0 and mID < 100 and cID > 0 and cID < 10 and pWarrantyPeriod > 0 and pWarrantyPeriod < 100 and pAvailableQuantity >= 0 and pAvailableQuantity < 100)"
                    +
                    ")";
            stmt.executeUpdate(sql);
            sql = "create table salesperson (" +
                    "sID integer not null," +
                    "sName varchar(20) not null," +
                    "sAddress varchar(50) not null," +
                    "sPhoneNumber integer not null," +
                    "sExperience integer not null," +
                    "primary key (sID)," +
                    "check (sID > 0 and sID < 100 and sPhoneNumber > 9999999 and sPhoneNumber < 100000000 and sExperience > 0 and sExperience < 10)"+
                    ")";
            stmt.executeUpdate(sql);
            sql = "create table transaction (" +
                    "tID integer not null," +
                    "pID integer not null," +
                    "sID integer not null," +
                    "tDate date not null," +
                    "primary key (tID)," +
                    "foreign key (pID) references part(pID)," +
                    "foreign key (sID) references salesperson(sID)," +
                    "check (tID > 0 and tID < 10000 and pID > 0 and pID < 1000 and sID > 0 and sID < 100)" +
                    ")";
            stmt.executeUpdate(sql);
        }

    public static void delet_tables(Connection con)throws Exception
        {
                Statement stmt = null;
                stmt = con.createStatement();
                stmt.executeUpdate("drop table if exists transaction, part, category, manufacturer, salesperson");
            }

    public static void load_data(Connection con, String path) throws Exception {
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

        file = new Scanner(new File(path + "/salesperson.txt"));
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
            String temp = attributes[0] + ", " + attributes[1] + ", " + attributes[2] + ", str_to_date('" + attributes[3] + "', '%d/%m/%Y')";
            stmt = con.createStatement();
            stmt.executeUpdate("insert into transaction values (" + temp + ")");
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
                int pAvailableQuantity = rs.getInt("pAvailableQuantity");
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
                System.out.println("| " + tID + " | " + pID + " | " + sID + " | " + tDate + " |");
            }
        } else {
            System.out.println("INVALID INPUT.");
        }
    }

    public static void SearchKeyword(int choicea, int choiceb, Connection con, String keywords) throws Exception {
        System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
        Statement stmt = con.createStatement();

        String query;
        if (choiceb == 1) {
                query = "SELECT p.pID, p.pName, s.mName, c.cName, p.pAvailableQuantity, p.pWarrantyPeriod, p.pPrice " +
                    "FROM part p, manufacturer s, category c " +
                    "WHERE c.cID = p.cID AND p.mID = s.mID " +
                    "ORDER BY p.pPrice ASC";
        } else {
                query = "SELECT p.pID, p.pName, s.mName, c.cName, p.pAvailableQuantity, p.pWarrantyPeriod, p.pPrice " +
                    "FROM part p, manufacturer s, category c " +
                    "WHERE c.cID = p.cID AND p.mID = s.mID " +
                    "ORDER BY p.pPrice DESC";
        }
        ResultSet result = stmt.executeQuery(query);
        while (result.next()) {
            int ID = result.getInt("pID");
            String Name = result.getString("pName");
            String Manufacturer = result.getString("mName");
            String Category = result.getString("cName");
            int Quantity = result.getInt("pAvailableQuantity");
            int Warranty = result.getInt("pWarrantyPeriod");
            int Price = result.getInt("pPrice");
            if (choicea == 1) {
                if (Name.toLowerCase().contains(keywords.toLowerCase())) {
                    System.out.println("| " + ID + " | " + Name + " | " + Manufacturer + " | " + Category + " | " + Quantity + " | "
                                    + Warranty + " | " + Price + " |");
                }
            } else if (choicea == 2) {
                if (Manufacturer.toLowerCase().contains(keywords.toLowerCase())) {
                    System.out.println("| " + ID + " | " + Name + " | " + Manufacturer + " | " + Category + " | " + Quantity + " | "
                                    + Warranty + " | " + Price + " |");
                }
            }
        }
    }

    public static void Perform_Transaction(Connection con, int choicea, int choiceb) throws Exception {
        Statement stmt = con.createStatement();
        String Query = "SELECT pName, pAvailableQuantity " +
        "FROM part " +
        "WHERE pID = " + choicea;
        ResultSet result = stmt.executeQuery(Query);
        if(!result.next()){
            System.out.println("The part is unavailable!");
            return;
        }
        int Quantity = -1;
        String Pname = null;
        do {
            Pname = result.getString("pName");
            Quantity = result.getInt("pAvailableQuantity");
        } while(result.next());
        String time = "SELECT curdate() as curtime";
        result = stmt.executeQuery(time);
        java.sql.Date Time = null;
        while(result.next()){
            Time = result.getDate("curtime");
        }

        String Tid = "SELECT MAX(T.tid) AS mtid " +
        "FROM transaction T";
        int tid = -1;
        result = stmt.executeQuery(Tid);
        while(result.next()) {
            tid = result.getInt("mtid") +1;
        }
        if(Quantity <= 0){
            System.out.printf("Error Sell for Quantity not enough");
            return;
        }
        stmt.executeUpdate("INSERT INTO transaction " +
                        "VALUES(" + tid + ", " + choicea + ", " + choiceb + ", '" + Time + "')");
        Quantity  = Quantity - 1;
        stmt.executeUpdate("UPDATE part SET pAvailableQuantity = " + Quantity + 
            " WHERE pid = " + choicea);
        System.out.printf("Product: " + Pname + "(id: " + choicea + ") Remaing Quantity: " + Quantity);
    }

    public static void List_Salespersons(Connection con, int order) throws SQLException
    {
        System.out.println("| ID | Name | Mobile Phone | Years of Experience |");
        Statement stmt = con.createStatement();
        String query = null;
        if (order == 1) 
            query = "SELECT s.sID, s.sName, s.sPhoneNumber, s.sExperience "
                  + "FROM salesperson s "
                  + "ORDER BY sExperience ASC"; 
        else 
            query = "SELECT s.sID, s.sName, s.sPhoneNumber, s.sExperience "
                  + "FROM salesperson s "
                  + "ORDER BY sExperience DESC";
        ResultSet result = stmt.executeQuery(query);
        while(result.next())
        {
            int ID = result.getInt("sID");
            String Name = result.getString("sName");
            int PhoneNumber = result.getInt("sPhoneNumber");
            int Experience = result.getInt("sExperience");
            System.out.println("| "+ ID +" | "+ Name +" | "+ PhoneNumber +" | "+ Experience + " |");
        }
    }
    
    public static void Count_the_Transaction(Connection con,int low,int high) throws SQLException
    {
        System.out.println("| ID | Name | Years of Experience | Number of Transaction |");
        Statement stmt = con.createStatement();
        String query = null;
        query = "SELECT S.sID, S.sName, S.sExperience, COUNT(t.tID) as num "
              + "FROM salesperson S, transaction t "
              + "WHERE S.sExperience <=" + high + " AND S.sExperience >=" + low + " AND S.sid = t.sid "
              + "GROUP BY S.sid "
              + "ORDER BY S.sid DESC";

        ResultSet result = stmt.executeQuery(query);
        while(result.next())
        {
            int ID = result.getInt("sID");
            String Name = result.getString("sName");
            int Experience = result.getInt("sExperience");
            int Transaction = result.getInt("num");
            System.out.println("| "+ ID +" | "+ Name +" | "+ Experience +" | "+ Transaction + " |");
        }
    }
    
    public static void Sort_and_List(Connection con) throws SQLException
    {
        System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
        String  query;
        query = "SELECT M.mID, M.mName, SUM(P.pPrice) AS sum "
              + "FROM manufacturer M, part P "
              + "WHERE P.mID = M.mID "
              + "GROUP BY M.mID "
              + "ORDER BY sum DESC";
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        while(result.next())
        {
            int ID = result.getInt("mID");
            String Name = result.getString("mName");
            int Value = result.getInt("sum");
            System.out.println("| "+ ID + " | " + Name + " | " + Value + " |");
        }
    }
    
    public static void Show_Popular_Parts(Connection con,int N) throws SQLException
    {
        System.out.println("| Part ID | Part Name | No. of Transaction |");
        Statement stmt = con.createStatement();
        String query = null;
        query = " SELECT P.pID, P.pName, COUNT(T.tID) AS num "
                    + "FROM part P, transaction T "
                    + "WHERE P.pID = T.pID "
                    + "GROUP BY P.pID "
                    + "ORDER BY num DESC "
                    + "LIMIT " + N;
        ResultSet result = stmt.executeQuery(query);
        while(result.next())
        {
            int ID = result.getInt("pID");
            String Name = result.getString("pName");
            int Value = result.getInt("num");
            System.out.println("| "+ ID + " | " + Name + " | " + Value + " |");
        }
    }
}

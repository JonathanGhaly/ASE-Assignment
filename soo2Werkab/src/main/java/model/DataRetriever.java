package model;

import controller.*;

import java.sql.*;
import controller.Driver;
import java.util.ArrayList;
import java.util.Calendar;

public class DataRetriever {
    Connection c = null;
    Statement stmt;
    static DataRetriever dataRetriever;
    int accountId;

    /**
     * Method that make only one object of type dataRetriever is available to use during runtime
     *
     * @return dataRetriever object if found or new one if not found
     */
    public static DataRetriever getInstance() {
        if (dataRetriever == null) {
            dataRetriever = new DataRetriever();
            dataRetriever.Builder();
            dataRetriever.adminAccountRegister(new Account("admin", "admin", "null", "null"));
        }
        return dataRetriever;
    }

    private DataRetriever() {
        this.connect();
    }

    /**
     * Established connection to the database file
     *
     * @return connection
     */
    public Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }

    public void AccountDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Accounts " +
                    "(IDAccount INTEGER PRIMARY KEY     NOT NULL," +
                    " UserName       CHAR(50)    NOT NULL  , " +
                    " Password       CHAR(50)         NOT NULL, " +
                    " Email          CHAR(50)  NULL , " +
                    " mobileNo         CHAR(11) NOT NULL ," +
                    "isSuspended SMALLINT ," +
                    "BirthDate CHAR(10) DEFAULT NULL ," +
                    "createTime DEFAULT CURRENT_TIMESTAMP ," +
                    "UNIQUE(UserName))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void RidesDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Rides " +
                    "(IDRides INTEGER PRIMARY KEY     NOT NULL," +
                    " SourceArea       CHAR(50)    NOT NULL, " +
                    " DestinationArea  CHAR(50)         NOT NULL," +
                    " PassengersNo INTEGER    NOT NULL ," +
                    " Alone SMALLINT , " +
                    " RideStatus          TEXT CHECK( RideStatus IN ('Pending','InRide','Completed') )   NOT NULL DEFAULT 'Pending' )";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void UserAccountsDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS UserAccounts " +
                    "(AccountID INTEGER ," +
                    " UserStatus   TEXT  NOT NULL DEFAULT 'idle'," +
                    "FOREIGN KEY(AccountID)  REFERENCES Accounts(IDAccount))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void driverAccountsDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS DriverAccount " +
                    "(DriverID INTEGER ," +
                    "LicenceNo CHAR(50) NOT NULL ," +
                    "NationalID Char(50) NOT NULL ," +
                    "IsVerified SMALLINT DEFAULT 0," +
                    "IsAccepted SMALLINT DEFAULT 0," +
                    "AvgRating REAL DEFAULT 0," +
                    "NumOfRatings INTEGER DEFAULT 0," +
                    "DriverStatus   TEXT CHECK( DriverStatus IN ('Inactive','InRide','Pending','idle') )   NOT NULL DEFAULT 'idle'," +
                    "Balance INTEGER DEFAULT 0," +
                    "FOREIGN KEY(DriverID)  REFERENCES Accounts(IDAccount)," +
                    "UNIQUE (LicenceNo,NationalID))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void offersDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Offers " +
                    "(DriverID INTEGER ," +
                    "DriverName CHAR(50) NOT NULL ," +
                    "RideID INTEGER NOT NULL ," +
                    "Rating REAL NOT NULL ," +
                    "Price INTEGER NOT NULL," +
                    "FOREIGN KEY(DriverName)  REFERENCES Accounts(UserName)," +
                    "FOREIGN KEY(DriverID)  REFERENCES DriverAccount(DriverID)," +
                    "FOREIGN KEY(RideID)  REFERENCES Requests(RideID)," +
                    "FOREIGN KEY(Rating)  REFERENCES DriverAccount(Rating)," +
                    "UNIQUE(DriverID))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void AreaDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Areas " +
                    "(ID INTEGER PRIMARY KEY NOT NULL," +
                    "AreaName CHAR(50) NOT NULL," +
                    "DISCOUNT INTEGER DEFAULT 0, " +
                    "UNIQUE(AreaName))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void favoriteAreaDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS FavoriteArea " +
                    "(AreaID INTEGER NOT NULL," +
                    "DriverID INTEGER NOT NULL," +
                    "FOREIGN KEY (AreaID) REFERENCES Areas(ID)," +
                    "FOREIGN KEY (DriverID)REFERENCES DriverAccount(DriverID))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void LoggerDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Logger " +
                    "(EventType CHAR(50) NOT NULL ," +
                    "SourceUser CHAR(50)," +
                    "Info CHAR(50) ," +
                    "RideID INTEGER ," +
                    "DateTime Timestamp  DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void RequestDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Requests" +
                    "(RequestID INTEGER PRIMARY KEY NOT NULL ," +
                    "DriverID INTEGER ," +
                    "CustomerID INTEGER ," +
                    "CustomerID2 INTEGER  DEFAULT  -1," +
                    "CustomerID3 INTEGER DEFAULT -1," +
                    "CustomerID4 INTEGER  DEFAULT -1," +
                    "RideID INTEGER UNIQUE ," +
                    "driverOffer DOUBLE NULL," +
                    "isAccepted SMALLINT NULL ," +
                    "createTime DEFAULT CURRENT_TIMESTAMP," +
                    "Alone SMALLINT ," +
                    "FOREIGN KEY(DriverID)  REFERENCES DriverAccount(DriverID)," +
                    "FOREIGN KEY(CustomerID) REFERENCES UserAccounts(AccountID)," +
                    "FOREIGN KEY(RideID) REFERENCES Rides(IDRides)," +
                    "FOREIGN KEY(CustomerID2) REFERENCES UserAccounts(AccountID)," +
                    "FOREIGN KEY(CustomerID3) REFERENCES UserAccounts(AccountID)," +
                    "FOREIGN KEY(CustomerID4) REFERENCES UserAccounts(AccountID))";

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void adminAccountsDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS AdminAccounts " +
                    "(IDAccount INTEGER PRIMARY KEY     NOT NULL," +
                    " UserName       CHAR(50)    NOT NULL  , " +
                    " Password       CHAR(50)         NOT NULL, " +
                    "createTime DEFAULT CURRENT_TIMESTAMP ," +
                    "UNIQUE(UserName))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void RatingDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Ratings " +
                    "(UserID INTEGER NOT NULL ," +
                    "DriverID INTEGER NOT NULL," +
                    "Rating       Integer    NOT NULL  , " +
                    "createTime DEFAULT CURRENT_TIMESTAMP ," +
                    "FOREIGN KEY (UserID) REFERENCES  UserAccounts(UserID) )";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Add controller.Account attributes to database by controller.Account object if not exist
     *
     * @param a controller.Account object that hold its attributes
     */
    private void AccountRegister(Account a) {
        String sql = "INSERT OR IGNORE INTO Accounts (IDAccount,UserName,Password,Email,mobileNo,isSuspended,BirthDate) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(IDAccount) AS MAX FROM Accounts ;");
            accountId = rs.getInt("MAX") + 1;
            pstmt.setInt(1, accountId);
            pstmt.setString(2, a.getUsername());
            pstmt.setString(3, a.getPassword());
            pstmt.setString(4, a.getEmail());
            pstmt.setString(5, a.getMobileNumber());
            pstmt.setInt(6, 0);
            pstmt.setString(7, a.getBirthDate());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    /**
     * Normal account register in addition add the account to UserAccounts as a foreign key
     *
     * @param a controller.Account object that hold its attributes
     */
    public void UserRegister(Account a) {
        AccountRegister(a);
        String sql = "INSERT OR IGNORE INTO UserAccounts (AccountID) VALUES (?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(IDAccount) AS MAX FROM Accounts ;");
            accountId = rs.getInt("MAX");
            pstmt.setInt(1, accountId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    /**
     * Add controller.Account attributes to database by controller.Driver object if not exist in addition add it to DriverAccount
     *
     * @param d controller.Driver object that hold controller.Account and controller.Driver attributes
     */
    public void DriverRegister(controller.Driver d) {
        AccountRegister(d.getAccount());
        String sql = "INSERT OR IGNORE INTO DriverAccount (DriverID,LicenceNo,NationalID) VALUES (?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(IDAccount) AS MAX FROM Accounts ;");
            accountId = rs.getInt("MAX");
            pstmt.setInt(1, accountId);
            pstmt.setString(2, d.getDrivingLicenseNumber());
            pstmt.setString(3, d.getNationalID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    /**
     * controller.Register new admin to controller.Admin Table and Ignore if exists
     *
     * @param account controller.Account object holds UserName and password
     */
    void adminAccountRegister(Account account) {
        String sql = "INSERT OR IGNORE INTO AdminAccounts (IDAccount,UserName,Password) VALUES (?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(IDAccount) AS MAX FROM AdminAccounts ;");
            pstmt.setInt(1, rs.getInt("MAX"));
            pstmt.setString(2, account.getUsername());
            pstmt.setString(3, account.getPassword());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Check if account is in the database and enter a correct credentials
     *
     * @param acc object that hold username and password to check
     * @return true if it is valid
     */
    public Boolean Login(controller.Login acc) {
        if (adminLogin(acc)) {
            acc.setIsAdmin(true);
            //acc.isAdmin = true;
            System.out.println("Logged in as admin");
            return true;
        }
        String sql = "SELECT Password "
                + " FROM Accounts where UserName = ?";
        String sql2 = "SELECT isSuspended " +
                "FROM Accounts where UserName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            pstmt.setString(1, acc.getUser().getAccount().getUsername());
            ResultSet rs = pstmt.executeQuery();
            if (rs.getString("Password").equals(acc.getUser().getAccount().getPassword())) {
                pstmt2.setString(1, acc.getUser().getAccount().getUsername());
                rs = pstmt2.executeQuery();
                if (rs.getInt("isSuspended") == 0) {
                    acc.setIsDriver(this.isDriver(acc));
                   // acc.isDriver = this.isDriver(acc);
                    return true;
                } else {
                    System.out.println("The account is suspended");
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Wrong username or password");
            System.exit(0);
            return false;
        }
    }

    /**
     * Method check if the logged in controller.Account admin credentials is valid
     *
     * @param acc object that hold userName and Password
     * @return true if valid credentials
     */
    public Boolean adminLogin(Login acc) {
        String sql = "SELECT Password "
                + " FROM AdminAccounts where UserName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, acc.getUsername());
            ResultSet rs = pstmt.executeQuery();
            if (rs.getString("Password").equals( acc.getPassword())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Check if the logging in account is a driver
     *
     * @param acc object that hold username and password to check
     * @return true if it is found in DriverAccount table
     */
    Boolean isDriver(Login acc) {
        String sql = "SELECT DriverID " +
                "FROM DriverAccount where DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, getID(acc.getUser().getAccount().getUsername()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return getID(acc.getUser().getAccount().getUsername()) == rs.getInt("DriverID");
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the driver verified by the admin
     *
     * @param driver controller.Driver to check
     * @return true if IsVerified attribute in table equals 1
     */
    public Boolean isVerified(controller.Driver driver) {
        String sql = "SELECT IsVerified "
                + " FROM DriverAccount where DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            int id = this.getID(driver.getAccount().getUsername());
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.getBoolean("IsVerified");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method that create admin object from database
     *
     * @param username UserName of the admin
     * @return controller.Admin object if exists in database, else Null
     */
    public Admin getAdmin(String username) {
        String sql = "SELECT IDAccount,UserName,Password "
                + " FROM AdminAccounts where UserName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            Account account = new Account(rs.getString("UserName"), rs.getString("Password"), "", "");
            conn.close();
            return new Admin(account);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Generate a driver object from the database
     *
     * @param username Name of driver to be generated
     * @return controller.Driver object
     */
    public controller.Driver getDriver(String username) {
        String sql = "SELECT IDAccount,UserName,Password,Email,mobileNo "
                + " FROM Accounts where UserName = ?";
        String sql2 = "SELECT LicenceNo,NationalID, isVerified, isAccepted, Balance " +
                "FROM DriverAccount where DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            pstmt2.setInt(1, rs.getInt("IDAccount"));
            ResultSet rs2 = pstmt2.executeQuery();
            Account account = new Account(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), rs.getString("mobileNo"));
            controller.Driver driver = new controller.Driver(account, rs2.getString("NationalID"), rs2.getString("LicenceNo"), rs2.getBoolean("isVerified"), rs2.getBoolean("isAccepted"), rs2.getInt("Balance"));
            pstmt.close();
            pstmt2.close();
            c.close();
            return driver;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Generate a controller.User object from the database
     *
     * @param username Name of controller.User to be generated
     * @return controller.User object
     */
    public User getUser(String username) {
        String sql = "SELECT IDAccount,UserName,Password,Email,mobileNo,BirthDate "
                + " FROM Accounts where UserName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
//            Date birth = rs.getTimestamp("BirthDate");
            Account account = new Account(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), rs.getString("mobileNo"));
            User ret = new User(account);
            pstmt.close();
            c.close();
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Method get controller.Account id by username
     *
     * @param username username to get ID from
     * @return controller.Account ID
     */
    public int getID(String username) {
        int id = - 1;
        String sql = "SELECT IDAccount FROM Accounts Where UserName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            //stmt = conn.createStatement();
            ResultSet rs = pstmt.executeQuery();
            id = rs.getInt("IDAccount");
            stmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return id;
    }

    /**
     * Get the ID of the desired controller.Area
     *
     * @param area controller.Area Object that hold controller.Area name
     * @return AreaID (Int)
     */
    public int getAreaId(Area area) {
        this.addArea(area);
        String sql = "SELECT ID FROM Areas where AreaName = ?" + ";";
        int id = - 1;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, area.toString());
            ResultSet rs = pstmt.executeQuery();
            id = rs.getInt("ID");
            pstmt.close();
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return id;
    }

    /**
     * Get driver rating
     *
     * @param driverID ID of the driver to get his rating
     * @return rating (double)
     */
    public ArrayList<Integer> getRating(int driverID) {
        String sql = "select Rating from Ratings where DriverID = ?;";
        ArrayList<Integer> rate = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, driverID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rate.add(rs.getInt("Rating"));
            }
            pstmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return rate;
    }

    public ArrayList<controller.Event> getEventsOfRide(int rideID) {
        ArrayList<controller.Event> events = new ArrayList<>();
        String sql = "Select EventType,SourceUser,Info,RideID,DateTime FROM Logger " +
                "Where RideID = " + rideID + ";";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                events.add(new controller.Event(rs.getString("controller.EventType")
                        , rs.getString("SourceUser")
                        , rs.getString("Info")
                        , rs.getInt("RideID")
                        , rs.getString("DateTime")));
            }
            pstmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return events;
    }

    /**
     * Method for user to get all drivers offer for his ride
     *
     * @param user the user who ordered the ride
     * @return ArrayList of offers
     */
    public ArrayList<Offer> getDriverOffer(User user) {
        ArrayList<Offer> offers = new ArrayList<>();
        try (Connection conn = this.connect()) {
            stmt = conn.createStatement();
            String sql = "SELECT DriverID,DriverName,Rating,Price,RideID FROM Offers " +
                    "Where RideID = (SELECT RideID FROM Requests WHERE CustomerID = " + this.getID(user.getAccount().getUsername()) + ");";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Offer offer = new Offer(rs.getDouble("Price"), getDriver(rs.getString("DriverName")), rs.getInt("RideID"));
                offers.add(offer);
            }
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return offers;
    }

    /**
     * Get all controller.Driver Favorite Areas
     *
     * @param driver controller.Driver to list his favorite area
     * @return ArrayList of controller.Area type
     */
    public ArrayList<controller.Area> getCarDriverFavouriteArea(controller.Driver driver) {
        ArrayList<controller.Area> areas = new ArrayList<>();
        String sql = "SELECT AreaID FROM FavoriteArea Where DriverID = " + getID(driver.getAccount().getUsername()) + ";";
        String sql2 = "SELECT AreaName FROM Areas WHERE ID = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql), rs2;
            while (rs.next()) {
                pstmt2.setInt(1, rs.getInt("AreaID"));
                rs2 = pstmt2.executeQuery();
                areas.add(new Area(rs2.getString("AreaName")));
            }
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return areas;
    }

    /**
     * Get list of unverified drivers to admin
     *
     * @return ArrayList of unverified drivers
     */
    public ArrayList<String> getNotVerifiedDriver() {
        ArrayList<String> drivers = new ArrayList<>();
        String sql = "SELECT UserName FROM Accounts Where IDAccount = (SELECT DriverID FROM DriverAccount WHERE IsVerified = 0)";

        try (Connection conn = this.connect()) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                drivers.add(rs.getString("UserName"));
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return drivers;
    }

    /**
     * Get list of rides where its source is a favorite area for the driver
     *
     * @param driver controller.Driver object who want to list all rides from his favorite area
     * @return ArrayList of Rides
     */
    public ArrayList<Ride> getRidesFromArea(controller.Driver driver) {
        ArrayList<Ride> rides = new ArrayList<>();
        String sql = "SELECT IDRides,SourceArea,DestinationArea,RideStatus,PassengersNo FROM Rides WHERE SourceArea =(" +
                "SELECT AreaName FROM Areas WHERE ID = (SELECT AreaID FROM FavoriteArea WHERE DriverID = ?))";
        //FavoriteArea WHERE DriverID = ? AND AreaID = ?
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, getID(driver.getAccount().getUsername()));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Area Source = new Area(rs.getString("SourceArea"));
                Area Destination = new Area(rs.getString("DestinationArea"));
                Ride ride = new Ride(Source, Destination, rs.getInt("PassengersNo"));
                ride.setRideID(rs.getInt("IDRides"));
                rides.add(ride);
            }
            conn.close();
            return rides;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return rides;
    }


    public ArrayList<Ride> getCarpoolRides(Area source, Area destination) {
        ArrayList<Ride> ret = new ArrayList<>();
        String sql = "SELECT * From Rides WHERE Alone = 0 AND PassengersNo < 3 AND PassengersNo > 0" +
                " AND RideStatus= 'Pending' AND SourceArea = ? AND DestinationArea = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, source.areaName);
            pstmt.setString(2, destination.areaName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Area src = new Area(rs.getString("SourceArea"));
                Area dest = new Area(rs.getString("DestinationArea"));
                Ride ride = new Ride(src, dest, rs.getInt("PassengersNo"));
                ret.add(ride);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }

    public int getAvaliblePlace(int rideID) {
        String sql = "SELECT CustomerID2,CustomerID3,CustomerID4 From Requests WHERE RideID =" + rideID;
        int ind = - 1;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = pstmt.executeQuery();
            int[] id = {rs.getInt("CustomerID2"), rs.getInt("CustomerID3"), rs.getInt("CustomerID4")};
            for (int i = 0; i < 3; i++) {
                if (id[i] == - 1) {
                    ind = id[i];
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ind;
    }

    public void joinCarRequest(controller.User user, int rideID) {
        int id = getAvaliblePlace(rideID) + 1;
        String sql = "INSERT INTO Requests VALUES CustomerID2 = ?,CustomerID3 = ?,CustomerID4 = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(id, getID(user.getAccount().getUsername()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Add area if not found in the Areas table
     *
     * @param area controller.Area object to be added in the database
     */
    public void addArea(Area area) {
        String sql = "INSERT OR IGNORE INTO Areas (AreaName) Values(?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, area.toString());
            pstmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Insert ride to database
     *
     * @param ride controller.Ride object that hold source and destination
     */
    public void insertRide(Ride ride) {
        String sql = "INSERT OR IGNORE INTO Rides (IDRides,SourceArea,DestinationArea,PassengersNo,Alone,RideStatus) Values(?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(IDRides) AS MAX FROM Rides;");
            int rideID = rs.getInt("MAX") + 1;
            ride.setRideID(rideID);
            pstmt.setInt(1, rideID);
            pstmt.setString(2, ride.getSourceArea().toString());
            pstmt.setString(3, ride.getDestinationArea().toString());
            pstmt.setInt(4, ride.getPassengersNo());
            if (ride.getPassengersNo() > 0) pstmt.setBoolean(5, false);
            else pstmt.setBoolean(5, true);

            pstmt.setString(6, ride.getRideStatus());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }


    /**
     * Assign to driver favorite area and it doesn't allow duplicates
     *
     * @param driver controller.Driver who want to add favorite area
     * @param area   controller.Area to be added to the controller.Driver
     */
    public void insertDriverFavoriteArea(controller.Driver driver, Area area) {
        String sql = "INSERT OR IGNORE INTO FavoriteArea (AreaID,DriverID) Values(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            stmt = conn.createStatement();
            pstmt.setInt(1, getAreaId(area));
            pstmt.setInt(2, getID(driver.getAccount().getUsername()));
            pstmt.executeUpdate();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    /**
     * Update the ride status
     *
     * @param rideID id of controller.Ride to be updated
     * @param status Updated status of the ride
     */
    public void updateRideStatus(int rideID, String status) {
        String sql = "UPDATE Rides SET RideStatus = ? WHERE IDRides = ? ;";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setString(1, status);
            psmt.setInt(2, rideID);
            psmt.executeUpdate();
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Insert controller.Driver to Requests table
     *
     * @param carRequest
     */
    public void updateCarRequest(CarRequest carRequest) {
        try (Connection conn = this.connect()) {
            String sql = "UPDATE Requests SET DriverID = " + getID(carRequest.getDriver().getAccount().getUsername()) + "," +
                    "driverOffer = " + carRequest.getDriverOffer() + ", isAccepted = " + carRequest.isAccepted() +
                    " Where RequestID = " + carRequest.getCarRequestID() + ";";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void logEvent(controller.Event event) {
        String sql = "INSERT INTO Logger (EventType,SourceUser,Info,RideID,DateTime) VAlUES (?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getType());
            pstmt.setString(2, event.getSource());
            pstmt.setString(3, event.getInfo());
            pstmt.setInt(4, event.getRideID());
            pstmt.setString(5, event.getTime());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    /**
     * Insert to Requests database carRequest attributes if exists Ignore
     *
     * @param carRequest
     */
    public void makeCarRequest(CarRequest carRequest) {
        String sql = "INSERT OR IGNORE INTO Requests (DriverID,CustomerID,RideID,driverOffer,isAccepted,Alone) VALUES (?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(RequestID) AS MAX FROM Requests;");
            carRequest.setCarRequestID(rs.getInt("MAX"));
            if (carRequest.getDriver() != null) {
                pstmt.setInt(1, getID(carRequest.getDriver().getAccount().getUsername()));
            } else {
                pstmt.setInt(1, 0);
            }
            pstmt.setInt(2, getID(carRequest.getClient().getAccount().getUsername()));
            pstmt.setInt(3, carRequest.getRide().getRideID());
            pstmt.setDouble(4, 0);
            pstmt.setInt(5, 0);
            pstmt.setBoolean(6, carRequest.getAloneStatus());
            pstmt.executeUpdate();
            pstmt.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Method to insert driver offer to database
     *
     * @param carDriver driver who make offer
     * @param offer     offer value
     * @param ride      ride to make offer for
     */
    //TODO Check its functionality
    public void makeDriverOffer(controller.Driver carDriver, Double offer, Ride ride) {
        String sql1 = "INSERT INTO Offers (DriverID,DriverName,RideID,Rating,Price) VALUES (?,?,?,?,?)";
        String sql2 = "SELECT RideID,CustomerID FROM Requests WHERE " +
                "RideID = ?;";
        DriverOperations op = new DriverOperations();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            pstmt2.setInt(1, ride.getRideID());
            ResultSet rs2 = pstmt2.executeQuery();
            pstmt.setInt(1, this.getID(carDriver.getAccount().getUsername()));
            pstmt.setString(2, carDriver.getAccount().getUsername());
            pstmt.setInt(3, rs2.getInt("RideID"));
            pstmt.setDouble(4, calAvgRating(carDriver));
            pstmt.setDouble(5, offer);
            pstmt.executeUpdate();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Method for user to rate driver by userName
     *
     * @param driver to be rated
     * @param rate   Rating of driver from user from 1 -> 5
     */
    public void rateDriver(controller.User user, controller.Driver driver, int rate) {
        String pstmtSql = "INSERT OR IGNORE INTO Ratings (UserID   , Rating, DriverID ) VALUES (?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(pstmtSql)
        ) {
            stmt = conn.createStatement();
            psmt.setInt(1, getID(user.getAccount().getUsername()));
            psmt.setInt(2, rate);
            psmt.setInt(3, getID(driver.getAccount().getUsername()));
            String sql = "UPDATE DriverAccount SET AvgRating = " + this.calAvgRating(driver);
            stmt.executeUpdate(sql);
            psmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Remove desired area from FavoriteArea Table
     *
     * @param carDriver controller.Driver who want to delete his area
     * @param area      controller.Area to be deleted
     */
    public void removeCarDriverFavouriteArea(controller.Driver carDriver, Area area) {
        try (Connection conn = this.connect()) {
            String sql = "DELETE FROM FavoriteArea WHERE DriverID = " + this.getID(carDriver.getAccount().getUsername()) + " AND AreaID = " + getAreaId(area) + ";";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Method where controller.Admin change isSuspended attribute in controller.Account Table
     *
     * @param username UserName of controller.Account to Suspend
     * @param value    1 to suspend, 0 to unsuspend
     */
    public void changeAccountState(String username, int value) {
        String sql = "UPDATE Accounts\n" +
                "SET isSuspended = ?\n" +
                "WHERE UserName = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, value);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            stmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Method where controller.Admin verify driver account
     *
     * @param id The driver id to be verified
     */
    public void verifyDriver(Integer id) {
        String sql = "UPDATE DriverAccount\n" +
                "SET IsVerified = 1\n" +
                "WHERE DriverID = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            stmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    /**
     * Build soo2Werkab Database tables
     */
    void Builder() {
        this.AccountDB();
        this.driverAccountsDB();
        this.RequestDB();
        this.RidesDB();
        this.UserAccountsDB();
        this.adminAccountsDB();
        this.offersDB();
        this.AreaDB();
        this.favoriteAreaDB();
        this.RatingDB();
        this.LoggerDB();
    }

    public double calAvgRating(controller.Driver driver) {
        double avgRatings = 0.0;
        int sum = 0;
        String sql = "SELECT Rating FROM Ratings WHERE DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            ArrayList<Integer> ratings = new ArrayList<>();
            psmt.setInt(1, getID(driver.getAccount().getUsername()));
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                ratings.add(rs.getInt("Rating"));
            }
            if (! ratings.isEmpty()) {
                for (int rating : ratings) {
                    sum += rating;
                }
                avgRatings = (double) sum / ratings.size();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return avgRatings;
    }

    public void setDiscount(Area area, int discount) {
        try (Connection conn = this.connect()) {
            String sql = "UPDATE Areas SET DISCOUNT = " + discount + " WHERE ID = " + getAreaId(area);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public ArrayList<Area> getAllAreas() {
        ArrayList<Area> areasList = new ArrayList<>();
        int sum = 0;
        String sql = "SELECT areaName FROM Areas";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                areasList.add(new Area(rs.getString("AreaName")));
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return areasList;
    }

    //    public void acceptRide(controller.User user,int rideID){
//        String sql = "SELECT areaName FROM Areas";
//        try (Connection conn = this.connect();
//             PreparedStatement psmt = conn.prepareStatement(sql)
//        ) {}catch (Exception e){
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//    }
    public void acceptRide(controller.User user, controller.Offer offer) {
        String sql = "SELECT * FROM Offers WHERE DriverName = ?";
        String sql2 = "UPDATE Requests SET DriverID = ?, driverOffer = ? , IsAccepted = 1 WHERE RideID = ?";

        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql);
             PreparedStatement psmt2 = conn.prepareStatement(sql2)
        ) {
            psmt.setString(1, offer.toString());
            ResultSet rs = psmt.executeQuery();
            int rideID = rs.getInt("RideID");
            int DriverID = rs.getInt("DriverID");
            double price = rs.getDouble("Price");
            psmt2.setInt(1, DriverID);
            psmt2.setDouble(2, price);
            psmt2.setInt(3, rideID);
            psmt2.executeUpdate();
            psmt.close();
            psmt2.close();
            rs.close();
            changeUserStatus(user, "InRide");
            changeDriverStatus(getDriver(offer.toString()), "InRide");
            updateRideStatus(rideID, "InRide");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void changeUserStatus(controller.User user, String status) {
        String sql = "UPDATE UserAccounts SET UserStatus = ? WHERE AccountID = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(2, getID(user.getAccount().getUsername()));
            psmt.setString(1, status);
            psmt.executeUpdate();
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void changeDriverStatus(controller.Driver driver, String status) {
        String sql = "UPDATE DriverAccount SET DriverStatus = ? WHERE DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(2, getID(driver.getAccount().getUsername()));
            psmt.setString(1, status);
            psmt.executeUpdate();
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public String getDriverStatus(controller.Driver driver) {
        String ret = "";
        String sql = "SELECT DriverStatus FROM DriverAccount WHERE DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(1, getID(driver.getAccount().getUsername()));
            ResultSet rs = psmt.executeQuery();
            ret = rs.getString("DriverStatus");
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }

    public String getUserStatus(User user) {
        String ret = "";
        String sql = "SELECT UserStatus FROM UserAccounts WHERE AccountID = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(1, getID(user.getAccount().getUsername()));
            ResultSet rs = psmt.executeQuery();
            ret = rs.getString("UserStatus");
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }

    public boolean areaDiscount(int rideID) {
        boolean ret = false;
        String sql = "SELECT DestinationArea FROM Rides WHERE IDRides = ?";
        String sql2 = "SELECT DISCOUNT FROM Areas WHERE AreaName = ?";

        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql);
             PreparedStatement psmt2 = conn.prepareStatement(sql2)

        ) {
            psmt.setInt(1, rideID);
            ResultSet rs = psmt.executeQuery();
            String dest = rs.getString("DestinationArea");
            psmt2.setString(1, dest);
            rs.close();
            ResultSet rs2 = psmt2.executeQuery();
            if (rs2.getInt("DISCOUNT") > 0) {
                ret = true;
            }
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }

    public boolean isFirstRide(User user) {
        boolean ret = false;
        String sql = "SELECT RideID FROM Requests WHERE CustomerID = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(1, getID(user.getAccount().getUsername()));
            ResultSet rs = psmt.executeQuery();
            int c = 0;
            while (rs.next()) {
                c++;
            }
            if (c == 1) {
                ret = true;
            }
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }

    public boolean passengerNumCheck(int rideID) {
        boolean ret = false;
        String sql = "SELECT PassengersNo FROM Rides WHERE IDRides = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(1, rideID);
            ResultSet rs = psmt.executeQuery();
            if (rs.getInt("PassengersNo") == 2) {
                ret = true;
            }
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }

    public String getBirthday(User user) {
        String ret = "";
        String sql = "SELECT BirthDate FROM Accounts WHERE IDAccount = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setInt(1, getID(user.getAccount().getUsername()));
            ResultSet rs = psmt.executeQuery();
            ret = rs.getString("BirthDate");
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }

    public void endRide(controller.Driver driver) {
        String sql = "SELECT * FROM Requests WHERE DriverID = ?";
        String sql2 = "SELECT UserName FROM Accounts WHERE IDAccount = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql);
             PreparedStatement psmt2 = conn.prepareStatement(sql2)

        ) {
            psmt.setInt(1, getID(driver.getAccount().getUsername()));
            ResultSet rs = psmt.executeQuery();
            psmt2.setInt(1, rs.getInt("CustomerID"));
            ResultSet rs2 = psmt2.executeQuery();

            int rideID = rs.getInt("RideID");
            String username = rs2.getString("UserName");
            rs2.close();
            rs.close();
            psmt.close();
            psmt2.close();
            changeDriverStatus(driver, "idle");
            changeUserStatus(getUser(username), "complete");
            updateRideStatus(rideID, "Completed");
            logEvent(new controller.Event(EventType.ArrivedToDest, driver.getAccount().getUsername(), username, rideID, Calendar.getInstance().getTime()));
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void logArrival(String driverUsername){
        String sql = "SELECT * FROM Requests WHERE DriverID = ?";
        try(Connection conn  = this.connect();
            PreparedStatement psmt = conn.prepareStatement(sql);

        ){
            psmt.setString(1, driverUsername);
            ResultSet rs = psmt.executeQuery();
            logEvent(new controller.Event(EventType.ArrivedToUserLoc, driverUsername, rs.getString("CustomerID"),
                    rs.getInt("RideID"),Calendar.getInstance().getTime() ));
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public String getDriverName(controller.User user) {
        String ret = "";
        String sql = "SELECT DriverID FROM Requests WHERE CustomerID = ?";
        String sql2 = "SELECT UserName FROM Accounts WHERE IDAccount = ?";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(sql);
             PreparedStatement psmt2 = conn.prepareStatement(sql2)

        ) {
            psmt.setInt(1, getID(user.getAccount().getUsername()));
            ResultSet rs = psmt.executeQuery();
            ArrayList<Integer> id = new ArrayList<>();
            while (rs.next()) {
                id.add(rs.getInt("DriverID"));
            }
            psmt2.setInt(1, id.get(id.size() - 1));
            ResultSet rs2 = psmt2.executeQuery();
            ret = rs2.getString("UserName");
            psmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }
}


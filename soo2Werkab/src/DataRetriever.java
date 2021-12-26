import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DataRetriever {
    Connection c = null;
    Statement stmt;
    static DataRetriever dataRetriever;
    int accountId;

    /**
     * Method that make only one object of type dataRetriever is available to use during runtime
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
                    "create_time TEXT NULL ," +
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
                    " DestinationArea  CHAR(50)         NOT NULL, " +
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
                    " UserStatus   TEXT CHECK( UserStatus IN ('Inactive','InRide','Pending','idle') )   NOT NULL DEFAULT 'idle'," +
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
                    "Rating INTEGER DEFAULT 0," +
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
                    "Rating DOUBLE NOT NULL ," +
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

    public void AreaDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Areas " +
                    "(ID INTEGER PRIMARY KEY NOT NULL," +
                    "AreaName CHAR(50) NOT NULL," +
                    "DISCOUNT INTEGER DEFAULT 0)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void favoriteAreaDB(){
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

    public void RequestDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Requests" +
                    "(RequestID INTEGER PRIMARY KEY NOT NULL ," +
                    "DriverID INTEGER ," +
                    "CustomerID INTEGER ," +
                    "RideID INTEGER ," +
                    "driverOffer DOUBLE NULL," +
                    "isAccepted SMALLINT NULL ," +
                    "FOREIGN KEY(DriverID)  REFERENCES DriverAccount(DriverID)," +
                    "FOREIGN KEY(CustomerID) REFERENCES UserAccounts(AccountID)," +
                    "FOREIGN KEY(RideID) REFERENCES Rides(IDRides))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Add Account attributes to database by Account object if not exist
     * @param a Account object that hold its attributes
     */
    private void AccountRegister(Account a) {
        String sql = "INSERT OR IGNORE INTO Accounts (IDAccount,UserName,Password,Email,mobileNo,isSuspended,create_time) VALUES (?,?,?,?,?,?,?)";
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
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    /**
     * Normal account register in addition add the account to UserAccounts as a foreign key
     * @param a Account object that hold its attributes
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
     * Add Account attributes to database by Driver object if not exist in addition add it to DriverAccount
     * @param d Driver object that hold Account and Driver attributes
     */
    public void DriverRegister(Driver d) {
        AccountRegister(d.account);
        String sql = "INSERT OR IGNORE INTO DriverAccount (DriverID,LicenceNo,NationalID) VALUES (?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(IDAccount) AS MAX FROM Accounts ;");
            accountId = rs.getInt("MAX");
            pstmt.setInt(1, accountId);
            pstmt.setString(2, d.drivingLicenseNumber);
            pstmt.setString(3, d.nationalID);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    /**
     * Check if account is in the database and enter a correct credentials
     * @param acc object that hold username and password to check
     * @return true if it is valid
     */
    public Boolean Login(Login acc) {
        if (adminLogin(acc)) {
            acc.isAdmin = true;
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
            pstmt.setString(1, acc.username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.getString("Password").equals(acc.password)) {
                pstmt2.setString(1, acc.username);
                rs = pstmt2.executeQuery();
                if (rs.getInt("isSuspended") == 0) {
                    acc.isDriver = this.isDriver(acc);
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
     * Check if the logging in account is a driver
     * @param acc object that hold username and password to check
     * @return true if it is found in DriverAccount table
     */
    Boolean isDriver(Login acc) {
        String sql = "SELECT IDAccount "
                + " FROM Accounts where UserName = ?";
        String sql2 = "SELECT DriverID " +
                "FROM DriverAccount where DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            pstmt.setString(1, acc.username);
            ResultSet rs = pstmt.executeQuery();
            pstmt2.setInt(1, rs.getInt("IDAccount"));
            ResultSet rs2 = pstmt2.executeQuery();
            int id = rs2.getInt("DriverID");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the driver verified by the admin
     * @param driver Driver to check
     * @return true if IsVerified attribute in table equals 1
     */
    Boolean isVerified(Driver driver){
        String sql = "SELECT IsVerified "
                + " FROM DriverAccount where DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            int id = this.getID(driver.account.getUsername());
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.getBoolean("IsVerified")){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Insert ride to database
     * @param ride Ride object that hold source and destination
     */
    public void insertRide(Ride ride) {
        String sql = "INSERT OR IGNORE INTO Rides (IDRides,SourceArea,DestinationArea,RideStatus) Values(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(IDRides) AS MAX FROM Rides;");
            int rideID = rs.getInt("MAX") + 1;
            ride.setRideID(rideID);
            pstmt.setInt(1, rideID);
            pstmt.setString(2, ride.getSourceArea().toString());
            pstmt.setString(3, ride.getDestinationArea().toString());
            pstmt.setString(4, ride.getRideStatus());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    /**
     * Update the ride status
     * @param ride Ride to be updated
     * @param status Updated status of the ride
     */
    public void updateRideStatus(Ride ride, String status) {
        try (Connection conn = this.connect()) {
            stmt = conn.createStatement();
            String sql = "UPDATE Rides" + "SET RideStatus = " + status + "WHERE IDRides = " + ride.getRideID() + ";";
            stmt.executeQuery(sql);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Add area if not found in the Areas table
     * @param area Area object to be added in the database
     */
    public void addArea(Area area){
        String sql = "INSERT OR IGNORE INTO Areas (AreaName) Values(?)";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ){
            pstmt.setString(1,area.toString());
            pstmt.executeUpdate();
            stmt.close();
            pstmt.close();
        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Get the ID of the desired Area
     * @param area Area Object that hold Area name
     * @return AreaID (Int)
     */
    public int getAreaId(Area area){
        this.addArea(area);
        String sql = "SELECT ID FROM Areas where AreaName = ?" + ";";
        int id = -1;
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ){
            pstmt.setString(1,area.toString());
            ResultSet rs = pstmt.executeQuery();
            id = rs.getInt("ID");
            pstmt.close();
            rs.close();
        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return id;
    }

    /**
     * Assign to driver favorite area and it doesn't allow duplicates
     * @param driver Driver who want to add favorite area
     * @param area Area to be added to the Driver
     */
    public void insertDriverFavoriteArea(Driver driver, Area area) {
        String sql = "INSERT OR IGNORE INTO FavoriteArea (AreaID,DriverID) Values(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            stmt = conn.createStatement();
            String username = driver.account.getUsername();
            pstmt.setInt(1, getAreaId(area));
            pstmt.setInt(2, getID(driver.account.getUsername()));
            pstmt.executeUpdate();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    /**
     * Get all Driver Favorite Areas
     * @param driver Driver to list his favorite area
     * @return ArrayList of Area type
     */

    public ArrayList<Area> getCarDriverFavouriteArea(Driver driver) {
        ArrayList<Area> areas = new ArrayList<>();
        String sql = "SELECT AreaID FROM FavoriteArea Where DriverID = " + getID(driver.account.getUsername()) + ";";
        String sql2 = "SELECT AreaName FROM Areas WHERE ID = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);
             ) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql),rs2;
            while (rs.next()) {
                pstmt2.setInt(1,rs.getInt("AreaID"));
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

    public Driver getDriver(String username) {
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
            int id = rs.getInt("IDAccount");
            Account account = new Account(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), rs.getString("mobileNo"));
            Driver driver = new Driver(account, rs2.getString("NationalID"), rs2.getString("LicenceNo"), rs2.getBoolean("isVerified"), rs2.getBoolean("isAccepted"), rs2.getInt("Balance"));
            pstmt.close();
            pstmt2.close();
            c.close();
            return driver;
        } catch (Exception e) {
            return null;
        }
    }


    User getUser(String username) {
        String sql = "SELECT IDAccount,UserName,Password,Email,mobileNo "
                + " FROM Accounts where UserName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            Account account = new Account(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), rs.getString("mobileNo"));
            User ret = new User(account);
            pstmt.close();
            c.close();
            return ret;
        } catch (Exception e) {
            return null;
        }
    }
//TODO edit function to adapt with new db
    public ArrayList<Ride> getRidesFromArea(Driver driver,Area area) {
        ArrayList<Ride> rides = new ArrayList<>();
        String sql = "SELECT IDRides,SourceArea,DestinationArea,RideStatus FROM Rides WHERE SourceArea =(" +
                "SELECT Areas FROM FavoriteArea WHERE DriverID = ? AND AreaID = ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(2,area.toString());
            pstmt.setInt(1,getID(driver.account.getUsername()));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Area Source = new Area(rs.getString("SourceArea"));
                Area Destination = new Area(rs.getString("DestinationArea"));
                Ride ride = new Ride(Source, Destination);
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

    public void removeCarDriverFavouriteArea(Driver carDriver, Area area) {
        try (Connection conn = this.connect()) {

            String sql = "SELECT IDAccount FROM Accounts WHERE UserName = " + carDriver.account.getUsername() + ";";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int id = rs.getInt("IDAccount");
            sql = "DELETE FROM DriverAccount WHERE DriverID = " + id + " AND Areas = " + area.toString() + ";";
            stmt.executeQuery(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    void Builder() {
        this.AccountDB();
        this.driverAccountsDB();
        //this.carDriverDB();
        this.RequestDB();
        this.RidesDB();
        this.UserAccountsDB();
        this.adminAccountsDB();
        this.offersDB();
        this.AreaDB();
        this.favoriteAreaDB();
    }

    int getRating(int driverID) {
        String sql = "select Rating from DriverAccount\nwhere DriverID = ?;";
        int rate = 0;
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, driverID);
            ResultSet rs = pstmt.executeQuery();
            rate = rs.getInt("Rating");
            pstmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return rate;

    }

    public User getUserDB(Integer id) {
        String sql = "select Rating from DriverAccount\nwhere DriverId = " + id + ";";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            accountId = rs.getInt("AccountID");
            pstmt.setInt(1, accountId);
            pstmt.executeUpdate();
            stmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return new User();
    }

    public void changeStateDB(String username, int value) {
        String sql = "UPDATE Accounts\n" +
                "SET isSuspended = ?\n" +
                "WHERE UserName = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, value);
            pstmt.setString(2, username);
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            //pstmt = c.createStatement();
            pstmt.executeUpdate();
            stmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void verifyDriverDB(Integer id) {
        String sql = "UPDATE DriverAccount\n" +
                "SET IsVerified = 1\n" +
                "WHERE DriverID = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            //pstmt = c.createStatement();
            pstmt.executeUpdate();
            stmt.close();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public int getID(String username) {
        int id = -1;
        String sql = "SELECT IDAccount FROM Accounts Where UserName = ?" ;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,username);
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

    public void makeCarRequest(CarRequest carRequest) {
        String sql = "INSERT OR IGNORE INTO Requests (RequestID,DriverID,CustomerID,RideID,driverOffer,isAccepted) VALUES (?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(RequestID) AS MAX FROM Requests;");
            int reqID = rs.getInt("MAX") + 1;
            carRequest.carRequestID = reqID;
            pstmt.setInt(1, reqID);
            if(carRequest.driver != null) {
                pstmt.setInt(2, getID(carRequest.driver.account.getUsername()));
            }
            else{
                pstmt.setInt(2,0);
            }
            pstmt.setInt(3, getID(carRequest.client.account.getUsername()));
            pstmt.setInt(4, carRequest.ride.getRideID());
            pstmt.setDouble(5, 0);
            pstmt.setInt(6, 0);
            pstmt.executeUpdate();
            pstmt.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void updateCarRequest(CarRequest carRequest) {
        try (Connection conn = this.connect()) {
            String sql = "UPDATE Requests SET DriverID = " + getID(carRequest.driver.account.getUsername()) + "," +
                    "driverOffer = " + carRequest.driverOffer + ", isAccepted = " + carRequest.isAccepted +
                    " Where RequestID = " + carRequest.carRequestID + ";";
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void makeDriverOffer(Driver cardriver, Integer offer, Ride ride) {
        String sql1 = "INSERT INTO Offers (DriverID,DriverName,RideID,Rating,Price) VALUES (?,?,?,?,?)";
        String sql = "SELECT DriverId,RideID,CustomerID FROM Requests WHERE " +
                "RideID = ?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql);
        ) {
            pstmt2.setInt(1,ride.getRideID());
            ResultSet rs2 = pstmt2.executeQuery();
            pstmt.setInt(1, rs2.getInt("DriverID"));
            pstmt.setString(2, cardriver.account.getUsername());
            pstmt.setInt(3, rs2.getInt("RideID"));
            // TODO fix rating
            //pstmt.setDouble(4, cardriver.Rating);
            pstmt.setInt(5, offer);
            pstmt.executeUpdate();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public ArrayList<Offer> getDriverOffer(CarRequest carRequest) {
        ArrayList<Offer> offers = new ArrayList<>();
        try (Connection conn = this.connect()) {
            stmt = conn.createStatement();
            String sql = "SELECT DriverID,DriverName,Rating,Price FROM Offers " +
                    "Where RideID = " + carRequest.ride.getRideID() + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Integer price = rs.getInt("Price");
                Offer offer = new Offer(price, carRequest.driver);
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

    public void rateDriver(Driver driver, Integer rate) {
        String pstmtSql = "UPDATE DriverAccount SET Rating= ? ,NumOfRatings = ? WHERE DriverID = ?;";
        try (Connection conn = this.connect();
             PreparedStatement psmt = conn.prepareStatement(pstmtSql)
        ) {
            Integer driverID= getID(driver.account.getUsername());
            stmt = conn.createStatement();
            String sql = "SELECT Rating,NumOfRatings FROM DriverAccount" +
                    " WHERE DriverID = "+driverID +";";
            ResultSet rs=stmt.executeQuery(sql);
            Integer rating = rs.getInt("Rating");
            Integer numRating = rs.getInt("NumOfRatings") +1;
            Integer avgRate = (rate + rating)/numRating;
            psmt.setInt(1,avgRate);
            psmt.setInt(2,numRating);
            psmt.setInt(3,driverID);
            psmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Double getDriverOffer(User user) {
        return 1.0;
    }

    public Boolean adminLogin(Login acc) {
        String sql = "SELECT Password "
                + " FROM AdminAccounts where UserName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, acc.username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.getString("Password").equals(acc.password)) {
                return true;
            }
            conn.close();
        } catch (Exception e){
            return false;
        }
        return false;
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
                    "create_time TEXT NULL ," +
                    "UNIQUE(UserName))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    void adminAccountRegister(Account account) {
        String sql = "INSERT OR IGNORE INTO AdminAccounts (IDAccount,UserName,Password) VALUES (?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(IDAccount) AS MAX FROM AdminAccounts ;");
            accountId = rs.getInt("MAX");
            pstmt.setInt(1, accountId);
            pstmt.setString(2, account.getUsername());
            pstmt.setString(3, account.getPassword());
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Admin getAdmin(String username) {
        String sql = "SELECT IDAccount,UserName,Password "
                + " FROM AdminAccounts where UserName = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            Account account = new Account(rs.getString("UserName"), rs.getString("Password"),"","");
            Admin admin = new Admin(account);
            conn.close();
            return new Admin(account);
        } catch (Exception e) {
            return null;
        }
    }
}

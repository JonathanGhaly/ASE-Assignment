import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DataRetriever {
    Connection c = null;
    Statement stmt;
    static DataRetriever dataRetriever;
    int accountId;

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
                    " DriverStatus   TEXT CHECK( DriverStatus IN ('Inactive','InRide','Pending','idle') )   NOT NULL DEFAULT 'idle'," +
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
                    "FOREIGN KEY(DriverName)  REFERENCES Account(UserName)," +
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

    public void carDriverDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS CarDriver" +
                    "(DriverID INTEGER ," +
                    "LicenceNo CHAR(50) NOT NULL ," +
                    "Areas CHAR(50) NULL," +
                    "FOREIGN KEY(DriverID)  REFERENCES DriverAccount(DriverID)" +
                    "UNIQUE(DriverID,Areas))";
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

    Boolean isVerified(CarDriver driver){
        String sql = "SELECT IsVerified "
                + " FROM DriverAccount where DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            int id = this.getID(driver.account.getUsername());
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.getInt("IsVerified")>0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
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

    public void insertCarDriverFavouriteArea(CarDriver carDriver, Area area) {
        String sql = "INSERT OR IGNORE INTO CarDriver (DriverID,LicenceNo,Areas) Values(?,?,?)";
        String sql2 = "SELECT IDAccount FROM Accounts where UserName = ?" + ";";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            stmt = conn.createStatement();
            String username = carDriver.account.getUsername();
            pstmt2.setString(1, username);
            //   ResultSet rs = stmt.executeQuery("SELECT IDAccount FROM Accounts where UserName = " + username + ";");
            ResultSet rs = pstmt2.executeQuery();
            int id = rs.getInt("IDAccount");
            pstmt.setInt(1, id);
            pstmt.setString(2, carDriver.drivingLicenseNumber);
            pstmt.setString(3, area.toString());
            pstmt.executeUpdate();
            pstmt.close();
            pstmt2.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    public ArrayList<Area> getCarDriverFavouriteArea(CarDriver carDriver) {
        ArrayList<Area> areas = new ArrayList<>();
        String sql = "SELECT Areas FROM CarDriver Where LicenceNo = " + carDriver.drivingLicenseNumber + ";";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                areas.add(new Area(rs.getString("Areas")));
            }
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return areas;
    }

    public CarDriver getCarDriver(String username) {
        String sql = "SELECT IDAccount,UserName,Password,Email,mobileNo "
                + " FROM Accounts where UserName = ?";
        String sql2 = "SELECT DriverID,LicenceNo,NationalID " +
                "FROM DriverAccount where DriverID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            pstmt2.setInt(1, rs.getInt("IDAccount"));
            ResultSet rs2 = pstmt2.executeQuery();
            int id = rs2.getInt("DriverID");
            Account driver = new Account(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), rs.getString("mobileNo"));
            CarDriver ret = new CarDriver(driver, rs2.getString("NationalID"), rs2.getString("LicenceNo"));
            pstmt.close();
            pstmt2.close();
            c.close();
            return ret;
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

    public ArrayList<Ride> getRidesFromArea(CarDriver carDriver,Area area) {
        ArrayList<Ride> rides = new ArrayList<>();
        String sql = "SELECT IDRides,SourceArea,DestinationArea,RideStatus FROM Rides WHERE SourceArea =(" +
                "SELECT Areas FROM CarDriver WHERE DriverID = ? AND Areas = ?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)
            ) {
            pstmt.setString(2,area.toString());
            pstmt.setInt(1,getID(carDriver.account.getUsername()));
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
            sql = "DELETE FROM CarDriver WHERE DriverID = " + id + " AND Areas = " + area.toString() + ";";
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
        this.carDriverDB();
        this.RequestDB();
        this.RidesDB();
        this.UserAccountsDB();
        this.adminAccountsDB();
        this.offersDB();
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
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "UPDATE Accounts\n " +
                    "SET isSuspended = " + value + "\n" +
                    "WHERE UserName = " + username + ";";
            stmt.executeUpdate(sql);
            stmt.close();
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
            if(carRequest.carDriver != null) {
                pstmt.setInt(2, getID(carRequest.carDriver.account.getUsername()));
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
            String sql = "UPDATE Requests SET DriverID = " + getID(carRequest.carDriver.account.getUsername()) + "," +
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

    public void makeDriverOffer(CarDriver cardriver, Integer offer, Ride ride) {
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
            pstmt.setDouble(4, cardriver.Rating);
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
                Offer offer = new Offer(price, carRequest.carDriver);
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

    public void rateDriver(CarDriver driver, Integer rate) {
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

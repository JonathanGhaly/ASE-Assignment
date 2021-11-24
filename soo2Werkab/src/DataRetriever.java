import javax.xml.transform.Result;
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
            return dataRetriever;
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
        System.out.println("Opened database successfully");
        return c;
    }

    public void AccountDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE Accounts " +
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
        System.out.println("Opened database successfully");
    }

    public void RidesDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE Rides " +
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
        System.out.println("Opened database successfully");
    }

    public void UserAccountsDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE UserAccounts " +
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
        System.out.println("Opened database successfully");
    }

    public void driverAccountsDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE DriverAccount " +
                    "(DriverID INTEGER ," +
                    "LicenceNo CHAR(50) NOT NULL ," +
                    "NationalID Char(50) NOT NULL ," +
                    "IsVerified SMALLINT DEFAULT 0," +
                    "IsAccepted SMALLINT DEFAULT 0," +
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
        System.out.println("Opened database successfully");
    }

    public void carDriverDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE CarDriver" +
                    "(DriverID INTEGER ," +
                    "LicenceNo CHAR(50) NOT NULL ," +
                    "Areas CHAR(50) NULL," +
                    "FOREIGN KEY(DriverID)  REFERENCES DriverAccount(DriverID)"+
                    "UNIQUE(DriverID,Areas))";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void RequestDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:soo2Werkab.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE Requests" +
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
        System.out.println("Opened database successfully");
    }

    private void AccountRegister(Account a) {
        String sql = "INSERT INTO Accounts (IDAccount,UserName,Password,Email,mobileNo,isSuspended,create_time) VALUES (?,?,?,?,?,?,?)";
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
        String sql = "INSERT INTO UserAccounts (AccountID) VALUES (?)";
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
        String sql = "INSERT INTO DriverAccount (DriverID,LicenceNo,NationalID) VALUES (?,?,?)";
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


    public void insertRide(Ride ride) {
        String sql = "INSERT INTO Rides (IDRides,SourceArea,DestinationArea,RideStatus) Values(?,?,?,?)";
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
        String sql = "INSERT INTO CarDriver (DriverID,LicenceNo,Areas) Values(?,?,?)";
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
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Ride> getRidesFromArea(Area area) {
        ArrayList<Ride> rides = new ArrayList<>();
        String sql = "SELECT IDRides,SourceArea,DestinationArea,RideStatus FROM Rides WHERE(" +
                "SELECT Areas FROM CarDriver WHERE Areas = " + area.toString() +
                ")";
        try (Connection conn = this.connect()) {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Area Source = new Area(rs.getString("SourceArea"));
                Area Destination = new Area(rs.getString("DestinationArea"));
                Ride ride = new Ride(Source, Destination);
                rides.add(ride);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return rides;
    }




    public void Builder() {
        this.AccountDB();
        this.driverAccountsDB();
        this.carDriverDB();
        this.RequestDB();
        this.RidesDB();
        this.UserAccountsDB();

    }

    static int getRating(Driver driver) {
        return 0;
    }
}

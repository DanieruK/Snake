package MainFolder;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private Connection con;

    public DataBase() {

        String table1 = "CREATE TABLE IF NOT EXISTS SCHWIERIGKEIT ( " +
                "SCHWIERIGKEITID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "SCHWIERIGKEIT TEXT);";

        String sqlInsert = "INSERT INTO SCHWIERIGKEIT(SCHWIERIGKEIT)" +
                "VALUES (\"Leicht\")," +
                "(\"Mittel\")," +
                "(\"Schwer\");";

        String query = "SELECT COUNT(*) FROM SCHWIERIGKEIT WHERE SCHWIERIGKEIT LIKE 'Leicht'";

        String table2 = "CREATE TABLE IF NOT EXISTS SPIELER ( " +
                "SPIELERID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "SPIELERNAME TEXT);";

        String table3 = "CREATE TABLE IF NOT EXISTS SPIEL (" +
                "SPIELID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "ZEIT INT," +
                "PUNKTE INT," +
                "SPIELERID INT," +
                "SCHWIERIGKEITID INT," +
                "SPIELEFELDID INT," +
                "FOREIGN KEY (SPIELERID)" +
                "REFERENCES SPIELER (SPIELERID)" +
                "FOREIGN KEY (SCHWIERIGKEITID)" +
                "REFERENCES SCHWIERIGKEIT (SCHWIERIGKEITID)" +
                "FOREIGN KEY (SPIELEFELDID)" +
                "REFERENCES SPIELFELD (SPIELEFELDID));";

        String table4 = "CREATE TABLE IF NOT EXISTS SPIELFELD (" +
                "SPIELEFELDID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "SPIELFELD TEXT)";

        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            con = DriverManager.getConnection("jdbc:sqlite:snake.db", config.toProperties());
            Statement stmt = con.createStatement();
            stmt.executeUpdate(table1);
            try (ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    boolean found = rs.getBoolean(1);
                    if (!found) {
                        stmt.executeUpdate(sqlInsert);
                    }
                }
            }
            stmt.executeUpdate(table2);
            stmt.executeUpdate(table3);
            stmt.executeUpdate(table4);
            stmt.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMap(String pSpielfeld) {
        String sqlInsert = "INSERT INTO SPIELFELD(SPIELFELD) VALUES (\"" + pSpielfeld + "\");";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sqlInsert);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePlayer(String playerName) {
        String query = "SELECT COUNT(*) FROM SPIELER WHERE SPIELERNAME LIKE '" + playerName + "'";
        String sqlInsert = "INSERT INTO SPIELER(SPIELERNAME)"
                + "VALUES (\"" + playerName + "\");";
        try {
            Statement stmt = con.createStatement();
            try (ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    boolean found = rs.getBoolean(1);
                    if (!found) {
                        stmt.executeUpdate(sqlInsert);
                    }
                }
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveSpiel(String playerName, int punkte, int schwierigkeit, int zeit, int mapID) {
        int spielerID = 0;
        String query = "SELECT SPIELERID " +
                "FROM SPIELER " +
                "WHERE SPIELERNAME = \"" + playerName + "\"";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                spielerID = rs.getInt(1);
            }
            String sqlInsert = "INSERT INTO SPIEL(SPIELERID,SCHWIERIGKEITID,ZEIT,PUNKTE,SPIELEFELDID)" +
                    "VALUES (" + spielerID + "," + schwierigkeit + "," + zeit + "," + punkte + "," + mapID + ");";
            stmt.executeUpdate(sqlInsert);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String searchForMap(int mapID) {
        String map = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT SPIELFELD FROM SPIELFELD WHERE SPIELEFELDID = ?");
            pstmt.setInt(1, mapID);
            ResultSet rs = pstmt.executeQuery();
            map = rs.getString(1);
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public void resetTable(String tableName) {
        try {
            Statement stmt = con.createStatement();
            String sqlReset = "DELETE from " + tableName;
            stmt.execute(sqlReset);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList getbestenliste(String pName, String pSchwierigkeit) {
        try {
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            if (pName.equals("") && pSchwierigkeit.equals("")) {
                pstmt = con.prepareStatement("SELECT SPIELERNAME, SCHWIERIGKEIT, SPIELEFELDID, ZEIT, PUNKTE " +
                        "FROM SPIELER SPI, SCHWIERIGKEIT SCH, SPIEL SP " +
                        "WHERE SPI.SPIELERID = SP.SPIELERID " +
                        "AND SP.SCHWIERIGKEITID = SCH.SCHWIERIGKEITID " +
                        "ORDER BY PUNKTE DESC, ZEIT ASC");
                rs = pstmt.executeQuery();
            } else if (pName.equals("")) {
                pstmt = con.prepareStatement("SELECT SPIELERNAME, SCHWIERIGKEIT, SPIELEFELDID, ZEIT, PUNKTE " +
                        "FROM SPIELER SPI, SCHWIERIGKEIT SCH, SPIEL SP " +
                        "WHERE SPI.SPIELERID = SP.SPIELERID " +
                        "and SP.SCHWIERIGKEITID = SCH.SCHWIERIGKEITID " +
                        "and SCHWIERIGKEIT = ? " +
                        "ORDER BY PUNKTE DESC, ZEIT ASC");
                pstmt.setString(1, pSchwierigkeit);
                rs = pstmt.executeQuery();
            } else if (pSchwierigkeit.equals("")) {
                pstmt = con.prepareStatement("SELECT SPIELERNAME, SCHWIERIGKEIT, SPIELEFELDID, ZEIT, PUNKTE " +
                        "FROM SPIELER SPI, SCHWIERIGKEIT SCH, SPIEL SP " +
                        "WHERE SPI.SPIELERID = SP.SPIELERID " +
                        "and SP.SCHWIERIGKEITID = SCH.SCHWIERIGKEITID " +
                        "and SPIELERNAME = ? " +
                        "ORDER BY PUNKTE DESC, ZEIT ASC");
                pstmt.setString(1, pName);
                rs = pstmt.executeQuery();
            } else {
                pstmt = con.prepareStatement("SELECT SPIELERNAME, SCHWIERIGKEIT, SPIELEFELDID, ZEIT, PUNKTE " +
                        "FROM SPIELER SPI, SCHWIERIGKEIT SCH, SPIEL SP " +
                        "WHERE SPI.SPIELERID = SP.SPIELERID " +
                        "and SP.SCHWIERIGKEITID = SCH.SCHWIERIGKEITID " +
                        "and SPIELERNAME = ? " +
                        "and SCHWIERIGKEIT = ? " +
                        "ORDER BY PUNKTE DESC, ZEIT ASC");
                pstmt.setString(1, pName);
                pstmt.setString(2, pSchwierigkeit);
                rs = pstmt.executeQuery();
            }
            ArrayList<String[]> data = new ArrayList<>();
            while (rs.next()) {
                data.add(new String[]{rs.getString("SPIELERNAME"), rs.getString("SCHWIERIGKEIT"), rs.getString("SPIELEFELDID"), rs.getString("ZEIT"), rs.getString("PUNKTE")});
            }
            pstmt.close();
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeCon() {
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

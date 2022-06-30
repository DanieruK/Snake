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
                "SPIELERID INT," +
                "SCHWIERIGKEITID INT," +
                "ZEIT INT," +
                "PUNKTE INT," +
                "FOREIGN KEY (SPIELERID)" +
                "REFERENCES SPIELER (SPIELERID)" +
                "FOREIGN KEY (SCHWIERIGKEITID)" +
                "REFERENCES SCHWIERIGKEIT (SCHWIERIGKEITID));";

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
            stmt.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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

    public void saveSpiel(String playerName, int punkte, int schwierigkeit, int zeit) {
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
            String sqlInsert = "INSERT INTO SPIEL(SPIELERID,SCHWIERIGKEITID,ZEIT,PUNKTE)" +
                    "VALUES (" + spielerID + "," + schwierigkeit + "," + zeit + "," + punkte + ");";
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                pstmt = con.prepareStatement("SELECT SPIELERNAME, SCHWIERIGKEIT, ZEIT, PUNKTE " +
                        "FROM SPIELER SPI, SCHWIERIGKEIT SCH, SPIEL SP " +
                        "WHERE SPI.SPIELERID = SP.SPIELERID " +
                        "AND SP.SCHWIERIGKEITID = SCH.SCHWIERIGKEITID " +
                        "ORDER BY PUNKTE DESC, ZEIT ASC");
                rs = pstmt.executeQuery();
            } else if (pName.equals("")) {
                pstmt = con.prepareStatement("SELECT SPIELERNAME, SCHWIERIGKEIT, ZEIT, PUNKTE " +
                        "FROM SPIELER SPI, SCHWIERIGKEIT SCH, SPIEL SP " +
                        "WHERE SPI.SPIELERID = SP.SPIELERID " +
                        "and SP.SCHWIERIGKEITID = SCH.SCHWIERIGKEITID " +
                        "and SCHWIERIGKEIT = ? " +
                        "ORDER BY PUNKTE DESC, ZEIT ASC");
                pstmt.setString(1, pSchwierigkeit);
                rs = pstmt.executeQuery();
            } else if (pSchwierigkeit.equals("")) {
                pstmt = con.prepareStatement("SELECT SPIELERNAME, SCHWIERIGKEIT, ZEIT, PUNKTE " +
                        "FROM SPIELER SPI, SCHWIERIGKEIT SCH, SPIEL SP " +
                        "WHERE SPI.SPIELERID = SP.SPIELERID " +
                        "and SP.SCHWIERIGKEITID = SCH.SCHWIERIGKEITID " +
                        "and SPIELERNAME = ? " +
                        "ORDER BY PUNKTE DESC, ZEIT ASC");
                pstmt.setString(1, pName);
                rs = pstmt.executeQuery();
            } else {
                pstmt = con.prepareStatement("SELECT SPIELERNAME, SCHWIERIGKEIT, ZEIT, PUNKTE " +
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
            while (rs.next()){
                data.add(new String[]{rs.getString("SPIELERNAME"),rs.getString("SCHWIERIGKEIT"),rs.getString("ZEIT"),rs.getString("PUNKTE")});
            }
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

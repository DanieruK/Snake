package MainFolder;

import java.sql.*;

public class DataBase {

    private Connection con;

    public DataBase() {

        String sql = "CREATE TABLE IF NOT EXISTS SCHWIERIGKEIT ( " +
                "SCHWIERIGKEITID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "SCHWIERIGKEIT TEXT);";

        String sqlInsert = "INSERT INTO SCHWIERIGKEIT(SCHWIERIGKEIT)" +
                "VALUES (\"Leicht\")," +
                "(\"Mittel\")," +
                "(\"Schwer\");";

        String query = "SELECT COUNT(*) FROM SCHWIERIGKEIT WHERE SCHWIERIGKEIT LIKE 'Leicht'";

        String sql2 = "CREATE TABLE IF NOT EXISTS SPIELER ( " +
                "SPIELERID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "SPIELERNAME TEXT);";

        String sql3 = "CREATE TABLE IF NOT EXISTS SPIEL (" +
                "SPIELID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "SPIELERID INT," +
                "SCHWIERIGKEITID INT," +
                "ZEIT INT," +
                "PUNKTE INT," +
                "FOREIGN KEY (SPIELERID)" +
                "REFERENCES SPIELER (SPIELERID)" +
                "FOREIGN KEY (SPIELERID)" +
                "REFERENCES SCHWIERIGKEIT (SCHWIERIGKEITID));";

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Snake.db");
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            try (ResultSet rs = stmt.executeQuery(query)) {
                if (rs.next()) {
                    boolean found = rs.getBoolean(1);
                    if (!found) {
                        stmt.executeUpdate(sqlInsert);
                    }
                }
            }
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
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
                    "VALUES (" + spielerID + "," + schwierigkeit+ "," + zeit + "," + punkte + ");";
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

    public void closeCon() {
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

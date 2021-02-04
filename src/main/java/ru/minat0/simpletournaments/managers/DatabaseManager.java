package ru.minat0.simpletournaments.managers;

import org.bukkit.configuration.file.FileConfiguration;
import ru.minat0.simpletournaments.SimpleTournaments;
import ru.minat0.simpletournaments.utility.ErrorsUtil;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    final String dbName = "SimpleTournaments";
    private Connection connection;

    public void setup() {
        try {
            Statement statement = getConnection().createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS tournaments_players "
                    + "(id INTEGER PRIMARY KEY,"
                    + "uuid varchar(255) NOT NULL,"
                    + "kills int NOT NULL,"
                    + "deaths int NOT NULL,"
                    + "victories int NOT NULL,"
                    + "defeats int NOT NULL,"
                    + "locale varchar(10) NOT NULL);"
            );
            statement.execute("CREATE TABLE IF NOT EXISTS tournaments_winners "
                    + "(date varchar(10) NOT NULL,"
                    + "tournament varchar(255) NOT NULL,"
                    + "winner varchar(255) NOT NULL);"
            );
        } catch (SQLException ex) {
            ErrorsUtil.error("Error while creating the tables: " + ex.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            initialize();
        }

        return connection;
    }

    private void initialize() throws SQLException {
        FileConfiguration config = SimpleTournaments.getConfiguration().getConfig();
        String dbType = config.getString("DataSource.backend", "SQLITE");

        if (dbType.equalsIgnoreCase("MYSQL")) {

            String hostname = config.getString("DataSource.SQLHost");
            String port = config.getString("DataSource.SQLPort");
            String database = config.getString("DataSource.SQLDatabase");
            String username = config.getString("DataSource.SQLUsername");
            String password = config.getString("DataSource.SQLPassword");

            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database +
                        "?useSSL=false", username, password);
            } catch (ClassNotFoundException ex) {
                ErrorsUtil.error("MySQL driver not found!");
            }
        } else {
            ErrorsUtil.error("Error while getting the type of DB, please check \"DataSource.backend\" in config.yml! Using SQLite instead...");
            File sqlFile = new File(SimpleTournaments.getInstance().getDataFolder(), dbName + ".db");

            if (!sqlFile.exists()) {
                try {
                    sqlFile.createNewFile();
                } catch (IOException ex) {
                    ErrorsUtil.error("File write error: " + dbName + ".db");
                }
            }

            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + sqlFile);
            } catch (ClassNotFoundException | SQLException ex) {
                ErrorsUtil.error("SQLite driver not found!");
            }
        }

    }
}

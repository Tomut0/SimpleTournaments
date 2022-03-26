package ru.minat0.simpletournaments.managers;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.minat0.simpletournaments.utility.Log;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.logging.Level.SEVERE;
import static ru.minat0.simpletournaments.managers.DatabaseManager.DBType.SQLITE;

/**
 * @author Minat0_
 * I'd seen example from RoinujNosde DatabaseManager's class.
 * https://github.com/RoinujNosde/TitansBattle/blob/master/src/main/java/me/roinujnosde/titansbattle/managers/DatabaseManager.java
 */
public class DatabaseManager {
    private final JavaPlugin plugin;
    private final FileConfiguration config;

    private HikariDataSource dataSource;

    public DatabaseManager(JavaPlugin plugin, FileConfiguration config, DBType type) {
        this.plugin = plugin;
        this.config = config;
        this.dataSource = getDataSource(type);
        setup();
    }

    @SuppressWarnings("SqlNoDataSourceInspection")
    private void setup() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS tournaments_players "
                    + "(id INTEGER PRIMARY KEY,"
                    + "uuid varchar(255) NOT NULL,"
                    + "kills int NOT NULL,"
                    + "deaths int NOT NULL,"
                    + "victories int NOT NULL,"
                    + "defeats int NOT NULL,"
                    + "locale varchar(10) NOT NULL);"
            );
        } catch (SQLException ex) {
            Log.getLogger().log(SEVERE, "Error while creating the tables!", ex);
        }
    }

    private HikariDataSource getDataSource(DBType type) {
        dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(20);
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        switch (type) {
            case SQLITE:
                File SQLFile = createFileIfNotExist();
                dataSource.setDriverClassName("org.sqlite.JDBC");
                dataSource.setJdbcUrl("jdbc:sqlite:" + SQLFile);
                return dataSource;
            case MYSQL:
                String database = config.getString("DataSource.mySQLDatabase");
                String hostname = config.getString("DataSource.mySQLHost");
                String port = config.getString("DataSource.mySQLPort");
                String username = config.getString("DataSource.mySQLUsername");
                String password = config.getString("DataSource.mySQLPassword");
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                dataSource.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false&characterEncoding=utf-8&autoReconnect=true");
                return dataSource;
            default:
                Log.getLogger().warning("Unable to get type of database connection, please check \"DataSource.backend\" in config.yml! Using SQLite instead...");
                return getDataSource(SQLITE);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File createFileIfNotExist() {
        File sqlFile = new File(plugin.getDataFolder(), plugin.getDescription().getName() + ".db");

        if (!sqlFile.exists()) {
            try {
                sqlFile.createNewFile();
                return sqlFile;
            } catch (IOException ex) {
                Log.getLogger().log(SEVERE, "File writing error: {0}.db", plugin.getDescription().getName());
            }
        }

        return sqlFile;
    }

    public enum DBType {
        SQLITE, MYSQL
    }
}

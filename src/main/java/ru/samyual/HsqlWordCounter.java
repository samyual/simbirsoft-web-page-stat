package ru.samyual;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HsqlWordCounter implements WordCounter {

    private static final String JDBC_DRIVER_CLASS = "org.hsqldb.jdbc.JDBCDriver";
    private static final String CONNECTION_TMPL = "jdbc:hsqldb:file:db/wordcount;shutdown=true;hsqldb.sqllog=1";
    private static final String USER = "SA";
    private static final String PASSWORD = "";
    private static final String SQL_DROP_TABLE = "DROP TABLE wc IF EXISTS;";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE wc (word VARCHAR(255) PRIMARY KEY, count BIGINT);";
    private static final String SQL_GET = "SELECT word, count FROM wc WHERE word = ?";
    private static final String SQL_INSERT = "INSERT INTO wc (word, count) VALUES (?, 1)";
    private static final String SQL_UPDATE = "UPDATE wc SET (count) = ? WHERE word = ?";
    private static final String SQL_SELECT = "SELECT word, count FROM wc ORDER BY word";

    private Connection connection = null;

    public HsqlWordCounter(final String pathToDb) {
        final String connectionString = String.format(CONNECTION_TMPL, pathToDb);

        Logger logger = Logger.getLogger("hsqldb.db");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.WARNING);
        try {
            logger.addHandler(new FileHandler("db/wordcount.log"));
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            Class.forName(JDBC_DRIVER_CLASS);
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            connection = DriverManager.getConnection(connectionString, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute(SQL_DROP_TABLE);
            statement.execute(SQL_CREATE_TABLE);
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Logger.getLogger("org.hsqldb.persist").setLevel(Level.WARNING);
    }

    @Override
    public void count(final String[] words) {
        for (final String word : words) {
            count(word);
        }
    }

    @Override
    public void count(final String word) {
        if (!word.isEmpty()) {
            final long count = get(word);
            if (count == 0) {
                insert(word);
            } else {
                update(word, count + 1);
            }
        }
    }

    private void insert(final String word) {
        try {
            final PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, word);
            statement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void update(final String word, final long count) {
        try {
            final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setLong(1, count);
            statement.setString(2, word);
            statement.execute();
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public long get(final String word) {
        try {
            final PreparedStatement statement = connection.prepareStatement(SQL_GET);
            statement.setString(1, word);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                final long count = rs.getLong("count");
                return count;
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return 0;
    }

    @Override
    public void report() {
        report(System.out);
    }

    @Override
    public void report(final PrintStream out) {
        try {
            final PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                final String word = rs.getString(1);
                final long count = rs.getLong(2);
                out.println(word + " - " + count);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
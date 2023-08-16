package carsharing.controllers;

import carsharing.utility.Console;
import carsharing.utility.Constants;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBController {

    private static DBController instance;
    private final Connection connection;

    public DBController(String[] args) throws SQLException {
        File dbFile = getDBFile(args);
        String dbUrl = Constants.DB_URL + dbFile.getName();
        connection = DriverManager.getConnection(dbUrl);
        try {
            Class.forName(Constants.DRIVER);
            connection.setAutoCommit(true);
            createDatabase(dbFile);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBController getInstance(String[] args) throws SQLException {
        if (instance == null) {
            instance = new DBController(args);
        }
        return instance;
    }

    public static DBController getInstance() {
        return instance;
    }

    public List<List<Object>> executeQuery(String query) {
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(query);
            return convertResultSetToObjectList(set);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void execute(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<List<Object>> convertResultSetToObjectList(ResultSet set) throws SQLException {
        List<List<Object>> list = new ArrayList<>();
        ResultSetMetaData metaData = set.getMetaData();
        int columns = metaData.getColumnCount();
        int counter = 0;
        while (set.next()) {
            list.add(new ArrayList<>());
            for (int i = 1; i <= columns; i++) {
                list.get(counter).add(set.getObject(i));
            }
            counter++;
        }
        return list;
    }

    private File getDBFile(String[] args) {
        File dbFile = null;
        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                if ("-databaseFileName".equals(args[i]) && args[i + 1] != null) {
                    dbFile = new File(Constants.PATH + args[i + 1]);
                    break;
                }
            }
        }

        if (dbFile == null) {
            dbFile = new File(Constants.PATH + "database.mv.db");
        }

        return dbFile;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createDatabase(File dbFile) throws IOException {
        new File(Constants.PATH).mkdirs();
        dbFile.createNewFile();

        newDBCreateQueries();
    }

    private void newDBCreateQueries() {
        try (Statement statement = connection.createStatement()) {
            Class.forName(Constants.DRIVER);
            connection.setAutoCommit(true);

            statement.execute(Constants.CREATE_COMPANY_TABLE);
            statement.execute(Constants.CREATE_CAR_TABLE);
            statement.execute(Constants.CREATE_CUSTOMER_TABLE);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void terminateConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            Console.writeLine("terminateConnection() SQLException caught");
            Console.writeLine(e.getMessage());
        }
    }
}

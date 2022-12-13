package com.login.hth.connection;

import com.ibm.as400.access.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class iSeries {
    private static final String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
    private static final String SERVER = "printers.hi-techhealth.com";
    private static final String URL = "jdbc:as400://printers.hi-techhealth.com";
    private static final String HOSTNAME = "JAVALOOK";
    private static final String PASSWORD = "TWMC1990";
    private static AS400 system = null;

    public static String[] executeSQLByAliasArray(String sql, String alias, String file) {
        String aliasSQL = "CREATE ALIAS " + alias + " FOR " + file;
        String[] result = null;
        Statement statement;
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, HOSTNAME, PASSWORD);
            statement = connection.createStatement();
            statement.execute(aliasSQL);

            if (sql.substring(0, 6).equalsIgnoreCase("SELECT")) {
                resultSet = statement.executeQuery(sql);
                resultSetMetaData = resultSet.getMetaData();

                while (resultSet.next()) {
                    result = new String[resultSetMetaData.getColumnCount()];
                    for (int idx = 0; idx < result.length; idx++) {
                        result[idx] = resultSet.getString(idx + 1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return result;
    }

    public static List<String[]> executeSQL(String sql) {
        List<String[]> resultList = new ArrayList<>();
        String[] result;
        Statement statement;
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, HOSTNAME, PASSWORD);
            statement = connection.createStatement();

            if (sql.substring(0, 6).equalsIgnoreCase("SELECT")) {
                resultSet = statement.executeQuery(sql);
                resultSetMetaData = resultSet.getMetaData();

                while (resultSet.next()) {
                    result = new String[resultSetMetaData.getColumnCount()];
                    for (int idx = 0; idx < result.length; idx++) {
                        result[idx] = resultSet.getString(idx + 1);
                    }
                    resultList.add(result);
                }
            } else {
                String rowCount = Integer.toString(statement.executeUpdate(sql));
                result = new String[] {rowCount};
                resultList.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return resultList;
    }

    public static List<String[]> executeSQLByAlias(String sql, String alias, String file) {
        String aliasSQL = "CREATE ALIAS " + alias + " FOR " + file;
        List<String[]> resultList = new ArrayList<>();
        String[] result;
        Statement statement;
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, HOSTNAME, PASSWORD);
            statement = connection.createStatement();
            System.out.println("::" + aliasSQL);
            statement.execute(aliasSQL);

            if (sql.substring(0, 6).equalsIgnoreCase("SELECT")) {
                resultSet = statement.executeQuery(sql);
                resultSetMetaData = resultSet.getMetaData();

                while (resultSet.next()) {
                    result = new String[resultSetMetaData.getColumnCount()];
                    for (int idx = 0; idx < result.length; idx++) {
                        result[idx] = resultSet.getString(idx + 1);
                    }
                    resultList.add(result);
                }
            } else {
                String rowCount = Integer.toString(statement.executeUpdate(sql));
                result = new String[]{rowCount};
                resultList.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return resultList;
    }

    public static void createAlias(String alias, String file) {
        String aliasSQL = "CREATE ALIAS " + alias + " FOR " + file;
        List<String[]> resultList = new ArrayList<>();
        String[] result;
        Statement statement;
        ResultSet resultSet;
        ResultSetMetaData resultSetMetaData;
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, HOSTNAME, PASSWORD);
            statement = connection.createStatement();
            statement.execute(aliasSQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }

    public static void executeCL(String clCommand) {
        try {
            if (system == null) {
                system = new AS400(SERVER, HOSTNAME, PASSWORD);
            }

            CommandCall command = new CommandCall(system);
            System.out.println(clCommand);
            command.run(clCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

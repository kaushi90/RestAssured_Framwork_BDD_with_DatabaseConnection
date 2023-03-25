package crm.framework.db.sql.executers;


import crm.framework.db.QueryResult;
import crm.framework.db.connections.OracleJDBCConnection;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class SQLExecuter {
    private static String dateFormat = "yyyy-MM-dd h:mm:ss";
    private static SimpleDateFormat sdf;

    public SQLExecuter() {
    }

    public static QueryResult getQueryResults(String Query, String newDateFormat) throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(newDateFormat);
        return executeQuery(Query, dateFormatter);
    }

    public static QueryResult getQueryResults(String Query, String newDateFormat, int dbInstance) throws Exception {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(newDateFormat);
        return executeQuery(Query, dateFormatter, dbInstance);
    }

    public static QueryResult getQueryResults(String Query) throws Exception {
        return executeQuery(Query, sdf);
    }

    public static QueryResult getQueryResults(String Query, int dbInstance) throws Exception {
        return executeQuery(Query, sdf, dbInstance);
    }

    private static QueryResult executeQuery(String Query, DateFormat dateFormatter) throws Exception {
        QueryResult queryResult = new QueryResult();

        try {
            Connection connection = OracleJDBCConnection.getConnection();
            Statement statement = null;

            try {
                statement = connection.createStatement();
                ResultSet resultSet = null;

                try {
                    resultSet = statement.executeQuery(Query);

                    while(resultSet.next()) {
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int columnCount = rsmd.getColumnCount();
                        Map<String, String> row = new HashMap();

                        for(int i = 1; i < columnCount + 1; ++i) {
                            String name = rsmd.getColumnName(i);
                            Object value = resultSet.getObject(name);
                            if (value == null) {
                                value = "";
                            }

                            if (value instanceof Date) {
                                java.util.Date date = resultSet.getTimestamp(name);
                                value = dateFormatter.format(date);
                            }

                            row.put(name, value.toString().trim().replace("  ", " "));
                        }

                        queryResult.enqueRow(row);
                    }
                } finally {
                    resultSet.close();
                }
            } finally {
                statement.close();
            }

            return queryResult;
        } catch (Exception var23) {
            System.err.println("SQL Connection error : " + var23.getMessage());
            throw var23;
        }
    }

    private static QueryResult executeQuery(String Query, DateFormat dateFormatter, int dbInstance) throws Exception {
        QueryResult queryResult = new QueryResult();

        try {
            Connection connection = OracleJDBCConnection.getConnection2();
            Statement statement = null;

            try {
                statement = connection.createStatement();
                ResultSet resultSet = null;

                try {
                    resultSet = statement.executeQuery(Query);

                    while(resultSet.next()) {
                        ResultSetMetaData rsmd = resultSet.getMetaData();
                        int columnCount = rsmd.getColumnCount();
                        Map<String, String> row = new HashMap();

                        for(int i = 1; i < columnCount + 1; ++i) {
                            String name = rsmd.getColumnName(i);
                            Object value = resultSet.getObject(name);
                            if (value == null) {
                                value = "";
                            }

                            if (value instanceof Date) {
                                java.util.Date date = resultSet.getTimestamp(name);
                                value = dateFormatter.format(date);
                            }

                            row.put(name, value.toString().trim().replace("  ", " "));
                        }

                        queryResult.enqueRow(row);
                    }
                } finally {
                    resultSet.close();
                }
            } finally {
                statement.close();
            }

            return queryResult;
        } catch (Exception var24) {
            System.err.println("SQL Connection error : " + var24.getMessage());
            throw var24;
        }
    }

    public static Integer executeUpdateQuery(String Query) throws Exception {
        Integer result = 0;

        try {
            Connection connection = OracleJDBCConnection.getConnection();
            Statement statement = null;

            try {
                statement = connection.createStatement();
                result = statement.executeUpdate(Query);
            } finally {
                statement.close();
            }

            return result;
        } catch (Exception var8) {
            System.err.println("SQL Connection error : " + var8.getMessage());
            throw var8;
        }
    }

    public static Integer executeUpdateQuery(String Query, int dbInstance) throws Exception {
        Integer result = 0;

        try {
            Connection connection = OracleJDBCConnection.getConnection2();
            Statement statement = null;

            try {
                statement = connection.createStatement();
                result = statement.executeUpdate(Query);
            } finally {
                statement.close();
            }

            return result;
        } catch (Exception var9) {
            System.err.println("SQL Connection error : " + var9.getMessage());
            throw var9;
        }
    }

    static {
        sdf = new SimpleDateFormat(dateFormat);
    }
}

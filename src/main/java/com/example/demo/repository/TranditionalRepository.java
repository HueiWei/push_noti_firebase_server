//package com.example.demo.repository;
//
////import com.example.demo.connection.OracleDb;
//import com.google.gson.JsonObject;
//import oracle.jdbc.OracleTypes;
//import oracle.sql.CLOB;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//import java.sql.*;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
////import static com.example.demo.connection.OracleDb.getConnection;
//
//@Repository
//public class TranditionalRepository {
//    public void update_user_by_marketing(String user, JsonObject data)
//            throws SQLException {
//        Connection connection = null;
//        CallableStatement callableStatement = null;
//
//        try {
//            connection = OracleDb.getConnection();
//
//            CLOB clobParam = CLOB.createTemporary(connection, false, CLOB.DURATION_SESSION);
//            clobParam.putChars(1, data.toString().toCharArray());
//
//            String sql = "{call DEMO.edit_marketingid(?, ?)}";
//            callableStatement = connection.prepareCall(sql);
//
//            callableStatement.setString(1, user);
//            callableStatement.setClob(2, clobParam);
//
//            callableStatement.executeUpdate();
//
//            clobParam.freeTemporary();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (callableStatement != null) {
//                    callableStatement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void get_log_update_user(String user, JsonObject data)
//            throws SQLException {
//        Connection connection = null;
//        CallableStatement callableStatement = null;
//
//        try {
//            connection = OracleDb.getConnection();
//
//            CLOB clobParam = CLOB.createTemporary(connection, false, CLOB.DURATION_SESSION);
//            clobParam.putChars(1, data.toString().toCharArray());
//
//            String sql = "{ DEMO.get_log_user(?, ?, ?, ?, ?) }";
//            callableStatement = connection.prepareCall(sql);
//
//            // Thiết lập tham số vào procedure
//            callableStatement.setString(1, user);
//            callableStatement.setClob(2, clobParam);
//            callableStatement.registerOutParameter(3, Types.INTEGER);    // po_int_result_code
//            callableStatement.registerOutParameter(4, Types.VARCHAR);    // po_str_result_msg
//            callableStatement.registerOutParameter(5, OracleTypes.CURSOR); // po_result (Oracle cursor)
//
//            callableStatement.executeUpdate();
//
//            int resultCode = callableStatement.getInt(3);
//            String resultMsg = callableStatement.getString(4);
//            ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
//
//            System.out.println("resultCode: " + resultCode);
//            System.out.println("resultMsg: " + resultMsg);
//            System.out.println("resultSet: " + resultSet);
//
//            clobParam.freeTemporary();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (callableStatement != null) {
//                    callableStatement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

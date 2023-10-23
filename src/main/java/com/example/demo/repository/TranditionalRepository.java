package com.example.demo.repository;

//import com.example.demo.connection.OracleDb;
import com.example.demo.connection.OracleDb;
import com.example.demo.model.BaseHeader;
import com.example.demo.model.Response;
import com.example.demo.model.entity.ParameterEntity;
import com.example.demo.model.entity.StoreAccountEntity;
import com.google.gson.JsonObject;
import oracle.jdbc.OracleTypes;
import oracle.sql.CLOB;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//import static com.example.demo.connection.OracleDb.getConnection;

@Repository
public class TranditionalRepository {
    public void update_user_by_marketing(String user, JsonObject data)
            throws SQLException {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = OracleDb.getConnection();

            CLOB clobParam = CLOB.createTemporary(connection, false, CLOB.DURATION_SESSION);
            clobParam.putChars(1, data.toString().toCharArray());

            String sql = "{call DEMO.edit_marketingid(?, ?)}";
            callableStatement = connection.prepareCall(sql);

            callableStatement.setString(1, user);
            callableStatement.setClob(2, clobParam);

            callableStatement.executeUpdate();

            clobParam.freeTemporary();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Response tranditional_service(String user, JsonObject data, String session, String cmd, String group)
            throws SQLException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Response response = null;
        List<StoreAccountEntity> storeAccountEntities = new ArrayList<>();
        List<ParameterEntity> parameterEntities = new ArrayList<>();

        try {
            connection = OracleDb.getConnection();
            CLOB clobParam = CLOB.createTemporary(connection, false, CLOB.DURATION_SESSION);
            clobParam.putChars(1, data.toString().toCharArray());

            BaseHeader baseHeader = new BaseHeader(user, "", "BACK", "VI", group);
            JsonObject jsonObject = baseHeader.toJsonObject();

            String sql = "{ call PKG_API.cur_query_process(?, ?, ?, ?, ?, ?, ?) }";
//            String sql = "{ call STORE_ACCOUNT.get_all(?, ?, ?, ?, ?) }";

            callableStatement = connection.prepareCall(sql);

            // Thiết lập tham số vào procedure
            callableStatement.setString(1, user);
            callableStatement.setString(2, cmd);
            callableStatement.setString(3, jsonObject.toString());
            callableStatement.setClob(4, clobParam);
            callableStatement.registerOutParameter(5, Types.INTEGER);    // po_int_result_code
            callableStatement.registerOutParameter(6, Types.VARCHAR);    // po_str_result_msg
            callableStatement.registerOutParameter(7, OracleTypes.CURSOR); // po_result (Oracle cursor)

            callableStatement.executeUpdate();

            Integer resultCode = callableStatement.getInt(5);
            String resultMsg = callableStatement.getString(6);
            ResultSet resultSet = (ResultSet) callableStatement.getObject(7);

            System.out.println("resultCode: " + resultCode);
            System.out.println("resultMsg: " + resultMsg);
            System.out.println("resultSet: " + resultSet);

            Integer size = resultSet.getFetchSize();
            if(cmd.equals("BACK.GET_ALL_STORE_ACCOUNT")) {
                while(resultSet.next()) {
                    StoreAccountEntity storeAccountEntity = new StoreAccountEntity();
                    storeAccountEntity.setRowNum(resultSet.getInt("ROW_NUM"));
                    storeAccountEntity.setPK_STORE_ACCOUNT(resultSet.getString("PK_STORE_ACCOUNT"));
                    storeAccountEntity.setC_ACCOUNT_CODE(resultSet.getString("C_ACCOUNT_CODE"));
                    storeAccountEntity.setC_ACCOUNT_FULL(resultSet.getString("C_ACCOUNT_FULL"));
                    storeAccountEntity.setC_ACCOUNT_NAME(resultSet.getString("C_ACCOUNT_NAME"));
                    storeAccountEntity.setC_ACCOUNT_TYPE(resultSet.getString("C_ACCOUNT_TYPE"));
                    storeAccountEntity.setC_STORE_CODE(resultSet.getString("C_STORE_CODE"));
                    storeAccountEntity.setC_DEPOSIT_CODE(resultSet.getString("C_DEPOSIT_CODE"));
//                    Date oracleOpenDate = resultSet.getDate("C_OPEN_DATE");
//                    storeAccountEntity.setC_OPEN_DATE(oracleOpenDate);
//                    Date oracleCloseDate = resultSet.getDate("C_CLOSE_DATE");
//                    storeAccountEntity.setC_CLOSE_DATE(new java.util.Date(oracleCloseDate.getTime()));
                    storeAccountEntity.setC_STATUS(resultSet.getString("C_STATUS"));
                    storeAccountEntity.setC_CONTENT(resultSet.getString("C_CONTENT"));
                    storeAccountEntity.setC_CREATOR_CODE(resultSet.getString("C_CREATOR_CODE"));
//                    Date oracleCreateTime = resultSet.getDate("C_CREATE_TIME");
//                    storeAccountEntity.setC_CREATE_TIME(new java.util.Date(oracleCreateTime.getTime()));
                    storeAccountEntity.setC_TOTAL_RECORD(resultSet.getInt("C_TOTAL_RECORD"));
                    storeAccountEntities.add(storeAccountEntity);
//                    System.out.println(storeAccountEntity.);
                }

                response = new Response(resultCode.toString(), resultMsg, "", storeAccountEntities);
            } else if(cmd.equals("BACK.GET_ALL_PARAMETER")) {
                while(resultSet.next()) {
                    ParameterEntity parameterEntity = new ParameterEntity();
                    parameterEntity.setPk_LIST_SYSTEM_PARAMETER(resultSet.getString("PK_LIST_SYSTEM_PARAMETER"));
                    parameterEntity.setC_SYSTEM_PARAMETER_CODE(resultSet.getString("C_SYSTEM_PARAMETER_CODE"));
                    parameterEntity.setC_SYSTEM_PARAMETER_NAME(resultSet.getString("C_SYSTEM_PARAMETER_NAME"));
                    parameterEntity.setC_SYSTEM_PARAMETER_VALUE(resultSet.getString("C_SYSTEM_PARAMETER_VALUE"));
                    parameterEntity.setC_STATUS(resultSet.getInt("C_STATUS"));
                    parameterEntity.setC_ORDER(resultSet.getInt("C_ORDER"));
                    parameterEntity.setC_NOTE(resultSet.getString("C_NOTE"));
                    parameterEntity.setC_TOTAL_RECORD(resultSet.getInt("C_TOTAL_RECORD"));

                    parameterEntities.add(parameterEntity);
                    response = new Response(resultCode.toString(), resultMsg, "", parameterEntities);
                }
            } else if(cmd.equals("BACK.GET_SINGLE_STORE_ACCOUNT")) {
                while(resultSet.next()) {
                    StoreAccountEntity storeAccountEntity = new StoreAccountEntity();
                    storeAccountEntity.setPK_STORE_ACCOUNT(resultSet.getString("PK_STORE_ACCOUNT"));
                    storeAccountEntity.setC_ACCOUNT_CODE(resultSet.getString("C_ACCOUNT_CODE"));
                    storeAccountEntity.setC_ACCOUNT_FULL(resultSet.getString("C_ACCOUNT_FULL"));
                    storeAccountEntity.setC_ACCOUNT_NAME(resultSet.getString("C_ACCOUNT_NAME"));
                    storeAccountEntity.setC_ACCOUNT_TYPE(resultSet.getString("C_ACCOUNT_TYPE"));
                    storeAccountEntity.setC_STORE_CODE(resultSet.getString("C_STORE_CODE"));
                    storeAccountEntity.setC_DEPOSIT_CODE(resultSet.getString("C_DEPOSIT_CODE"));
//                    Date oracleOpenDate = resultSet.getDate("C_OPEN_DATE");
//                    storeAccountEntity.setC_OPEN_DATE(oracleOpenDate);
//                    Date oracleCloseDate = resultSet.getDate("C_CLOSE_DATE");
//                    storeAccountEntity.setC_CLOSE_DATE(new java.util.Date(oracleCloseDate.getTime()));
                    storeAccountEntity.setC_STATUS(resultSet.getString("C_STATUS"));
                    storeAccountEntity.setC_CONTENT(resultSet.getString("C_CONTENT"));
                    storeAccountEntity.setC_CREATOR_CODE(resultSet.getString("C_CREATOR_CODE"));
//                    Date oracleCreateTime = resultSet.getDate("C_CREATE_TIME");
//                    storeAccountEntity.setC_CREATE_TIME(new java.util.Date(oracleCreateTime.getTime()));
                    storeAccountEntities.add(storeAccountEntity);
//                    System.out.println(storeAccountEntity.);
                }

                response = new Response(resultCode.toString(), resultMsg, "", storeAccountEntities);
            }else {
                response = new Response(resultCode.toString(), resultMsg, "", null);
            }

            clobParam.freeTemporary();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}

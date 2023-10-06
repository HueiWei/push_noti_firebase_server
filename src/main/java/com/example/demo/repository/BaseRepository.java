//package com.example.demo.repository;
//
//import com.example.demo.connection.OracleDb;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.sql.CallableStatement;
//import java.sql.Clob;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class BaseRepository {
//
//  @Autowired
//  OracleDb OracleDb;
//
//  protected Connection getConnection() throws SQLException {
//    return OracleDb.getConnection();
//  }
//
//  protected CallableStatement setInParamArgs(CallableStatement callableStatement, List<Object> args)
//      throws SQLException {
//    for (int i = 0; i < args.size(); i++) {
//      Object arg = args.get(i);
//
//      if (arg instanceof String) {
//        String value = (String) arg;
//        callableStatement.setString(i + 1, value);
//      } else if (arg instanceof Clob) {
//        Clob clob = (Clob) arg;
//        callableStatement.setClob(i + 1, clob);
//      }
//    }
//
//    return callableStatement;
//  }
//
//  protected List<Object> toInitParamsList(Object... params) {
//    List<Object> listParam = new ArrayList<>();
//
//    for (Object param : params) {
//      listParam.add(param);
//    }
//
//    return listParam;
//  }
//
//  protected Map<String, Integer> toOutParamsMap(List<?> initParams, String... outParams) {
//    return this.toOutParamsMap(initParams.size(), outParams);
//  }
//
//  private Map<String, Integer> toOutParamsMap(Integer initParamSize, String... outParams) {
//    Map<String, Integer> map = new HashMap<>();
//
//    for (int i = 0; i < outParams.length; i++) {
//      map.put(outParams[i], initParamSize + i + 1);
//    }
//
//    return map;
//  }
//}

package com.example.demo.connection;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.ValidConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class OracleDb {
  static PoolDataSource pds;

 @Bean
  public void start() throws Exception {
    initPoolConnectionUCP();
    checkInitConnection();
   System.out.println("Khoi tao thanh cong");
  }

  private void initPoolConnectionUCP() throws SQLException {
    pds = PoolDataSourceFactory.getPoolDataSource();

    pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
    pds.setURL("jdbc:oracle:thin:@(DESCRIPTION=(FAILOVER=yes)(LOAD_BALANCE=yes)(ADDRESS=(PROTOCOL=TCP)(HOST=103.170.123.129)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=orclcdb)))");
//    pds.setUser("back");
//    pds.setPassword("back");
      pds.setUser("tktong");
      pds.setPassword("tktong");
    pds.setConnectionPoolName("ORACLE_JDBC_UCP_POOL");

    pds.setInitialPoolSize(5);
    pds.setMinPoolSize(5);
    pds.setMaxPoolSize(20);
    pds.setTimeoutCheckInterval(5);
    pds.setInactiveConnectionTimeout(10);
  }

  private void checkInitConnection() throws SQLException {
    Connection connection = pds.getConnection();
    if (!((ValidConnection) connection).isValid()) {
      throw new SQLException("Connection is invalid");
    }
  }

  public static Connection getConnection() throws SQLException {
    if (pds != null) {
      return pds.getConnection();
    } else {
      throw new SQLException("Data Source is null");
    }
  }
}

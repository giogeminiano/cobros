package com.sms.walmart.cobros.consql;

import com.sms.walmart.cobros.utils.UtilsConstans;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ConnectionDBCobro {

    public Map<Integer,String> getCobroData(String fecha){
        Map<Integer,String> data = new HashMap<>();
        Connection conn = null;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(UtilsConstans.JDBC_COBROS_URL, UtilsConstans.JDBC_COBROS_USER, UtilsConstans.JDBC_COBROS_PASS);
            if (conn != null) {
                log.info("The connection has been successfully established.");
                DatabaseMetaData dm = conn.getMetaData();

                String selectQuery = "select B.clAfiltmk,B.IndCobro from \n" +
                        "dbo.TMKGC_ResumenCobroMS A inner join dbo.TMKGC_RespMS B on \n" +
                        "A.clResCobro = B.clResCobro\n" +
                        "where A.FechaGen >'2023-12-01' and FechaProc is not NULL";

                PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
                ResultSet resultSet = selectStatement.executeQuery();

                while (resultSet.next()) {
                    data.put(resultSet.getInt("clAfiltmk"),resultSet.getString("IndCobro"));
                }
                selectStatement.close();
            }
        }catch(Exception ex) {
            log.error("An error occurred while establishing the connection:");
            ex.printStackTrace();
        }finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return data;
    }
}
package com.sms.walmart.cobros.consql;

import com.sms.walmart.cobros.utils.UtilsConstans;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ConnectionDBSise {

    public List<AfiliadoWalmart> getSiseData(List<Integer> ids){
        List<AfiliadoWalmart> afiliados = new ArrayList<>();
        Connection conn = null;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            conn = DriverManager.getConnection(UtilsConstans.JDBC_SISE_URL, UtilsConstans.JDBC_SISE_USER, UtilsConstans.JDBC_SISE_PASS);
            if (conn != null) {
                log.info("The connection has been successfully established.");
                DatabaseMetaData dm = conn.getMetaData();

                var stmt = String.format("select Clave from dbo.cafiliadoMMS where clAfiltmk in(%s)",
                        ids.stream()
                                .map(v -> "?")
                                .collect(Collectors.joining(", ")));
                int index = 1;
                PreparedStatement selectStatement = conn.prepareStatement(stmt);
                for( Object o : ids ) {
                    selectStatement.setObject(  index++, o );
                }

                ResultSet resultSet = selectStatement.executeQuery();

                while (resultSet.next()) {
                    AfiliadoWalmart tmp =  new AfiliadoWalmart();
                    tmp.setPhone(resultSet.getString("Clave"));
                    afiliados.add(tmp);
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
        return afiliados;
    }
}
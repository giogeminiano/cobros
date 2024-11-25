package com.sms.walmart.cobros.consql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AfiliadoWalmart {

    private Integer afiliadoId;
    private Integer clafiltmk;
    private String finfecha;
    private String phone;
    private boolean isCurrent;
    /*private String cuenta;
    private String activo;
    private Timestamp fechaadd;
*/
}

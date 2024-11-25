package com.sms.walmart.cobros.utils;

public class UtilsConstans {

    public static final String REJECT_MESSAGE="Lo sentimos la activación de tu Programa Salud no fue exitosa, intenta el pago nuevamente en línea de cajas para hacer uso de tus beneficios.";

    public static final String ACTIVE_MESSAGE="Pagaste con exito tu Programa Salud. Ahora, preparate para ahorrar y disfrutar de increibles beneficios. Usalos hasta el @@ en: https://bit.ly/46RIC6G";

    public static final String JDBC_COBROS_URL="jdbc:sqlserver://172.21.10.88:1533;SelectMethod=cursor;DatabaseName=TELEMARKETING_DEV;useLOBs=false;applicationName=ApiCobros";

    public static final String JDBC_COBROS_USER="usrApiSMSCobro_Test";

    public static final String JDBC_COBROS_PASS="owIm3TqmJqbY4G37eHxwr-z_b";

    public static final String JDBC_SISE_URL="jdbc:sqlserver://172.21.10.185:21518;SelectMethod=cursor;DatabaseName=IKE_QA;useLOBs=false;applicationName=ApiAfiliados";

    public static final String JDBC_SISE_USER="ApiAfiliadoMx_QA";

    public static final String JDBC_SISE_PASS="XVCUb4OakfsHQAG";

    public static final String COBRO_MESSAGE_FAIL="Algo salio mal con el pago de tu programa Salud. Por favor, revisa tu metodo de pago o prueba con otra opcion para seguir disfrutando de tus beneficios.";
    public static final String COBRO_MESSAGE_SUCCESS="Tu pago fue exitoso. No olvides  disfrutar de todos tus beneficios que tiene tu Programa Salud. ¡Gracias por elegirnos!";

    public static final String URL_DIRECTO_BULK="https://smsrp.directo.com/rest/bulk_send_sms?message_label=IKEC";
    public static final String URL_DIRECTO_TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJhY2MiOiI3ODIiLCJjYXIiOiI3OTIiLCJpcCI6IjAuMC4wLjBcLzAiLCJsb2dpbiI6IklLRV9TQyIsImlwYWRkciI6IjE4Ny4xOTAuMTU0LjI1NSIsImV4cCI6IjE3NTU0OTExMzkifQ.TdLW0ld21X9MEZ_StZpqTdUZYG5dnTroDYLVTbdzI5Y";
}

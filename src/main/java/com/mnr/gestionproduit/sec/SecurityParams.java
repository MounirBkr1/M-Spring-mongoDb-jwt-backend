package com.mnr.gestionproduit.sec;

public interface SecurityParams {
    //definir des constantes
    public static final String JWT_HEADER_NAME="Authorization";
    public static final String SECRET = "miirbri1@gmail.com";
    //1000 ms * 60 =>(min) * 60 =>(h) * 24 => jr * 10 => (10 jrs)
    public static final int EXPIRATION = 1000*60*60*24*10;
    public static final String HEADER_PREFIX = "Bearer ";
}
package com.example.demo;

public class Constants {
    public final static int ACCOUNT_COUNT = 1000;
    public final static int ACCOUNT_COUNT_WITH_PROBLEM = 10;
    public final static int BATCH_FOR_FORK_JOIN=1000;
    public final static String ACCOUNT_ERROR_PREFIX = "ERROR_ACC_-";
    public final static String ACCOUNT_GENERATE_PREFIX = "GEN_ACC_-";
    public final static int ITERATIONS_FOR_SPEED_TEST = 1_000_000;
    public final static String PHONE_NUMBER = "1234";
    public static final String URL_PHONE_BY_GOOD_ACCOUNT = "/api/account/{accountNumber}/phone";
    public static final String URL_PHONE_BY_BAD_ACCOUNT = "/api/account/{accountNumber}/phone-with-problem";
    public static final String LOCAL_HOST = "http://localhost:8080";
    public static final String UKNOWN = "UKNOWN";
}

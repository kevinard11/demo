package com.yukiii.demo.constant;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class AppConstant {

    public static final String HEADER_SECURITY_API_SECRET = "demo-secret";
    public static final String HEADER_SECURITY_API_AUTHORIZATION = "Authorization";
    public static final String HEADER_SECURITY_API_AUTHORIZATION_STARTS = "Bearer ";
    public static final String HEADER_X_RETRIES_COUNT = "x-retries-count";
    public static final String HEADER_X_IDEMPOTENCE_KEY = "X-Idempotency-Key";
    public static final String HEADER_X_DL_EXCHANGE = "x-dead-letter-exchange";
    public static final String HEADER_X_DL_ROUTING_KEY = "x-dead-letter-routing_key";
    public static final String HEADER_X_QUEUE_MODE = "x-queue-mode";
    public static final String HEADER_X_MESSAGE_TTL = "x-message-ttl";

    public static final int PARAM_SORT_COLUMN_DIRECTION = 2;
    public static final int PARAM_SORT_COLUMN_ONLY = 1;
    public static final String PARAM_ROLE = "roles";
    public static final String PARAM_AUTH_BEARER = "bearer";
    public static final String PARAM_AUTH_JWT = "JWT";

    public static final String PREFIX_REDIS_DEMO = "demo";
    public static final String PREFIX_REDIS_DEMO_ID = "demo_id";
    public static final String PREFIX_MQ_QUEUE = "q.";
    public static final String PREFIX_MQ_EXCHANGE = "x.";
    public static final String PREFIX_AUTH_ROLE = "ROLE_";
    public static final String SUFFIX_MQ_WORK = ".work";
    public static final String SUFFIX_MQ_DL_QUEUE = ".wait";
    public static final String SUFFIX_MQ_SUCCESS = ".success";

    public static final long SECONDS_REPUBLISH_MESSAGE = 60;
    public static final long SECONDS_TTL_WORK_MESSAGE = 86100000;
    public static final long SECONDS_TTL_DL_MESSAGE = 60000;

    public static final Integer COUNT_MAX_RETRIES = 3;

    public final static Locale DATE_LOCALE = new Locale("in", "ID");
    public final static String DATE_DEFAULTDATE_PATTERN = "dd/MM/yyyy";
    public final static String DATE_DEFAULTDATETIME_PATTERN = "dd/MM/yyyy kk:mm";
    public final static String DATE_DEFAULTTIME_PATTERN  = "kk:mm";

    public enum ResponseConstant{
        SUCCESS("000","Success.", HttpStatus.OK),
        CREATE_SUCCESS("001", "Data created.", HttpStatus.CREATED),
        ACCESS_UNAUTHORIZED("100", "Unauthorized.", HttpStatus.UNAUTHORIZED),
        ACCESS_FORBIDDEN("101","Forbidden.", HttpStatus.FORBIDDEN),
        PARAM_INVALID("200", "Invalid param.", HttpStatus.BAD_REQUEST),
        PARAM_EMPTY("201", "Param can't empty", HttpStatus.BAD_REQUEST),
        PARAM_CONTAINWHITESPACE("202", "Param can;t contain white space.", HttpStatus.BAD_REQUEST),
        DATA_NOTFOUND("300", "Data not found.", HttpStatus.NOT_FOUND),
        DATA_PARSEBODYERROR("301", "Fail to convert data to object.", HttpStatus.INTERNAL_SERVER_ERROR),
        DATA_PUBLISHFAIL("302", "Fail to publish message.", HttpStatus.INTERNAL_SERVER_ERROR),
        DATA_ALREADYEXIST("303", "Data already exist", HttpStatus.BAD_REQUEST),
        DATA_NOTMATCH("304", "Data not match", HttpStatus.BAD_REQUEST),
        CLIENT_ERROR("400", "Fail to get data from client.", HttpStatus.INTERNAL_SERVER_ERROR),
        CLIENT_PARSEBODYERROR("400", "Fail to get body response from client.", HttpStatus.INTERNAL_SERVER_ERROR),
        ;

        String code;
        String message;
        HttpStatus status;

        ResponseConstant(String code, String message, HttpStatus status) {
            this.code = code;
            this.message = message;
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getStatus() {
            return status;
        }
    }

    public enum AuthenticationClaimer{
        USER,
        ADMIN,
        SYSTEM_SERVICE;

    }

    public enum QueueMode{
        LAZY("lazy");

        String mode;

        QueueMode(String mode) {
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }
    }
}

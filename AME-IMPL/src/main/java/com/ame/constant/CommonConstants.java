package com.ame.constant;


import com.ame.spring.BeanManager;

public interface CommonConstants {

    /**
     * build version
     */
    public static String BUILD_VERSION = BeanManager.getEnvironment().getProperty("meper.build.version", "5.6.1-SNAPSHOT");

    /**
     * build number
     */
    public static String BUILD_NUMBER = BeanManager.getEnvironment().getProperty("meper.build.number", "1");

    public static final String USER_SETTING_PATH = "Setting";
    public static final String ADMIN = "admin";
    public static final String ADMIN_ROLE = "管理员角色";

    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static final int INTERVAL = 10;

    public static final String SEQNENCE_TYPE_OTHERS = "others";

    public static final String MES_HOME = "MES-HOME";

    public static final String Jasper = "Jasper";

    public static final String SLASH = "/";
    public static final String DOT = ".";
    String SEPARATOR_UNDERLINE = "_";
    String SEPARATOR_SEMICOLON = ";";
    String SEPARATOR_HASHTAG = "#";
    String SEPARATOR_DOT = ".";
    String SEPARATOR_ASTERISK = "*";

    public static final String SYSTEM_EVENT_SEPARATOR = "->";

    public static final String MES = "MES";
    public static final String USER_DEFAULT_PASSWORD = "123456";

    public static final String COPYRIGHT = "2016-现在  苏州华冠科技有限公司 ";
    public static final String BEIAN_URL = "http://www.miibeian.gov.cn/";
    public static final String RESOURCES_PATH = "vaadin://resources//";

    public static final String REPORT = "Report";

    // activiti
    public static final String ASSIGNEE = "assignee";
    public static final String COMMENT = "comment";
    public static final String AUDIT_RESULT = "auditResult";
    public static final String AUDIT_DATE = "auditDate";
    public static final String ACTIVITY_NAME = "activityName";
    public static final String PROC_INST_ID = "procInstId";
    public static final String EXECUTION_ID = "executionId";
    public static final String TASK_ID = "taskId";
    public static final String CHECK_LICENCE_OK = "ok";
    public static final String WORKFLOW_BUSSINESS_ID = "WORKFLOW_BUSSINESS_ID";
    public static final String BACKGROUND_TASK_DESTINATION = "BACKGROUND_TASK_COUNTOFPROCESSANDNEW";
    public static final String SEPARATOR_COLON = ":";
    public static final String SEPARATOR_PERCENT_SIGN = "%";
    String SEPARATOR_LINE = "-";
    public static final String EXPRESSION_CONNECTOR = "+";
    public static final int OBJGRID_SPLIT_POSITION = 300;
    public static final String DATA_PERMISSION_CODE_SEQUENCE = "data_permission_code_serial";
    public static final String DATA_PERMISSION_PREDICATE = "dataPermissionPredicate";
    public static final String DATA_PERMISSION_MODE_MES = "MES";
    public static final String DATA_PERMISSION_MODE_OC = "OC";
    public static final String DATA_PERMISSION_MODE_OFF = "OFF";
    public static final String LOCAL_LANGUAGE_ZH = "zh";
    public static final String LOCAL_LANGUAGE_EN = "en";
    public static final String LOCAL_REGION_US = "US";
    public static final String LOCAL_REGION_CN = "CN";
    public static final String LOCAL_REGION_TW = "TW";
    public static final String LOCAL_ZH_CN_NAME = "中文简体";
    public static final String LOCAL_ZH_TW_NAME = "中文繁體";
    public static final String LOCAL_EN_US_NAME = "English";
    public static final long USER_PHOTO_BYTE_SIZE = 100 * 1024; // 100k
    public static final int USER_SESSION_TIMEOUT = 30; // 分钟
    public static final String USER_HISTORY_ID = "userHistoryId";
    String OPERATION_INSERT = "INSERT";
    String OPERATION_UPDATE = "UPDATE";
    String OPERATION_DELETE = "DELETE";

    public interface ErrorCode {
        public static final String PRODUCT_CONFIGURATION_BOMITEM_QUANTITY_EXCEED = "02001001";
    }

}

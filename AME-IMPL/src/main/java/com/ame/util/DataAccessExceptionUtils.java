package com.ame.util;


import com.ame.constant.CommonErrorCode;
import com.ame.core.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;

import java.sql.SQLException;

public class DataAccessExceptionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessExceptionUtils.class);

    public static <T> T processDataAccessException(DataAccessException e) {
        LOGGER.error("processDataAccessException", e);
        if (e.getCause() != null && e.getCause().getCause() instanceof SQLException) {
            SQLException exception = (SQLException) e.getCause().getCause();
            if ("23000".equals(exception.getSQLState())) {
                throw new PlatformException(CommonErrorCode.OBJECT_EXISTS_OR_BE_REFERENCED,
                        "Please check if the object exists or referenced by another object!");
            }
            if ("22001".equals(exception.getSQLState())) {                        //Mysql
                throw new PlatformException(CommonErrorCode.INPUT_FIELD_LENGTH_IS_TOO_LONG,
                        "Input field length is greater than table field length");
            }
            if ("72000".equals(exception.getSQLState())) {                        // Orcale
                throw new PlatformException(CommonErrorCode.INPUT_FIELD_LENGTH_IS_TOO_LONG,
                        "Input field length is greater than table field length");
            }
            if ("22002".equals(exception.getSQLState())) {
                throw new PlatformException(CommonErrorCode.NULL_VALUE_DETECTED,
                        "Null value detected or missing indicator parameter");
            }
        }
        if (e instanceof OptimisticLockingFailureException) {
            throw new PlatformException(CommonErrorCode.OBJECT_UPDATED_OR_DELETED_BY_ANOTHER,
                "The data was updated or deleted by another user");
        }
        LOGGER.error("", e);
        throw new PlatformException(CommonErrorCode.DATABASE_ACCESS_ERROR);
    }

}

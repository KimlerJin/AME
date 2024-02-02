package com.ame.constant;



public interface CommonErrorCode extends ErrorCodeConstants {

    /**
     * 未知错误
     */
    String UNKNOWN_ERROR = "common.error.unknow.error";
    /**
     * FTP服务器拒绝连接
     */
    String FTP_SERVER_REFUSED_CONNECTION = "common.error.ftp.server.refused.connection";
    /**
     * 无法登录FTP服务器
     */
    String FTP_SERVER_LOGIN_FAILURE = "common.error.ftp.server.login.failure";

    /**
     * FTP服务器
     */
    String FTP_SERVER_URL_INCORRECT = "common.error.ftp.server.url.incorrect";

    /**
     * Can not get the local ip/mac address
     */
    String GET_LOCAL_MAC_IP_ADDRESS_ERROR = "common.error.get.local.mac.ip.address";

    String HTTP_REQUEST_ERROR = "common.error.http.request.error";

    // 01 006 - Customized Table Definition
    String CTD_COLUMN_NOT_EMPTY = "common.ctd.column.not.empty";
    String CTD_COLUMN_EXIST = "common.ctd.column.exist";
    String CTD_TABLE_NOT_EMPTY_DISALLOW_MODIFY_EXIST_COLUMNS =
        "common.ctd.table.not.empty.disallow.modify.exit.columns";
    String CTD_TABLE_NOT_EMPTY = "common.ctd.table.not.empty";

    // Persistence
    String PERSISTENCE_NOT_GET_ENTITY_ID_IN_SQLTYPE = "persistence.not.get.entity";
    String PERSISTENCE_NOT_CREATE_TABLE = "persistence.not.create.table";
    String PERSISTENCE_NOT_IPDATE_TABLE = "persistence.not.update.table";

    String PERSISTENCE_NOT_DELETE_TABLE = "persistence.not.delete.table";
    String PERSISTENCE_NOT_CREATE_INDEX = "persistence.not.create.index";
    String PERSISTENCE_NOT_DELETE_INDEX = "persistence.not.delete.index";
    String PERSISTENCE_NOT_GET_DATABASE_METADATA = "persistence.not.get.database.metadata";
    String PERSISTENCE_NOT_CREATE_INDEX_WITH_SAME_COLUMN = "persistence.not.create.index.same.column";

    String DATABASE_ACCESS_ERROR = "common.data.error";
    String OBJECT_EXISTS_OR_BE_REFERENCED = "common.data.error.object.exists.or.be.referenced";
    String OBJECT_UPDATED_OR_DELETED_BY_ANOTHER = "common.data.error.object.updated.or.deleted.by.another.transaction";
    String OBJECT_REFERENCED_BY_OTHER = "Common.RelationShipCheck.Failed";
    String INPUT_FIELD_LENGTH_IS_TOO_LONG = "common.data.error.input.field.length.is.too.long";
    String NULL_VALUE_DETECTED = "common.data.error.detected.null.value";

    String INCORRENT_ARGUMENT_FORMAT = "common.incorrent.argument.format";
    String SERIAL_NUMBER_BEYOUND_RANGE = "common.serial.number.beyound.range";
    String SERIAL_NUMBER_CANOTGET_CUSTPARAMVAL = "common.serial.number.cannotget.custparamval";
    String FAIL_FORMAT_VALUE = "common.fail.format.value";

    // 00 002 - Relation Ship
    String RELATIONSHIP_ENTITY_NO_FK = "relation.ship.entity.no.fk";

    // 00 002 - Relation Ship
    String PRINTER_TEMPLATE_NOTFOUND = "printer.template.notfound";
    String PRINTER_TYPE_NOT_SUPPORTED = "printer.not.supported";

    String SEQUENCE_PARAMETER_NOTFOUND = "sequence.parameter.notfound";

    String LICENCE_ABOUT_TO_EXPIRE = "01001008";

    String EXTENSIONENTITY_SAVE_FAILURE = "customized.extensionentity.save.failure";

    String REQUIRED_NOT_EMPTY = "common.parameter.requiredfilednotempty";

    // sequence
    String EXECUTE_SQL_FAILURE = "admin.sequennce.executesql.failure";

    //UOM
    public static final String UOMCONVERSION_CHECK_FAILURE = "uom.conversion.check.failure";

    //device agent
    String INPUTSTREAM_CANNOT_BE_NULL = "device.inputstream.cannotbenull";
    String UNSUPPORTED_FILE_FORMAT = "device.unsupported.file.format";

    //BOM
    public static final String BOM_COPY_CAN_NOT_FIND_PART_OR_PARTBOM = "bom.copy.cannot.find.part.of.partbom";

    public static final String KEY_NOT_FOUND = "workorder.key.not.found";

}

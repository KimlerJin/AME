package com.ame.service;



import com.ame.entity.UserEntity;
import com.ame.pagination.PageInfo;
import com.ame.pagination.PageModel;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface IUserService extends IBaseEntityService<UserEntity> {

    String OPERATION_ASSIGN_ROLE = "ASSIGN_ROLE";
    String OPERATION_ASSIGN_PERMISSION = "ASSIGN_PERMISSION";
    String OPERATION_ASSIGN_SKILL = "ASSIGN_SKILL";

    void changePassword(String userName, String oldPassword, String newPassword);

    /**
     * 检查licence
     */

    Boolean checkPassword(String userName, String password);


    UserEntity getByName(String userName);

    UserEntity getbyDepartmentCodeAndJobPositionCode(String jobPositionCode, String departmentCode);


    List<UserEntity> listByFuzzyName(String name);


    void resetPassword(UserEntity userEntity);


    int getUserCount();

    void saveWithoutSynchronize(UserEntity entity);

    /**
     * 根据用户找shift id， 没有就找UserGroup的shift
     * @param userId
     * @return
     */

    UserEntity copyUser(UserEntity userEntity, UserEntity newUserEntity);

}

package com.ame.service;


import com.ame.constant.CommonConstants;
import com.ame.core.RequestInfo;
import com.ame.core.exception.PlatformException;
import com.ame.dao.UserDao;
import com.ame.entity.UserAuthEntity;
import com.ame.entity.UserEntity;
import com.ame.enums.AuthType;
import com.ame.enums.UserStatusType;
import com.ame.filter.EntityFilter;
import org.apache.commons.lang3.StringUtils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractBaseEntityService<UserEntity> implements IUserService {
    /**
     *
     */
    private static final long serialVersionUID = -3689693554794567421L;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;


    @Autowired
    private IUserAuthService userAuthHandler;

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();



    @Override
    @Transactional
    public void changePassword(String userName, String oldPassword, String newPassword) {
        UserAuthEntity userAuthEntity = userAuthHandler.getByAccessIdAndAuthType(userName, AuthType.SYSTEM);
        if (null == userAuthEntity) {
            throw new PlatformException(
                    "User does not exist or is deleted");
        }
        // 验证修改的密码和近5次是否相同
        List<String> passwordHistory = new ArrayList<>();
        List<String> saltHistory = new ArrayList<>();
        if (StringUtils.isNotBlank(userAuthEntity.getPasswordHistory())) {
            String[] arr = userAuthEntity.getPasswordHistory().split(CommonConstants.SEPARATOR_SEMICOLON);
            passwordHistory = Arrays.asList(arr);
        }
        if (StringUtils.isNotBlank(userAuthEntity.getPasswordHistory())) {
            String[] arr = userAuthEntity.getSaltHistory().split(CommonConstants.SEPARATOR_SEMICOLON);
            saltHistory = Arrays.asList(arr);
        }
        for (int i = 0; i < passwordHistory.size(); i++) {
            byte[] salt = Base64.getDecoder().decode(saltHistory.get(i));
            String newPasswordBase64 = new Sha256Hash(newPassword, ByteSource.Util.bytes(salt), 1024).toBase64();
            if (Objects.equals(passwordHistory.get(i), newPasswordBase64)) {
                throw new PlatformException(
                        "The modified password cannot be the same as nearly 5 times");
            }
        }
        // 验证修改的密码和当前密码是否相同
        if (Objects.equals(oldPassword, newPassword)) {
            throw new PlatformException("The modified password cannot be the same as current password");
        }

        String currentPassword = userAuthEntity.getPassword();
        byte[] decodeSalt = Base64.getDecoder().decode(userAuthEntity.getSalt());

        String hashedOldPasswordBase64 =
                new Sha256Hash(oldPassword, ByteSource.Util.bytes(decodeSalt), 1024).toBase64();
        if (Objects.equals(currentPassword, hashedOldPasswordBase64)) {
            ByteSource byteSource = randomNumberGenerator.nextBytes();
            String hashedNewPasswordBase64 = new Sha256Hash(newPassword, byteSource, 1024).toBase64();
            userAuthEntity
                    .setPasswordHistory(updatePasswordHistory(userAuthEntity.getPasswordHistory(), currentPassword));
            userAuthEntity
                    .setSaltHistory(updatePasswordHistory(userAuthEntity.getSaltHistory(), userAuthEntity.getSalt()));
            userAuthEntity.setPassword(hashedNewPasswordBase64);
            userAuthEntity.setSalt(Base64.getEncoder().encodeToString(byteSource.getBytes()));
            userAuthEntity.setPasswordStartUseDate(ZonedDateTime.now());
            userAuthHandler.save(userAuthEntity);
        } else {
            throw new PlatformException( "Original password is wrong.");
        }

    }


    @Override
    public Boolean checkPassword(String userName, String password) {
        UserAuthEntity userAuthEntity = userAuthHandler.getByAccessIdAndAuthType(userName, AuthType.SYSTEM);
        if (userAuthEntity == null) {
            return false;
        }
        String currentPassword = userAuthEntity.getPassword();
        byte[] decodeSalt = Base64.getDecoder().decode(userAuthEntity.getSalt());
        String hashedOldPasswordBase64 = new Sha256Hash(password, ByteSource.Util.bytes(decodeSalt), 1024).toBase64();
        return Objects.equals(currentPassword, hashedOldPasswordBase64);
    }



    @Override
    @Transactional
    public void deleteByIds(List<Long> objIds) {
        List<UserEntity> userEntities = new ArrayList<>();
        for (Long userId : objIds) {
            UserEntity userEntity = getById(userId);
            //admin不能删除
            if ("admin".equalsIgnoreCase(userEntity.getUserName())) {
                throw new PlatformException("Admin cannot be deleted!");
            }
            userEntities.add(userEntity);
            List<UserAuthEntity> byUserId = userAuthHandler.listByUserId(userId);
            userAuthHandler.deleteByIds(byUserId.stream().map(UserAuthEntity::getId).collect(Collectors.toList()));
        }
        super.deleteByIds(objIds);
    }

    @Override
    public UserEntity getByName(String userName) {
        EntityFilter filter = createFilter();
        filter.fieldEqualTo(UserEntity.NAME, userName).setCacheable(true);
        return getByFilter(filter);
    }

    @Override
    public UserEntity getbyDepartmentCodeAndJobPositionCode(String jobPositionCode, String departmentCode) {
        EntityFilter filter = createFilter();
        filter.fieldEqualTo(UserEntity.POSITION_CODE, jobPositionCode);
        filter.fieldEqualTo(UserEntity.DEPARTMENT_CODE, departmentCode);
        return getByFilter(filter);
    }


    @Override
    public List<UserEntity> listByFuzzyName(String name) {
        EntityFilter filter = createFilter();
        filter.fieldContains(UserEntity.NAME, name);
        return listByFilter(filter);
    }


    @Override
    @Transactional
    public void resetPassword(UserEntity userEntity) {
        resetPassword(userEntity, true);
    }

    private void resetPassword(UserEntity userEntity, boolean resetStatus) {
        ByteSource byteSource = randomNumberGenerator.nextBytes();
        String newSalt = Base64.getEncoder().encodeToString(byteSource.getBytes());
        String newPassword = new Sha256Hash(userEntity.getPassword(), byteSource, 1024).toBase64();
        UserAuthEntity userAuthEntity =
                userAuthHandler.getByAccessIdAndAuthType(userEntity.getUserName(), AuthType.SYSTEM);
        if (userAuthEntity == null) {
            // 兼容之前版本导入的用户数据
            userAuthEntity = new UserAuthEntity();
            userAuthEntity.setUserId(userEntity.getId());
            userAuthEntity.setAccessId(userEntity.getUserName());
            userAuthEntity.setAuthType(AuthType.SYSTEM.getName());
        } else {
            userAuthEntity.setPasswordHistory(
                    updatePasswordHistory(userAuthEntity.getPasswordHistory(), userAuthEntity.getPassword()));
            userAuthEntity
                    .setSaltHistory(updatePasswordHistory(userAuthEntity.getSaltHistory(), userAuthEntity.getSalt()));
        }
        userAuthEntity.setPassword(newPassword);
        userAuthEntity.setSalt(newSalt);
        userAuthEntity.setPasswordStartUseDate(ZonedDateTime.now());
        userAuthHandler.save(userAuthEntity);
        if (resetStatus) {
            userEntity.setPasswordInputFailedCount(0);
            userEntity.setStatus(UserStatusType.ACTIVE.getName());
        }
        super.save(userEntity);
    }

    @Override
    public void saveAll(List<UserEntity> entities) {
        entities.forEach(this::save);
    }

    @Override
    public void clearCacheManually() {

    }


    @Override
    protected UserDao getDao() {
        return userDao;
    }

    /***
     * 更新历史密码
     *
     * @param passwordHistory
     * @param password
     * @return
     */
    private String updatePasswordHistory(String passwordHistory, String password) {
        if (StringUtils.isBlank(passwordHistory)) {
            return password;
        } else {
            // 更新历史记录
            StringJoiner joiner = new StringJoiner(CommonConstants.SEPARATOR_SEMICOLON);
            String[] arrPwHistory = passwordHistory.split(CommonConstants.SEPARATOR_SEMICOLON);
            int index = arrPwHistory.length < 5 ? 0 : 1;
            for (; index < arrPwHistory.length; index++) {
                joiner.add(arrPwHistory[index]);
            }
            joiner.add(password);
            return joiner.toString();
        }
    }

    @Override
    public int getUserCount() {
        return userDao.getUserCount();
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity entity) {
        saveWithoutSynchronize(entity);
        // 通知CAS的逻辑，目前不再使用CAS
        // sendUserChangeMessage(false, entity);
        return entity;
    }

    @Override
    @Transactional
    public void saveWithoutSynchronize(UserEntity entity) {
        String password = entity.getPassword();
        if (entity.getId() > 0) {
            super.save(entity);
            if (StringUtils.isNotBlank(password)) {
                // 导入的用户密码覆盖原始密码
                resetPassword(entity, false);
            }
        } else {
            super.save(entity);
            // 新用户创建密码
            if (StringUtils.isNotBlank(entity.getPassword())) {
                ByteSource byteSource = randomNumberGenerator.nextBytes();
                String newSalt = Base64.getEncoder().encodeToString(byteSource.getBytes());
                String newPassword = null;
                if (password != null) {
                    newPassword = new Sha256Hash(password, byteSource, 1024).toBase64();
                }
                UserAuthEntity userAuthEntity = new UserAuthEntity();
                userAuthEntity.setUserId(entity.getId());
                userAuthEntity.setAccessId(entity.getUserName());
                userAuthEntity.setAuthType(AuthType.SYSTEM.getName());
                userAuthEntity.setPassword(newPassword);
                userAuthEntity.setSalt(newSalt);
                userAuthEntity.setPasswordStartUseDate(ZonedDateTime.now());
                userAuthHandler.save(userAuthEntity);
            }
        }
    }



    @Override
    @Transactional
    public UserEntity copyUser(UserEntity userEntity, UserEntity newUserInfo) {
        UserEntity newUserEntity = userEntity.clone();
        newUserEntity.setUserName(newUserInfo.getUserName());
        newUserEntity.setPassword(newUserInfo.getPassword());
        newUserEntity.setFirstName(newUserInfo.getFirstName());
        newUserEntity.setLastName(newUserInfo.getLastName());
        newUserEntity.setDescription(newUserInfo.getDescription());
        newUserEntity.setCellPhone(newUserInfo.getCellPhone());
        newUserEntity.setWorkPhone(newUserInfo.getWorkPhone());
        newUserEntity.setWechatAccount(newUserInfo.getWechatAccount());
        newUserEntity.setEmail(newUserInfo.getEmail());
        newUserEntity = save(newUserEntity);
        long newUserId = newUserEntity.getId();

        return newUserEntity;
    }

    @Override
    public void delete(UserEntity userEntity) {
        long userId = RequestInfo.current().getUserId();
        String userName = RequestInfo.current().getUserName();
        //admin不能删除
        if ("admin".equalsIgnoreCase(userName)) {
            throw new PlatformException("Admin cannot be deleted!");
        }
        super.delete(userEntity);
    }

    @Override
    public void delete(List<UserEntity> entities) {
        for (UserEntity userEntity : entities) {
            //admin不能删除
            if ("admin".equalsIgnoreCase(userEntity.getUserName())) {
                throw new PlatformException( "Admin cannot be deleted!");
            }
        }
        super.delete(entities);
    }

    @Override
    public void deleteById(long id) {
        //admin不能删除
        if (id == 1) {
            throw new PlatformException( "Admin cannot be deleted!");
        }
        super.deleteById(id);
    }

    @Override
    public void deleteByFilter(EntityFilter entityFilter) {
        //admin不能删除
        entityFilter.fieldNotEqualTo(UserEntity.NAME, "admin");
        super.deleteByFilter(entityFilter);
    }
}

package com.ame.rest;

import com.ame.SecurityConstants;
import com.ame.core.exception.PlatformException;
import com.ame.dto.*;
import com.ame.entity.UserEntity;
import com.ame.service.IUserService;
import com.ame.util.ExceptionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/rest/security")
public class LoginController extends BaseController {


    static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/logon", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<LoginResultDto> logon(@RequestBody LoginParam param,
                                              HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
        usernamePasswordToken.setPassword(param.getPassword().toCharArray());
        usernamePasswordToken.setUsername(param.getName());

        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            // log.warn("User Name or Password is Invalid!", e);
            PlatformException causeByClass = ExceptionUtils.getCauseByClass(e, PlatformException.class);
            if (causeByClass != null) {
                throw causeByClass;
            } else {
                throw new PlatformException("User Name or Password is Invalid!");
            }
        }

        UserEntity user = userService.getByName(param.getName());
        LoginResultDto loginResultDto =
                new LoginResultDto().setAccessToken((String) httpServletRequest.getAttribute(SecurityConstants.TOKEN_NAME))
                        .setUserId(user.getId()).setUserName(user.getUserName());
        return new RestResponse<>(RestResponseCode.OK, true, "login successful", loginResultDto);
    }

    @RequestMapping(value = "/getCurrentUserName", method = RequestMethod.GET)
    public RestResponse<CurrentUserDto> getCurrentUser() throws IOException {
        Subject subject = SecurityUtils.getSubject();
        CurrentUserDto currentUserDto = new CurrentUserDto();
        if (!subject.isAuthenticated()) {
            return new RestResponse<>(RestResponseCode.NOTFOUND, true, "login first", currentUserDto);
        } else {
            UserEntity userInfo = (UserEntity) SecurityUtils.getSubject().getPrincipal();
            currentUserDto.setName(userInfo.getUserName()).setEmployeeNo(currentUserDto.getName());
            return new RestResponse<>(RestResponseCode.OK, true, "get current user information successful",
                    currentUserDto);
        }
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}

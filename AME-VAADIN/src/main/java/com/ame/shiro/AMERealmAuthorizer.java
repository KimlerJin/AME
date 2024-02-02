package com.ame.shiro;


import com.ame.UserInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;


public class AMERealmAuthorizer extends AuthorizingRealm {

//    @Autowired
//    @Lazy
//    private IPermissionService permissionService;
//
//    @Autowired
//    @Lazy
//    private IRoleService roleService;

    // @Autowired
    // private IDataPermissionService dataPermissionService;
    //
    // @Autowired
    // private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserInfo userPrincipal = (UserInfo)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        if (userPrincipal.getUsername().equals("admin")) {
//            simpleAuthorizationInfo.addRole("admin");
//            List<String> permissions = permissionService.listNamesByUserId(userPrincipal.getUserId());
//            Set<String> permissionSet = new HashSet<>(permissions);
//            if (permissionSet.size() > 0) {
//                simpleAuthorizationInfo.setStringPermissions(permissionSet);
//            }
//        } else {
//            // 查询用户所有角色
//            List<String> roles = roleService.listRoleNamesByUserId(userPrincipal.getUserId());
//            Set<String> roleSet = new HashSet<>(roles);
//            if (roleSet.size() > 0) {
//                simpleAuthorizationInfo.setRoles(roleSet);
//            }
//            // 查询用户所有权限
//            List<String> permissions = permissionService.listNamesByUserId(userPrincipal.getUserId());
//            Set<String> permissionSet = new HashSet<>(permissions);
//            if (permissionSet.size() > 0) {
//                simpleAuthorizationInfo.setStringPermissions(permissionSet);
//            }
//        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return false;
    }
}

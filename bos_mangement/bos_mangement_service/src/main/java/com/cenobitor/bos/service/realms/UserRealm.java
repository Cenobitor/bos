package com.cenobitor.bos.service.realms;

import com.cenobitor.bos.dao.system.PermissionRepository;
import com.cenobitor.bos.dao.system.RoleRepository;
import com.cenobitor.bos.dao.system.UserRepository;
import com.cenobitor.bos.domain.system.Permission;
import com.cenobitor.bos.domain.system.Role;
import com.cenobitor.bos.domain.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Cenobitor
 * @Description:
 * @Date: Created in 7:50 PM 26/03/2018
 * @Modified By:
 */
@Component
public class UserRealm extends AuthorizingRealm{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    //授权的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据当前用户的用户名去查询对应的权限和角色
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if ("admin".equals(user.getUsername())){
            List<Role> list = roleRepository.findAll();
            for (Role role : list) {
                info.addRole(role.getKeyword());
            }

            List<Permission> permissions = permissionRepository.findAll();
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
        }else {
           List<Role> roles =roleRepository.findbyUid(user.getId());
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            List<Permission> permissions = permissionRepository.findbyUid(user.getId());
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
        }

        return info;
    }
    //认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        //根据用户名查找用户
        User user = userRepository.findByUsername(username);
        if (user != null){

    /*@param principal   当事人,主体,通常是从数据库中查询到的用户
     * @param credentials 凭证,密码,是从数据库中查询出来的密码
     * @param realmName   the realm from where the principal and credentials were acquired.*/
            //找到 -> 对比密码
            AuthenticationInfo info =  new SimpleAuthenticationInfo(user,user.getPassword(),getName());
            //      比对成功-> 执行后续的逻辑
            //      比对失败-> 抛异常
            return info;
        }
        //找不到 -> 抛异常
        return null;
    }
}

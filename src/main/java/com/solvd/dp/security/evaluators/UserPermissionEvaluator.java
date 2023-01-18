package com.solvd.dp.security.evaluators;

import com.solvd.dp.domain.user.Role;
import com.solvd.dp.security.JwtUser;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;

public class UserPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        Long targetId = (Long) targetDomainObject;
        JwtUser userDetails = (JwtUser) authentication.getPrincipal();
        Long id = userDetails.getId();

        return targetId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        //not implemented
        return false;
    }

    private boolean hasAnyRole(Authentication authentication, Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if(authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }
}

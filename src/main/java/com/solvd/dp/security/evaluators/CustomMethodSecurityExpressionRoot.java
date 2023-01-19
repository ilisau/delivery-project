package com.solvd.dp.security.evaluators;

import com.solvd.dp.domain.user.Role;
import com.solvd.dp.security.JwtEntity;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private HttpServletRequest request;

    private UserService userService;
    private AddressService addressService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean canAccessUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        JwtEntity userDetails = (JwtEntity) authentication.getPrincipal();
        Long id = userDetails.getId();

        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(Authentication authentication, Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAccessAddress(Long addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        JwtEntity userDetails = (JwtEntity) authentication.getPrincipal();
        Long userId = userDetails.getId();

        return addressService.isUserOwner(addressId, userId) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    public boolean canAccessOrder(Long orderId) {
        return true;
    }

    public boolean canAccessCouriers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        return hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    public boolean canAccessCourier(Long courierId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        JwtEntity userDetails = (JwtEntity) authentication.getPrincipal();
        Long id = userDetails.getId();

        return courierId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }
}

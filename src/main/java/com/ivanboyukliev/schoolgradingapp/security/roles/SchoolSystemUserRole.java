package com.ivanboyukliev.schoolgradingapp.security.roles;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.ivanboyukliev.schoolgradingapp.security.roles.SchoolSystemPermission.*;

public enum SchoolSystemUserRole {
    USER(Sets.newHashSet(AVERAGE_MARK_READ,MARK_READ)),
    ADMIN(Sets.newHashSet(
            MARK_READ, MARK_WRITE,
            STUDENT_READ, STUDENT_WRITE,
            COURSE_READ, COURSE_WRITE,
            AVERAGE_MARK_READ
    ));

    private final Set<SchoolSystemPermission> permissions;

    SchoolSystemUserRole(Set<SchoolSystemPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SchoolSystemPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}

package com.ninjaTurtles.champtheatre.bean;

import com.ninjaTurtles.champtheatre.models.EmployeeRole;
import com.ninjaTurtles.champtheatre.models.RoleModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class RoleBean {
    private Long id;
    private String role;
    private String description;
    private Set<EmployeeRole> employee;
    private Set<ModuleBean> module;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @CreationTimestamp
    private LocalDateTime updateOn;

    public RoleBean(){

    }
    public static RoleBean create(String roleName, String roleDescription) {
        RoleBean role = new RoleBean();
        role.setRole(roleName);
        role.setDescription(roleDescription);
        return role;
    }

}

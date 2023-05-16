package com.ninjaTurtles.champtheatre.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.ninjaTurtles.champtheatre.service.RoleManagementService;
import lombok.*;

@AllArgsConstructor
@Entity
@Data
@Table(name = RoleModule.TABLE_NAME)
public class RoleModule {
    protected static final String TABLE_NAME = "role_module";

    public RoleModule() {

    }

    @EmbeddedId
    private RoleModuleId id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;
    
    @ManyToOne
    @MapsId("moduleId")
    @JoinColumn(name = "module_id")
    private Module module;


}

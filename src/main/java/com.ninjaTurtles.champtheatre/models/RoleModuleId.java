package com.ninjaTurtles.champtheatre.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RoleModuleId implements Serializable {
    private static final long serialVersionUID = 2360992123657946697L;

    
    @Column(name = "role_id")
    private Long roleId;
    
    @Column(name = "module_id")
    private Long moduleId;
   
}

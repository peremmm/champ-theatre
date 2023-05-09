package com.ninjaTurtles.champtheatre.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "ROLES")
public class Role implements Serializable{
    private static final long serialVersionUID = -4330760658341102884L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_role_sequence")
    @SequenceGenerator(sequenceName = "custom_role_sequence", name = "custom_role_sequence", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String role;

    @OneToMany(mappedBy = "role")
    private Set<EmployeeRole> employee = new HashSet<>();
    
    @OneToMany(mappedBy = "role")
    private Set<RoleModule> module = new HashSet<>();

}
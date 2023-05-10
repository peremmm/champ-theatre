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
@Table(name = "MODULES")
public class Module implements Serializable{
    private static final long serialVersionUID = 617222588677070861L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_module_sequence")
    @SequenceGenerator(sequenceName = "custom_module_sequence", name = "custom_module_sequence", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String module;

    @OneToMany(mappedBy = "module")
    private Set<RoleModule> role = new HashSet<>();

}
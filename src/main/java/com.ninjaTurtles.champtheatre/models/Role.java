package com.ninjaTurtles.champtheatre.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "ROLES")
public class Role implements Serializable {
    private static final long serialVersionUID = -4330760658341102884L;

    public Role() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_role_sequence")
    @SequenceGenerator(sequenceName = "custom_role_sequence", name = "custom_role_sequence", allocationSize = 1)
    private Long id;

    @Column(columnDefinition = "VARCHAR(75)", nullable = false)
    private String role;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<EmployeeRole> employee = new HashSet<>();

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<RoleModule> module = new HashSet<>();

    @Transient // This annotation indicates that the field is not persistent
    private String description; // Non-persistent field for description
}

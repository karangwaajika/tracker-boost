package com.lab.trackerboost.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include         // hashCode/equals use ID only
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "skills")
    private Set<DeveloperEntity> developers = new HashSet<>(); // no duplicate
}

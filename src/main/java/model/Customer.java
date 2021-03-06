package model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "projects")
@ToString(exclude = "projects")
@Entity
@Table(name = "customers")
public class Customer implements BaseEntity<Long>{

    private static final long serialVersionUID = -491444963840943957L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "head_office")
    private String headOffice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "customers_projects",
            joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projects;

}

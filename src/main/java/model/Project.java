package model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"developers", "customers", "companies"})
@ToString(exclude = {"developers", "customers", "companies"})
@Entity
@Table(name = "projects")
public class Project implements BaseEntity<Long> {

    private static final long serialVersionUID = -3110977488413061254L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "base_technology")
    private String baseTechnology;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "cost")
    private Double cost;

    @ManyToMany(mappedBy = "projects")
    private Set<Company> companies;

    @ManyToMany(mappedBy = "projects")
    private Set<Customer> customers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "developers_projects", joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "developer_id")})
    private Set<Developer> developers;

}
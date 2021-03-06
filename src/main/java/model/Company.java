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
@Table(name = "companies")
public class Company implements BaseEntity<Long> {

    private static final long serialVersionUID = 6830094129952320061L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "head_office")
    private String headOffice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "companies_projects",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})

    private Set<Project> projects;
}

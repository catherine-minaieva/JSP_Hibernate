package model;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"developers","companies"})
@ToString(exclude = {"developers","companies"})
@Entity(name = "skills")
public class Skill implements BaseEntity<Long> {

    private static final long serialVersionUID = 8034251749519518623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "language")
    private String language;

    @Column(name = "level")
    private String level;

    @ManyToMany(mappedBy = "skills")
    private Set<Developer> developers;

    @ManyToMany(mappedBy = "skills")
    private Set<Developer> companies;

}

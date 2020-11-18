package guru.sfg.brewery.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles = new HashSet<>();

}

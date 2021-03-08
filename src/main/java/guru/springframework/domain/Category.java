package guru.springframework.domain;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToMany(mappedBy = "categories")
    Set<Recipe> recipes;


    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }
}

package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unitName;

    public UnitOfMeasure() {

    }

    public Long getId() {
        return id;
    }

    public String getUnitName() {
        return unitName;
    }
}

package daniel.varga.petclinic.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    //Hibernate prefers using boxed types (Long) as opposed to primitives (long), because boxed types can be null.
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

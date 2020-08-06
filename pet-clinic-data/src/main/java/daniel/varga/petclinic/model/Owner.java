package daniel.varga.petclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    /* The @Builder annotation needs to be placed on a specific constructor in order to work, if there is more than one
    constructor available. */
    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String phone, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.phone = phone;

        if (pets != null) {
            this.pets = pets;
        }
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param aName to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String aName) {
        return getPet(aName, false);
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param aName to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String aName, boolean ignoreNew) {
        aName = aName.toLowerCase();
        for (Pet pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(aName)) {
                    return pet;
                }
            }
        }
        return null;
    }
}

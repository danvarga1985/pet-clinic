package daniel.varga.petclinic.services.map;

import daniel.varga.petclinic.model.Owner;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Data
class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;
    private final Long ownerId = 1L;
    private final String lastName = "Silvestri";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());

        Owner owner1 = new Owner();
        owner1.setId(ownerId);
        owner1.setLastName(lastName);
        ownerServiceMap.save(owner1);
    }

    @Test
    public void findAll() {
        Set<Owner> owners = ownerServiceMap.findAll();

        assertEquals(1, owners.size());
    }

    @Test
    public void findById() {
        Owner owner = ownerServiceMap.findById(ownerId);

        assertEquals(ownerId, owner.getId());
    }

    @Test
    public void saveExistingId() {
        Long id = 2L;

        Owner owner = new Owner();
        owner.setId(id);

        Owner savedOwner = ownerServiceMap.save(owner);

        assertEquals(id, savedOwner.getId());
    }

    @Test
    public void saveNoId() {
        Owner savedOwner = ownerServiceMap.save(new Owner());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    public void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(ownerId));

        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    public void deleteById() {
        ownerServiceMap.deleteById(ownerServiceMap.findById(ownerId).getId());

        assertNull(ownerServiceMap.findById(ownerId));
    }

    @Test
    public void findByLastName() {
        Owner sil = ownerServiceMap.findByLastName(lastName);

        assertNotNull(sil);
    }
}
package daniel.varga.petclinic.services.map;

import daniel.varga.petclinic.model.Owner;
import daniel.varga.petclinic.model.Pet;
import daniel.varga.petclinic.services.OwnerService;
import daniel.varga.petclinic.services.PetService;
import daniel.varga.petclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;

    private final PetService petService;

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        //.this would recurse indefinitely -> super is used
        return super.findAll();
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner save(Owner object) {
        if (object != null) {
            //If Owner has pets
            if (!object.getPets().isEmpty()) {
                object.getPets().forEach(pet -> {
                    //If Pet has PetType
                    if (pet.getPetType().getId() != null) {
                        pet.setPetType(petTypeService.save(pet.getPetType()));
                    } //If Pet has no PetType
                    else {
                        throw new RuntimeException("Pet type is required!");
                    }
                    //If Pet has no Id -> assign one to it
                    if (pet.getId() == null) {
                        Pet savedPet = petService.save(pet);
                        pet.setId(savedPet.getId());
                    }
                });
            }
            //If Owner has no pets --> save Owner
            return super.save(object);
        } //If there is no Owner --> return null
        else {
            return null;
        }
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}

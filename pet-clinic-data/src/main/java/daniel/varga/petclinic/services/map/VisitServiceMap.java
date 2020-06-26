package daniel.varga.petclinic.services.map;

import daniel.varga.petclinic.model.Visit;
import daniel.varga.petclinic.services.PetService;
import daniel.varga.petclinic.services.VisitService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService{

    private final VisitService visitService;

    private final PetService petService;

    public VisitServiceMap(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Visit save(Visit object) {

        if (object.getPet() == null || object.getPet().getOwner() == null || object.getPet().getId() == null
            || object.getPet().getOwner().getId() == null) {
            throw new RuntimeException("Invalid Visit!");
        }

        return super.save(object);
    }

    @Override
    public void delete(Visit object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}

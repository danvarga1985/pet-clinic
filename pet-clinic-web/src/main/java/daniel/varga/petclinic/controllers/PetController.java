package daniel.varga.petclinic.controllers;

import daniel.varga.petclinic.model.Owner;
import daniel.varga.petclinic.model.PetType;
import daniel.varga.petclinic.services.OwnerService;
import daniel.varga.petclinic.services.PetService;
import daniel.varga.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    public static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/crateOrUpdatePetForm";
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long aOwnerId) {
        return ownerService.findById(aOwnerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }


}

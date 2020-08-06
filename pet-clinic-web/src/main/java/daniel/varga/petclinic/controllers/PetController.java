package daniel.varga.petclinic.controllers;

import daniel.varga.petclinic.model.Owner;
import daniel.varga.petclinic.model.Pet;
import daniel.varga.petclinic.model.PetType;
import daniel.varga.petclinic.services.OwnerService;
import daniel.varga.petclinic.services.PetService;
import daniel.varga.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    public static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
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
    public void initOwnerBinder(WebDataBinder aDataBinder) {
        aDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner aOwner, Model aModel) {
        Pet pet = new Pet();
        aOwner.getPets().add(pet);
        pet.setOwner(aOwner);
        aModel.addAttribute("pet", pet);

        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner aOwner, @Validated Pet aPet, BindingResult aResult, ModelMap aModel) {
        if (StringUtils.hasLength(aPet.getName()) && aPet.isNew() && aOwner.getPet(aPet.getName(), true) != null) {
            aResult.rejectValue("name", "duplicate", "already exists");
        }

        aOwner.getPets().add(aPet);

        if (aResult.hasErrors()) {
            aModel.put("pet", aPet);

            return  VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petService.save(aPet);

            return "redirect:/owners/" + aOwner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Validated Pet pet, BindingResult result, Owner owner, Model model) {
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            owner.getPets().add(pet);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }


}

package daniel.varga.petclinic.controllers;

import daniel.varga.petclinic.model.Pet;
import daniel.varga.petclinic.model.Visit;
import daniel.varga.petclinic.services.PetService;
import daniel.varga.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService aVisitService, PetService aPetService) {
        this.visitService = aVisitService;
        this.petService = aPetService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder aDataBinder) {
        aDataBinder.setDisallowedFields("id");

        aDataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }

    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param aPetId
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long aPetId, Map<String, Object> aModel) {
        Pet pet = petService.findById(aPetId);
        aModel.put("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") Long aPetId, Map<String, Object> aModel) {
        return "pets/createOrUpdateVisitForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Validated Visit aVisit, BindingResult aResult) {
        if (aResult.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            visitService.save(aVisit);

            return "redirect:/owners/{ownerId}";
        }
    }
}

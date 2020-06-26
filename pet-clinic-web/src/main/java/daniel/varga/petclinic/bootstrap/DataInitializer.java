package daniel.varga.petclinic.bootstrap;

import daniel.varga.petclinic.model.*;
import daniel.varga.petclinic.services.OwnerService;
import daniel.varga.petclinic.services.PetTypeService;
import daniel.varga.petclinic.services.SpecialityService;
import daniel.varga.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final VetService vetService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataInitializer(VetService vetService, OwnerService ownerService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.vetService = vetService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count== 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);


        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Terry");
        owner1.setLastName("Davis");
        owner1.setAddress("132 Mulberry");
        owner1.setCity("Mobile");
        owner1.setPhone("3242342");

        Pet terrysDog = new Pet();
        terrysDog.setPetType(savedDogPetType);
        terrysDog.setOwner(owner1);
        terrysDog.setName("Preacher");
        terrysDog.setBirthDate(LocalDate.now());
        owner1.getPets().add(terrysDog);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Sylvester");
        owner2.setLastName("Matuska");
        owner2.setAddress("14 Kossuth");
        owner2.setCity("Kisoroszi");
        owner2.setPhone("32132154");

        Pet sylvestersCat = new Pet();
        sylvestersCat.setPetType(savedCatPetType);
        sylvestersCat.setOwner(owner2);
        sylvestersCat.setName("Anfang");
        sylvestersCat.setBirthDate(LocalDate.now());
        owner2.getPets().add(sylvestersCat);

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Francis");
        vet1.setLastName("Black");
        vet1.getSpecialities().add(savedDentistry);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Malcolm");
        vet2.setLastName("X");
        vet2.getSpecialities().add(savedSurgery);
        vet2.getSpecialities().add(savedRadiology);

        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}

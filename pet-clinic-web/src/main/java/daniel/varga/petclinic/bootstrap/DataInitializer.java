package daniel.varga.petclinic.bootstrap;

import daniel.varga.petclinic.model.Owner;
import daniel.varga.petclinic.model.Vet;
import daniel.varga.petclinic.services.OwnerService;
import daniel.varga.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final VetService vetService;
    private final OwnerService ownerService;

    public DataInitializer(VetService vetService, OwnerService ownerService) {
        this.vetService = vetService;
        this.ownerService = ownerService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Terry");
        owner1.setLastName("Davis");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Sylvester");
        owner2.setLastName("Matuska");

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Francis");
        vet1.setLastName("Black");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Malcolm");
        vet2.setLastName("X");

        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}

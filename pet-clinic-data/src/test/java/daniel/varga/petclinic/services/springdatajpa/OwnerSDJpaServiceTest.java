package daniel.varga.petclinic.services.springdatajpa;

import daniel.varga.petclinic.model.Owner;
import daniel.varga.petclinic.repositories.OwnerRepository;
import daniel.varga.petclinic.repositories.PetRepository;
import daniel.varga.petclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private OwnerSDJpaService ownerSDJpaService;
    private String lastName = "Silvestri";
    private Long id = 1L;
    private Owner persistedOwner;

    @BeforeEach
    void setUp() {
        persistedOwner = Owner.builder().id(id).lastName(lastName).build();
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(persistedOwner);

        Owner sil = ownerSDJpaService.findByLastName(lastName);

        assertNotNull(sil);
        assertEquals(lastName, sil.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> returnedOwnerSet = new HashSet<>();
        returnedOwnerSet.add(Owner.builder().id(2L).build());
        returnedOwnerSet.add(Owner.builder().id(3L).build());

        when(ownerRepository.findAll()).thenReturn(returnedOwnerSet);

        Set<Owner> owners = ownerSDJpaService.findAll();

        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(persistedOwner));

        Owner owner = ownerSDJpaService.findById(id);

        assertNotNull(owner);
        assertEquals(id, owner.getId());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = ownerSDJpaService.findById(1L);

        assertNull(owner);

    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(2L).lastName(lastName).build();

        when(ownerRepository.save(any())).thenReturn(ownerToSave);

        ownerSDJpaService.save(ownerToSave);

        assertNotNull(ownerToSave);

    }

    @Test
    void delete() {
        ownerSDJpaService.delete(persistedOwner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(persistedOwner.getId());

        verify(ownerRepository).deleteById(any());


    }
}
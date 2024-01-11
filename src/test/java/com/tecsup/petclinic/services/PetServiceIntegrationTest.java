package com.tecsup.petclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.services.PetService;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.exception.PetNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PetServiceIntegrationTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetService petService;

    @Test
    public void testCreatePet() {
        // Crear una mascota
        Pet pet = new Pet("Fluffy", 1, 1);
        Pet savedPet = petService.create(pet);

        // Verificar que la mascota se haya creado correctamente
        assertNotNull(savedPet.getId());
        assertEquals("Fluffy", savedPet.getName());
    }

    @Test
    public void testUpdatePet() throws PetNotFoundException {
        // Crear una mascota
        Pet pet = new Pet("Rambo", 1, 1);
        Pet savedPet = petService.create(pet);

        // Modificar la información de la mascota creada
        savedPet.setName("Serpentino");

        // Actualizar la mascota a través del servicio
        Pet updatedPet = petService.update(savedPet);

        // Verificar que la mascota se haya actualizado correctamente
        assertNotNull(updatedPet);
        assertEquals(savedPet.getId(), updatedPet.getId());
        assertEquals("Serpentino", updatedPet.getName());
        assertEquals(savedPet.getOwnerId(), updatedPet.getOwnerId());
        assertEquals(savedPet.getTypeId(), updatedPet.getTypeId());
    }

}

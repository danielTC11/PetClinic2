package com.tecsup.petclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.repositories.PetRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Test
    public void testCreatePet() {
        // Crear una mascota
        Pet pet = new Pet("Fluffy", 1, 1);
        Pet savedPet = petRepository.save(pet);

        // Verificar que la mascota se haya guardado correctamente
        assertNotNull(savedPet.getId());
        assertEquals("Fluffy", savedPet.getName());
    }

    @Test
    public void testUpdatePet() {
        // Crear una mascota
        Pet pet = new Pet("Rambo", 1, 1);
        Pet savedPet = petRepository.save(pet);

        // Modificar la información de la mascota creada en la primera prueba
        savedPet.setName("Serpentino");

        // Actualizar la mascota en la base de datos
        Pet updatedPet = petRepository.save(savedPet);

        // Verificar que la mascota se haya actualizado correctamente
        assertNotNull(updatedPet);
        assertEquals(savedPet.getId(), updatedPet.getId());
        assertEquals("Serpentino", updatedPet.getName());
        assertEquals(savedPet.getOwnerId(), updatedPet.getOwnerId());
        assertEquals(savedPet.getTypeId(), updatedPet.getTypeId());
    }

    // Otras pruebas de capa de acceso a datos según sea necesario
}

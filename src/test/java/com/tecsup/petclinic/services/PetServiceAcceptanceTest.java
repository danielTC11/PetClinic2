package com.tecsup.petclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.services.PetService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PetServiceAcceptanceTest {

    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @Test
    public void testAddNewPet() {
        // Given
        Pet newPet = new Pet("Max", 1, 1);

        // When
        Pet savedPet = petService.create(newPet);

        // Then
        assertAll(
                () -> assertNotNull(savedPet),
                () -> assertNotNull(savedPet.getId()),
                () -> assertEquals("Max", savedPet.getName())
        );
    }

    @Test
    public void testListAllPets() {

        // When
        List<Pet> allPets = (List<Pet>) petService.findAll();

        // Then
        assertNotNull(allPets);

        // Imprimir información
        System.out.println("Lista de todas las mascotas:");
        allPets.forEach(pet -> System.out.println("ID: " + pet.getId() + ", Nombre: " + pet.getName()));


    }

}

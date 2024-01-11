package com.tecsup.petclinic.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.services.PetService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PetServiceStressTest {

    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @Test
    public void testStressCreatePets() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Simula la creación de 100 mascotas concurrentemente
        IntStream.range(0, 100).forEach(i -> executorService.submit(() -> {
            Pet pet = new Pet("Pet" + i, 1, 1);
            Pet savedPet = petService.create(pet);
            assertNotNull(savedPet.getId());
        }));

        // Espera a que todas las tareas se completen
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // Verifica que todas las mascotas se hayan creado correctamente
        IntStream.range(0, 100).forEach(i -> {
            Pet pet = petRepository.findByName("Pet" + i).get(0);
            assertAll(
                    () -> assertNotNull(pet),
                    () -> assertNotNull(pet.getId())
            );
        });

        // Verifica la visualización del listado de mascotas
        List<Pet> allPets = (List<Pet>) petService.findAll();
        assertEquals(100, allPets.size());
    }

    // Puedes agregar más pruebas de stress según sea necesario
}

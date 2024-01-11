package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.repositories.PetRepository;

@SpringBootTest
public class PetServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(PetServiceTest.class);

	@Autowired
	private PetService petService;

	@MockBean
	private PetRepository petRepository;

	@Test
	public void testCreatePet() {
		// Given
		Pet inputPet = new Pet("Fluffy", 1, 1);
		Pet savedPet = new Pet(1L, "Fluffy", 1, 1);

		// Simula el comportamiento del repositorio
		when(petRepository.save(any())).thenReturn(savedPet);

		// When
		Pet createdPet = petService.create(inputPet);

		// Then
		assertEquals(savedPet, createdPet);
	}

	@Test
	public void testFindPetById() throws PetNotFoundException {
		// Given
		Pet pet = new Pet(1L, "Fluffy", 1, 1);

		// Simula el comportamiento del repositorio
		when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

		// When
		Pet foundPet = petService.findById(1L);

		// Then
		assertEquals(pet, foundPet);
	}

	@Test
	public void testDeletePet() throws PetNotFoundException {
		// Given
		Pet pet = new Pet(1L, "Fluffy", 1, 1);

		// Simula el comportamiento del repositorio
		when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

		// When
		petService.delete(1L);

		// Then: La prueba pasa si no se lanza una excepción
	}

	@Test
	public void testFindAllPets() {
		// Given
		List<Pet> pets = Arrays.asList(new Pet(1L, "Fluffy", 1, 1), new Pet(2L, "Fido", 2, 1));

		// Simula el comportamiento del repositorio
		when(petRepository.findAll()).thenReturn(pets);

		// When
		Iterable<Pet> allPets = petService.findAll();

		// Then
		assertEquals(pets, allPets);
	}

	@Test
	public void testFindPetByName() {
		// Given
		String petName = "Fluffy";
		List<Pet> pets = Arrays.asList(new Pet(1L, petName, 1, 1), new Pet(2L, petName, 2, 1));

		// Simula el comportamiento del repositorio
		when(petRepository.findByName(petName)).thenReturn(pets);

		// When
		List<Pet> foundPets = petService.findByName(petName);

		// Then
		assertEquals(pets, foundPets);
	}

	@Test
	public void testFindPetsByTypeId() {
		// Given
		int typeId = 1;
		List<Pet> pets = Arrays.asList(new Pet(1L, "Fluffy", typeId, 1), new Pet(2L, "Fido", typeId, 1));

		// Simula el comportamiento del repositorio
		when(petRepository.findByTypeId(typeId)).thenReturn(pets);

		// When
		List<Pet> foundPets = petService.findByTypeId(typeId);

		// Then
		assertEquals(pets, foundPets);
	}

}

package org.springframework.samples.petclinic.owner;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PetController.class,
	includeFilters = {
		@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetService.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE),
	})
public class PetControllerTest {

	@MockBean
	private PetRepository petRepository;
	@MockBean
	private OwnerRepository ownerRepository;
	@Autowired
	private MockMvc mockMvc;

	private Pet pet;


	@BeforeEach
	void setUp() {
		PetType petType = new PetType();
		petType.setId(1);
		petType.setName("Dog");
		Owner owner = new Owner();
		owner.setFirstName("Emily");
		owner.setId(1);
		pet = new Pet();
		pet.setType(petType);
		pet.setId(1);
		pet.setBirthDate(LocalDate.now());
		pet.setName("Strawberry");
		pet.setOwner(owner);
		when(ownerRepository.findById(1)).thenReturn(owner);
		when(petRepository.findById(1)).thenReturn(pet);
		when(petRepository.findPetTypes()).thenReturn(Lists.newArrayList(petType));
	}

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/new", 1))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void testProcessCreationFormWhenThereIsErrorInInput() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/new", 1))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"))
			.andExpect(model().hasErrors());
	}

	@Test
	void testProcessCreationFormWhenThereIsNoError() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/new", 1)
			.param("name", pet.getName())
			.param("type", pet.getType().getName())
			.param("birthDate", pet.getBirthDate().toString()))
			.andDo(print())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", 1, 1))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void testProcessUpdateFormWhenThereIsErrorInInput() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", 1, 1))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void testProcessUpdateFormWhenThereIsNoError() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", 1, 1)
			.param("name", pet.getName())
			.param("birthDate", pet.getBirthDate().toString())
			.param("type", pet.getType().getName()))
			.andDo(print())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
}

package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindPetFeatureSteps {

	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner owner;
	private  Pet pet;
	private Pet foundedPet;

	@Given("There is a pet owner")
	public void thereIsAPetOwnerCalled() {
		owner = new Owner();
		owner.setFirstName("Zahra");
		owner.setLastName("Mo");
		owner.setAddress("Emdad");
		owner.setCity("Tehran");
		owner.setTelephone("09191919223");
		ownerRepository.save(owner);
	}

	@Given("The owner has a pet called {string}")
	public void theOwnerHasAPetCalled(String name) {
		pet = new Pet();
		pet.setName(name);
		pet.setBirthDate(LocalDate.now());
		PetType petType = new PetType();
		petType.setName("Dog");
		petType.setId(2);
		pet.setType(petType);
		petService.savePet(pet, owner);
	}

	@When("I want to find the pet with its ID")
	public void iWantToFindThePetWithItsID() {
		foundedPet = petService.findPet(pet.getId());
	}

	@Then("The pet should be found successfully")
	public void thePetIsFound() {
		assertEquals(foundedPet.getId(), pet.getId());
	}
}

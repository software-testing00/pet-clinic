package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FindOwnerFeatureSteps {

	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	private Owner owner;
	private Owner foundedOwner;

	@Given("There is a pet owner called {string}")
	public void thereIsAPetOwnerCalled(String name) {
		owner = new Owner();
		owner.setFirstName(name);
		owner.setLastName("Mo");
		owner.setAddress("Emdad");
		owner.setCity("Tehran");
		owner.setTelephone("09191919223");
		ownerRepository.save(owner);
	}

	@When("I want to find the owner with his ID")
	public void iWantToFindTheOwnerWithHisID() {
		foundedOwner = ownerRepository.findById(owner.getId());
	}

	@Then("The owner should be found successfully")
	public void theOwnerIsFound() {
		assertEquals(foundedOwner.getId(), owner.getId());
	}

	@Given("There is not a pet owner with ID {string}")
	public void thereIsNotAPetOwnerWithID(String id) {
	}

	@When("I want to find an owner with ID {string}")
	public void iWantToFindTheOwnerWithID(String id) {
		foundedOwner = petService.findOwner(Integer.valueOf(id));
	}

	@Then("The owner should not be found successfully")
	public void theOwnerIsNotFound() {
		System.out.println(foundedOwner);
		assertNull(foundedOwner);
	}

}

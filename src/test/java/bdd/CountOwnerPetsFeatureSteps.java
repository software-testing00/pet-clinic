package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountOwnerPetsFeatureSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner owner;
	private int ownersPets;

	@Given("There is a pet owner with one pet")
	public void thereIsAPetOwnerWithOnePet() {
		owner = new Owner();
		owner.setFirstName("Zahra");
		owner.setLastName("Mo");
		owner.setAddress("Emdad");
		owner.setCity("Tehran");
		owner.setTelephone("09191919223");
		ownerRepository.save(owner);
		petService.newPet(owner);
	}

	@When("I want to find the number of the owners' pets")
	public void iWantToCountTheOwnerPets() {
		ownersPets = owner.getPets().size();
	}

	@Then("The result should be one")
	public void theResultShouldBeOne() {
		assertEquals(ownersPets, 1);
	}
}

package org.springframework.samples.petclinic.utility;

import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberOptions(publish = true)
public class PriceCalculatorTest {

	@Test
	public void when_petWithZeroVisit() {
		PriceCalculator pc = new PriceCalculator();
		List<Pet> pets = new ArrayList();
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.of(2019, 1, 1));
		pets.add(pet);
		double res = (int)pc.calcPrice(pets, 17,23);
		double expRes = 38;
		assertEquals(res, (int)expRes);
	}

	@Test
	public void when_petWithTodayVisits() {
		PriceCalculator pc = new PriceCalculator();
		List<Pet> pets = new ArrayList();
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.of(2019, 1, 1));
		for (int j = 0; j < 5; j++){
			Visit visit = new Visit();
			pet.addVisit(visit);
		}
		pets.add(pet);
		double res = (int)pc.calcPrice(pets, 17,23);
		double expRes = 38;
		assertEquals(res, (int)expRes);
	}

	@Test
	public void when_petWithVariableVisits() {
		PriceCalculator pc = new PriceCalculator();
		List<Pet> pets = new ArrayList();
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.of(2019, 1, 1));
		for (int j = 0; j < 5; j++){
			Visit visit = new Visit();
			if (j==3){
				visit.setDate(LocalDate.of(2021, 1, 1));
			}
			pet.addVisit(visit);
		}
		pets.add(pet);
		double res = (int)pc.calcPrice(pets, 17,23);
		double expRes = 38;
		assertEquals(res, (int)expRes);
	}
	@Test
	public void when_petsWithVariableVisits() {
		PriceCalculator pc = new PriceCalculator();
		List<Pet> pets = createPetList(10);
		double res = (int)pc.calcPrice(pets, 17,23);
		double expRes = 238084;
		assertEquals(res, (int)expRes);
	}

	public List<Pet> createPetList(int petSize){
		List<Pet> pets = new ArrayList();
		for (int i = 0; i < petSize; i++){
			Pet pet = new Pet();
			pet.setBirthDate(LocalDate.of(2021-i, (i+1)%12, (i+1)%30));
			for (int j = 0; j < i; j++){
				Visit visit = new Visit();
				if (j%3 == 2)
					visit.setDate(LocalDate.now().minus(100, ChronoUnit.DAYS));
				if (j%3 == 1)
					visit.setDate(LocalDate.of(2021, 1, 1));

				pet.addVisit(visit);
			}
			pets.add(pet);
		}
		return pets;
	}
}

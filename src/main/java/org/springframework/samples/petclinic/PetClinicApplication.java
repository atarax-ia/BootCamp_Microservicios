/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 */

@Slf4j
@SpringBootApplication
public class PetClinicApplication {

	public static void main(String[] args) {
		System.out.println("prueba");
		SpringApplication.run(PetClinicApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoVetRepository(VetRepository vetRepository, SpecialtyRepository specialtyRepository,
											   PetRepository petRepository, VisitReppository visitRepository) {
		return (args) -> {
			System.out.println("*****************************************************");
			System.out.println("BOOTCAMP - Spring y Spring Data - vetRepository");
			System.out.println("*****************************************************");

			System.out.println("Creamos un objeto Vet");
			Vet vet = new Vet();
			vet.setFirstName("Sergio");
			vet.setLastName("Raposo Vargas");

			System.out.println("*****************************************************");

			System.out.println("Persistimos en BBDD");
			vetRepository.save(vet);

			System.out.println("*****************************************************");

			System.out.println("Comprobamos que se ha creado correctamente");
			Vet vetAux = vetRepository.findById(vet.getId());
			System.out.println(vetAux.toString());

			System.out.println("*****************************************************");

			System.out.println("Editamos el objeto y añadimos una Speciality");
			Specialty s = specialtyRepository.findById(1);
			vet.addSpecialty(s);
			vetRepository.save(vet);
			System.out.println(vet.toString());

			System.out.println("*****************************************************");

			System.out.println("Listamos todos los veterinarios");
			for (Vet v : vetRepository.findAll()) {
				System.out.println("Vet: " + v.getFirstName() + " " + v.getLastName());
			}
			System.out.println("*****************************************************");

			System.out.println("Listamos todos los veterinarios con especialidad en Radiologia");
			for (Vet v : vetRepository.findBySpecialtyName("radiology")) {
				System.out.println("Vet: " + v.getFirstName() + " " + v.getLastName() + " - " + v.getSpecialties());
			}

			System.out.println("*****************************************************");

			System.out.println("Mascotas nacidas en 2010, ordenadas ascendentemente por fecha de nacimiento");
			for (Pet p : petRepository.findByYearOfBirthDate(2010)) {
				System.out.println("Pet: " + p.getName() + " " + p.getBirthDate());
			}

			System.out.println("*****************************************************");

			System.out.println("Mascotas nacidas en 2000, ordenadas ascendentemente por fecha de nacimiento");
			for (Pet p : petRepository.findByYearOfBirthDate(2000)) {
				System.out.println("Pet: " + p.getName() + " \t " + p.getBirthDate());
			}

			System.out.println("*****************************************************");

			System.out.println("Crear 3 visitas para diferentes mascotas");
			for (int i = 0; i < 3; i++) {
				Pet p = petRepository.findById(i + 1);
				Visit v = new Visit();
				if(i%3==0) {v.setDate(LocalDate.of(2000, 1, 15)); }
				if(i%3==1) {v.setDate(LocalDate.of(2001, 3, 15)); }
				if(i%3==2) {v.setDate(LocalDate.of(2002, 5, 15)); }
				v.setDescription("Routine check");
				visitRepository.save(v);
				p.addVisit(v);
				petRepository.save(p);
				System.out.println(p.toString());
			}

			System.out.println("*****************************************************");

			System.out.println("Obtener todas las visitas para una mascota");
//			for (Visit v : visitRepository.findVisitsByPetId(8)) {
//				System.out.println("Visit: " + v.getId() + " " + v.getDate() + " - " + v.getDescription());
//			}

			System.out.println("*****************************************************");

			System.out.println("Obtener las 4 visitas más recientes de todo el sistema");
			List<Visit> v = visitRepository.findAllInDescendingOrder();
			for (int i = 0; i < 4; i++) {
				System.out.println("Visit: " + v.get(i).getDate());
			}

			System.out.println("*****************************************************");
		};
	}

}

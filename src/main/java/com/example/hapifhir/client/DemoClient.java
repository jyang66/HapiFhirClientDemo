package com.example.hapifhir.client;

import java.util.List;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.StringClientParam;

/*
 * @author jyang
 * @date 4/11/2016
 */

public class DemoClient {

	static enum testEnum {
		readById, readByName, readExtension
	};

	public static void main(String[] args) {

		testEnum choice = testEnum.readById;

		FhirContext ctx = FhirContext.forDstu2();

		if (choice == testEnum.readById) {
			
			// Read by id

			IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2");

			Patient patient = client.read().resource(Patient.class)
					.withId("5401").execute();
			
			String encoded = ctx.newXmlParser().setPrettyPrint(true)
					.encodeResourceToString(patient);
			
			System.out.println("\n\nGet patient by id  - " + encoded + "\n\n");

			// Bundle bundle = client.search().forResource(Patient.class)
			// .where(new StringClientParam("_id").matches().value("5149"))
			// .execute();
			//

		} else if (choice == testEnum.readByName) {

			// Read by name

			IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseDstu2");

			ca.uhn.fhir.model.api.Bundle patients = client.search()
					.forResource(Patient.class)
					.where(Patient.FAMILY.matches().value("Bernhart"))
					.execute();

			String encoded = ctx.newXmlParser().setPrettyPrint(true)
					.encodeBundleToString(patients);
			System.out.println("\n\nGet patient by name - " + encoded + "\n\n");

		} else if (choice == testEnum.readExtension) {

			// Read extensions

			IGenericClient client = ctx.newRestfulGenericClient("http://localhost:8080/fhir");

			Patient patient = client.read().resource(Patient.class).withId("1005")
					.execute();
			String encoded = ctx.newXmlParser().setPrettyPrint(true)
					.encodeResourceToString(patient);
			System.out.println("\n\nGet patient by id  - " + encoded + "\n\n");

			// get patient level extensions
			for (ExtensionDt ext : patient.getUndeclaredExtensions()) {

				CodeableConceptDt coded = (CodeableConceptDt) ext.getValue();
				System.out.println("\nGet patient extension value - "
						+ ext.getUrl() + " "
						+ coded.getCoding().get(0).getCode() + " "
						+ coded.getText());

			}

			// get name level extensions
			for (ExtensionDt ext : patient.getNameFirstRep()
					.getUndeclaredExtensions()) {

				System.out.println("\nGet patient name extension value - "
						+ ext.getUrl() + " " + ext.getValue() + "\n");

			}
		}

	}

}

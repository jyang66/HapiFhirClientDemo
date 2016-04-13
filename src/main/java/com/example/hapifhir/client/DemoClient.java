package com.example.hapifhir.client;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.rest.gclient.StringClientParam;

/*
 * @author jyang
 * @date 12/21/2015
 */

public class DemoClient {

	public static void main(String[] args) {
		FhirContext ctx = FhirContext.forDstu2();
		String serverBase = "http://fhirtest.uhn.ca/baseDstu2";

		IGenericClient client = ctx.newRestfulGenericClient(serverBase);
		
		Patient patient = client.read()
                .resource(Patient.class)
                .withId("5401")
                .execute();
		String encoded = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(patient);
		System.out.println("\n\nGet patient by id  - " + encoded + "\n\n");
		
		
//		Bundle bundle = client.search().forResource(Patient.class)
//				.where(new StringClientParam("_id").matches().value("5149"))
//				.execute();
//		
		
				
		
		ca.uhn.fhir.model.api.Bundle patients = client
			      .search()
			      .forResource(Patient.class)
			      .where(Patient.FAMILY.matches().value("Bernhart"))
			      .execute();
		
		encoded = ctx.newXmlParser().setPrettyPrint(true).encodeBundleToString(patients);
		System.out.println("\n\nGet patient by name - " + encoded + "\n\n");
		
	}

}

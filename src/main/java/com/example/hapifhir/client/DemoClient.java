package com.example.hapifhir.client;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.rest.client.IGenericClient;

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
                .withId("966682")
                .execute();
		
		String encoded = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(patient);
				
		
//		ca.uhn.fhir.model.api.Bundle patients = client
//			      .search()
//			      .forResource(Patient.class)
//			      .where(Patient.FAMILY.matches().value("Anwendertreffen"))
//			      .execute();
//		
//		String encoded = ctx.newXmlParser().setPrettyPrint(true).encodeBundleToString(patients);
		
		System.out.println("Get patients - " + encoded);
		
	}

}

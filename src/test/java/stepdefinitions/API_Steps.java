package stepdefinitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.cucumber.java.en.*;

import java.util.List;

public class API_Steps {
	private static final String BASE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/api/v1";
	private String authToken;
	private int candidateId;

	// Authenticate and store the token
	private void authenticate() {
		if (authToken == null) {
			Response response = given().header("Content-Type", "application/json")
					.body("{\"username\":\"Admin\",\"password\":\"admin123\"}").when().post(BASE_URL + "/authenticate")
					.then().statusCode(200).extract().response();

			authToken = response.jsonPath().getString("token");
			System.out.println("Authenticated. Token: " + authToken);
		}
	}

	@Given("I add a candidate through API")
	public void addCandidate() {
		authenticate();

		Response response = given().headers("Content-Type", "application/json", "Authorization", "Bearer " + authToken)
				.body("{ \"firstName\": \"Islam\", \"lastName\": \"Sabbah\", \"email\": \"Islam.Sabbah@email.com\" }")
				.when().post(BASE_URL + "/recruitment/candidates").then().statusCode(201)
				.body("message", equalTo("Candidate added successfully")).extract().response();

		candidateId = response.jsonPath().getInt("id");
		System.out.println("Candidate added with ID: " + candidateId);
	}

	@Then("I delete a candidate through API")
	public void deleteCandidate() {
		authenticate();

		List<Integer> candidateIds = given().header("Authorization", "Bearer " + authToken).when()
				.get(BASE_URL + "/recruitment/candidates").then().extract().jsonPath().getList("data.id");

		if (candidateIds.isEmpty()) {
			System.out.println("No candidates found to delete.");
			return;
		}

		int candidateIdToDelete = candidateIds.get(0);
		given().header("Authorization", "Bearer " + authToken).when()
				.delete(BASE_URL + "/recruitment/candidates/" + candidateIdToDelete).then().statusCode(200)
				.body("message", equalTo("Candidate deleted successfully"));

		System.out.println("Candidate deleted with ID: " + candidateIdToDelete);
	}
}

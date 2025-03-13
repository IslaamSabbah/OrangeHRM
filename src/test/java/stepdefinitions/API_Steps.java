package stepdefinitions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.cucumber.java.en.*;

import java.util.List;

public class API_Steps {
	String baseURI = "https://opensource-demo.orangehrmlive.com/web/index.php/api/v1";
	String authToken;
	int candidateId;

	// Step 1: Authenticate and store the token
	public void authenticate() {
		Response response = given().header("Content-Type", "application/json")
				.body("{\"username\":\"Admin\",\"password\":\"admin123\"}").when().post(baseURI + "/authenticate")
				.then().statusCode(200).extract().response();

		authToken = response.jsonPath().getString("token");
		System.out.println("Auth Token: " + authToken);
	}

	@Given("I add a candidate through API")
	public void i_add_candidate() {
		authenticate();
		Response response = given().header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + authToken)
				.body("{ \"firstName\": \"Islam\", \"lastName\": \"Sabbah\", \"email\": \"Islam.Sabbah@email.com\" }")
				.when().post(baseURI + "/recruitment/candidates").then().assertThat().statusCode(201)
				.body("message", equalTo("Candidate added successfully")).extract().response();

		candidateId = response.jsonPath().getInt("id");
	}

	@Then("I delete a candidate through API")
	public void i_delete_candidate() {
		authenticate();

		Response getResponse = given().header("Authorization", "Bearer " + authToken).when()
				.get(baseURI + "/recruitment/candidates").then().extract().response();

		List<Integer> candidateIds = getResponse.jsonPath().getList("data.id");

		if (candidateIds.isEmpty()) {
			System.out.println("No candidates found to delete.");
			return;
		}

		int candidateIdToDelete = candidateIds.get(0);

		given().header("Authorization", "Bearer " + authToken).when()
				.delete(baseURI + "/recruitment/candidates/" + candidateIdToDelete).then().assertThat().statusCode(200)
				.body("message", equalTo("Candidate deleted successfully"));

	}
}

package plataformaFilmes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlataformaFilmesTest {

    @Test
    public void validarLogin(){
        RestAssured.baseURI = "http://localhost:8080/";

        String json = "{" +
                "\"email\": \"aluno@email.com\"," +
                "\"senha\": \"123456\"" +
                "}";

        Response response = post(json, ContentType.JSON, "auth");

        //String token = response.body().jsonPath().get("token"); - Podemos simplificar tirando o "body", pois o RestAssured sabe que va,os pegar a informação do response
        String token = response.jsonPath().get("token");
        //System.out.println(token);

        assertEquals(200, response.statusCode());
        assertEquals("Bearer", response.jsonPath().get("tipo"));
    }

    public Response post(Object json, ContentType contentType, String endpoint) {

        return RestAssured.given()
                .relaxedHTTPSValidation()
                .contentType(contentType)
                .body(json)
                .when()
                .post(endpoint)
                .thenReturn(); // essa linha é resumo de: .then() + .extract() + .response();
    }

}

package plataformaFilmes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlataformaFilmesTest {

    static String token;
    static String tipo;

    @BeforeAll
    public static void validarLogin(){
        RestAssured.baseURI = "http://localhost:8080/";

        //teste enviando diretamente o json em string
        /* String json = "{" +
                "\"email\": \"aluno@email.com\"," +
                "\"senha\": \"123456\"" +
                "}";
        Response response = post(json, ContentType.JSON, "auth"); */

        //teste enviando json atraves de map
        Map<String, String> map = new HashMap<>();
        map.put("email", "aluno@email.com");
        map.put("senha", "123456");

        Response response = post(map, ContentType.JSON, "auth");

        //String token = response.body().jsonPath().get("token"); - Podemos simplificar tirando o "body", pois o RestAssured sabe que va,os pegar a informação do response
        //token = response.jsonPath().get("token");
        //System.out.println(token);

        assertEquals(200, response.statusCode());

        //guarda token
         token = response.jsonPath().get("token");
         tipo = response.jsonPath().get("tipo");
    }

    @Test
    public void validarTipoBearer(){
        assertEquals("Bearer", tipo);
    }

    @Test
    public void validarConsultaCategorias(){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", tipo + " " + token);

        Response response = getCategorias(header, "categorias");
        assertEquals(200, response.statusCode());

        System.out.println(response.jsonPath().get().toString());
    }

    private static Response getCategorias(Map<String, String> header, String endpoint) {
        return RestAssured.given()
                .relaxedHTTPSValidation()
                .headers(header)
                .when()
                .get(endpoint)
                .thenReturn();
    }

    public static Response post(Object json, ContentType contentType, String endpoint) {

        return RestAssured.given()
                .relaxedHTTPSValidation()
                .contentType(contentType)
                .body(json)
                .when()
                .post(endpoint)
                .thenReturn(); // essa linha é resumo de: .then() + .extract() + .response();
    }

}

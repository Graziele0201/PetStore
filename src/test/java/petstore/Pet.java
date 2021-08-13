// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri ="https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test   // identifica o método ou função para o Teste
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then

        given() //dado
                .contentType("application/json") // comum em API REST - antigas era "test/xml"
                .log().all()
                .body(jsonBody)
        .when() //quando
                .post(uri)
        .then() //então
                .log().all()
                .statusCode(200)
                .body("name", is("Snoopy"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("sta"))
        ;
    }
    @Test
    public void consultarPet(){
        String petId = "9223372000001125817";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Snoopy"))
                .body("category.name", is("dog"))
                .body("status", is("available"))
        ;
    }
    @Test
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

                given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)

                .when()
                        .put(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("name", is("Snoopy"))
                        .body("status", is("sold"))
                ;
    }

    @Test
    public void excluirPet(){
        String petId = "9223372000001125817";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)

        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;

    }
}

// 1 - Pacote

package petstore;

// 2 - Bibliotecas

import javafx.scene.shape.Path;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    // 3.1 - Atributos (Caracteristicas)
    String uri = "https://petstore.swagger.io/v2/pet"; //Endereço da entidade Pet


    // 3.2 - Métodos(não retornam valor) e Funções(trazem retorno)
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test (priority = 1) //Identifica o metodo ou funcao como Teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //Sintaxe Gherkin
        //Dado - Quando - Então
        //Given - When - Then

        given() // Dado
                .contentType("application/json") //comum em API REST; APIs Antigas "text/xml"
                .log().all()
                .body(jsonBody)
        .when() //Quando
                .post(uri)
        .then() //Então
                .log().all()
                .statusCode(200)
                .body("name", is("Puppy"))
                .body("status", is("Vermifugado"))
                .body("category.name", is("AXLUHAM2021"))
                .body("tags.name", contains("Consulta"))

        ;

    }
    @Test (priority = 2)
    public void consultarPet(){
        String petId = "1982022811102021";

        String token =

        given() // Dado
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" +petId)

        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Puppy"))
                .body("category.name", is("AXLUHAM2021"))
                .body("status", is("Vermifugado"))
        .extract()
                .path("category.name")
        ;
        System.out.println("Token é " + token);

    }

    @Test (priority = 3)
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
                .body("name", is("Bob"))
                .body("status", is("Vacinado"))
    ;

    }


//    @Test (priority = 3)
//    public void deletaPet() throws IOException {
//        String petId = "1982022811102021";
//
//        given() // Dado
//                .contentType("application/json")
//                .log().all()
//        .when()
//                .delete(uri + "/" + petId)
//
//        .then()
//                .log().all()
//                .statusCode(200)
//        ;
//    }
}
    
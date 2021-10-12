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

public class Pet {
    // 3.1 - Atributos (Caracteristicas)
    String uri = "https://petstore.swagger.io/v2/pet"; //Endereço da entidade Pet


    // 3.2 - Métodos(não retornam valor) e Funções(trazem retorno)
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test //Identifica o metodo ou funcao como Teste para o TestNG
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
        ;
    }

}
    
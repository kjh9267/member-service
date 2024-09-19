package me.jun.memberservice.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static me.jun.memberservice.support.MemberFixture.registerRequest;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MemberIntegrationTest {

    @LocalServerPort
    private int port;

    private String token;

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Test
    void MemberTest() {
        register();
    }

    private void register() {
        String response = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
                .body(registerRequest())

                .when()
                .post("/api/member/register")

                .then()
                .statusCode(OK.value())
                .assertThat().body("$", x -> hasKey("id"))
                .assertThat().body("$", x -> hasKey("email"))
                .assertThat().body("$", x -> hasKey("name"))
                .assertThat().body("$", x -> hasKey("role"))
                .extract()
                .asString();

        JsonElement element = JsonParser.parseString(response);
        System.out.println(gson.toJson(element));
    }
}

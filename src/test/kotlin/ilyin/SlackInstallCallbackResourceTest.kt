package ilyin

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test

@QuarkusTest
class SlackInstallCallbackResourceTest(

) {
    @Test
    fun testAuthOK() {
        given()
            .basePath("health")
            .get()
            .then()
            .statusCode(200)
    }
}

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LiveEndPointNegativeTest {
    private static Response response;

    @Test
    public void noAPIKeyTest() {
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT);
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", containsString("No API key found in request"));
    }

    @Test
    public void wrongAPIKeyTest() {
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT +"?"+ "apikey=123ABC");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", startsWith("Invalid authentication"));
    }

    @Test
    public void currencyNoAPIKeyTest(){
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT +"?" + "currencies=CAD");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", containsString("No API key found in request"));
    }

    @Test
    public void currencyWrongAPIKeyTest() {
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT +"?" + "apikey=123ABC" + "&" + "currencies=CAD");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", startsWith("Invalid authentication"));
    }

    @Test
    public void incorrectCurrencyTest(){
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT +"?" + Consts.API_KEY + "&" + "currencies=xxx");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", startsWith("You have provided one or more invalid Currency Codes."));
    }

    @Test
    public void currenciesIncorrectCombinationTest(){
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT +"?" + Consts.API_KEY + "&" + "currencies=xxx,CAD");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", startsWith("You have provided one or more invalid Currency Codes."));
        response.then().body("quotes", hasKey("USDCAD"));
    }

    @Test
    public void currenciesCombinationWithoutCommaTest(){
        response = given().get(Consts.URL + Consts.LIVE_ENDPOINT + "?" + Consts.API_KEY + "&"+ "currencies=CAD EUR");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", startsWith("You have provided one or more invalid Currency Codes"));
    }
}

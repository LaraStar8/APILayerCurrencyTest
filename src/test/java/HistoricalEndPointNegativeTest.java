import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;

public class HistoricalEndPointNegativeTest {

    private static Response response;
    private static String date = "2023-08-28";

    @Test
    public void historicalNoAPIKeyTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + "date=" + date);
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", containsString("No API key found in request"));
    }

    @Test
    public void historicalWrongAPIKeyTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + "date=" + date + "&" + "apikey=123ABC");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", startsWith("Invalid authentication"));
    }

    @Test
    public void historicalNoDateTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + Consts.API_KEY);
        System.out.println(response.asString());
        response.then().statusCode(301);
        response.then().body("message", startsWith("User did not specify a date"));
    }

    @Test
    public void historicalWithCurrencyNoAPIKeyTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + "date=" + date + "&" + "currencies=CAD");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", containsString("No API key found in request"));
    }

    @Test
    public void historicalWithCurrencyNoDateTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + Consts.API_KEY + "&" + "currencies=CAD");
        System.out.println(response.asString());
        response.then().statusCode(301);
        response.then().body("message", startsWith("User did not specify a date"));
    }

    @Test
    public void historicalWithCurrencyWrongAPIKeyTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + "date=" + date + "&" + "currencies=CAD" + "&" + "apikey=123ABC");
        System.out.println(response.asString());
        response.then().statusCode(401);
        response.then().body("message", startsWith("Invalid authentication"));
    }

    @Test
    public void historicalIncorrectCurrencyTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + Consts.API_KEY + "&" + "date=" + date + "&" + "currencies=xxx");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", startsWith("You have provided one or more invalid Currency Codes."));
    }
    @Test
    public void historicalCurrenciesIncorrectCombinationTest(){
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT +"?" + Consts.API_KEY +"&"+"date=" + date + "&" + "currencies=xxx,CAD");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", startsWith("You have provided one or more invalid Currency Codes."));
        response.then().body("quotes", hasKey("USDCAD"));
    }
    @Test
    public void currenciesCombinationWithoutCommaTest(){
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + Consts.API_KEY  +"&"+"date=" + date + "&"+"currencies=EUR CAD");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("success", equalTo(false));
        response.then().body("error.code", equalTo(202));
        response.then().body("error.info", startsWith("You have provided one or more invalid Currency Codes"));
    }
}

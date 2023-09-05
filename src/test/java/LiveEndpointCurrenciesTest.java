import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LiveEndpointCurrenciesTest {

    private static Response response;

    @Test
    public void retrievingCADTest() {
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT +"?" + Consts.API_KEY  + "&" + "currencies=CAD");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes", hasKey("USDCAD")).and().body("quotes.USDCAD", notNullValue());
        response.then().body("success", equalTo(true));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
    }

    @Test
    public void retrievingEURTest() {
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT +"?"+ Consts.API_KEY + "&" + "currencies=EUR");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes", hasKey("USDEUR")).and().body("quotes.USDEUR", notNullValue());
        response.then().body("success", equalTo(true));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
    }

    @Test
    public void retrievingRUBTest() {
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT +"?" + Consts.API_KEY + "&" + "currencies=RUB");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes", hasKey("USDRUB")).and().body("quotes.USDRUB", notNullValue());
        response.then().body("success", equalTo(true));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
    }

    @Test
    public void retrievingNISTest() {
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT +"?"+Consts.API_KEY + "&" + "currencies=NIS");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes", hasKey("USDNIS")).and().body("quotes.USDNIS", notNullValue());
        response.then().body("success", equalTo(true));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
    }

    @Test
    public void retrievingSeveralCurrenciesTest() {
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT +"?" + Consts.API_KEY + "&" + "currencies=CAD,EUR,RUB");
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes", hasKey("USDCAD")).and().body("quotes.USDCAD", notNullValue());
        response.then().body("quotes", hasKey("USDEUR")).and().body("quotes.USDEUR", notNullValue());
        response.then().body("quotes", hasKey("USDRUB")).and().body("quotes.USDRUB", notNullValue());
    }
}

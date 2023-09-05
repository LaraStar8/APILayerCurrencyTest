import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;

public class HistoricalEndpointCurrenciesTest {

    private static Response response;
    private static String date = "2023-08-28";

    @Test
    public void retrievingHistoricalCADTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT +"?"+"date="+date +"&"+"currencies=CAD"+"&"+ Consts.API_KEY );
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes", hasKey("USDCAD")).and().body("quotes.USDCAD", notNullValue());
        response.then().body("success", equalTo(true));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo(date));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
    }
    @Test
    public void retrievingHistoricalEURTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT +"?"+"date="+date +"&"+"currencies=EUR"+"&"+ Consts.API_KEY );
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes", hasKey("USDEUR")).and().body("quotes.USDEUR", notNullValue());
        response.then().body("success", equalTo(true));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo(date));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
    }
    @Test
    public void retrievingHistoricalRUBTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT +"?"+"date="+date +"&"+"currencies=RUB"+"&"+ Consts.API_KEY );
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes", hasKey("USDRUB")).and().body("quotes.USDRUB", notNullValue());
        response.then().body("success", equalTo(true));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo(date));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
    }
    @Test
    public void retrievingHistoricalNISTest() {
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT +"?"+"date="+date +"&"+"currencies=NIS"+"&"+ Consts.API_KEY );
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("quotes", hasKey("USDNIS")).and().body("quotes.USDNIS", notNullValue());
        response.then().body("success", equalTo(true));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo(date));
        response.then().body("timestamp", notNullValue());
        response.then().body("source", equalTo("USD"));
    }
    @Test
    public void retrievingSeveralCurrenciesHistoricalEndpointTest() {
        response = given().contentType("application/json").get(Consts.URL + Consts.HISTORICAL_ENDPOINT +"?"+"date="+date +"&"+"currencies=CAD,EUR,RUB"+"&"+ Consts.API_KEY );
        System.out.println(response.asString());
        response.then().statusCode(200);
        response.then().body("timestamp", notNullValue());
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo(date));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes", hasKey("USDCAD")).and().body("quotes.USDCAD", notNullValue());
        response.then().body("quotes", hasKey("USDEUR")).and().body("quotes.USDEUR", notNullValue());
        response.then().body("quotes", hasKey("USDRUB")).and().body("quotes.USDRUB", notNullValue());
    }
}

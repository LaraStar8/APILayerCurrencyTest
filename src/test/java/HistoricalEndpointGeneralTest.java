import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;

public class HistoricalEndpointGeneralTest {

    private static Response response;
    private static String date = "2023-08-28";

    @BeforeAll
    public static void setup() {
        response = given().contentType("application/json").get(Consts.URL + Consts.HISTORICAL_ENDPOINT + "?" + Consts.API_KEY + "&" + "date=" + date);
        System.out.println(response.asString());
    }

    @Test
    public void historicalEndpointResponseCodeTest() {
        response.then().statusCode(200);
    }

    @Test
    public void successHistoricalEndpointInBodyTest() {
        response.then().body("success", equalTo(true));
    }

    @Test
    public void historicalEndpointInBodyTest() {
        response.then().body("historical", equalTo(true));
    }

    @Test
    public void dateHistoricalEndpointInBodyTest() {
        response.then().body("date", equalTo(date));
    }

    @Test
    public void sourceHistoricalEndpointInBodyTest() {
        response.then().body("source", equalTo("USD"));
    }

    @Test
    public void timestampHistoricalEndpointInBodyTest() {
        response.then().body("timestamp", notNullValue());
    }

    @Test
    public void quotesHistoricalEndpointInBodyTest() {
        response.then().body("quotes", hasKey("USDCAD")).and().body("quotes.USDCAD", notNullValue());
        response.then().body("quotes", hasKey("USDEUR")).and().body("quotes.USDEUR", notNullValue());
        response.then().body("quotes", hasKey("USDRUB")).and().body("quotes.USDRUB", notNullValue());
        response.then().body("quotes", hasKey("USDNIS")).and().body("quotes.USDNIS", notNullValue());
    }

}

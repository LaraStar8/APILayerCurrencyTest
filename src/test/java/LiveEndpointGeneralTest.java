import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class LiveEndpointGeneralTest {

    private static Response response;

    @BeforeAll
    public static void setup() {
        response = given().contentType("application/json").get(Consts.URL + Consts.LIVE_ENDPOINT + "?" + Consts.API_KEY);
        System.out.println(response.asString());
    }

    @Test
    public void liveEndpointResponseCodeTest() {
        response.then().statusCode(200);
    }

    @Test
    public void successLiveEndpointInBodyTest() {
        response.then().body("success", equalTo(true));
    }

    @Test
    public void sourceLiveEndpointInBodyTest() {
        response.then().body("source", equalTo("USD"));
    }

    @Test
    public void timestampLiveEndpointInBodyTest() {
        response.then().body("timestamp", notNullValue());
    }

    @Test
    public void quotesLiveEndpointInBodyTest() {
        response.then().body("quotes", hasKey("USDCAD")).and().body("quotes.USDCAD", notNullValue());
        response.then().body("quotes", hasKey("USDEUR")).and().body("quotes.USDEUR", notNullValue());
        response.then().body("quotes", hasKey("USDRUB")).and().body("quotes.USDRUB", notNullValue());
        response.then().body("quotes", hasKey("USDNIS")).and().body("quotes.USDNIS", notNullValue());
    }

    @Test
    public void termsAndPrivacyPolicyTest() {
        response.then().body("terms", notNullValue());
        response.then().body("privacy", notNullValue());
    }
    @Test
    public void timeStampTest(){
        String expected = LocalDate.now().toString();
        //get timestamp from response
        Integer actualMs = response.path("timestamp");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //get Date from the timestamp in request
        Date date2 = new Date((long)actualMs*1000);
        //format date from response to match expected String date
        String actual = format.format(date2.getTime());
        Assertions.assertEquals(expected, actual);
    }
}

package crm.telbiz.api.Accesstoken;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class GetMIFEAccesstoken {
//    public static final String GET_ACCESS_TOKEN_KEY = "";
    public static String getAccessTokenKey() {


        try {
            useRelaxedHTTPSValidation();
            Response response = given().
                    when().
                    header("Authorization", "Basic OVI5YUFraVkxeFdKSmEwZDVBBYTp1QWgyR3BoZ1Y5c2VEdmxlMUhmZVZpM2ViNmNh").

                    post ("https://mife-stg.dil.com/apicall/token?grant_type=password&username=GIRM&password=GIM&scope=SANDBOX");

            System.out.println(response.getBody());

            response.then().log().all();

            JsonPath jsonPath = new JsonPath(response.asString());
            //String tokenRefresh=jsonPath.getString("refresh_token");
          String accessToken = jsonPath.getString("access_token");
            System.out.println("Refersh accessToken is" + accessToken);
           // System.out.println("accessToken is" + tokenRefresh);
            return accessToken;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

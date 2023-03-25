package crm.telbiz.api.calls;

import com.sun.istack.internal.NotNull;
import crm.framework.db.QueryResult;
import crm.framework.db.sql.executers.SQLExecuter;
import crm.telbiz.api.Accesstoken.GetMIFEAccesstoken;
import crm.telbiz.api.db.SQLQUERIES;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;


public class TestApp {

    private static String accessToken;
    String querymsisdnfromtelbiz = SQLQUERIES.GET_MSISDN_Telbiz;
    String CCBSMSISDN=SQLQUERIES.GET_MSISDN_CCBS;
    String queryemailaddress=SQLQUERIES.GET_EmailAddress_Telbiz;
    String querycontno=SQLQUERIES.Get_Contractnum_Telbiz;
    @BeforeClass
    public void getAccesstoken() {

        accessToken = GetMIFEAccesstoken.getAccessTokenKey();
    }

    @Test
    public void eEbillRegStatusAPI() throws Exception {
        QueryResult msisdn = SQLExecuter.getQueryResults(querymsisdnfromtelbiz);
        QueryResult ccbsmsisdn=SQLExecuter.getQueryResults(CCBSMSISDN);
        QueryResult email = SQLExecuter.getQueryResults(queryemailaddress);
        QueryResult contno=SQLExecuter.getQueryResults(querycontno);

        List<String> telbizArea = msisdn.getAllValues("AREA");
        List<String> telbizSubno = msisdn.getAllValues("SUBNO");
        List<String> CCBS_MSISDN=ccbsmsisdn.getAllValues("SUB_NUMBER");
        List<String> telbizEmail=email.getAllValues("EMAIL");
        List<String> telbizContno=contno.getAllValues("CONTRNO");
        telbizArea.addAll(telbizSubno);
        String mobilenum = telbizArea.toString().replaceAll("\\[|\\]|\"","").replaceAll(",","").replaceAll("\\s+","");
        System.out.println(mobilenum);
        useRelaxedHTTPSValidation();


        //---------------------------------------GetEbillRegStatusAPI--------TELBIZ------------------------------------------------------------------------------
        Response response = given().
                when().
                contentType("application/json").
                header("Authorization", accessToken).
                get("https://mife-stg.dil.com/apicall/crm/regStatusService/v1.0.0/rest/api/getEbillRegStatus/msisdn/{mobilenum}",mobilenum);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath = new JsonPath(response.asString());
        String resultcode = jsonPath.getString("result");
        String status = jsonPath.getString("status");
        Assert.assertEquals(resultcode,"SUCCESS");
        Assert.assertEquals(status,"1");

        //---------------------------------------GetManuallVoiceStatus-------TELBIZ-------------------------------------------------------------------------------
        given().
                when().
                contentType("application/json").
                header("Authorization", accessToken).
                get("https://mife-stg.dil.com/apicall/crm/InvoiceStatusRetrieval/v1.0.0/rest/api/getManualInvoiceStatus/Msisdn/{mobilenum}",mobilenum);
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath2 = new JsonPath(response.asString());
        String resultcode2 = jsonPath2.getString("result");
        String status1 = jsonPath2.getString("status");
        Assert.assertEquals(resultcode2,"SUCCESS");
        Assert.assertEquals(status1,"1");
        //---------------------------------------GET EbillAddressAPI-------TELBIZ-------------------------------------------------------------------------------
        given().
                when().
                contentType("application/json").
                header("Authorization", "Bearer 7b7a50574eae6012dc8d55915ce71d1").
                get("https://mife-stg.dil.com/apicall/crm/ebillService/v1.0.0/rest/api/getEbillAddress/Msisdn/{mobilenum}",mobilenum);

        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath3 = new JsonPath(response.asString());
        String ebill = jsonPath3.getString("ebill");
        System.out.println(ebill);

        //---------------------------------------GetCDMAFWDAddress-----------TELBIZ---------------------------------------------------------------------------
        given().
                when().
                contentType("application/json").
                header("Authorization", accessToken).
                get("https://mife-stg.dil.com/apicall/crm/connectionDetails/v1.0.0/rest/api/CDMALTEConnection/{telbizContno}",telbizContno);
        Assert.assertEquals(response.getStatusCode(), 200);


//        JsonPath jsonPath4=new JsonPath(response.asString());
//        String resultcode4=jsonPath4.getString("result");
//        String subno=jsonPath4.getString("list.subno");
//        String contractNo=jsonPath4.getString("list.contractNo");
//        String area=jsonPath4.getString("list.area");
//        String connectionType=jsonPath4.getString("list.connectionType");
//        String substatus=jsonPath4.getString("list.status");
//        Assert.assertEquals(resultcode4,"SUCCESS");
//             Assert.assertEquals(aa,subno);
//             Assert.assertEquals(aa,contractNo);
//             Assert.assertEquals(aa,area);
//             Assert.assertEquals(aa,connectionType);
//             Assert.assertEquals(aa,substatus);

        //*******************************POST METHODS**********************************************************************************************************
        //---------------------------------------ActivateDetailBill-----------TELBIZ---------------------------------------------------------------------------
        given().
                when().
                contentType("application/json").
                header("Authorization", accessToken).
                body("{\"status\": \"1\" }").
                post("https://mife-stg.dil.com/apicall/crm/activateDetailBill/v1.0.0/rest/api/activateDetailBill/{mobilenum}",mobilenum);
        JsonPath jsonPathPost = new JsonPath(response.asString());
        String descriptione = jsonPathPost.getString("description");
        String r_code1 = jsonPathPost.getString("r_code");
        System.out.println(r_code1);
        System.out.println(descriptione);
        String result=jsonPathPost.getString("result");
        System.out.println(result);


            Assert.assertEquals(result, "SUCCESS");
            Assert.assertEquals(descriptione, "Detail Bill Activated successfully");
            System.out.println("my name:" + descriptione);


        }


@Test
    public void ActivaeEbill() throws Exception
    {
        QueryResult msisdn = SQLExecuter.getQueryResults(querymsisdnfromtelbiz);
        List<String> telbizArea = msisdn.getAllValues("AREA");
        List<String> telbizSubno = msisdn.getAllValues("SUBNO");
        telbizArea.addAll(telbizSubno);
        String mobilenum = telbizArea.toString().replaceAll("\\[|\\]|\"","").replaceAll(",","").replaceAll("\\s+","");
        useRelaxedHTTPSValidation();
        //---------------------------------------GetEbillRegStatusAPI--------TELBIZ------------------------------------------------------------------------------
        Response response = given().
                when().
                contentType("application/json").
                header("Authorization", "af6e6d30bbd6d04ff6f13aa429a7").
                body("{\"status\": \"1\" }").
                post("https://mife-stg.dil.com/apicall/crm/activateDetailBill/v1.0.0/rest/api/activateDetailBill/{mobilenum}",mobilenum);
        JsonPath jsonPathPost = new JsonPath(response.asString());
        String descriptione = jsonPathPost.getString("description");
        String r_code1 = jsonPathPost.getString("r_code");
        System.out.println(r_code1);
        System.out.println(descriptione);
        String result=jsonPathPost.getString("result");
        System.out.println(result);
        if (descriptione.equalsIgnoreCase("Already Active"))

        {
                given().
                    when().
                    contentType("application/json").
                    header("Authorization", "Bearer 7b7a50574eae6012dc8d559d1").
                    body("{\"status\": \"0\" }").
                    post("https://mife-stg.dil.com/apicall/crm/activateDetailBill/v1.0.0/rest/api/activateDetailBill/{mobilenum}",mobilenum);
               System.out.println(response.getBody());
               if(r_code1.equalsIgnoreCase("ERR-6")) {
                   given().
                           when().
                           contentType("application/json").
                           header("Authorization", "Bearer 7b7a50574eae6012d15ce71d1").
                           body("{\"status\": \"1\" }").
                           post("https://mife-stg.dil.com/apicall/crm/activateDetailBill/v1.0.0/rest/api/activateDetailBill/{mobilenum}", mobilenum);
                   System.out.println(response.getBody().asString());
                   Assert.assertEquals(result, "SUCCESS");
                   Assert.assertEquals(descriptione, "Detail Bill Activated successfully");
               }
        }

    else {
        Assert.assertEquals(result, "SUCCESS");
        Assert.assertEquals(descriptione, "Detail Bill Activated successfully");
        System.out.println("my name:" + descriptione);
//      Reporter.log("This is test cases kaushi");
         }
    }

}

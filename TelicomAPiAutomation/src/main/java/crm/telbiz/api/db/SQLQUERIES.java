package crm.telbiz.api.db;

public interface SQLQUERIES {

//    String GET_CONTRACT_LIST="select CONTRNO  from DLG_CONTRACT_DM  where REGISTRY_TYPE='TIN'AND CONTACT_PERSON Like '%s' and ROWNUM<='1'";
    String GET_MSISDN_Telbiz="select AREA,SUBNO from SUBSCRIBER where  contrno ='982462'";
    String GET_MSISDN_CCBS="select SUB_NUMBER  from DCS.SUBSCRIBER where SUB_NUMBER='0114615374'";
    String GET_EmailAddress_Telbiz="select EMAIL from SUBSCRIBER where CONTRNO='982462'";
    String Get_Contractnum_Telbiz="Select CONTRNO from SUBSCRIBER where AREA='011' and SUBNO='4184461'";
    String GET_Contronum_CCBS="select ACCOUNT_NUM  from DCS.SUBSCRIBER where SUB_NUMBER=0114615374";
    String Get_responsevalues_Telbiz="SELECT bb.area,bb.subno, cc.contrno,bb.CONTROL_EXCA, ss.STATTXT\n" +
            "FROM tims.contract cc, tims.subscriber bb, tims.sustat ss\n" +
            "WHERE bb.CONTROL_EXCA = 'LTEB' and cc.REGISTRY_TYPE= 'TIN' and ss.STATCODE = bb.STATUS /*and ss.STATCODE = '10' */and cc.contrno = bb.contrno  and cc.contrno = 'B723732'";
}

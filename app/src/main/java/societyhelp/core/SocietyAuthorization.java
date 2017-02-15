package societyhelp.core;

/**
 * Created by divang.sharma on 9/13/2016.
 */
public class SocietyAuthorization {

    public enum Type {
           ADMIN,                         //0
           MY_DUES_VIEWS,                 //1
           NOTIFICATION_SEND,             //2
           USER_DETAIL_VIEW,              //3
           USER_DETAIL_CREATE,            //4
           FLAT_DETAIL_VIEW,              //5
           FLAT_DETAIL_CREATE,            //6
           LOGIN_VIEW,                    //7
           LOGIN_CREATE,                  //8
           TRANSACTION_HOME_VIEW,         //9
           RAW_DATA_VIEW,                 //10
           TRANSACTIONS_DETAIL_VIEW,      //11
           PDF_TRANSACTION_VIEW,          //12
           PDF_TRANSACTION_UPLOAD_TO_STAGING_TABLE,     //13
           MAP_USER_WITH_MONTHLY_PDF_NAME,              //14
           MONTHLY_MAINTENANCE_GENERATOR,               //15
           VERIFIED_PDF_TRANSACTIONS_UPLOAD,            //16
           ADD_USER_EXPEND,                             //17
           VIEW_SPLIT_TRANSACTIONS,                     //18
           VIEW_REPORT,                                 //19
           MANUAL_SPLIT,                                //20
           ADD_WATER_READING,                           //21
           VIEW_WATER_READING                           //22
           //22,21,19,5,3,1
    }
}

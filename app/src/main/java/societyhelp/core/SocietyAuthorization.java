package societyhelp.core;

/**
 * Created by divang.sharma on 9/13/2016.
 */
public class SocietyAuthorization {

    public enum Type {
           ADMIN,
           MY_DUES_VIEWS,
           NOTIFICATION_SEND,
           USER_DETAIL_VIEW,
           USER_DETAIL_CREATE,
           FLAT_DETAIL_VIEW,
           FLAT_DETAIL_CREATE,
           LOGIN_VIEW,
           LOGIN_CREATE,
           MONTHLY_STATEMENT_UPLOAD,
           RAW_DATA_VIEW,
           TRANSACTIONS_DETAIL_VIEW,
           PDF_TRANSACTION_VIEW,
           PDF_TRANSACTION_UPLOAD_TO_STAGING_TABLE,
           MAP_USER_WITH_MONTHLY_PDF_NAME,
           MONTHLY_MAINTENANCE_GENERATOR,
           VERIFIED_PDF_TRANSACTIONS_UPLOAD,
           ADD_USER_EXPEND,
           VIEW_SPLIT_TRANSACTIONS
    }
}

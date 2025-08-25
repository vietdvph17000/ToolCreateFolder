package servlet;

import javax.management.Notification;
import javax.management.NotificationListener;

import job.TCMJobCreateFolder;
import job.TCMJobCreateFolderPath;

public class TCMNotificationGetDataListener implements NotificationListener {
public static final String XLY_TAO_TMUC_TCM= "XLY_TAO_TMUC_TCM";
public static final String XLY_TAO_DDAN_TMUC_TCM= "XLY_TAO_DDAN_TMUC_TCM";
    @Override
    public void handleNotification(Notification notif, Object handback) {
		if (XLY_TAO_TMUC_TCM.equals(notif.getType())) {
			TCMJobCreateFolder tcmjob = (TCMJobCreateFolder) notif.getUserData();
			try {
				tcmjob.TCMCenterProcess();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (XLY_TAO_DDAN_TMUC_TCM.equals(notif.getType())) {
			TCMJobCreateFolderPath tcmjob = (TCMJobCreateFolderPath) notif.getUserData();
			try {
				tcmjob.TCMCenterProcess();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
         
    }
    
}

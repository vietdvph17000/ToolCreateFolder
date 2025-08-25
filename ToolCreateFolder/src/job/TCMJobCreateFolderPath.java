package job;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.timer.Timer;

import createAndDeleteFolder.CreateFolderPathTCM;
import createAndDeleteFolder.CreateFolderTCM;
import dao.FolderTCMDao;
import dao.TCM_Connection;
import dto.JobParameterDTO;
import servlet.TCMNotificationGetDataListener;

public class TCMJobCreateFolderPath {
	public static final Date TCM_GET_DATE = new Date();
    public static final long TCM_TIME_LOOP = 10 * Timer.ONE_SECOND;
    private Timer timer;
    private Integer notifID;
    public static FolderTCMDao dao = new FolderTCMDao();
    public static void scheduleTask(Timer timer) {
    	TCMJobCreateFolderPath job = new TCMJobCreateFolderPath();
        Integer notifID;
        job.setTimer(timer);
        notifID = timer.addNotification(TCMNotificationGetDataListener.XLY_TAO_DDAN_TMUC_TCM, null, job,
        		job.TCM_GET_DATE, job.TCM_TIME_LOOP);
        job.setNotifID(notifID);
    }
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

	public void TCMCenterProcess() throws Exception {
		System.err.println("da bat dau chay job ------------CreateFolderPathTCM");
		Connection conn = null;
		try {
			// Sua lai luong: truyen conn chung, han che mo conn den DB
			conn = new TCM_Connection().getConnection();
			System.err.println("connn=>" + conn);
			Map<String, String> mapJob = new HashMap<String, String>();
			mapJob.put("param_code", "JOB_CREATE_PATH");
			String status_job = "OFF";
			JobParameterDTO jobParameter = dao.getJobParameter(conn,mapJob);
			if(jobParameter != null && jobParameter.getParam_value()!= null ) {
				status_job = jobParameter.getParam_value();
			}
			System.out.println("statusJob==>"+status_job);
			if (status_job.equals("ON")) {
				CreateFolderPathTCM startProcess = new CreateFolderPathTCM();
				startProcess.createFolderPath(conn);				
			}
		} catch (Exception ex) {
			System.out.println("CreateFolderTCM error");
			Logger.getLogger(TCMJobCreateFolderPath.class.getName()).log(Level.SEVERE, null, ex);
			try {
				if (conn != null && !conn.isClosed())
					conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			// System.out.println("[KDTCenterProcess] -> finally -> conn close: "+conn);
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
	}
	
    public Timer getTimer() {
        return timer;
    }

    public void setNotifID(Integer notifID) {
        this.notifID = notifID;
    }

    public Integer getNotifID() {
        return notifID;
    }
}

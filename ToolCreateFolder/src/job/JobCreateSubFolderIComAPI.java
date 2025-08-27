package job;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.timer.Timer;

import createAndDeleteFolder.CreateFolderIComAPI;
import createAndDeleteFolder.CreateSubFolderIComAPI;
import dao.FolderTCMDao;
import dao.TCM_Connection;
import dto.FolderDTO;
import dto.JobParameterDTO;
import dto.SubFolderDTO;
import servlet.TCMNotificationGetDataListener;

public class JobCreateSubFolderIComAPI {
	public static final Date TCM_GET_DATE = new Date();
    public static final long TCM_TIME_LOOP = 10 * Timer.ONE_SECOND;
    private Timer timer;
    private Integer notifID;
    public static FolderTCMDao dao = new FolderTCMDao();
    
    public static void scheduleTask(Timer timer) {
    	JobCreateSubFolderIComAPI jobGetHsoTCM = new JobCreateSubFolderIComAPI();
        Integer notifID;
        jobGetHsoTCM.setTimer(timer);
        notifID = timer.addNotification(TCMNotificationGetDataListener.XLY_TAO_TMUC_SUB_ICOM, null, jobGetHsoTCM,
                                  jobGetHsoTCM.TCM_GET_DATE, jobGetHsoTCM.TCM_TIME_LOOP);
        jobGetHsoTCM.setNotifID(notifID);
    }
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    private static final Logger LOGGER = Logger.getLogger(JobCreateSubFolderIComAPI.class.getName());

    public void TCMCenterProcess() {
        LOGGER.info("=== Bắt đầu chạy job: CreateSubFolderICOM ===");

        // dùng try-with-resources để tự động đóng conn
        try (Connection conn = new TCM_Connection().getConnection()) {

            Map<String, String> mapJob = new HashMap<>();
            mapJob.put("param_code", "JOB_CREATE_SUB_FOLDER_ICOM_API");

            String statusJob = "OFF";
            JobParameterDTO jobParameter = dao.getJobParameter(conn, mapJob);

            if (jobParameter != null && jobParameter.getParam_value() != null) {
                statusJob = jobParameter.getParam_value();
            }
            LOGGER.info("Trạng thái job: " + statusJob);
            LocalDateTime now = LocalDateTime.now();
            
            //chi chay ngay 25 cua thang
            if ("ON".equalsIgnoreCase(statusJob) && now.getDayOfMonth() == 27) {
                LOGGER.info("Job CreateSubFolderICOM hoàn tất thành công.");
				List<FolderDTO> listFolder = new ArrayList<>();
				listFolder = dao.getListFolderLast(conn);
				if (listFolder.size() > 0) {
					try {
						// creating a pool of n threads
						ExecutorService executor = Executors.newFixedThreadPool(5);
						while (!listFolder.isEmpty()) {
							Runnable worker = new CreateSubFolderIComAPI(listFolder, conn);
							executor.execute(worker);
						}
						executor.shutdown();
						while (!executor.isTerminated()) {
						}
					} catch (Exception ex) {
						System.out.println("error threads");
						ex.printStackTrace();
					}
				}
			
            } else {
                LOGGER.info("Job CreateSubFolderICOM đang OFF, không chạy.");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi chạy CreateSubFolderICOM", ex);
            // rollback nếu có lỗi
            try {
                // cần mở lại conn rollback khi dùng try-with-resources
                // -> nên xử lý rollback trong class CreateFolderTCM luôn
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Rollback thất bại", e);
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

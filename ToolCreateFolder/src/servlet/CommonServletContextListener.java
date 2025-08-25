package servlet;


import javax.management.timer.Timer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import job.TCMJobCreateFolder;
import job.TCMJobCreateFolderPath;

public class CommonServletContextListener implements ServletContextListener {
    protected ServletContext sc;
    protected Timer timer;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        sc = servletContextEvent.getServletContext();   
        scheduleTasks(sc);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        timer.stop();
        timer.removeAllNotifications();
        System.out.println("Stop job CreateFolderTCM-----------------------");
    }
     protected void scheduleTasks(ServletContext sc) {
        timer = new Timer();
        notifyTask(timer, sc);
        timer.start();
        System.out.println("Start job CreateFolderTCM-----------------------");
    }
   protected void notifyTask(Timer timer, ServletContext sc) {
        TCMNotificationGetDataListener lsnr = new TCMNotificationGetDataListener();
        timer.addNotificationListener(lsnr, null, null);
        TCMJobCreateFolder.scheduleTask(timer);
//        TCMJobCreateFolderPath.scheduleTask(timer);
    }
}

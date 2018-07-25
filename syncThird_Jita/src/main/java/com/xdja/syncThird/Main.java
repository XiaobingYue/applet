package com.xdja.syncThird;

import com.xdja.syncThird.service.SyncThirdService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Main方法
 */
public class Main {

    private static final Log logger = LogFactory.getLog(Main.class);
    // 线程睡眠时间，以分钟为单位
    private static long sleepTime = 10;

    public static void main(String[] args) {
        int relateCount = 0;
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SyncThirdService syncService = context.getBean(SyncThirdService.class);
        if (args != null && args.length >= 1) {
            String syncModel = args[0];
            if ("setLevel".equalsIgnoreCase(syncModel)){
                try {
                        logger.debug("开始设置部门等级操作");
                        syncService.setLevel();
                        logger.debug("设置部门等级操作结束");
                } catch (Exception e) {
                    logger.error("设置部门等级操作失败", e);
                }
            }else if ("sync".equalsIgnoreCase(syncModel)) {
                if (args.length >= 2) {
                    sleepTime = Long.parseLong(args[1]);
                }
                logger.debug("数据同步服务开启，将每隔"+sleepTime+"分钟进行一次数据同步");
                while (true) {
                    try {
                            logger.debug("同步单位到中间表开始");
                             syncService.syncDepartment();
                            logger.debug("同步单位到中间表结束");
                    } catch (Exception e) {
                        logger.error("同步单位到中间表失败", e);
                    }
                    try {
                            logger.debug("同步人员到中间表开始");
                            syncService.syncPerson();
                            logger.debug("同步人员到中间表结束");
                    } catch (Exception e) {
                        logger.error("同步人员到中间表失败", e);
                    }
                    try {
                            logger.debug("同步设备到中间表开始");
                            syncService.syncDevice();
                            logger.debug("同步设备到中间表结束");
                    } catch (Exception e) {
                        logger.error("同步设备到中间表失败", e);
                    }
                    logger.debug("同步结束，开始关联操作");
                    try {
                        logger.debug("开始关联部门操作");
                        relateCount = syncService.relateDep();
                        logger.debug("关联部门操作结束");
                    } catch (Exception e) {
                        logger.error("关联部门操作失败", e);
                    }
                    try {
                        if (relateCount > 0) {
                            logger.debug("开始设置部门等级操作");
                            syncService.setLevel();
                            logger.debug("设置部门等级操作结束");
                        }
                    } catch (Exception e) {
                        logger.error("设置部门等级操作失败", e);
                    }
                    try {
                        logger.debug("开始关联人员操作");
                        syncService.relatePerson();
                        logger.debug("关联人员操作结束");
                    } catch (Exception e) {
                        logger.error("关联人员操作失败", e);
                    }
                    try {
                        logger.debug("开始关联设备操作");
                        syncService.relateDevice();
                        logger.debug("关联设备操作结束");
                    } catch (Exception e) {
                        logger.error("关联设备操作失败", e);
                    }
                    try {
                        TimeUnit.MINUTES.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            logger.error("启动参数错误-sync");
        }
        context.close();
    }
}

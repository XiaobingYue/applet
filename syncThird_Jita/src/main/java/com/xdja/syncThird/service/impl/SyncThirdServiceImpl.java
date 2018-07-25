package com.xdja.syncThird.service.impl;

import com.xdja.syncThird.bean.ThirdDepartmentBean;
import com.xdja.syncThird.bean.ThirdDeviceBean;
import com.xdja.syncThird.bean.ThirdPersonBean;
import com.xdja.syncThird.common.Const;
import com.xdja.syncThird.dao.*;
import com.xdja.syncThird.entity.*;
import com.xdja.syncThird.service.SyncThirdService;
import com.xdja.syncThird.util.Md5PwdEncoder;
import com.xdja.syncThird.util.PinYin4j;
import com.xdja.syncThird.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfaster.mango.plugin.page.Page;
import org.jfaster.mango.transaction.Transaction;
import org.jfaster.mango.transaction.TransactionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yxb on  2018/7/2
 */
@Service
public class SyncThirdServiceImpl implements SyncThirdService {

    private static final Log logger = LogFactory.getLog(SyncThirdServiceImpl.class);
    private final JitaDao jitaDao;
    private final SyncRecordDao recordDao;
    private final ThirdDao thirdDao;
    private final DepartmentDao departmentDao;
    private final PersonDao personDao;
    private final DeviceDao deviceDao;

    @Autowired
    public SyncThirdServiceImpl(JitaDao jitaDao, SyncRecordDao recordDao, ThirdDao thirdDao, DepartmentDao departmentDao,
                                PersonDao personDao, DeviceDao deviceDao) {
        this.jitaDao = jitaDao;
        this.recordDao = recordDao;
        this.thirdDao = thirdDao;
        this.departmentDao = departmentDao;
        this.personDao = personDao;
        this.deviceDao = deviceDao;
    }

    @Override
    public void syncDepartment() {
        // 开启事务
        Transaction tx = TransactionFactory.newTransaction("oracle");
        try {
            ThirdSyncRecordEntity record = recordDao.getLatastRecord(Const.SYNC_TYPE_DEP);
            String lastUpdateTime = "0";
            String lastUpdateTimeNew = "0";
            if (record != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lastUpdateTimeNew = record.getLastUpdateTime();
                lastUpdateTime = sdf.format(new Date(Long.parseLong(record.getLastUpdateTime())));
            }
            int beginPageNum = 1;
            int count = 0;
            logger.info("开始分页同步单位数据到中间表，每次同步" + Const.SYNC_SIZE + "条记录");
            while (true) {
                Page page = Page.create(beginPageNum, Const.SYNC_SIZE);
                List<ThirdDepartmentBean> thirdDepartmentBeans = jitaDao.queryDepartmentByPage(lastUpdateTime, page);
                if (CollectionUtils.isEmpty(thirdDepartmentBeans)) {
                    logger.info("单位同步：未查到数据");
                    break;
                }
                count = count + thirdDepartmentBeans.size();
                logger.info("单位同步：第" + beginPageNum + "页，本页" + thirdDepartmentBeans.size() + "条，共计" + count + "条");
                beginPageNum++;
                lastUpdateTimeNew = this.depDataProcess(thirdDepartmentBeans);
                if (thirdDepartmentBeans.size() < Const.SYNC_SIZE) {
                    break;
                }
            }
            ThirdSyncRecordEntity newRecord = new ThirdSyncRecordEntity();
            newRecord.setCreateDate(new Date());
            newRecord.setLastUpdateTime(lastUpdateTimeNew);
            newRecord.setType(Const.SYNC_TYPE_DEP);
            recordDao.addRecord(newRecord);
        } catch (Exception e) {
            // 回滚事务
            tx.rollback();
            logger.error("单位同步：同步单位失败：" + e.getMessage(), e);
            throw e;
        } finally {
            // 提交事务
            tx.commit();
        }
    }

    private String depDataProcess(List<ThirdDepartmentBean> thirdDepartmentBeans) {
        String lastUpdateTime = "";
        for (ThirdDepartmentBean thirdDepartmentBean : thirdDepartmentBeans) {
            if (StringUtils.isBlank(thirdDepartmentBean.getThirdId())) {
                continue;
            }
            try {
                if (thirdDepartmentBean.getLastUpdateTimeDate() != null) {
                    lastUpdateTime = String.valueOf(thirdDepartmentBean.getLastUpdateTimeDate().getTime());
                }
                ThirdDepartmentEntity thirdDepartmentEntity = thirdDao.queryDepByThirdId(thirdDepartmentBean.getThirdId());
                if (thirdDepartmentEntity == null) {
                    // 三方表中该单位不存在，创建
                    thirdDepartmentEntity = new ThirdDepartmentEntity();
                    BeanUtils.copyProperties(thirdDepartmentBean, thirdDepartmentEntity);
                    thirdDepartmentEntity.setTimestamp(String.valueOf(thirdDepartmentBean.getLastUpdateTimeDate().getTime()));
                    thirdDepartmentEntity.setId(Utils.getUUID());
                    thirdDao.saveThirdDepartment(thirdDepartmentEntity);
                } else {
                    // 三方表中存在，说明以前同步过，进行更新操作
                    thirdDepartmentEntity.setCode(thirdDepartmentBean.getCode());
                    thirdDepartmentEntity.setName(thirdDepartmentBean.getName());
                    thirdDepartmentEntity.setShortName(thirdDepartmentBean.getShortName());
                    thirdDepartmentEntity.setParentCode(thirdDepartmentBean.getParentCode());
                    thirdDepartmentEntity.setThirdParentId(thirdDepartmentBean.getThirdParentId());
                    // depId设置为空，下次关联要用
                    thirdDepartmentEntity.setDepId(Const.EMP);
                    thirdDepartmentEntity.setErrInfo(Const.EMP);
                    thirdDepartmentEntity.setFlag(thirdDepartmentBean.getFlag());
                    thirdDepartmentEntity.setTimestamp(String.valueOf(thirdDepartmentBean.getLastUpdateTimeDate().getTime()));
                    thirdDao.updateThirdDepartment(thirdDepartmentEntity);
                }
            } catch (Exception e) {
                logger.error("同步单位：" + thirdDepartmentBean.getThirdId() + "到中间表失败：" + e.getMessage(), e);
            }
        }
        return lastUpdateTime;
    }

    @Override
    public void syncPerson() {
        // 开启事务
        Transaction tx = TransactionFactory.newTransaction("oracle");
        try {
            ThirdSyncRecordEntity record = recordDao.getLatastRecord(Const.SYNC_TYPE_PERSON);
            String lastUpdateTime = "0";
            String lastUpdateTimeNew = "0";
            if (record != null) {
                lastUpdateTimeNew = record.getLastUpdateTime();
                // 将上次同步时间转换为特定格式的字符串
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lastUpdateTime = sdf.format(new Date(Long.parseLong(record.getLastUpdateTime())));
            }
            int beginPageNum = 1;
            int count = 0;
            logger.info("开始分页同步人员数据到中间表，每次同步" + Const.SYNC_SIZE + "条记录");
            while (true) {
                Page page = Page.create(beginPageNum, Const.SYNC_SIZE);
                List<ThirdPersonBean> personBeans = jitaDao.queryPersonByPage(lastUpdateTime, page);
                if (CollectionUtils.isEmpty(personBeans)) {
                    logger.info("人员同步：未查到数据");
                    break;
                }
                count = count + personBeans.size();
                logger.info("人员同步：第" + beginPageNum + "页，本页" + personBeans.size() + "条，共计" + count + "条");
                beginPageNum++;
                lastUpdateTimeNew = this.personDataProcess(personBeans);
                if (personBeans.size() < Const.SYNC_SIZE) {
                    break;
                }
            }
            ThirdSyncRecordEntity newRecord = new ThirdSyncRecordEntity();
            newRecord.setCreateDate(new Date());
            newRecord.setLastUpdateTime(lastUpdateTimeNew);
            newRecord.setType(Const.SYNC_TYPE_PERSON);
            newRecord.setId(Utils.getUUID());
            recordDao.addRecord(newRecord);
        } catch (Exception e) {
            tx.rollback();
            logger.error("同步人员失败：" + e.getMessage(), e);
            throw e;
        } finally {
            tx.commit();
        }
    }

    private String personDataProcess(List<ThirdPersonBean> personBeans) {
        String lastUpdateTime = "0";
        for (ThirdPersonBean personBean : personBeans) {
            try {
                if (StringUtils.isBlank(personBean.getThirdId())) {
                    continue;
                }
                if (personBean.getLastUpdateTimeDate() != null) {
                    lastUpdateTime = String.valueOf(personBean.getLastUpdateTimeDate().getTime());
                }
                ThirdPersonEntity personEntity = thirdDao.queryPersonByThirdId(personBean.getThirdId());
                if (personEntity == null) {
                    personEntity = new ThirdPersonEntity();
                    BeanUtils.copyProperties(personBean, personEntity);
                    personEntity.setTimestamp(String.valueOf(personBean.getLastUpdateTimeDate().getTime()));
                    personEntity.setId(Utils.getUUID());
                    thirdDao.saveThirdPerson(personEntity);
                } else {
                    personEntity.setIdentifier(personBean.getIdentifier());
                    personEntity.setSex(personBean.getSex());
                    personEntity.setPolice(personBean.getPolice());
                    personEntity.setPosition(personBean.getPosition());
                    personEntity.setOfficePhone(personBean.getOfficePhone());
                    personEntity.setSort(personBean.getSort());
                    personEntity.setCode(personBean.getCode());
                    personEntity.setMobile(personBean.getMobile());
                    personEntity.setDepId(personBean.getDepId());
                    personEntity.setName(personBean.getName());
                    personEntity.setFlag(personBean.getFlag());
                    personEntity.setTfMobile(personBean.getTfMobile());
                    personEntity.setTfCommType(personBean.getTfCommType());
                    personEntity.setPosTfMobile(personBean.getPosTfMobile());
                    personEntity.setPosTfCommType(personBean.getPosTfCommType());
                    personEntity.setUsbKeyMobile(personBean.getUsbKeyMobile());
                    personEntity.setUsbKeyCommType(personBean.getUsbKeyCommType());
                    personEntity.setPersonId(Const.EMP);
                    personEntity.setErrInfo(Const.EMP);
                    personEntity.setTimestamp(String.valueOf(personBean.getLastUpdateTimeDate().getTime()));
                    personEntity.setPersonType(personBean.getPersonType());
                    personEntity.setCommType(personBean.getCommType());
                    thirdDao.updateThirdPerson(personEntity);
                }
            } catch (Exception e) {
                logger.error("同步人员【" + personBean.getThirdId() + "】到中间表失败：" + e.getMessage(), e);
            }
        }
        return lastUpdateTime;
    }


    /**
     * 人员类型代码转换
     *
     * @param personType 吉大人员类型
     * @return pams人员类型
     */
    private String formatPersonType(String personType) {
        if ("jingyuan".equals(personType) || "jy".equals(personType)) {
            personType = "1";
        } else {
            personType = "2";
        }
        return personType;
    }

    /**
     * 运营商代码转换
     *
     * @param commType 吉大运营商代码
     * @return pams运营商代码
     */
    private String formatCommType(String commType) {
        if (StringUtils.isBlank(commType)) {
            commType = Const.COMM_CODE_1;
        } else if (commType.contains("移动") || commType.contains("0001")) {
            commType = Const.COMM_CODE_1;
        } else if (commType.contains("联通") || commType.contains("0002")) {
            commType = Const.COMM_CODE_2;
        } else if (commType.contains("电信") || commType.contains("0003")) {
            commType = Const.COMM_CODE_3;
        } else {
            commType = Const.COMM_CODE_1;
        }
        return commType;
    }

    @Override
    public void syncDevice() {
        // 开启事务
        Transaction tx = TransactionFactory.newTransaction("oracle");
        try {
            ThirdSyncRecordEntity record = recordDao.getLatastRecord(Const.SYNC_TYPE_DEVICE);
            String lastUpdateTime = "0";
            String lastUpdateTimeNew = "0";
            if (record != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lastUpdateTimeNew = record.getLastUpdateTime();
                lastUpdateTime = sdf.format(new Date(Long.parseLong(record.getLastUpdateTime())));
            }
            int beginPageNum = 1;
            int count = 0;
            logger.info("开始分页同步设备数据到中间表，每次同步" + Const.SYNC_SIZE + "条记录");
            while (true) {
                Page page = Page.create(beginPageNum, Const.SYNC_SIZE);
                List<ThirdDeviceBean> deviceBeans = jitaDao.queryDeviceByPage(lastUpdateTime, page);
                if (CollectionUtils.isEmpty(deviceBeans)) {
                    logger.info("设备同步：未查到数据");
                    break;
                }
                count = count + deviceBeans.size();
                logger.info("设备同步：第" + beginPageNum + "页，本页" + deviceBeans.size() + "条，共计" + count + "条");
                beginPageNum++;
                lastUpdateTimeNew = this.deviceDataProcess(deviceBeans);
                if (deviceBeans.size() < Const.SYNC_SIZE) {
                    break;
                }
            }
            ThirdSyncRecordEntity newRecord = new ThirdSyncRecordEntity();
            newRecord.setCreateDate(new Date());
            newRecord.setLastUpdateTime(lastUpdateTimeNew);
            newRecord.setType(Const.SYNC_TYPE_DEVICE);
            recordDao.addRecord(newRecord);
        } catch (Exception e) {
            tx.rollback();
            logger.error("同步设备失败：" + e.getMessage(), e);
            throw e;
        } finally {
            tx.commit();
        }
    }

    private String deviceDataProcess(List<ThirdDeviceBean> deviceBeans) {
        String lastUpdateTime = "";
        for (ThirdDeviceBean deviceBean : deviceBeans) {
            if (StringUtils.isBlank(deviceBean.getThirdId())) {
                continue;
            }
            if (deviceBean.getLastUpdateTimeDate() != null) {
                lastUpdateTime = String.valueOf(deviceBean.getLastUpdateTimeDate().getTime());
            }
            ThirdDeviceEntity deviceEntity = thirdDao.queryDeviceByThirdId(deviceBean.getThirdId());
            if (deviceEntity == null) {
                deviceEntity = new ThirdDeviceEntity();
                BeanUtils.copyProperties(deviceBean, deviceEntity);
                deviceEntity.setTimestamp(String.valueOf(deviceBean.getLastUpdateTimeDate().getTime()));
                deviceEntity.setId(Utils.getUUID());
                thirdDao.saveThirdDevice(deviceEntity);
            } else {
                // 如果是修改 , 将原卡id保存在备份字段
                String deviceIdOrg = deviceEntity.getDeviceId();
                deviceEntity.setDeviceIdOrg(deviceIdOrg);
                deviceEntity.setCardNo(deviceBean.getCardNo());
                deviceEntity.setCertentityEncRSA(deviceBean.getCertentityEncRSA());
                deviceEntity.setCertentityEncSM2(deviceBean.getCertentityEncSM2());
                deviceEntity.setCertentitySignRSA(deviceBean.getCertentitySignRSA());
                deviceEntity.setCertentitySignSM2(deviceBean.getCertentitySignSM2());
                deviceEntity.setCertType(deviceBean.getCertType());
                deviceEntity.setDeviceId(Const.EMP);
                deviceEntity.setCardType(deviceBean.getCardType());
                deviceEntity.setErrInfo(Const.EMP);
                deviceEntity.setPersonId(deviceBean.getPersonId());
                deviceEntity.setSnEncCertRSA(deviceBean.getSnEncCertRSA());
                deviceEntity.setSnEncCertSM2(deviceBean.getSnEncCertSM2());
                deviceEntity.setSnSignCertRSA(deviceBean.getSnSignCertRSA());
                deviceEntity.setSnSignCertSM2(deviceBean.getSnSignCertSM2());
                deviceEntity.setFlag(deviceBean.getFlag());
                deviceEntity.setTimestamp(lastUpdateTime);
                thirdDao.updateThirdDevice(deviceEntity);
            }
        }
        return lastUpdateTime;
    }

    @Override
    public void setLevel() {
        Transaction tx = TransactionFactory.newTransaction("oracle");
        try {
            Department root = departmentDao.queryDepById(Const.ROOT_DEP_ID);
            List<Department> rootList = new ArrayList<>();
            rootList.add(root);
            this.setLevel(rootList);
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            tx.commit();
        }
    }

    private void setLevel(List<Department> list) {
        for (Department department : list) {
            int level;
            if (StringUtils.isBlank(department.getParentID())) {
                level = 1;
            } else {
                Department parentDep = departmentDao.queryDepById(department.getParentID());
                if (parentDep == null) level = 1;
                else level = Integer.parseInt(parentDep.getLevel()) + 1;
            }
            if (StringUtils.isBlank(department.getLevel()) || !department.getLevel().equals(level + "")) {
                department.setLevel(level + "");
                department.setTimestamp(System.currentTimeMillis());
                departmentDao.updateDepLevel(department);
            }
            List<Department> childDepList = departmentDao.queryChildDepListByParentId(department.getId());
            if (CollectionUtils.isEmpty(childDepList)) {
                continue;
            } else {
                this.setLevel(childDepList);
            }
        }
    }

    @Override
    public int relateDep() {
        int relateCount = 0;
        int beginNum = 1;
        int count = 0;
        while (true) {
            Page page = Page.create(beginNum, Const.SYNC_SIZE);
            // 每次只查第一页
            List<ThirdDepartmentEntity> departmentEntityList = thirdDao.queryUnRelatedDep(page);
            if (CollectionUtils.isEmpty(departmentEntityList)) {
                logger.debug("单位关联：无需要关联的数据");
                return 0;
            }
            count = count + departmentEntityList.size();
            // logger.info("单位关联：第" + beginNum + "页，本页" + departmentEntityList.size() + "条，共计" + count + "条");
            for (ThirdDepartmentEntity thirdDep : departmentEntityList) {
                Transaction tx = TransactionFactory.newTransaction("oracle");
                try {
                    Department department = departmentDao.queryByCode(thirdDep.getCode());
                    if ("1".equals(thirdDep.getFlag())) {
                        // 如果同步过来的单位是删除状态，删除pams本地单位
                        if (department != null && !Const.ROOT_DEP_ID.equals(department.getId()) && department.getFlag().equals("0")) {
                            // pams本地存在该单位，且不是顶级部门，则删除
                            department.setTimestamp(System.currentTimeMillis());
                            // 逻辑删除该部门及其所有子部门
                            departmentDao.deleteDepById(department);
                            logger.debug("单位关联：pams删除一个单位，code：" + department.getCode() + "，name：" + department.getName());
                            relateCount++;
                            thirdDep.setDepId(department.getId());
                        } else {
                            // pams本地无此单位，直接设置中间表deviceId为delete状态
                            thirdDep.setDepId(Const.STATE_DELETE);
                        }
                        thirdDao.updateThirdDepartment(thirdDep);
                        tx.commit();
                        continue;
                    }
                    if (department == null) {
                        // 本地没有该单位，则新增
                        department = new Department();
                        department.setCode(thirdDep.getCode());
                        department.setName(thirdDep.getName());
                        Department parentDep = this.getParentDep(thirdDep.getThirdParentId(), thirdDep.getParentCode());
                        department.setParentID(parentDep.getId());
                        department.setLevel((Integer.parseInt(parentDep.getLevel()) + 1) + "");
                        department.setId(Utils.getUUID());
                        departmentDao.saveDep(department);
                        logger.debug("单位关联：pams新增一个单位，code：" + department.getCode() + "，name：" + department.getName());
                        thirdDep.setDepId(department.getId());
                    } else {
                        department.setName(thirdDep.getName());
                        department.setNameAbbr(thirdDep.getShortName());
                        department.setTimestamp(System.currentTimeMillis());
                        department.setFlag(thirdDep.getFlag());
                        if (!Const.ROOT_DEP_ID.equals(department.getId())) {
                            Department parentDep = getParentDep(thirdDep.getThirdParentId(), thirdDep.getParentCode());
                            department.setParentID(parentDep.getId());
                            department.setLevel((Integer.parseInt(department.getParentDep().getLevel()) + 1) + "");
                            department.setDepType(Const.DEP_TYPE_DEPT);
                            departmentDao.updateDep(department);
                        } else {
                            department.setDepType(Const.DEP_TYPE_ORG);
                            departmentDao.updateDep(department);
                        }
                        logger.debug("单位关联：pams更新一个单位，code：" + department.getCode() + "，name：" + department.getName());
                        //使用单位编码关联， 重新设置关联ID
                        thirdDep.setDepId(department.getId());
                    }
                    relateCount++;
                } catch (Exception e) {
                    thirdDep.setDepId("error");
                    thirdDep.setErrInfo(e.getMessage());
                    logger.error("单位关联：关联单位失败：" + e.getMessage() + "，单位编码：" + thirdDep.getCode(), e);
                }
                thirdDao.updateThirdDepartment(thirdDep);
                tx.commit();
            }
            if (departmentEntityList.size() < Const.SYNC_SIZE) {
                break;
            }
        }
        return relateCount;
    }

    @Override
    public void relatePerson() {
        int beginNum = 1;
        int count = 0;
        while (true) {
            Page page = Page.create(beginNum, Const.SYNC_SIZE);
            List<ThirdPersonEntity> thirdPersonList = thirdDao.queryUnRelatedPerson(page);
            if (CollectionUtils.isEmpty(thirdPersonList)) {
                logger.debug("人员关联：无需要关联的数据");
                return;
            }
            count = count + thirdPersonList.size();
            logger.info("人员关联：本页" + thirdPersonList.size() + "条，共计" + count + "条");
            end:
            for (ThirdPersonEntity thirdPerson : thirdPersonList) {
                Transaction tx = TransactionFactory.newTransaction("oracle");
                try {
                    Person person = personDao.queryPersonByIdentifier(thirdPerson.getIdentifier().toUpperCase());
                    if ("1".equals(thirdPerson.getFlag())) {
                        if (person != null) {
                            person.setTimestamp(System.currentTimeMillis());
                            // 删除pams人员
                            this.deletePerson(person);
                            logger.debug("人员关联：pams删除一个人员，code：" + person.getCode() + "，name：" + person.getName());
                            thirdPerson.setPersonId(person.getId());
                        } else {
                            thirdPerson.setPersonId(Const.STATE_DELETE);
                        }
                        thirdDao.updateThirdPerson(thirdPerson);
                        tx.commit();
                        continue;
                    }
                    if (person == null) {
                        person = new Person();
                        if (StringUtils.isBlank(thirdPerson.getIdentifier())) {
                            logger.debug("人员关联：关联失败，人员身份证号为空，code：" + thirdPerson.getCode() + "，name：" + thirdPerson.getName());
                            thirdPerson.setPersonId("error");
                            thirdPerson.setErrInfo("person identifier is null");
                            thirdDao.updateThirdPerson(thirdPerson);
                            tx.commit();
                            continue;
                        }
                        person.setIdentifier(thirdPerson.getIdentifier());
                        if (StringUtils.isBlank(thirdPerson.getCode())) {
                            logger.debug("人员关联：关联失败，人员警号为空，identifier：" + thirdPerson.getIdentifier() + "，name：" + thirdPerson.getName());
                            thirdPerson.setPersonId("error");
                            thirdPerson.setErrInfo("person code is null");
                            thirdDao.updateThirdPerson(thirdPerson);
                            tx.commit();
                            continue;
                        } else {
                            person.setCode(thirdPerson.getCode());
                        }
                        person.setOfficePhone(thirdPerson.getOfficePhone());
                        Department personDep = this.getPersonDep(thirdPerson.getDepId());
                        if (personDep == null) {
                            logger.debug("人员关联：该人员所在部门不存在，默认放在无上级部门下，code：" + thirdPerson.getCode() + "，name：" + thirdPerson.getName());
                            personDep = this.getNotParentDep();
                        }
                        person.setDepId(personDep.getId());
                        person.setDepCode(personDep.getCode());
                        person.setName(thirdPerson.getName());

                        if (thirdPerson.getPersonType() != null && thirdPerson.getPersonType().length() <= 2) {
                            person.setPersonType(this.formatPersonType(thirdPerson.getPersonType()));
                        } else {
                            person.setPersonType("1");
                        }

                        if (thirdPerson.getCommType() != null && thirdPerson.getCommType().length() <= 1) {
                            person.setCommType(this.formatCommType(thirdPerson.getCommType()));
                        } else {
                            person.setCommType(Const.COMM_CODE_1);
                        }

                        if (thirdPerson.getPosition() != null && thirdPerson.getPosition().length() <= 4) {
                            person.setPosition(thirdPerson.getPosition());
                        } else {
                            person.setPosition("0");
                        }
                        if (thirdPerson.getPolice() != null && thirdPerson.getPolice().length() <= 4) {
                            person.setPolice(thirdPerson.getPolice());
                        } else {
                            person.setPolice("0");
                        }
                        if (thirdPerson.getSex() != null && thirdPerson.getSex().length() <= 2) {
                            person.setSex(thirdPerson.getSex());
                        } else {
                            person.setSex("0");
                        }
                        person.setId(Utils.getUUID());
                        String mobile = thirdPerson.getMobile();
                        if (StringUtils.isBlank(mobile)) {
                            if (StringUtils.isNotBlank(thirdPerson.getTfMobile())) {
                                mobile = thirdPerson.getTfMobile();
                            } else if (StringUtils.isNotBlank(thirdPerson.getPosTfMobile())) {
                                mobile = thirdPerson.getPosTfMobile();
                            } else if (StringUtils.isNotBlank(thirdPerson.getUsbKeyMobile())) {
                                mobile = thirdPerson.getUsbKeyMobile();
                            } else if (person.getCode() != null && person.getCode().length() <= 11) {
                                mobile = "13000000000".substring(0, 11 - person.getCode().length()) + person.getCode();
                            } else {
                                mobile = "13000000000";
                            }
                            thirdPerson.setMobile(mobile);
                        }
                        Set<String> mobileStrList = this.getMobileStrList(thirdPerson);
                        mobileStrList.add(mobile);
                        for (String str : mobileStrList) {
                            if (!mobile.contains(str)) {
                                // 将同步过来的thirdDevice.mobile字段内的手机号放在字符串首位
                                mobile = mobile + "," + str;
                            }
                        }
                        person.setMobile(mobile);
                        person.setCreatorId("0");
                        if (person.getIdentifier() != null) {
                            person.setIdentifier(person.getIdentifier().toUpperCase());
                        }
                        person.setPassword(Md5PwdEncoder.getInstance().encodePassword(
                                personDao.getValueByCode(Const.DEFAULT_PASS) == null ? "111111" : personDao.getValueByCode(Const.DEFAULT_PASS)));
                        person.setLockState(Const.USER_UNLOCK_STATE);
                        person.setFlag(Const.UN_DELETE_FLAG);
                        person.setCreateDate(new Date());
                        if (StringUtils.isBlank(person.getOrderField() + "")) {
                            person.setOrderField(999);
                        }
                        person.setGrade(Utils.checkLevel(person.getGrade(), personDep.getCode()) + "");
                        person.setNameBriefSpell(PinYin4j.getNameSimplicityWithPolyphone(person.getName()));
                        List<Mobile> mobileList = new ArrayList<>();
                        // 开始遍历新手机号
                        for (String mobileStr : mobileStrList) {
                            // 查询新手机号库中是否存在
                            Mobile m = personDao.queryByMobile(mobileStr);
                            if (m == null) {
                                // 不存在新增
                                m = new Mobile(Utils.getUUID(), mobileStr, person.getId());
                                mobileList.add(m);
                            } else {
                                // 存在说明该人员的手机号已被别人使用，关联失败
                                logger.debug("人员关联：关联失败，该手机号已经被其他人员使用，手机号：" + mobileStr + "，code：" + person.getCode() + "，name：" + person.getName());
                                thirdPerson.setPersonId("error");
                                thirdPerson.setErrInfo("mobile be used by others");
                                thirdDao.updateThirdPerson(thirdPerson);
                                tx.commit();
                                continue end;
                            }
                        }
                        // 先保存人员，后保存手机号
                        personDao.savePerson(person);
                        personDao.saveMobileBatch(mobileList);
                        thirdPerson.setPersonId(person.getId());
                        logger.debug("人员关联：新增一个人员，code：" + person.getCode() + "，name：" + person.getName());
                    } else {
                        // 人员存在，进行更新操作
                        if (StringUtils.isBlank(person.getCode())) {
                            logger.debug("人员关联：关联失败，人员警号为空，identifier：" + thirdPerson.getIdentifier() + "，name：" + thirdPerson.getName());
                            thirdPerson.setPersonId("error");
                            thirdPerson.setErrInfo("person code is null");
                            thirdDao.updateThirdPerson(thirdPerson);
                            tx.commit();
                            continue;
                        } else {
                            person.setCode(thirdPerson.getCode());
                        }
                        person.setFlag(thirdPerson.getFlag());
                        person.setOfficePhone(thirdPerson.getOfficePhone());
                        Department department = this.getPersonDep(thirdPerson.getDepId());
                        if (department == null) {
                            department = this.getNotParentDep();
                        }
                        person.setDepId(department.getId());
                        person.setDepCode(department.getCode());
                        person.setName(thirdPerson.getName());

                        if (thirdPerson.getPersonType() != null && thirdPerson.getPersonType().length() <= 2) {
                            person.setPersonType(this.formatPersonType(thirdPerson.getPersonType()));
                        } else {
                            person.setPersonType("1");
                        }
                        if (thirdPerson.getCommType() != null && thirdPerson.getCommType().length() <= 1) {
                            person.setCommType(this.formatCommType(thirdPerson.getCommType()));
                        } else {
                            person.setCommType("1");
                        }
                        if (thirdPerson.getPosition() != null && thirdPerson.getPosition().length() <= 4) {
                            person.setPosition(thirdPerson.getPosition());
                        } else {
                            person.setPosition("0");
                        }
                        if (thirdPerson.getPolice() != null && thirdPerson.getPolice().length() <= 4) {
                            person.setPolice(thirdPerson.getPolice());
                        } else {
                            person.setPolice("0");
                        }
                        if (thirdPerson.getSex() != null && thirdPerson.getSex().length() <= 2) {
                            person.setSex(thirdPerson.getSex());
                        } else {
                            person.setSex("0");
                        }
                        String mobileStr = thirdPerson.getMobile();
                        if (StringUtils.isBlank(mobileStr)) {
                            if (StringUtils.isNotBlank(thirdPerson.getTfMobile())) {
                                mobileStr = thirdPerson.getTfMobile();
                            } else if (StringUtils.isNotBlank(thirdPerson.getPosTfMobile())) {
                                mobileStr = thirdPerson.getPosTfMobile();
                            } else if (StringUtils.isNotBlank(thirdPerson.getUsbKeyMobile())) {
                                mobileStr = thirdPerson.getUsbKeyMobile();
                            } else if (person.getCode() != null && person.getCode().length() <= 11) {
                                mobileStr = "13000000000".substring(0, 11 - person.getCode().length()) + person.getCode();
                            } else {
                                mobileStr = "13000000000";
                            }
                            thirdPerson.setMobile(mobileStr);
                        }
                        // 获得该人员的旧手机号对象集合
                        List<Mobile> oldMobileList = personDao.queryPersonMobile(person.getId());
                        // 旧手机号集合
                        List<String> oldMobileStrList = new ArrayList<>();
                        for (Mobile mobile : oldMobileList) {
                            oldMobileStrList.add(mobile.getMobile());
                        }
                        // 新手机号集合
                        Set<String> newMobileStrSet = this.getMobileStrList(thirdPerson);
                        newMobileStrSet.add(mobileStr);
                        // 取差集获取需要更新的手机号
                        oldMobileStrList.removeAll(newMobileStrSet);
                        // 拼装新手机号，逗号隔开，放入mobile字段
                        for (String str : newMobileStrSet) {
                            if (!mobileStr.contains(str)) {
                                mobileStr = mobileStr + "," + str;
                            }
                        }
                        person.setMobile(mobileStr);
                        // 需要添加的手机对象集合
                        List<Mobile> newMobileList = new ArrayList<>();
                        // 不需要更新的手机对象集合
                        List<Mobile> unUpdateMobileList = new ArrayList<>();
                        // 循环遍历新手机号set集合，判断手机号是否别人在用
                        for (String mobile : newMobileStrSet) {
                            Mobile m = personDao.queryByMobile(mobile);
                            if (m != null && !m.getPersonId().equals(person.getId())) {
                                // 如果手机号不为空且手机号的关联人员不是该人员，说明该手机号已经被别人使用，关联失败
                                logger.debug("人员关联：，关联失败，该人员的手机号已被别人使用，code：" + person.getCode() + "，name：" + person.getName());
                                thirdPerson.setPersonId("error");
                                thirdPerson.setErrInfo("mobile have be used");
                                thirdDao.updateThirdPerson(thirdPerson);
                                tx.commit();
                                continue end;
                            } else if (m == null) {
                                // 如果手机号不存在，则新增一个该人员的手机号
                                m = new Mobile(Utils.getUUID(), mobile, person.getId());
                                newMobileList.add(m);
                            } else {
                                // 手机号存在且是本人的，说明该手机号无需更新
                                unUpdateMobileList.add(m);
                            }
                        }
                        // 更新需要更新的手机号的person_id为null
                        personDao.updateNeedUpdateMobile(oldMobileStrList, person.getId());
                        // 保存新的手机号
                        personDao.saveMobileBatch(newMobileList);
                        person.setCreatorId("0");
                        if (person.getIdentifier() != null) {
                            person.setIdentifier(person.getIdentifier().toUpperCase());
                        }
                        person.setNameBriefSpell(PinYin4j.getNameSimplicityWithPolyphone(person.getName()));
                        person.setTimestamp(System.currentTimeMillis());
                        personDao.updatePerson(person);
                        // 特殊处理，设置没有关联人员的手机号所关联的设备的手机为null
                        deviceDao.updateDeviceWhereMobileUnExist();
                        // 特殊处理，删除没有关联人员的手机号
                        personDao.deleteNoPersonMobile();
                        // 特殊处理，如果之前操作导致该人员的设备的mobile_id 为空，但是该设备还在用，将设备的手机号重新通过卡类型设置进去
                        List<Device> deviceList = deviceDao.queryNoMobileDeviceByPersonId(person.getId());
                        // 该人员现在的手机号集合
                        newMobileList.addAll(unUpdateMobileList);
                        // 循环遍历该人员的设备，为设备重新关联手机号
                        if (!CollectionUtils.isEmpty(deviceList)) {
                            for (Device device : deviceList) {
                                this.setDeviceMobileId(device, person, thirdPerson, newMobileList);
                                if (device.getMobileId() != null) {
                                    // 更新设备手机号
                                    deviceDao.updateDevice(device);
                                }
                                // 如果找不到手机号，说明该设备可能更换了手机号，等到设备关联时会再次进行关联
                            }
                        }
                        thirdPerson.setPersonId(person.getId());
                        logger.debug("人员关联：更新一个人员，code：" + person.getCode() + "，name：" + person.getName());
                    }
                } catch (Exception e) {
                    logger.error("人员关联：关联人员失败：" + e.getMessage() + "，code：" + thirdPerson.getCode() + "，name：" + thirdPerson.getName(), e);
                    thirdPerson.setPersonId("error");
                    thirdPerson.setErrInfo(e.getMessage());
                }
                thirdDao.updateThirdPerson(thirdPerson);
                tx.commit();
            }
            if (thirdPersonList.size() < Const.SYNC_SIZE) {
                break;
            }
        }
    }

    /**
     * 删除人员相关
     *
     * @param person 待删除人员
     */
    private void deletePerson(Person person) {
        /*
        1.删除人员信息
        2.撤销设备
        3.删除手机号
         */
        person.setFlag(Const.DELETE_FLAG);
        person.setTimestamp(System.currentTimeMillis());
        person.setMobile(null);
        // 设置人员的手机的person_id为空
        personDao.updateMobilePersonIdNull(person.getId());
        // 删除人员分组
        personDao.deletePersonGroup(person.getId());
        // 删除人员角色关系
        personDao.deletePersonRole(person.getId());
        // 删除人员管辖范围关系
        personDao.deletePersonControlDep(person.getId());
        // 删除人员管辖警种关系
        personDao.deletePersonControlPolice(person.getId());
        // 逻辑删除人员
        personDao.deletePersonById(person);
        // 撤销设备
        List<Device> deviceList = deviceDao.queryByPersonId(person.getId());
        if (!CollectionUtils.isEmpty(deviceList)) {
            for (Device device : deviceList) {
                this.deleteDevice(device);
            }
        }
        deviceDao.updateDeviceWhereMobileUnExist();
        // 删除手机号
        personDao.deleteNoPersonMobile();
    }

    @Override
    public void relateDevice() {
        int beginNum = 1;
        int count = 0;
        while (true) {
            Page page = Page.create(beginNum, Const.SYNC_SIZE);
            List<ThirdDeviceEntity> thirdDeviceList = thirdDao.queryUnRelatedDevice(page);
            if (CollectionUtils.isEmpty(thirdDeviceList)) {
                logger.debug("设备关联：无需要关联的设备信息");
                return;
            }
            count = count + thirdDeviceList.size();
            logger.info("设备关联：本页" + thirdDeviceList.size() + "条，共计" + count + "条");
            for (ThirdDeviceEntity thirdDevice : thirdDeviceList) {
                Transaction tx = TransactionFactory.newTransaction("oracle");
                try {
                /*
                 * 由于国密接口读取的硬件编号是24位，需要补全32，补全规则
				 * 新卡 TF、USBKEY 前补 78646A61
				 * 旧卡 2011 年出厂  前补 AC485010，后24位以1500开头
				 *     2013 年出厂  前补 140C0311，后24位已000A开头
				 * 其他暂未处理，统一前补78646A61
				 */

                    String cardNo = thirdDevice.getCardNo();
                    if (StringUtils.isBlank(cardNo)) {
                        // 添加兼容吉大撤卡记录
                        String deviceId = thirdDevice.getDeviceIdOrg();
                        if (!StringUtils.isBlank(deviceId) && "1".equals(this.formatCardState(thirdDevice.getFlag()))) {
                            // 已经撤销的 , 并且之前已经同步到的卡, 且不是撤销状态的卡，将卡撤销
                            Device deviceHaveRevoked = deviceDao.queryById(deviceId);
                            this.deleteDevice(deviceHaveRevoked);
                            thirdDevice.setDeviceId(deviceId);
                        } else {
                            // 卡号为空 或者 还未同步的卡但已经撤销的信息, 按照错误处理 ,
                            thirdDevice.setErrInfo("card no can not be null");
                            thirdDevice.setDeviceId("error-nocardno");
                        }
                        thirdDao.updateThirdDevice(thirdDevice);
                        tx.commit();
                        continue;
                    }
                    if (cardNo.length() < 32) {
                        if (cardNo.startsWith("1500")) {
                            cardNo = "AC485010" + cardNo;
                        } else if (cardNo.toUpperCase().startsWith("000A")) {
                            cardNo = "140C0311" + cardNo;
                        } else {
                            cardNo = "78646A61" + cardNo;
                        }
                        if (cardNo.length() > 32) {
                            cardNo = cardNo.substring(0, 32).toUpperCase();
                        }
                    }
                    Device device = deviceDao.getByCardNO(cardNo);
                    if (device == null) {
                        //如果32位硬件编号查不到，查询24位硬件编号，
                        device = deviceDao.getByCardNO(thirdDevice.getCardNo());
                    }
                    if ("1".equals(this.formatCardState(thirdDevice.getFlag()))) {
                        //如果已删除 或注销，删除pams
                        if (device != null) {
                            this.deleteDevice(device);
                            thirdDevice.setDeviceId(device.getId());
                        } else {
                            thirdDevice.setDeviceId("delete");
                        }
                        thirdDao.updateThirdDevice(thirdDevice);
                        tx.commit();
                        continue;
                    }
                    if (device == null) {
                        //不存在，创建
                        device = new Device();
                        device.setId(Utils.getUUID());
                        device.setHardNo(cardNo);
                        device.setIccid(cardNo);
                        device.setState(this.formatCardState(thirdDevice.getFlag()));
                        //device.setState(Const.CARD_STATE_PAUSE);
                        ThirdPersonEntity thirdPerson = thirdDao.queryPersonByThirdId(thirdDevice.getPersonId());
                        if (thirdPerson == null || thirdPerson.getPersonId() == null || "delete".equals(thirdPerson.getPersonId())) {
                            logger.debug("设备关联：设备关联失败，三方表中该设备所绑定的人员不存在或者是已删除状态");
                            thirdDevice.setErrInfo("third person is is not exist or deleted");
                            thirdDevice.setDeviceId("error-nothirdperson");
                            thirdDao.updateThirdDevice(thirdDevice);
                            tx.commit();
                            continue;
                        }
                        Person person = personDao.queryPersonById(thirdPerson.getPersonId());
                        if (person == null) {
                            logger.debug("设备关联：设备关联失败，pams中该设备所绑定的人员不存在或者是已删除状态");
                            thirdDevice.setErrInfo("pams person is is not exist or deleted");
                            thirdDevice.setDeviceId("error-nopamsperson");
                            thirdDao.updateThirdDevice(thirdDevice);
                            tx.commit();
                            continue;
                        }
                        device.setPersonId(person.getId());
                        List<Mobile> mobiles = personDao.queryPersonMobile(person.getId());
                        if (CollectionUtils.isEmpty(mobiles)) {
                            logger.debug("设备关联：设备关联失败，该设备所绑定的人员无手机号");
                            thirdDevice.setErrInfo("mobile can not be null");
                            thirdDevice.setDeviceId("error-nomobile");
                            thirdDao.updateThirdDevice(thirdDevice);
                            tx.commit();
                            continue;
                        }
                        device.setWriteCardDate(new Date());
                        device.setType(this.formatCardType(thirdDevice.getCardType()));
                        if (Const.CARD_TYPE_TF.equals(device.getType()) || Const.CARD_TYPE_ACE.equals(device.getType())) {
                            device.setUseType(Const.CARD_USE_TYPE_PHONE);
                        } else if (Const.CARD_TYPE_USBKEY.equals(device.getType()) || Const.CARD_TYPE_TF_JG.equals(device.getType())) {
                            device.setUseType(Const.CARD_USE_TYPE_JG);
                        }
                        if (Const.CARD_TYPE_TF_JG.equals(device.getType())) {
                            //吉林如果状态为5 则为tf卡加固使用
                            device.setType(Const.CARD_TYPE_TF);
                        }
                        // 根据卡类型设置device的手机号和运营商
                        this.setDeviceMobileId(device, person, thirdPerson, mobiles);
                        if (device.getMobileId() == null) {
                            logger.debug("设备关联：设备关联失败，设备的绑定的手机号类型在pams中找不到");
                            thirdDevice.setErrInfo("error type mobile");
                            thirdDevice.setDeviceId("error-error type mobile");
                            thirdDao.updateThirdDevice(thirdDevice);
                            tx.commit();
                            continue;
                        }
                        String sn = "";
                        String certificate = "";
                        if (thirdDevice.getSnEncCertSM2() != null && thirdDevice.getSnEncCertSM2().length() > 0) {
                            sn = thirdDevice.getSnEncCertSM2();
                        } else if (thirdDevice.getSnSignCertSM2() != null && thirdDevice.getSnSignCertSM2().length() > 0) {
                            sn = thirdDevice.getSnSignCertSM2();
                        } else if (thirdDevice.getSnEncCertRSA() != null && thirdDevice.getSnEncCertRSA().length() > 0) {
                            sn = thirdDevice.getSnEncCertRSA();
                        } else if (thirdDevice.getSnSignCertRSA() != null && thirdDevice.getSnSignCertRSA().length() > 0) {
                            sn = thirdDevice.getSnSignCertRSA();
                        }

                        if (thirdDevice.getCertentityEncSM2() != null && thirdDevice.getCertentityEncSM2().length() > 0) {
                            certificate = thirdDevice.getCertentityEncSM2();
                        } else if (thirdDevice.getCertentitySignSM2() != null && thirdDevice.getCertentitySignSM2().length() > 0) {
                            certificate = thirdDevice.getCertentitySignSM2();
                        } else if (thirdDevice.getCertentityEncRSA() != null && thirdDevice.getCertentityEncRSA().length() > 0) {
                            certificate = thirdDevice.getCertentityEncRSA();
                        } else if (thirdDevice.getCertentitySignRSA() != null && thirdDevice.getCertentitySignRSA().length() > 0) {
                            certificate = thirdDevice.getCertentitySignRSA();
                        }
                        device.setSn(sn);
                        device.setCertificate(certificate);
                        Terminal terminal = new Terminal();
                        terminal.setTerminalband("0");
                        terminal.setTerminalname("0");
                        terminal.setTerminalos("0");
                        terminal.setTerminaltype("1");
                        terminal.setId(Utils.getUUID());
                        device.setTerminalId(terminal.getId());
                        // 保存终端信息
                        deviceDao.saveTerminal(terminal);
                        // 保存设备信息
                        deviceDao.saveDevice(device);
                        thirdDevice.setDeviceId(device.getId());
                        logger.debug("设备关联：新增一个设备，deviceId：" + device.getId());
                    } else {
                        //pams存在，更新pams
                        device.setHardNo(cardNo);
                        device.setIccid(cardNo);
                        device.setState(this.formatCardState(thirdDevice.getFlag()));
                        device.setType(this.formatCardType(thirdDevice.getCardType()));
                        if (Const.CARD_TYPE_TF.equals(device.getType()) || Const.CARD_TYPE_ACE.equals(device.getType())) {
                            device.setUseType(Const.CARD_USE_TYPE_PHONE);
                        } else if (Const.CARD_TYPE_USBKEY.equals(device.getType()) || Const.CARD_TYPE_TF_JG.equals(device.getType())) {
                            device.setUseType(Const.CARD_USE_TYPE_JG);
                        }
                        if (Const.CARD_TYPE_TF_JG.equals(device.getType())) {
                            //吉林如果状态为5 则为tf卡加固使用
                            device.setType(Const.CARD_TYPE_TF);
                        }
                        ThirdPersonEntity thirdPerson = thirdDao.queryPersonByThirdId(thirdDevice.getPersonId());
                        if (thirdPerson == null || thirdPerson.getPersonId() == null || "delete".equals(thirdPerson.getPersonId())) {
                            logger.debug("设备关联：设备关联失败，三方表中该设备所绑定的人员不存在或是已删除状态");
                            thirdDevice.setErrInfo("third person is is not exist or deleted");
                            thirdDevice.setDeviceId("error-nothirdperson");
                            thirdDao.updateThirdDevice(thirdDevice);
                            tx.commit();
                            continue;
                        }
                        Person person = personDao.queryPersonById(thirdPerson.getPersonId());
                        if (person == null) {
                            logger.debug("设备关联：设备关联失败，pams人员表中该设备所绑定的人员不存在或者是已删除状态");
                            thirdDevice.setErrInfo("pams person is is not exist or deleted");
                            thirdDevice.setDeviceId("error-nopamsperson");
                            thirdDao.updateThirdDevice(thirdDevice);
                            tx.commit();
                            continue;
                        }
                        device.setPersonId(person.getId());
                        List<Mobile> mobiles = personDao.queryPersonMobile(person.getId());
                        if (mobiles == null || mobiles.size() < 1) {
                            logger.debug("设备关联：设备关联失败，该设备所绑定的人员无手机号");
                            thirdDevice.setErrInfo("mobile can not be null");
                            thirdDevice.setDeviceId("error-nomobile");
                            thirdDao.updateThirdDevice(thirdDevice);
                            tx.commit();
                            continue;
                        }
                        // 根据卡类型设置device的手机号
                        this.setDeviceMobileId(device, person, thirdPerson, mobiles);
                        if (device.getMobileId() == null) {
                            logger.debug("设备关联：设备关联失败，设备的绑定的手机号类型在pams中找不到");
                            thirdDevice.setErrInfo("error type mobile");
                            thirdDevice.setDeviceId("error-error type mobile");
                            thirdDao.updateThirdDevice(thirdDevice);
                            tx.commit();
                            continue;
                        }
                        device.setRevokeFlag("");
                        String sn = "";
                        String certificate = "";
                        if (thirdDevice.getSnEncCertSM2() != null && thirdDevice.getSnEncCertSM2().length() > 0) {
                            sn = thirdDevice.getSnEncCertSM2();
                        } else if (thirdDevice.getSnSignCertSM2() != null && thirdDevice.getSnSignCertSM2().length() > 0) {
                            sn = thirdDevice.getSnSignCertSM2();
                        } else if (thirdDevice.getSnEncCertRSA() != null && thirdDevice.getSnEncCertRSA().length() > 0) {
                            sn = thirdDevice.getSnEncCertRSA();
                        } else if (thirdDevice.getSnSignCertRSA() != null && thirdDevice.getSnSignCertRSA().length() > 0) {
                            sn = thirdDevice.getSnSignCertRSA();
                        }

                        if (thirdDevice.getCertentityEncSM2() != null && thirdDevice.getCertentityEncSM2().length() > 0) {
                            certificate = thirdDevice.getCertentityEncSM2();
                        } else if (thirdDevice.getCertentitySignSM2() != null && thirdDevice.getCertentitySignSM2().length() > 0) {
                            certificate = thirdDevice.getCertentitySignSM2();
                        } else if (thirdDevice.getCertentityEncRSA() != null && thirdDevice.getCertentityEncRSA().length() > 0) {
                            certificate = thirdDevice.getCertentityEncRSA();
                        } else if (thirdDevice.getCertentitySignRSA() != null && thirdDevice.getCertentitySignRSA().length() > 0) {
                            certificate = thirdDevice.getCertentitySignRSA();
                        }
                        device.setSn(sn);
                        device.setCertificate(certificate);
                        device.setWriteCardDate(new Date());
                        //使用单位编码关联， 重新设置关联ID
                        device.setTimestamp(System.currentTimeMillis());
                        deviceDao.updateDevice(device);
                        thirdDevice.setDeviceId(device.getId());
                        logger.debug("设备关联：更新了一个设备，deviceId：" + device.getId());
                    }
                } catch (Exception e) {
                    logger.error("关联设备信息失败：" + e.getMessage(), e);
                    thirdDevice.setDeviceId("error");
                    thirdDevice.setErrInfo(e.getMessage());
                }
                thirdDao.updateThirdDevice(thirdDevice);
                tx.commit();
            }
            if (thirdDeviceList.size() < Const.SYNC_SIZE) {
                break;
            }
        }
    }

    private void deleteDevice(Device deviceHaveRevoked) {
        // 卡状态为撤销时不需要再进行撤销操作
        if (deviceHaveRevoked != null && !Const.CARD_REVOKE.equals(deviceHaveRevoked.getRevokeFlag())) {
            deviceHaveRevoked.setIccid("");
            deviceHaveRevoked.setHardNo("");
            deviceHaveRevoked.setImei("");
            deviceHaveRevoked.setImsi("");
            deviceHaveRevoked.setSn("");
            deviceHaveRevoked.setCertificate("");
            deviceHaveRevoked.setSn2("");
            deviceHaveRevoked.setCertificate2("");
            deviceHaveRevoked.setMobileId(null);
            deviceHaveRevoked.setState(Const.CARD_STATE_REVOKE);
            deviceHaveRevoked.setRevokeFlag(Const.CARD_REVOKE);
            deviceHaveRevoked.setRevocationDate(new Date());
            deviceHaveRevoked.setTimestamp(System.currentTimeMillis());
            deviceDao.updateDevice(deviceHaveRevoked);
        }
    }

    /**
     *卡状态转换
     *
     * @param flag 吉大卡状态：0：使用中，1：冻结，2：注销
     * @return pams卡状态：0-未审批 1-已审批 2-已写卡 3 -已开通 4-暂停 11-撤销
     */
    private String formatCardState(String flag) {
        if ("0".equals(flag)) {
            return "3";
        } else if ("1".equals(flag)) {
            return "4";
        } else {
            return "1";
        }
    }

    /**
     * 根据卡类型设置设备的关联手机号
     *
     * @param device      关联设备
     * @param person      已关联人员
     * @param thirdPerson 已关联三方表中人员
     * @param mobiles     人员手机号集合
     */
    private void setDeviceMobileId(Device device, Person person, ThirdPersonEntity thirdPerson, List<Mobile> mobiles) {
        switch (device.getType()) {
            case Const.CARD_TYPE_TF:
                if (Const.CARD_USE_TYPE_JG.equals(device.getUseType())) {
                    // 加固用POS的手机号
                    this.setMobileAndCommType(person, device, mobiles, thirdPerson.getPosTfMobile(), thirdPerson.getMobile(), thirdPerson.getPosTfCommType());
                } else {
                    this.setMobileAndCommType(person, device, mobiles, thirdPerson.getTfMobile(), thirdPerson.getMobile(), thirdPerson.getTfCommType());
                }
                break;
            case Const.CARD_TYPE_ACE:
                this.setMobileAndCommType(person, device, mobiles, thirdPerson.getPosTfMobile(), thirdPerson.getMobile(), thirdPerson.getPosTfCommType());
                break;
            case Const.CARD_TYPE_USBKEY:
                this.setMobileAndCommType(person, device, mobiles, thirdPerson.getUsbKeyMobile(), thirdPerson.getMobile(), thirdPerson.getUsbKeyCommType());
                break;
            default:
                device.setMobileId(null);
                break;
        }
    }

    /**
     * 转换卡类型
     *
     * @param cardType 吉大卡类型
     * @return pams卡类型
     */
    private String formatCardType(String cardType) {
        if (StringUtils.isBlank(cardType)) {
            return Const.CARD_TYPE_USBKEY;
        }
        if (cardType.toUpperCase().contains("笔记本TF卡")) {
            return Const.CARD_TYPE_TF_JG;
        } else if (cardType.toUpperCase().contains("TF卡")
                || cardType.toUpperCase().equals("信大捷安")
                || cardType.toUpperCase().contains("贴膜卡")) {
            return Const.CARD_TYPE_TF;
        } else if (cardType.toUpperCase().contains("POS")) {
            return Const.CARD_TYPE_ACE;
        } else if (cardType.toUpperCase().contains("USBKEY")) {
            return Const.CARD_TYPE_USBKEY;
        } else {
            return Const.CARD_TYPE_USBKEY;
        }
    }

    /**
     * 根据特定的手机号给device设置mobileId
     * <p>
     * 如果根据特定类型的手机号查不到该手机号对象，
     * 则用thirdPerson中的mobile字段中的手机号查询，设置运营商为person.commType（兼容广东）
     *
     * @param person        pams人员
     * @param device        device
     * @param mobiles       人员手机号集合
     * @param mobile        特定手机号
     * @param originMobile  thirdPerson.mobile
     * @param thirdCommType 特定运营商
     */
    private void setMobileAndCommType(Person person, Device device, List<Mobile> mobiles, String mobile, String originMobile, String thirdCommType) {
        if (StringUtils.isNotBlank(mobile)) {
            for (Mobile m : mobiles) {
                if (mobile.equals(m.getMobile())) {
                    device.setMobileId(m.getId());
                    device.setCommType(this.formatCommType(thirdCommType));
                    return;
                }
            }
        }
        // 如果tfMobile/posMobile/usbKeyMobile为空，用原来的thirdPerson.mobile里的手机号进行关联，运营商选人员字段下的运营商
        if (StringUtils.isNotBlank(originMobile)) {
            for (Mobile m : mobiles) {
                if (m.getMobile().equals(originMobile)) {
                    device.setMobileId(m.getId());
                    device.setCommType(person.getCommType());
                    return;
                }
            }
        }
        device.setMobileId(null);
    }

    /**
     * 获取手机号set集合
     *
     * @param thirdPerson 三方表中待关联人员
     * @return 机号set集合
     */
    private Set<String> getMobileStrList(ThirdPersonEntity thirdPerson) {
        Set<String> mobileStrList = new HashSet<>();
        if (StringUtils.isNotBlank(thirdPerson.getTfMobile())) {
            mobileStrList.add(thirdPerson.getTfMobile());
        }
        if (StringUtils.isNotBlank(thirdPerson.getPosTfMobile())) {
            mobileStrList.add(thirdPerson.getPosTfMobile());
        }
        if (StringUtils.isNotBlank(thirdPerson.getUsbKeyMobile())) {
            mobileStrList.add(thirdPerson.getUsbKeyMobile());
        }
        return mobileStrList;
    }

    /**
     * 获取一个人的部门
     *
     * @param depId 第三方部门id
     * @return pams部门实体
     */
    private Department getPersonDep(String depId) {
        ThirdDepartmentEntity thirdDep = thirdDao.queryDepByThirdId(depId);
        if (thirdDep == null) {
            return null;
        }
        return departmentDao.queryDepById(thirdDep.getDepId());
    }

    /**
     * 获取父级部门（递归获取），如果父级部门未关联，则先进行关联
     *
     * @param thirdParentId 三方父级id
     * @param parentCode    父级部门编号
     * @return 已关联的父级部门
     */
    private Department getParentDep(String thirdParentId, String parentCode) {
        Department dep;
        ThirdDepartmentEntity thirdDep = null;
        if (StringUtils.isNotBlank(thirdParentId)) {
            thirdDep = thirdDao.getThirdDepByThirdId(thirdParentId);
        }
        if (thirdDep == null && StringUtils.isNotBlank(parentCode)) {
            thirdDep = thirdDao.getThirdDepByThirdCode(parentCode);
        }
        if (thirdDep == null) {
            //如果父级单位不存在 返回null
            return getNotParentDep();
        }
        if (thirdDep.getId().equals(thirdParentId)) {
            //如果父级单位时自身，跳出递归
            return getNotParentDep();
        }
        if ("1".equals(thirdDep.getFlag())) {
            //如果父级单位已删除，返回null
            return getNotParentDep();
        }
        if (StringUtils.isNotBlank(thirdDep.getDepId())) {
            //父级单位已关联单位
            dep = departmentDao.queryDepById(thirdDep.getDepId());
            if ("1".equals(dep.getFlag())) {
                //如果父级单位已删除
                return getNotParentDep();
            }
            return dep;
        }

        //父级单位未关联单位
        dep = departmentDao.queryByCode(thirdDep.getCode());
        if (dep == null) {
            //单位在pams不中不存在，新建单位
            dep = new Department();
            dep.setCode(thirdDep.getCode());
            dep.setName(thirdDep.getName());
            dep.setNameAbbr(thirdDep.getShortName());
            dep.setId(Utils.getUUID());
            if (!Const.ROOT_DEP_ID.equals(dep.getId())) {
                Department parentDep = getParentDep(thirdDep.getThirdParentId(), thirdDep.getParentCode());
                dep.setParentID(parentDep.getId());
                dep.setParentDep(parentDep);
            }
            int level = 1;
            if (dep.getParentDep() != null) {
                level = Integer.parseInt(dep.getParentDep().getLevel()) + 1;
            }
            dep.setLevel(String.valueOf(level));
            if (StringUtils.isBlank(dep.getOrderField() + "")) {
                dep.setOrderField(999);
            }
            dep.setFlag(Const.UN_DELETE_FLAG);
            dep.setDepType(Const.DEP_TYPE_DEPT);
            departmentDao.saveDep(dep);
            logger.debug("单位关联：pams新增一个单位，code：" + dep.getCode() + "，name：" + dep.getName());
        } else {
            dep.setCode(thirdDep.getCode());
            dep.setName(thirdDep.getName());
            dep.setNameAbbr(thirdDep.getShortName());
            dep.setTimestamp(System.currentTimeMillis());
            int level = 1;
            if (!Const.ROOT_DEP_ID.equals(dep.getId())) {
                Department department = getParentDep(thirdDep.getThirdParentId(), thirdDep.getParentCode());
                dep.setParentID(department.getId());
                level = Integer.parseInt(department.getLevel()) + 1;
                dep.setLevel(level + "");
                dep.setDepType(Const.DEP_TYPE_DEPT);
                departmentDao.updateDep(dep);
            } else {
                dep.setDepType(Const.DEP_TYPE_ORG);
                departmentDao.updateDep(dep);
            }
            logger.debug("单位关联：pams更新一个单位，code：" + dep.getCode() + "，name：" + dep.getName());
        }

        thirdDep.setDepId(dep.getId());
        thirdDao.updateThirdDepartment(thirdDep);
        return dep;
    }

    /**
     * 获取无上级单位
     *
     * @return 无上级单位
     */
    private Department getNotParentDep() {
        String code = "999999999990";
        Department dep = departmentDao.queryByCode(code);
        if (dep == null) {
            dep = new Department();
            dep.setCode(code);
            dep.setName("无上级单位数据");
            dep.setParentID(Const.ROOT_DEP_ID);
            dep.setLevel("2");
            dep.setOrderField(999);
            dep.setId(Utils.getUUID());
            departmentDao.saveDep(dep);
        }
        return dep;
    }
}

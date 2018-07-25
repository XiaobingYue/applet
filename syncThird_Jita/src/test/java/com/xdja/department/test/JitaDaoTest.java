package com.xdja.department.test;

import com.xdja.syncThird.bean.ThirdDepartmentBean;
import com.xdja.syncThird.bean.ThirdDeviceBean;
import com.xdja.syncThird.bean.ThirdPersonBean;
import com.xdja.syncThird.dao.JitaDao;
import com.xdja.syncThird.entity.Department;
import com.xdja.syncThird.entity.ThirdDepartmentEntity;
import com.xdja.syncThird.entity.ThirdDeviceEntity;
import org.jfaster.mango.plugin.page.Page;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by yxb on  2018/7/4
 */
public class JitaDaoTest {

    private JitaDao jitaDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context;
        String[] xmlPaths = new String[]{"applicationContext.xml"};
        context = new ClassPathXmlApplicationContext(xmlPaths);
        jitaDao=context.getBean(JitaDao.class);
    }

    @Test
    public void testQueryDepartmentByPage() {
        Page page = Page.create(1,10);
        List<ThirdDepartmentBean> depList = jitaDao.queryDepartmentByPage("0",page);
        System.out.println(depList.size());
    }

    @Test
    public void testQueryPersonByPage() {
        Page page = Page.create(1,10);
        List<ThirdPersonBean> personList = jitaDao.queryPersonByPage("0",page);
        System.out.println(personList.size());
    }

    @Test
    public void testQueryDeviceByPage() {
        Page page = Page.create(1,10);
        List<ThirdDeviceBean> depList = jitaDao.queryDeviceByPage("0",page);
        System.out.println(depList.size());
    }
}

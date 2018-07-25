package com.xdja.department.test;

import com.xdja.syncThird.dao.DepartmentDao;
import com.xdja.syncThird.entity.Department;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yxb on  2018/7/4
 */
public class DepartmentTest {

    private DepartmentDao departmentDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context;
        String[] xmlPaths = new String[]{"applicationContext.xml"};
        context = new ClassPathXmlApplicationContext(xmlPaths);
        departmentDao=context.getBean(DepartmentDao.class);
    }

    @Test
    public void testQueryByCode() {
        Department department = departmentDao.queryByCode("410000000020");
        System.out.println(department);

    }
}

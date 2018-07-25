package com.xdja.syncThird.service;

import java.text.ParseException;

/**
 * Created by yxb on  2018/7/2
 */
public interface SyncThirdService {


    void syncDepartment() throws ParseException;

    void syncPerson();

    void syncDevice();

    void setLevel();

    int relateDep();

    void relatePerson();

    void relateDevice();
}

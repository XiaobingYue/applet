package com.xdja.syncThird.common;

/**
 * Created by yxb on 2018/6/27
 */
public class Const {
    /**
     * 每次同步记录数
     */
    public final static int SYNC_SIZE = 1000;

    /**
     * 同步类型：部门
     */
    public final static String SYNC_TYPE_DEP = "dep";

    /**
     * 同步类型，人员
     */
    public final static String SYNC_TYPE_PERSON = "person";

    /**
     * 同步类型：设备
     */
    public final static String SYNC_TYPE_DEVICE= "device";

    /**
     * 空字符串
     */
    public final static String EMP = "";

    /**
     * 顶级部门id
     */
    public static final String ROOT_DEP_ID="8888888888";

    /**
     * 已删除状态
     */
    public static final String STATE_DELETE="delete";

    /**
     * 未删除状态
     */
    public static final String UN_DELETE_FLAG="0";

    /**
     * 删除状态
     */
    public static final String DELETE_FLAG="1";

    /**
     * pams默认密码code
     */
    public static final String DEFAULT_PASS = "defaultPass";

    /**
     * 用户未锁定状态
     */
    public static final String USER_UNLOCK_STATE="0";

    /**
     * 设备已撤销状态
     */
    public static final String CARD_STATE_REVOKE="11";

    /**
     *撤销标记
     */
    public static final String CARD_REVOKE="1";

    /**
     * 卡类型 TF5加固
     */
    public static final String CARD_TYPE_TF_JG="5";
    /**
     * 卡类型 TF3
     */
    public static final String CARD_TYPE_TF="3";
    /**
     * 卡类型 ACE4
     */
    public static final String CARD_TYPE_ACE="4";
    /**
     * 安全卡用途 手机用
     */
    public static final String CARD_USE_TYPE_PHONE = "0";
    /**
     * 卡类型 USBKEY2
     */
    public static final String CARD_TYPE_USBKEY="2";
    /**
     * 安全卡用途 加固用
     */
    public static final String CARD_USE_TYPE_JG = "1";

    /**
     * MYSQL数据库
     */
    public static final String DATA_SOURCE_TYPE_MYSQL = "MYSQL";
    /**
     * ORACLE数据库
     */
    public static final String DATA_SOURCE_TYPE_ORACLE = "ORACLE";
    /**
     * SQLSERVER数据库
     */
    public static final String DATA_SOURCE_TYPE_MSSQL = "MSSQL";

    /**
     * 运营商代号：移动
     */
    public static final String COMM_CODE_1 = "1";
    /**
     * 运营商代号：联通
     */
    public static final String COMM_CODE_2 = "2";
    /**
     * 运营商代号：电信
     */
    public static final String COMM_CODE_3 = "3";

    /**
     * 部门类型：org：初始化部门，dept：普通部门
     */
    public static final String DEP_TYPE_ORG = "org";

    public static final String DEP_TYPE_DEPT = "dept";


}

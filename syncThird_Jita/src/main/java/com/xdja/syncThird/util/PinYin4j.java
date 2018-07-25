package com.xdja.syncThird.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yxb on  2018/7/5
 */
public class PinYin4j {

    private static final Logger log = LoggerFactory.getLogger(PinYin4j.class);

    public static final String Separator = "#";

    /**
     * 获取汉字的首字母简拼。英文字符不变<br>
     * 本方法不考虑多音字的情况.执行效率高
     *
     * @param chinese
     *            汉字串
     * @return 汉语拼音首字母
     */
    public static String getNameSimplicity(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();// 汉语拼音格式输出类
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音的大小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 是否有第几声的要求
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {// 如果不是ASCII码，就进行获取拼音操作
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat);
                    if (_t != null) {
                        pybf.append(_t[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    log.error(e.getMessage(), e);
                }
            } else {// ASCII码的话，直接加进去返回
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字的配音。英文字符不变<br>
     * 本方法不考虑多音字的情况.执行效率高
     *
     * @param chinese
     *            汉字串
     * @return 汉语拼音首字母
     */
    public static String getNamePinyin(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();// 汉语拼音格式输出类
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音的大小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 是否有第几声的要求
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {// 如果不是ASCII码，就进行获取拼音操作
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat);
                    if (_t != null) {
                        pybf.append(_t[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    log.error(e.getMessage(), e);
                }
            } else {// ASCII码的话，直接加进去返回
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字的首字母简拼。英文字符不变<br>
     * 本方法对多音字进行处理，如果有多音字的词，返回多种结果。结果以给定的字符串分隔
     *
     * @param chinese
     *            汉字串
     * @return 汉语拼音首字母
     */
    public static String getNameSimplicityWithPolyphone(String chinese) {
        String[][] pinyinArr = getSimplicity(getPinyin(chinese));
        if (pinyinArr == null) {
            return null;
        }
        String[][] rtnArr = doAssemble(pinyinArr);
        StringBuffer sb = new StringBuffer();
        Set<String> set = new HashSet<String>();
        for (String str : rtnArr[0]) {
            set.add(str);
        }
        for (String str : set) {
            sb.append(str).append(Separator);
        }
        String rtnStr = sb.toString();
        if (rtnStr.length() > 0) {
            rtnStr = rtnStr.substring(0, rtnStr.length() - Separator.length());
        }
        return rtnStr;
    }

    /**
     * 获取汉字的拼音。英文字符不变<br>
     * 本方法对多音字进行处理，如果有多音字的词，返回多种结果。结果以给定的字符串分隔
     *
     * @param chinese
     *            汉字串
     * @return 汉语拼音首字母
     */
    public static String getNamePinyinWithPolyphone(String chinese) {
        String[][] pinyinArr = getPinyin(chinese);
        if (pinyinArr == null) {
            return null;
        }
        String[][] rtnArr = doAssemble(pinyinArr);
        StringBuffer sb = new StringBuffer();
        for (String str : rtnArr[0]) {
            sb.append(str).append(Separator);
        }
        String rtnStr = sb.toString();
        if (rtnStr.length() > 0) {
            rtnStr = rtnStr.substring(0, rtnStr.length() - Separator.length());
        }
        return rtnStr;

    }

    /**
     * 将保存字符串拼音的数组转换为保存字符串拼音首字母的数组
     *
     * @param pinyinArr
     * @return
     */
    private static String[][] getSimplicity(String[][] pinyinArr) {
        for (int i = 0; i < pinyinArr.length; i++) {
            for (int j = 0; j < pinyinArr[i].length; j++) {
                if (pinyinArr[i][j] != null && pinyinArr[i][j].length() > 0) {
                    pinyinArr[i][j] = pinyinArr[i][j].substring(0, 1);
                }

            }
        }
        return pinyinArr;
    }

    /**
     * 执行拼音字符串的组装工作。使用迭代的方法，将数组中前两组的数据组合，放到第一组。去掉第二组。然后返回组装后的数组。
     *
     * @param pinyinArr
     * @return
     */
    private static String[][] doAssemble(String[][] pinyinArr) {
        int len = pinyinArr.length;
        if (len >= 2) {// 将第一行和第二行的数据组合，并放入第一行中
            int len1 = pinyinArr[0].length;
            int len2 = pinyinArr[1].length;
            int newLen = len1 * len2;
            String[] temp = new String[newLen];

            int index = 0;
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    temp[index] = pinyinArr[0][i] + pinyinArr[1][j];
                    index++;
                }
            }
            String[][] newArray = new String[len - 1][];
            for (int i = 2; i < len; i++) {
                newArray[i - 1] = pinyinArr[i];
            }
            newArray[0] = temp;
            return doAssemble(newArray);
        }
        return pinyinArr;
    }

    /**
     * 获取字符串的拼音，返回对应的拼音数组<br>
     * 方法支持多音字的处理
     *
     * @param chinese
     *            待处理字符串
     * @return 返回对应的拼音二维数组，第一维表示对应位数的汉字，第二维表示该汉字的多个读音（如果存在的话）
     */
    private static String[][] getPinyin(String chinese) {
        if (chinese != null && !chinese.trim().equals("")) {
            char[] srcChar = chinese.toCharArray();
            String[][] temp = new String[chinese.length()][];

            HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();// 汉语拼音格式输出类
            hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 大小写
            hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 是否加声调
            hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);// ü用什么表示，这里用V

            for (int i = 0; i < srcChar.length; i++) {
                char charTemp = srcChar[i];
                if (String.valueOf(charTemp).matches("[\\u4E00-\\u9FA5]+")) {
                    try {
                        temp[i] = PinyinHelper.toHanyuPinyinStringArray(
                                charTemp, hanYuPinOutputFormat);
                        if (temp[i] == null) {
                            temp[i] = new String[] { "" };
                        }

                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        log.error(e.getMessage(), e);
                    }
                } else if ((charTemp >= 65 && charTemp <= 90)
                        || (charTemp >= 97 && charTemp <= 122)) {
                    temp[i] = new String[] { String.valueOf(srcChar[i]) };
                } else {
                    temp[i] = new String[] { "" };
                }
            }
            return temp;
        }
        return null;
    }
}

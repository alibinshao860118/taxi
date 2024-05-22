package com.alibinshao.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {


    public static final String DATETIME_PATTERN = "yyyyMMddHHmmss";
    public static final String DATETIME_PATTERN_TWO = "yyyy-MM-dd HH:mm:ss";

    /**
     * 校验手机号是否以规定前缀开头
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        int[] phoneNum = {144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 155, 156, 157, 158, 159, 165, 166, 167, 17, 18, 191, 195, 198, 199, 13, 141};
        for (int i : phoneNum
        ) {
            if (phone.startsWith(i + "")) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取当前时间（yyyyMMddHHmmss）
     *
     * @return yyyyMMddHHmmss
     */
    public static String getCurrentTime(){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return formatter.format(LocalDateTime.now());
    }


    /**
     * 获取当前时间（yyyyMMddHHmmssSSS）
     *
     * @return yyyyMMddHHmmssSSS
     */
    public static String getCurrentTimes(){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return formatter.format(LocalDateTime.now());
    }


    /**
     * 获取当前日期（yyyyMMdd）
     *
     * @return yyyyMMdd
     */
    public static String getCurrentDate(){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(LocalDate.now());
    }


    /**
     * 时间格式转换
     *
     * @param oldDateFormat 旧时间格式
     * @param newDateFormat 新时间格式
     * @param convertedData 转换时间内容
     * @return newFormatTime/newFormatData
     */
    public static String formatConversion(String oldDateFormat, String newDateFormat, String convertedData) throws ParseException {
        DateTimeFormatter oldFormatter= DateTimeFormatter.ofPattern(oldDateFormat);
        DateTimeFormatter newFormatter= DateTimeFormatter.ofPattern(newDateFormat);
        return newFormatter.format(oldFormatter.parse(convertedData));
    }

    /**
     * 时间格式转换
     *
     * @param convertedData 转换时间内容 格式： yyyyMMddHHmmss
     * @return  返回格式：yyyy-MM-dd HH:mm:ss
     */
    public static String formatConversion(String convertedData){
        DateTimeFormatter oldFormatter= DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        DateTimeFormatter newFormatter= DateTimeFormatter.ofPattern(DATETIME_PATTERN_TWO);
        return newFormatter.format(oldFormatter.parse(convertedData));
    }

    /**
     * 时间戳转换时间格式
     *
     * @param timeStamp 时间戳
     * @param format 转换格式
     * @return formatTimes
     */
    public static String timeStamp2Time(Long timeStamp, String... format) {
        String defaultFormat = "yyyyMMddHHmmssSSS";
        if (timeStamp == null || timeStamp == 0) {
            return "";
        }
        if (format != null && format.length>0) {
            defaultFormat = format[0];
        }
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern(defaultFormat);
        return formatter.format(LocalDateTime.ofEpochSecond(timeStamp/1000,(int)(timeStamp%1000*1000000), ZoneOffset.ofHours(+8)));
    }

    public static String getExceptionInfo(Exception e) {
        StringBuilder exceptionInfo = new StringBuilder();
        try {
            if (e == null) {
                return "";
            }
            exceptionInfo.append(e.toString()).append(":");
            int size = Math.min(e.getStackTrace().length, 5);
            for (int i = 0; i < size; i++) {
                exceptionInfo.append(e.getStackTrace()[i].toString());
            }
        } catch (Exception e0) {
        }
        return exceptionInfo.toString().replace("\n|\r|\n\r", "");
    }

    public static String uuid() {


        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 切分字符串列表
     * @param sourceList
     * @return
     */
    public static List<List<String>> splitListString(List<String> sourceList,int num){
        List<List<String>>  total = new LinkedList<>();
        List<String> list ;
        int size = sourceList.size();
        int times = size%num==0?((int)size/num):(int)(size/num+1);
        for (int i = 0;i<times;i++){
            if(i==(times-1)){
                list = sourceList.subList(num * i, size);
            }else{
                list = sourceList.subList(num*i,num*(i+1));
            }
            total.add(list);
        }
        return total;
    }

    /**
     * 员工号校验(4到16位并且由英文、数字、下划线等组成)
     * @param empNo
     * @return
     */
    public static boolean validateEmpNo(String empNo) {
        String regex = "^[a-zA-Z0-9_-]{4,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(empNo);
        return matcher.matches();
    }
    /**
     * 检验数据合法性
     * @param telOrEmp
     * @return
     */
    public static Boolean checkTelOrEmp(String telOrEmp,String dataType) {
        //员工号合法性判断
        if ("1".equals(dataType)) {
            return validateEmpNo(telOrEmp);
        } else {//手机号合法性
            String regex = "^[1][3-9][0-9]{9}$";
            if (StringUtils.isBlank(telOrEmp)){
                return false;
            } else {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(telOrEmp);
                if (!matcher.matches()) {
                    return false;
                }
                return true;
            }
        }
    }

    /**
     * 替换富文本图片url
     * @param context 富文本内容
     * @param oldChar 被替换字符
     * @param newChar 替换字符
     * @return
     */
    public static String replaceRichImgUrl(String context, String oldChar, String newChar){
        String result = context;
        Set<String> set = regexUtil(context, "src=\"?(.*?)(\"|>|\\s+)");
        for (String path : set) {
            String path2 = path.replace(oldChar, newChar);
            result = result.replaceAll(path, path2);
        }

        return result;
    }

    /**
     * 根据表达式获取匹配内容
     * @param context
     * @param regex
     * @return
     */
    public static Set<String> regexUtil(String context, String regex){
        Set<String> set = new HashSet<String>();
        Matcher m = Pattern.compile(regex).matcher(context);
        while(m.find()){
            set.add(m.group(1));
        }
        return set;
    }

    /**
     * 计算环比百分率，返回乘以100的结果
     * 环比增长速度=（本期数－上期数）÷上期数×100%
     * @param total 分母
     * @param part 分子
     * @param scale 保留小数位
     * @return
     */
    public static String calPercent(Integer total, Integer part, int scale) {

        if (part == null) {
            part = 0;
        }
        //相等则返回0
        if (total.equals(part)){
            return "0.00";
        }
        //分母为零时，直接返回100
        if (total == null || total.intValue() == 0) {
            return "100";
        }
        Integer sub = part-total;
        //本期数－上期数=0视为负增长
        if (sub == 0){
            return "-100";
        }
        if (scale < 0) {
            scale = 2;
        }

        return calculateResult(sub,total,scale);
    }

    /**
     * 计算百分比(四舍五入)
     * @param sub
     * @param total
     * @return
     */
    public static String calculateResult(Integer sub, Integer total, int scale) {
        BigDecimal decimal = new BigDecimal("100");
        return new BigDecimal(sub).multiply(decimal).divide(new BigDecimal(total), scale, BigDecimal.ROUND_HALF_UP).toString();
    }
}

package com.loan.system_loan.pojo;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class IDCardUtils {
    /**
     * 从身份证号中提取出生日期
     * @param idCard 身份证号
     * @return 出生日期 (格式: YYYY-MM-DD)
     */
    public static String extractBirthday(String idCard) {
        if (idCard == null || idCard.length() < 18) {
            throw new IllegalArgumentException("身份证号格式不正确");
        }
        String birthDateStr = idCard.substring(6, 14); // 提取第7到14位
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
        return birthDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 根据出生日期计算年龄
     * @param birthDate 出生日期 (格式: YYYY-MM-DD)
     * @return 年龄
     */
    public static int calculateAge(String birthDate) {
        LocalDate birth = LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate now = LocalDate.now();
        return Period.between(birth, now).getYears();
    }
}

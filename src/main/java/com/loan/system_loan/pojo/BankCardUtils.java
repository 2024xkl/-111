package com.loan.system_loan.pojo;

import java.util.Random;

public class BankCardUtils {
    // 生成六位银行卡号
    public static String generateBankCardNumber() {
        Random random = new Random();
        int cardNumber = 100000 + random.nextInt(900000);  // 生成一个6位数的数字
        return String.valueOf(cardNumber);
    }
}

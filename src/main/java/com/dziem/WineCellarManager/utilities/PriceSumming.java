package com.dziem.WineCellarManager.utilities;

import java.math.BigDecimal;

public class PriceSumming {
    public static String sumPrice(String p1, String p2) {
        BigDecimal price1 = new BigDecimal(p1.replace("$", ""));
        BigDecimal price2 = new BigDecimal(p2.replace("$", ""));
        BigDecimal summedPrice = price1.add(price2);
        return "$" + summedPrice;
    }
}

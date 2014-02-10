/**
 * hisuda copy right 1.0 
 */
package com.buyi.dal.entity.viewobject;

import java.math.BigDecimal;

/**
 * 
 * 
 * Money.java
 * 
 * @author cxf128
 */
public class Money {

    public static final Double COUNT_NUM = 100d;

    private final Integer money;

    /**
     * @param money 单位分
     */
    public Money(Integer money) {
        this.money = money;
    }

    public Money add(Money money) {
        int current = this.getAmount();
        int add = money.getAmount();
        return new Money(current + add);
    }

    public Money subtract(Money money) {
        int current = this.getAmount();
        int sub = money.getAmount();
        int total = current - sub;
        if (total < 0) {
            return null;
        }

        return new Money(total);
    }

    public String convertString() {
        BigDecimal bigDecimal = new BigDecimal(money / COUNT_NUM);
		return  bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 返回所有金额：单位分
     * @return
     */
    public Integer getAmount() {
        return money;
    }

    @Override
    public String toString() {
		return "￥" + convertString();
    }

}

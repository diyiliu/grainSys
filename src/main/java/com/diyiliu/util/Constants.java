package com.diyiliu.util;

/**
 * Description: Constants
 * Author: DIYILIU
 * Update: 2017-07-25 08:55
 */
public final class Constants {

    public enum StockState {
        DEBT(0, "未支付"), PAID(1, "已结算");

        StockState(int index, String name) {
            this.index = index;
            this.name = name;
        }

        private String name;
        private int index;

        // 普通方法
        public static String getName(int index) {
            for (StockState s : StockState.values()) {
                if (s.index == index) {
                    return s.name;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

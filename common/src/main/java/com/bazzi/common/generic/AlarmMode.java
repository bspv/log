package com.bazzi.common.generic;

public enum AlarmMode {
    EMAIL{
        public String toString() {
            return "邮件";
        }
    },
    SMS{
        public String toString() {
            return "短信";
        }
    },
    WECHAT{
        public String toString() {
            return "微信";
        }
    }
}

package com.csbaic.jd.extend.sms.gen;

public class RandomValidateCodeGenerator implements SmsValidateCodeGenerator {

    private final int CODE_LEN = 4;

    @Override
    public String generate() {

        StringBuilder code = new StringBuilder();

        for(int index = 0; index < CODE_LEN ; index++) {
            code.append((int) (Math.random() * 10));
        }
        return code.toString();
    }
}

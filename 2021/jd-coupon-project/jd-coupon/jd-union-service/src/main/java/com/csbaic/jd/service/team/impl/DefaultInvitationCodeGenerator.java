package com.csbaic.jd.service.team.impl;


import com.csbaic.jd.service.team.InvitationCodeGenerator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/***
 * 邀请码生成器
 */
@Component
public class DefaultInvitationCodeGenerator implements InvitationCodeGenerator {

    private static final String TABLE = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 生成code长度
     */
    private static final int LENGTH_OF_CODE = 6;

    @Override
    public String generate(Long uniqueId) {
        if(uniqueId == null){
            throw new IllegalStateException("uniqueId不能为空");
        }

//        log.info("generate invitation code for {}", uniqueId);

        StringBuffer buffer = new StringBuffer(LENGTH_OF_CODE);
        //反转uniqueId
//        long reveredUniqueId = Long.MAX_VALUE - uniqueId;
//        uniqueId = Math.max(reveredUniqueId, uniqueId);
        do {

            long mod = uniqueId % TABLE.length();
            buffer.append(TABLE.charAt((int) mod));
            uniqueId = uniqueId / TABLE.length();

        }while (uniqueId != 0 && buffer.length() < LENGTH_OF_CODE);

        //位数不够，随机数补全
        for(int index = buffer.length() ; index < LENGTH_OF_CODE; index++){
            buffer.append(TABLE.charAt(0));
        }
//        log.info("invitation code {}", buffer.toString());



        return buffer.toString();
    }


    public static void main(String[] args){
        DefaultInvitationCodeGenerator generator = new DefaultInvitationCodeGenerator();
        Set<String> codeSet = new HashSet<>();
        for(long i = 1239385766574223361L; i < 2239385766574223361L ; i++){
            String code  = generator.generate(i);
            if(codeSet.contains(code)){
                throw new IllegalStateException(code + ":" + i);
            }

            codeSet.add(code);
            System.out.println(code);
        }
    }
}

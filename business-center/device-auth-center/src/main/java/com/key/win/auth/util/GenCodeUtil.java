package com.key.win.auth.util;

import com.key.win.basic.util.DefaultIdentifierGeneratorUtils;
import org.springframework.util.Base64Utils;

import java.util.*;

/**
 * 邀请码生成器，算法原理：<br/>
 * 1) 获取id: 1127738 <br/>
 * 2) 使用自定义进制转为：gpm6 <br/>
 * 3) 转为字符串，并在后面加'o'字符：gpm6o <br/>
 * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br/>
 * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br/>
 */
public class GenCodeUtil {

    /**
     * 自定义进制(0,1没有加入,容易与o,l混淆)
     */
    private static final char[] r = new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};

    /**
     * (不能与自定义进制有重复)
     */
    private static final char b = 'o';

    /**
     * 进制长度
     */
    private static final int binLen = r.length;

    /**
     * 序列最小长度
     */
    private static final int s = 6;

    /**
     * 根据ID生成六位随机码
     *
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / binLen) > 0) {
            int ind = (int) (id % binLen);
            // System.out.println(num + "-->" + ind);
            buf[--charPos] = r[ind];
            id /= binLen;
        }
        buf[--charPos] = r[(int) (id % binLen)];
        // System.out.println(num + "-->" + num % binLen);
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for (int i = 1; i < s - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str += sb.toString();
        }
        return str;
    }

    public static long codeToId(String code) {
        char chs[] = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < binLen; j++) {
                if (chs[i] == r[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == b) {
                break;
            }
            if (i > 0) {
                res = res * binLen + ind;
            } else {
                res = ind;
            }
            // System.out.println(ind + "-->" + res);
        }
        return res;
    }

    public static void main(String[] args) throws InterruptedException {
//        Set<String> ss = new HashSet<>();
//        for (int i = 0; i < 10000; i++) {
//            Long generatorLongId = DefaultIdentifierGeneratorUtils.getGeneratorLongId();
//            System.out.println(generatorLongId);
//            String s = toSerialCode(DefaultIdentifierGeneratorUtils.getGeneratorLongId());
//            System.out.println(s);
//            ss.add(s);
//        }
//        System.out.println(ss.size());
        for (int i = 0; i < 10000; i++) {

            long time = new Date().getTime();
            // 使用基本型的编码器和解码器 对数据进行编码和解码
            //1.获取编码器
            Base64.Encoder encoder = Base64.getEncoder();
            //2.对字符串进行编码
            String str = time + "";//"张三";5byg5LiJ
            String s = encoder.encodeToString(str.getBytes());
            //3.输出编码后的字符串
            System.out.println("编码后的字符串：" + new StringBuffer(s).reverse().toString());

            //4.获取解码器
            Base64.Decoder decoder = Base64.getDecoder();
            //5.对编码后的字符串进行解码
            byte[] decode = decoder.decode(s);
            String s1 = new String(decode);
            //6.打印输出解码后的字符串
            System.out.println("解码后的字符串："+s1);
            Thread.sleep(1);
        }


    }
}
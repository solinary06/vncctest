package com.yeahmobi.vncctest.encrypt;

import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by qinpeng on 16/3/3.
 */
public class AppTypeEncryptUtil {

  private static Random random = new Random();

  public static String encode(AppTypeEnum type, int appId, int offerId) {

    int index = random.nextInt(EncryptKeys.SECRET_SELECTION_SEED);

    int high = type.getKey();

    high ^= appId;
    high ^= offerId;
    high ^= fetchKey(index);

    long value = (long) high << 32;
    value |= index;

    return StringUtils.leftPad(Long.toHexString(value), 16, '0');

  }

  public static AppTypeEnum decode(String token, int appId, int offerId) {

    BigInteger bigInteger = new BigInteger(token, 16);

    long value = bigInteger.longValue();

    int high = (int) (value >>> 32);
    int index = (int) (value << 32 >>> 32);

    if (index >= EncryptKeys.SECRET_SELECTION_SEED) {
      return null;
    }

    high ^= appId;
    high ^= offerId;
    high ^= fetchKey(index);

    return AppTypeEnum.fromKey(high);
  }

  private static int fetchKey(int index) {
    byte[] keys = EncryptKeys.wrapKey[index];

    int key = ((int) keys[0] << 24) + ((int) keys[1] << 16) + ((int) keys[2] << 8) +
                ((int) keys[3]);//32 bit

    return key;
  }

}

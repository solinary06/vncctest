package com.yeahmobi.vncctest;

/**
 * Created by steven.liu on 2015/10/27.
 */

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionUtil {

    private static final long CRYPT_MAST = 0xFFFFl; // 16 bit
    private static int SECRET_SELECTION_SEED = 64;

    public static String unwrap(String transactionId) {
        if (StringUtils.isEmpty(transactionId)) {
            return null;
        }
        transactionId = transactionId.replace("-", "");
        if (transactionId.length() != 64) {
            // logger.warn("transaction id size is not 64 ,{}", transactionId);
            return null;
        }
        String highValue = transactionId.substring(0, 16);
        String high0Value = transactionId.substring(16, 32);
        String low0Value = transactionId.substring(32, 48);
        String lowValue = transactionId.substring(48);

        BigInteger high = new BigInteger(highValue, 16);
        BigInteger high0 = new BigInteger(high0Value, 16);
        BigInteger low0 = new BigInteger(low0Value, 16);
        BigInteger low = new BigInteger(lowValue, 16);
        long[] v = decryptWrap(new long[] { high.longValue(),
                high0.longValue(), low0.longValue(), low.longValue() });
        if (v == null) {
            return null;
        }
        return formatUUID(StringUtils.leftPad(Long.toHexString(v[0]), 16, '0')
                + StringUtils.leftPad(Long.toHexString(v[1]), 16, '0')
                + StringUtils.leftPad(Long.toHexString(v[2]), 16, '0')
                + StringUtils.leftPad(Long.toHexString(v[3]), 16, '0'));
    }

    private static long[] decryptWrap(long[] values) {
        long high = values[0];
        long high0 = values[1];
        long low0 = values[2];
        long low = values[3];
        // debugFormat("before decrypt", high, high0, low0, low);
        int index = (int) (CRYPT_MAST & low);
        if (index > SECRET_SELECTION_SEED) {
            return null;
        }
        byte[] keys = EncryptKeys.wrapKey[index];
        long highKeys0 = ((long) keys[0] << 56) + ((long) keys[1] << 48)
                + ((long) keys[2] << 40) + ((long) keys[3] << 32)
                + ((long) keys[4] << 24) + ((long) keys[5] << 16)
                + ((long) keys[6] << 8) + ((long) keys[7]);// 64 bit

        long highKeys1 = ((long) keys[8] << 56) + ((long) keys[9] << 48)
                + ((long) keys[10] << 40) + ((long) keys[11] << 32)
                + ((long) keys[12] << 24) + ((long) keys[13] << 16)
                + ((long) keys[14] << 8) + ((long) keys[15]);// 64 bit

        long highKeys2 = ((long) keys[16] << 56) + ((long) keys[17] << 48)
                + ((long) keys[18] << 40) + ((long) keys[19] << 32)
                + ((long) keys[20] << 24) + ((long) keys[21] << 16)
                + ((long) keys[22] << 8) + ((long) keys[23]);// 64 bit

        long highKeys3 = ((long) keys[24] << 56) + ((long) keys[25] << 48)
                + ((long) keys[26] << 40) + ((long) keys[27] << 32)
                + ((long) keys[28] << 24) + ((long) keys[29] << 16) + 0x0000l; //

        // debugFormat("before decrypt ,keys ", highKeys0, highKeys1, highKeys2,
        // highKeys3);
        high ^= highKeys0;
        high0 ^= highKeys1;
        low0 ^= highKeys2;
        low ^= highKeys3;
        low &= ~CRYPT_MAST; // reset crypt bits to 0
        // debugFormat("after decrypt", high, high0, low0, low);
        return new long[] { high, high0, low0, low };
    }

    private static String formatUUID(String value) {
        // 3ec57eeb-7340-483e-9733-b4340d0d2114

        return Joiner.on("-").join(value.substring(0, 9),
                value.substring(9, 13), value.substring(13, 17),
                value.substring(17));
    }

    public static String rowKey(String transactionId) {
        String id = transactionId.replace("-", "");
        String seq = id.substring(0, 16);
        BigInteger v = new BigInteger(seq, 16);
        return StringUtils.leftPad("" + (v.longValue() % 256), 3, '0') + id;
    }
}

package com.tiha.anphat.utils.aes;

public interface IAESUtils {
    String encrypt(String cleartext) throws Exception;
    String decrypt(String encrypted)throws Exception;
    byte[] getRawKey()throws Exception;
    byte[] encrypt(byte[] raw, byte[] clear)throws Exception;
    byte[] decrypt(byte[] encrypted)throws Exception;
    byte[] toByte(String hexString);
    String toHex(byte[] buf);
    void appendHex(StringBuffer sb, byte b);
}

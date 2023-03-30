package classes;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimestampConverter {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static long stringToTimestamp(String date) {
        LocalDateTime target = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        return target.toEpochSecond(ZoneOffset.UTC);
    }

    public static String timestampToString(long timestamp) {
        LocalDateTime origin = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
        return origin.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public static String secondsToString(int time) {
        int days = time / 86400;
        time %= 86400;
        int hours = time / 3600;
        time %= 3600;
        int minutes = time / 60;
        int seconds = time % 60;

        return String.format("%02d-%02d:%02d:%02d", days, hours, minutes, seconds);
    }

    public static String bytesToString(byte[] value, String charsetName) {
        String result = "";
        try {
            Charset charset = Charset.forName(charsetName);
            result = new String(value, charset);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return result;
    }

    public static byte[] stringToBytes(String value, String charsetName, int numBytes) {
        try {
            byte[] source = value.getBytes(charsetName);
            byte[] target = new byte[numBytes];
            System.arraycopy(source, 0, target, 0, Math.min(source.length, target.length));
            return target;
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static String byteArrayToHexString(byte[] value) {
        StringBuilder result = new StringBuilder();
        for (byte b : value) {
            result.append(String.format("%02X-", b));
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static byte[] hexStringToByteArray(String value) {
        String[] hex = value.split("-");
        byte[] bytes = new byte[hex.length];
        for (int i = 0; i < hex.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex[i], 16);
        }
        return bytes;
    }

    public static long currentTimestamp() {
        return Instant.now().getEpochSecond();
    }
}

package com.yukiii.demo.util;

import com.yukiii.demo.adapter.builder.BigDecimalBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.number.CurrencyStyleFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NumberUtil {
  private static final Locale LOCALE = new Locale("in", "ID");
  public static final BigDecimal JUTAAN = new BigDecimal(1000000L);
  public static final BigDecimal PULUHAN;
  public static final BigDecimal MILYARAN;
  public static final BigDecimal TRILIUN;
  public static final BigDecimal RATUSAN;
  public static final BigDecimal SATUAN;
  public static final BigDecimal RIBUAN;
  public static final BigDecimal[] ORDER;
  public static final String[] ORDER_STR;

  public NumberUtil() {
  }

  public static boolean isEqual(BigDecimal n1, BigDecimal n2) {
    if(n1 != null && n2 != null) {
      return n1.compareTo(n2) == 0;
    } else {
      return false;
    }
  }

  public static boolean isNotEqual(BigDecimal n1, BigDecimal n2) {
    if(n1 != null && n2 != null) {
      return n1.compareTo(n2) != 0;
    } else {
      return false;
    }
  }

  public static boolean isLessThan(BigDecimal n1, BigDecimal n2) {
    if(n1 != null && n2 != null) {
      n1.compareTo(n2);
      return n1.compareTo(n2) < 0;
    } else {
      return false;
    }
  }

  public static boolean isGreaterThan(BigDecimal n1, BigDecimal n2) {
    if(n1 != null && n2 != null) {
      return n1.compareTo(n2) > 0;
    } else {
      return false;
    }
  }

  public static boolean isZero(BigDecimal number) {
    if(number == null) {
      return false;
    } else {
      return number.compareTo(BigDecimal.ZERO) == 0;
    }
  }

  public static boolean isNotZero(BigDecimal number) {
    return !isZero(number);
  }

  public static boolean isPositive(BigDecimal number) {
    if(number == null) {
      return false;
    } else {
      return number.compareTo(BigDecimal.ZERO) == 1;
    }
  }

  public static boolean isZeroOrPositive(BigDecimal number) {
    if(number == null) {
      return false;
    } else {
      return number.compareTo(BigDecimal.ZERO) == 0 || number.compareTo(BigDecimal.ZERO) == 1;
    }
  }

  public static boolean isNegative(BigDecimal number) {
    if(number == null) {
      return false;
    } else {
      return number.compareTo(BigDecimal.ZERO) == -1;
    }
  }

  public static boolean isZeroOrNegative(BigDecimal number) {
    if(number == null) {
      return false;
    } else {
      return number.compareTo(BigDecimal.ZERO) == 0 || number.compareTo(BigDecimal.ZERO) == -1;
    }
  }

  public static boolean isZeroOrNegative(int number) {
    return number <= 0;
  }

  public static boolean isZeroOrNegative(long number) {
    return number <= 0L;
  }

  public static BigDecimal ifZeroGetDefault(BigDecimal testingNumber, BigDecimal replacement) {
    return isZero(testingNumber) ? replacement : testingNumber;
  }

  public static BigDecimal ifNullGetDefault(BigDecimal testingNumber, BigDecimal replacement) {
    return testingNumber == null ? replacement : testingNumber;
  }

  public static BigDecimal ifNullGetZero(BigDecimal testingNumber) {
    return ifNullGetDefault(testingNumber, BigDecimal.ZERO);
  }

  public static BigDecimal ifZeroOrNegativeGetDefault(BigDecimal testingNumber, BigDecimal replacement) {
    return isZeroOrNegative(testingNumber) ? replacement : testingNumber;
  }

  public static BigDecimal ifNegativeGetZero(BigDecimal testingNumber) {
    return isNegative(testingNumber) ? BigDecimal.ZERO : testingNumber;
  }

  public static BigDecimal ifNegativeGetDefault(BigDecimal testingNumber, BigDecimal replacement) {
    return isNegative(testingNumber) ? replacement : testingNumber;
  }

  public static BigDecimal parseBigDecimalOrZero(String number) {
    BigDecimal result = BigDecimal.ZERO;
    if(StringUtils.isEmpty(number)) {
      return result;
    } else {
      try {
        result = new BigDecimal(number);
      } catch(Exception var3) {
        result = BigDecimal.ZERO;
      }

      return result;
    }
  }

  public static BigDecimal parseBigDecimal(String number) throws NumberFormatException {
    BigDecimal result = BigDecimal.ZERO;
    if(StringUtils.isEmpty(number)) {
      return result;
    } else {
      try {
        result = new BigDecimal(number);
        return result;
      } catch(NumberFormatException var3) {
        var3.printStackTrace();
        throw new NumberFormatException("Format tidak valid.");
      }
    }
  }

  public static BigDecimal parseCurrencyOrDefault(String number, BigDecimal replacement) {
    BigDecimal result = BigDecimal.ZERO;

    try {
      result = parseCurrency(number);
    } catch(Exception var4) {
      result = replacement;
    }

    return result;
  }

  public static BigDecimal parseCurrencyOrZero(String number) {
    return parseCurrencyOrDefault(number, BigDecimal.ZERO);
  }

  public static double parseDouble(String number) throws Exception {
    return Double.parseDouble(number);
  }

  public static double parseDoubleOrDefault(String number, double replacement) {
    try {
      return parseDouble(number);
    } catch(Exception var4) {
      return replacement;
    }
  }

  public static double parseDoubleOrZero(String number) {
    return parseDoubleOrDefault(number, 0.0D);
  }

  public static int parseInt(String number) throws Exception {
    return Integer.parseInt(number);
  }

  public static int parseIntOrDefault(String number, int replacement) {
    try {
      return parseInt(number);
    } catch(Exception var3) {
      return replacement;
    }
  }

  public static int parseIntOrZero(String number) {
    return parseIntOrDefault(number, 0);
  }

  public static long parseLong(String number) throws Exception {
    return Long.parseLong(number);
  }

  public static long parseLongOrZero(String number) {
    return parseLongOrDefault(number, 0L);
  }

  public static long parseLongOrDefault(String number, long replacement) {
    try {
      return parseLong(number);
    } catch(Exception var4) {
      return replacement;
    }
  }

  public static BigDecimal parseCurrency(String number) throws Exception {
    BigDecimal result = BigDecimal.ZERO;
    if(StringUtils.isEmpty(number)) {
      return result;
    } else {
      String numStr = number.replaceFirst("Rp.", "").replaceAll("\\.", "").replaceFirst(",", ".").trim();
      if(StringUtils.isEmpty(numStr)) {
        return result;
      } else {
        result = new BigDecimal(numStr);
        return result;
      }
    }
  }

  public static BigDecimal divide(BigDecimal multiplicand, BigDecimal multiplier) {
    multiplicand = ifNullGetZero(multiplicand);
    multiplier = ifNullGetZero(multiplier);
    BigDecimal result = BigDecimal.ZERO;

    try {
      result = multiplicand.setScale(2, RoundingMode.HALF_UP).divide(multiplier.setScale(2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP);
    } catch(Exception var4) {
    }

    return result;
  }

  public static BigDecimal divide(BigDecimal dividend, int divisor) {
    BigDecimal n2 = new BigDecimal(divisor);
    return divide(dividend, n2);
  }

  public static BigDecimal add(BigDecimal n1, BigDecimal n2) {
    n1 = ifNullGetZero(n1);
    n2 = ifNullGetZero(n2);
    BigDecimal result = BigDecimal.ZERO;

    try {
      result = n1.add(n2).setScale(2, RoundingMode.HALF_UP);
    } catch(Exception var4) {
    }

    return result;
  }

  public static BigDecimal substract(BigDecimal n1, BigDecimal n2) {
    n1 = ifNullGetZero(n1);
    n2 = ifNullGetZero(n2);
    BigDecimal result = BigDecimal.ZERO;

    try {
      result = n1.subtract(n2).setScale(2, RoundingMode.HALF_UP);
    } catch(Exception var4) {
    }

    return result;
  }

  public static BigDecimal times(BigDecimal n1, BigDecimal n2) {
    n1 = ifNullGetZero(n1);
    n2 = ifNullGetZero(n2);
    BigDecimal result = BigDecimal.ZERO;

    try {
      result = n1.setScale(2, RoundingMode.HALF_UP).multiply(n2.setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
    } catch(Exception var4) {
    }

    return result;
  }

  public static BigDecimal times(BigDecimal multiplicand, int multiplier) {
    BigDecimal n2 = new BigDecimal(multiplier);
    return times(multiplicand, n2);
  }

  public static BigDecimal times(BigDecimal multiplicand, long multiplier) {
    BigDecimal n2 = new BigDecimal(multiplier);
    return times(multiplicand, n2);
  }

  public static String toHumanReadable(BigDecimal number) {
    String terbilang = "";
    String str = number.toPlainString();
    int indexOf = str.indexOf(".");
    String komaStr = str.substring(str.indexOf(".") + 1);
    BigDecimal koma = new BigDecimal(komaStr);
    terbilang = getTerbilang(number);
    if(indexOf != -1 && koma.compareTo(BigDecimal.ZERO) != 0) {
      terbilang = terbilang + " Koma " + getTerbilang(koma);
    }

    return terbilang;
  }

  private static String getTerbilang(BigDecimal number) {
    Map<Integer, String> digits = new HashMap();
    digits.put(0, "Nol");
    digits.put(1, "Satu");
    digits.put(2, "Dua");
    digits.put(3, "Tiga");
    digits.put(4, "Empat");
    digits.put(5, "Lima");
    digits.put(6, "Enam");
    digits.put(7, "Tujuh");
    digits.put(8, "Delapan");
    digits.put(9, "Sembilan");
    digits.put(10, "Sepuluh");
    digits.put(11, "Sebelas");
    digits.put(12, "Dua belas");
    digits.put(13, "Tiga belas");
    digits.put(14, "Empat belas");
    digits.put(15, "Lima belas");
    digits.put(16, "Enam belas");
    digits.put(17, "Tujuh belas");
    digits.put(18, "Delapan belas");
    digits.put(19, "Sembilan belas");
    String result = "";
    if(number.compareTo(BigDecimal.ZERO) == 0) {
      return (String) digits.get(BigDecimal.ZERO.intValue());
    } else {
      BigDecimal remaining = number;
      BigDecimal koma = null;

      for(int i = 0; i < ORDER.length; ++i) {
        BigDecimal divisor = ORDER[i];
        BigDecimal[] divideAndRemainder = remaining.divideAndRemainder(divisor);
        BigDecimal r = divideAndRemainder[0];
        BigDecimal s = divideAndRemainder[1];
        if(r.compareTo(BigDecimal.ZERO) != 1) {
          remaining = s;
        } else {
          if(ORDER_STR[i].equals("Puluh") && r.compareTo(BigDecimal.ONE) == 0) {
            result = result + " " + (String) digits.get(remaining.intValue());
            break;
          }

          String str = "";
          if(r.compareTo(new BigDecimal("11")) == 1) {
            str = getTerbilang(r);
          } else {
            str = (String) digits.get(r.intValue());
          }

          result = result + " " + str + " " + ORDER_STR[i];
          result = result.replaceAll("Satu Ratus", "Seratus").replaceAll("Satu Ribu", "Seribu");
          remaining = s;
        }
      }

      return result.trim();
    }
  }

  public static BigDecimal round(BigDecimal number, String mode, BigDecimal rupiahTerdekat) {
    if(number == null) {
      return null;
    } else {
      mode = StringUtils.isEmpty(mode) ? "NONE" : mode;
      int scale = 0;
      BigDecimal result = BigDecimal.ZERO;
      byte var6 = -1;
      switch(mode.hashCode()) {
        case -1884927:
          if(mode.equals("RUPIAH_CEILING")) {
            var6 = 6;
          }
          break;
        case 2402104:
          if(mode.equals("NONE")) {
            var6 = 0;
          }
          break;
        case 66989036:
          if(mode.equals("FLOOR")) {
            var6 = 5;
          }
          break;
        case 740005891:
          if(mode.equals("RUPIAH_NORMAL")) {
            var6 = 8;
          }
          break;
        case 1150502190:
          if(mode.equals("HALF_DOWN")) {
            var6 = 2;
          }
          break;
        case 1150538150:
          if(mode.equals("HALF_EVEN")) {
            var6 = 3;
          }
          break;
        case 1378369693:
          if(mode.equals("CEILING")) {
            var6 = 4;
          }
          break;
        case 1409017383:
          if(mode.equals("HALF_UP")) {
            var6 = 1;
          }
          break;
        case 1817506128:
          if(mode.equals("RUPIAH_FLOOR")) {
            var6 = 7;
          }
      }

      switch(var6) {
        case 0:
          result = number;
          break;
        case 1:
          result = number.setScale(scale, RoundingMode.HALF_UP);
          break;
        case 2:
          result = number.setScale(scale, RoundingMode.HALF_DOWN);
          break;
        case 3:
          result = number.setScale(scale, RoundingMode.HALF_EVEN);
          break;
        case 4:
          result = number.setScale(scale, RoundingMode.CEILING);
          break;
        case 5:
          result = number.setScale(scale, RoundingMode.FLOOR);
          break;
        case 6:
          result = ceilMoney(number, rupiahTerdekat);
          break;
        case 7:
          result = floorMoney(number, rupiahTerdekat);
          break;
        case 8:
          result = roundMoney(number, rupiahTerdekat);
          break;
        default:
          result = number;
      }

      return result;
    }
  }

  public static BigDecimal floorMoney(BigDecimal money, BigDecimal smallestMoney) {
    BigDecimal sisa = money.remainder(smallestMoney);
    return isZero(sisa) ? money : substract(money, sisa);
  }

  public static BigDecimal ceilMoney(BigDecimal money, BigDecimal smallestMoney) {
    BigDecimal sisa = money.remainder(smallestMoney);
    if(isZero(sisa)) {
      return money;
    } else {
      BigDecimal addition = substract(smallestMoney, sisa);
      return add(money, addition);
    }
  }

  public static BigDecimal roundMoney(BigDecimal money, BigDecimal smallestMoney) {
    BigDecimal sisa = money.remainder(smallestMoney);
    if(isZero(sisa)) {
      return money;
    } else {
      BigDecimal two = new BigDecimal(2);
      BigDecimal half = divide(smallestMoney, two);
      int comparation = sisa.compareTo(half);
      return comparation >= 0 ? ceilMoney(money, smallestMoney) : floorMoney(money, smallestMoney);
    }
  }

  public static BigDecimal percentageFrom(double percentage, BigDecimal percentageResult) {
    percentageResult = ifNullGetZero(percentageResult);
    BigDecimal result = (new BigDecimalBuilder()).add(100.0D).times(percentageResult).divide(percentage).build();
    return result;
  }

  public static BigDecimal persentageRatio(BigDecimal n1, BigDecimal total) {
    n1 = ifNullGetZero(n1);
    total = ifNullGetZero(total);
    BigDecimal percentage = (new BigDecimalBuilder()).add(n1).times(100.0D).divide(total).build();
    return percentage;
  }

  public static BigDecimal percentageResult(double percentage, BigDecimal total) {
    total = ifNullGetZero(total);
    BigDecimal result = (new BigDecimalBuilder()).add(percentage).times(total).divide(100.0D).build();
    return result;
  }

  public static BigDecimal abs(BigDecimal number) {
    return isZeroOrPositive(number) ? number : number.negate();
  }

  public static boolean isStringDigit(String str) {
    try {
      Long.parseLong(str);
      return true;
    } catch(Exception var2) {
      return false;
    }
  }

  static {
    PULUHAN = BigDecimal.TEN;
    MILYARAN = new BigDecimal(1000000000L);
    TRILIUN = new BigDecimal(1000000000000L);
    RATUSAN = new BigDecimal(100L);
    SATUAN = BigDecimal.ONE;
    RIBUAN = new BigDecimal(1000L);
    ORDER = new BigDecimal[]{TRILIUN, MILYARAN, JUTAAN, RIBUAN, RATUSAN, PULUHAN, SATUAN};
    ORDER_STR = new String[]{"Triliun", "Milyar", "Juta", "Ribu", "Ratus", "Puluh", ""};
  }
}

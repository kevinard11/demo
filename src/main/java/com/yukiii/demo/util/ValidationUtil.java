package com.yukiii.demo.util;

import com.yukiii.demo.constant.AppConstant;
import com.yukiii.demo.exception.DemoException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ValidationUtil {
  public ValidationUtil() {
  }

  public static void checkEmptyField(String... fields) {

    for(String f : fields) {
      if(StringUtils.isEmpty(f)) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_EMPTY);
      }
    }

  }

  public static void checkEmptyField(String[]... fields){
    rejectIfEmptyField("kosong", fields);
  }

  public static void rejectIfEmptyField(String defaultMessage, String[]... fields){

    for(String[] f : fields) {
      if(StringUtils.isEmpty(f[1])) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_EMPTY, f[0] + " " + defaultMessage);
      }
    }

  }

  public static void checkWhitespaceField(String... fields){

    for(String f : fields) {
      if(StringUtils.containsWhitespace(f)) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_CONTAINWHITESPACE);
      }
    }

  }

  public static void checkWhitespaceField(String[]... fields){
    rejectIfWhitespaceField("kosong", fields);
  }

  public static void rejectIfWhitespaceField(String defaultMessage, String[]... fields){

    for(String[] f : fields) {
      if(StringUtils.containsWhitespace(f[1])) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_CONTAINWHITESPACE, f[0] + " " + defaultMessage);
      }
    }

  }

  public static void rejectIfNotAlphanumeric(String defaultMessage, String[]... fields){

    for(String[] f : fields) {
      if(!StringUtils.isAlphanumericSpace(f[1])) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, f[0] + " " + defaultMessage);
      }
    }

  }

  public static void validateEmail(String email, String errMesg) {
    errMesg = StringUtils.isEmpty(errMesg) ? "Invalid email." : errMesg;
    EmailValidator emailValidator = EmailValidator.getInstance();
    if(StringUtils.isNotEmpty(email) && !emailValidator.isValid(email)) {
      throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, errMesg);
    }
  }

  public static void validateEmail(String email){
    validateEmail(email, "");
  }

  public static void validateDate(String date, String pattern, String errMesg){
    errMesg = StringUtils.isEmpty(errMesg) ? "Invalid date." : errMesg;
    if(StringUtils.isNotEmpty(date)) {

      try {
        DateUtil.parseDate(date, pattern);
      } catch(Exception var5) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, errMesg);
      }
    }

  }

  public static void validateDate(String date, String errMesg){
    errMesg = StringUtils.isEmpty(errMesg) ? "Invalid date." : errMesg;
    if(StringUtils.isNotEmpty(date)) {

      try {
        DateUtil.parseDate(date, AppConstant.DATE_DEFAULTDATE_PATTERN);
      } catch(Exception var4) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, errMesg);
      }
    }

  }

  public static void validateTime(String time, String errMesg){
    errMesg = StringUtils.isEmpty(errMesg) ? "Invalid time." : errMesg;
    if(StringUtils.isNotEmpty(time)) {

      try {
        DateUtil.parseTime(time, AppConstant.DATE_DEFAULTTIME_PATTERN);
      } catch(Exception var4) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, errMesg);
      }
    }

  }

  public static void validateNumber(String number, String errMesg){
    errMesg = StringUtils.isEmpty(errMesg) ? "Invalid number." : errMesg;
    if(StringUtils.isNotEmpty(number)) {
      try {
        Long.parseLong(number);
      } catch(NumberFormatException var3) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, errMesg);
      }
    }

  }

  public static void validateDouble(String number, String errMesg){
    errMesg = StringUtils.isEmpty(errMesg) ? "Invalid number." : errMesg;
    if(StringUtils.isNotEmpty(number)) {
      try {
        Double.parseDouble(number);
      } catch(NumberFormatException var3) {
        throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, errMesg);
      }
    }

  }

  public static void validateValue(String val, String errMessage, String... expectedValues){
    if(!StringUtils.isEmpty(val)) {
      errMessage = StringUtils.isEmpty(errMessage) ? "Invalid value." : errMessage;

      for(String expectedValue : expectedValues) {
        if(val.equals(expectedValue)) {
          return;
        }
      }

      throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, errMessage);
    }
  }

  public static void validateBoolean(String val, String errMessage){
    if(!StringUtils.isEmpty(val)) {
      errMessage = StringUtils.isEmpty(errMessage) ? "Invalid boolean type." : errMessage;
      List<String> asList = Arrays.asList("0", "1", "true", "false");
      Iterator var3 = asList.iterator();

      String expectedValue;
      do {
        if(!var3.hasNext()) {
          throw new DemoException(AppConstant.ResponseConstant.PARAM_INVALID, errMessage);
        }

        expectedValue = (String) var3.next();
      } while(!val.equals(expectedValue));

    }
  }
}

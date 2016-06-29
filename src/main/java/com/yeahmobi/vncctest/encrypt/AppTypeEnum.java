package com.yeahmobi.vncctest.encrypt;

/**
 * Created by qinpeng on 16/3/3.
 */
public enum AppTypeEnum {

  AFF(0, "affiliate", Integer.valueOf("00000000", 2).intValue()),
  SDK(1, "sdk", Integer.valueOf("10000000", 2).intValue()),
  OFF(2, "offline_api", Integer.valueOf("01000000", 2).intValue()),
  REAL(3, "realtime_api", Integer.valueOf("00010000", 2).intValue());

  AppTypeEnum(int id, String value, int key) {
    this.id = id;
    this.value = value;
    this.key = key;
  }

  private int id;

  private String value;

  private int key;

  public static AppTypeEnum fromKey(int key) {
    for (AppTypeEnum item : AppTypeEnum.values()) {
      if (item.key == key) {
        return item;
      }
    }

    return null;
  }

  public static AppTypeEnum fromValue(String value) {
    for (AppTypeEnum item : AppTypeEnum.values()) {
      if (item.value.equalsIgnoreCase(value)) {
        return item;
      }
    }

    return null;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}

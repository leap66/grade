package com.leap.grade.data;

import java.io.Serializable;

/**
 * APP 版本更新实体类
 * <p>
 * </>Created by weiyaling on 2017/2/22.
 */
public class UpdateModel implements Serializable {
  public static final long serialVersionUID = 2965065498031596999L;
  private boolean hasFresh;// true 有更新
  private boolean force; // true 强制更新
  private String versionName;
  private String description;
  private String downloadUrl;
  private String md5;

  private boolean already;// 是否准备好
  private long position;// 下载位置
  private String fileName;// 下载文件名

  public boolean isHasFresh() {
    return hasFresh;
  }

  public void setHasFresh(boolean hasFresh) {
    this.hasFresh = hasFresh;
  }

  public boolean isForce() {
    return force;
  }

  public void setForce(boolean force) {
    this.force = force;
  }

  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5;
  }

  public boolean isAlready() {
    return already;
  }

  public void setAlready(boolean already) {
    this.already = already;
  }

  public long getPosition() {
    return position;
  }

  public void setPosition(long position) {
    this.position = position;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
package com.leap.grade.data;

import com.leap.base.data.base.BEntity;
import com.leap.base.data.base.BUcn;
import com.leap.base.util.IsEmpty;

/**
 * BAddress : 地址实体类
 * <p>
 * </> Created by ylwei on 2018/3/30.
 */
public class BAddress extends BEntity {
  private BUcn province;// 省
  private BUcn city;// 市
  private BUcn district;// 区
  private BUcn street;// 街道
  private String remark;// 详细地址
  private String zipcode;// 邮政编码

  public BUcn getProvince() {
    return province;
  }

  public void setProvince(BUcn province) {
    this.province = province;
  }

  public BUcn getCity() {
    return city;
  }

  public void setCity(BUcn city) {
    this.city = city;
  }

  public BUcn getDistrict() {
    return district;
  }

  public void setDistrict(BUcn district) {
    this.district = district;
  }

  public BUcn getStreet() {
    return street;
  }

  public void setStreet(BUcn street) {
    this.street = street;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    if (!IsEmpty.object(province) && !IsEmpty.object(province.getName()))
      buffer.append(province.getName());
    if (!IsEmpty.object(city) && !IsEmpty.object(city.getName()))
      buffer.append(" ").append(city.getName());
    if (!IsEmpty.object(district) && !IsEmpty.object(district.getName()))
      buffer.append(" ").append(district.getName());
    if (!IsEmpty.object(street) && !IsEmpty.object(street.getName()))
      buffer.append(" ").append(street.getName());
    if (!IsEmpty.object(remark))
      buffer.append(" ").append(remark);
    return buffer.toString();
  }
}

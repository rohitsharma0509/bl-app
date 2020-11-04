package com.adobe.blueapp.service.imsgraph.dto;

import java.util.List;

/**
 * 
 * @author siddgupt
 *
 */
public class CreateUserDto {

  private String id;
  private String responsibleAuthSrc;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String countryCode;
  private String status;
  private String orgName;
  private String phoneNum;
  private List<MrktPerm> mrktPerm = null;
  private List<Object> links = null;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getResponsibleAuthSrc() {
    return responsibleAuthSrc;
  }

  public void setResponsibleAuthSrc(String responsibleAuthSrc) {
    this.responsibleAuthSrc = responsibleAuthSrc;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public List<MrktPerm> getMrktPerm() {
    return mrktPerm;
  }

  public void setMrktPerm(List<MrktPerm> mrktPerm) {
    this.mrktPerm = mrktPerm;
  }

  public List<Object> getLinks() {
    return links;
  }

  public void setLinks(List<Object> links) {
    this.links = links;
  }

  public static class MrktPerm {

    private String code;
    private Boolean value;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public Boolean getValue() {
      return value;
    }

    public void setValue(Boolean value) {
      this.value = value;
    }

  }
}

package com.adobe.blueapp.service.imsgraph.dto;

/**
 * 
 * @author siddgupt
 *
 */
public class AssignRoleToUserRequest {

  private String namedRoleCode;
  private String target;

  public String getNamedRoleCode() {
    return namedRoleCode;
  }

  public void setNamedRoleCode(String namedRoleCode) {
    this.namedRoleCode = namedRoleCode;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

}

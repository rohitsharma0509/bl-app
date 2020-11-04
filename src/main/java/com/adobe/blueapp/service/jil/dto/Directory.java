package com.adobe.blueapp.service.jil.dto;

/**
 * 
 * @author siddgupt
 *
 */
public class Directory {

  private String id;
  private String name;
  private String externalId;
  private String type;
  private String ownershipStatus;
  private boolean externallyManaged;
  private String status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getOwnershipStatus() {
    return ownershipStatus;
  }

  public void setOwnershipStatus(String ownershipStatus) {
    this.ownershipStatus = ownershipStatus;
  }

  public boolean isExternallyManaged() {
    return externallyManaged;
  }

  public void setExternallyManaged(boolean externallyManaged) {
    this.externallyManaged = externallyManaged;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}

package com.adobe.blueapp.resource.blueapp.dto;

import com.adobe.blueapp.service.imsgraph.dto.CreateUserDto;
import com.adobe.blueapp.service.soup.dto.SoupUserProvisionResponse;

/**
 * 
 * @author siddgupt
 *
 */
public class CreateUserResponse {

  private CreateUserDto createUserDto;
  private SoupUserProvisionResponse soupUserProvisionResponse;

  public CreateUserResponse() {
    super();
  }

  public CreateUserResponse(CreateUserDto createUserDto, SoupUserProvisionResponse soupUserProvisionResponse) {
    super();
    this.createUserDto = createUserDto;
    this.soupUserProvisionResponse = soupUserProvisionResponse;
  }

  public CreateUserDto getCreateUserDto() {
    return createUserDto;
  }

  public void setCreateUserDto(CreateUserDto createUserDto) {
    this.createUserDto = createUserDto;
  }

  public SoupUserProvisionResponse getSoupUserProvisionResponse() {
    return soupUserProvisionResponse;
  }

  public void setSoupUserProvisionResponse(SoupUserProvisionResponse soupUserProvisionResponse) {
    this.soupUserProvisionResponse = soupUserProvisionResponse;
  }
}

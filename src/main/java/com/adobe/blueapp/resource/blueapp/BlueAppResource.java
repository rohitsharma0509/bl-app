package com.adobe.blueapp.resource.blueapp;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.adobe.asr.connector.ims.dto.Organization;
import com.adobe.blueapp.constants.ErrorCodes;
import com.adobe.blueapp.exception.ServiceException;
import com.adobe.blueapp.resource.blueapp.dto.CreateUserResponse;
import com.adobe.blueapp.resource.blueapp.dto.LoginRequest;
import com.adobe.blueapp.resource.blueapp.dto.LoginResponse;
import com.adobe.blueapp.service.adminsdk.AdminSDKConnector;
import com.adobe.blueapp.service.adminsdk.dto.ProductProfile;
import com.adobe.blueapp.service.ims.CustomIMSConnector;
import com.adobe.blueapp.service.ims.dto.IMSAccessToken;
import com.adobe.blueapp.service.ims.dto.ServicePrincipal;
import com.adobe.blueapp.service.imsgraph.IMSGraphConnector;
import com.adobe.blueapp.service.imsgraph.dto.CreateUserDto;
import com.adobe.blueapp.service.jil.CustomJilConnector;
import com.adobe.blueapp.service.jil.dto.Directory;
import com.adobe.blueapp.service.sign.SignConnector;
import com.adobe.blueapp.service.sign.dto.AgreementViewResponse;
import com.adobe.blueapp.service.sign.dto.BaseUriInfo;
import com.adobe.blueapp.service.sign.dto.UserViewInfoRequest;
import com.adobe.blueapp.service.sign.dto.UserViewResponse;
import com.adobe.blueapp.service.soup.SoupConnector;
import com.adobe.blueapp.service.soup.dto.SoupUserProvisionRequest;
import com.adobe.blueapp.service.soup.dto.SoupUserProvisionResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author siddgupt
 *
 */
@Path("/blueapp")
@Produces(MediaType.APPLICATION_JSON)
public class BlueAppResource {

  @POST
  @Path("login")
  public LoginResponse login(LoginRequest loginRequest) throws URISyntaxException {
    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setAccessToken(loginRequest.getEmail());
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return loginResponse;
  }

  @GET
  public Response generateAccessCode(@QueryParam("code") String authCode) throws URISyntaxException {
    userToken = customIMSConnector.generateAccessTokenFromAuthCode(authCode, null);
    // String serviceToken = customIMSConnector.getServiceToken();
    // accessToken = customIMSConnector.getExtendedAccessToken(serviceToken, accessToken.getAccess_token(), extendedScopes);

    return Response.temporaryRedirect(new URI("http://local-test.acrobat.com:3000/orgList")).build();
  }

  @GET
  @Path("organizations")
  public List<Organization> getOrganizations() {
    return customIMSConnector.getOrganizations(userToken.getAccess_token());
  }

  @GET
  @Path("adminorgs")
  public List<com.adobe.asr.connector.jil.dto.Organization> getAdminOrganizations() {
    return customJilConnector.findOrganizationsOwnedByUser(userToken.getAccess_token());
  }

  @GET
  @Path("clientcredentials")
  public IMSAccessToken createClientCredentials(@QueryParam("orgId") String orgId) {
    servicePrincipal = customIMSConnector.createServicePrincipal(userToken.getAccess_token(), orgId, servicePrincipalScopes);

    Directory firstT2eDirectory = customJilConnector.getorCreateFirstT2eDirectory(userToken.getAccess_token(), servicePrincipal.getOrganizationId());
    imsGraphConnector.assignRole(userToken.getAccess_token(), servicePrincipal.getOrganizationId(),
                                 servicePrincipal.getServicePrincipalId(), "ENT_ACCT_IMPERSONATE", firstT2eDirectory.getId());

    clientCredentialsToken = customIMSConnector.getClientCredentialsToken(servicePrincipal.getOrganizationId(), servicePrincipalScopes);

    return clientCredentialsToken;
  }

  @GET
  @Path("productprofiles")
  public ProductProfile[] getProductProfiles() {
    return adminSDKConnector.getProductProfiles(clientCredentialsToken.getAccess_token());
  }

  @GET
  @Path("defaultproductprofile")
  public String setDefaultProductProfiles(@QueryParam("profileId") String profileId) {
    defaultProductProfile = profileId;
    return "{}";
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("createuser")
  public CreateUserResponse createUser(@QueryParam("profileId") String profileId, CreateUserDto createUserDto) {
    createUserDto.setUsername(createUserDto.getEmail());

    CreateUserDto createdUserDto = imsGraphConnector.createUser(clientCredentialsToken.getAccess_token(), servicePrincipal.getOrganizationId(), createUserDto);

    String delegateGuid = createdUserDto.getId().split("@")[0];
    String authSrc = createdUserDto.getResponsibleAuthSrc();

    SoupUserProvisionRequest soupUserProvisionRequest = new SoupUserProvisionRequest(delegateGuid, authSrc, profileId);

    SoupUserProvisionResponse soupUserProvisionResponse = soupConnector.createUser(clientCredentialsToken.getAccess_token(), soupUserProvisionRequest);

    CreateUserResponse createUserResponse = new CreateUserResponse(createdUserDto, soupUserProvisionResponse);
    return createUserResponse;
  }

  @GET
  @Path("poseastoken")
  public IMSAccessToken getPoseAsToken(@QueryParam("userId") String userId) {
    return customIMSConnector.generatePoseAsToken(clientCredentialsToken.getAccess_token(), userId, servicePrincipalScopes);
  }

  @GET
  @Path("managepage")
  public UserViewResponse getManagePage(@QueryParam("email") String email) throws URISyntaxException {

    CreateUserDto createUserDto = new CreateUserDto();
    createUserDto.setEmail(email);
    createUserDto.setUsername(createUserDto.getEmail());
    createUserDto.setCountryCode("US");
    createUserDto.setFirstName("firstName");
    createUserDto.setLastName("lastName");

    CreateUserResponse createUserResponse = createUser(defaultProductProfile, createUserDto);
    if (createUserResponse.getSoupUserProvisionResponse().getCode().equals("CONSENT_ACCEPTANCE_REQUIRED")) {
      throw new ServiceException("Consent Acceptance Required").withCode(ErrorCodes.SOUP_ERROR).withStatus(Status.FORBIDDEN);
    }

    IMSAccessToken poseAsToken = customIMSConnector.generatePoseAsToken(clientCredentialsToken.getAccess_token(), createUserResponse.getCreateUserDto().getId(), servicePrincipalScopes);

    UserViewInfoRequest userViewInfoRequest = new UserViewInfoRequest();
    userViewInfoRequest.setName("ALL");
    UserViewInfoRequest.CommonViewConfiguration commonViewConfiguration = new UserViewInfoRequest.CommonViewConfiguration();
    commonViewConfiguration.setAutoLoginUser(true);
    commonViewConfiguration.setNoChrome(true);
    userViewInfoRequest.setCommonViewConfiguration(commonViewConfiguration);

    BaseUriInfo baseUriInfo = signConnector.getBaseUriInfo(poseAsToken.getAccess_token());
    UserViewResponse userViewResponse = signConnector.getUserViews(baseUriInfo, poseAsToken.getAccess_token(), "me", userViewInfoRequest);

    return userViewResponse;

  }

  @GET
  @Path("composepage")
  public AgreementViewResponse getComposePage(@QueryParam("email") String email) throws URISyntaxException {

    CreateUserDto createUserDto = new CreateUserDto();
    createUserDto.setEmail(email);
    createUserDto.setUsername(createUserDto.getEmail());
    createUserDto.setCountryCode("US");
    createUserDto.setFirstName("firstName");
    createUserDto.setLastName("lastName");

    CreateUserResponse createUserResponse = createUser(defaultProductProfile, createUserDto);
    if (createUserResponse.getSoupUserProvisionResponse().getCode().equals("CONSENT_ACCEPTANCE_REQUIRED")) {
      throw new ServiceException("Consent Acceptance Required").withCode(ErrorCodes.SOUP_ERROR).withStatus(Status.FORBIDDEN);
    }

    IMSAccessToken poseAsToken = customIMSConnector.generatePoseAsToken(clientCredentialsToken.getAccess_token(), createUserResponse.getCreateUserDto().getId(), servicePrincipalScopes);

    BaseUriInfo baseUriInfo = signConnector.getBaseUriInfo(poseAsToken.getAccess_token());
    AgreementViewResponse agreementViewResponse = signConnector.getComposePage(baseUriInfo, poseAsToken.getAccess_token());

    return agreementViewResponse;

  }

  private static IMSAccessToken clientCredentialsToken;
  private static IMSAccessToken userToken;
  private static ServicePrincipal servicePrincipal;
  private static String defaultProductProfile;

  private String servicePrincipalScopes = "AdobeID,openid,u.write,read_organizations,sign_user_write,partner_user_auth,u.roles.write,additional_info.roles,u.read,"
                                          + "sign_user_login,sign_user_read,agreement_read,agreement_write";
  // private String extendedScopes = "AdobeID,openid,service_principals.write,service_principals.write_restricted,read_organizations";
  @Autowired
  private CustomIMSConnector customIMSConnector;

  @Autowired
  private IMSGraphConnector imsGraphConnector;

  @Autowired
  private AdminSDKConnector adminSDKConnector;

  @Autowired
  private SoupConnector soupConnector;

  @Autowired
  private CustomJilConnector customJilConnector;

  @Autowired
  private SignConnector signConnector;

}

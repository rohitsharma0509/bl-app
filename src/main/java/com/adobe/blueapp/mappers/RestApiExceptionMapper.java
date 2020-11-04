/*************************************************************************
* ADOBE CONFIDENTIAL
* ___________________
*
* Copyright 2019 Adobe
* All Rights Reserved.
*
* NOTICE: All information contained herein is, and remains
* the property of Adobe and its suppliers, if any. The intellectual
* and technical concepts contained herein are proprietary to Adobe
* and its suppliers and are protected by all applicable intellectual
* property laws, including trade secret and copyright laws.
* Dissemination of this information or reproduction of this material
* is strictly forbidden unless prior written permission is obtained
* from Adobe.
**************************************************************************/

package com.adobe.blueapp.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.adobe.blueapp.exception.RestApiException;
import com.adobe.blueapp.mappers.dto.CustomErrorResponse;
import com.adobe.blueapp.mappers.dto.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class maps {@link RestApiException} to {@link Response}
 * 
 * @author siddgupt
 *
 */
@Provider
public class RestApiExceptionMapper implements ExceptionMapper<RestApiException> {

  @Override
  public Response toResponse(RestApiException exception) {
    logger.warn(exception.getMessage(), exception);

    Response.Status status = exception.getStatus();
    ErrorDetails errordetails = new ErrorDetails(exception.getCode(), exception.getMessage());

    return Response.status(status).entity(new CustomErrorResponse(errordetails)).build();
  }

  private static Logger logger = LoggerFactory.getLogger(RestApiExceptionMapper.class);
}

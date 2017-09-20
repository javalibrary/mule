/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.core.internal.exception;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mule.runtime.core.api.exception.Errors.ComponentIdentifiers.Handleable.CONNECTIVITY;
import static org.mule.runtime.core.api.exception.Errors.Identifiers.ANY_IDENTIFIER;
import static org.mule.runtime.core.api.exception.Errors.Identifiers.CONNECTIVITY_ERROR_IDENTIFIER;
import static org.mule.runtime.core.api.exception.Errors.Identifiers.CRITICAL_IDENTIFIER;
import static org.mule.test.allure.AllureConstants.ErrorHandlingFeature.ERROR_HANDLING;
import static org.mule.test.allure.AllureConstants.ErrorHandlingFeature.ErrorHandlingStory.ERROR_TYPES;
import org.mule.runtime.api.component.ComponentIdentifier;
import org.mule.runtime.api.message.ErrorType;
import org.mule.runtime.core.api.exception.ErrorTypeRepository;
import org.mule.tck.junit4.AbstractMuleTestCase;

import java.util.Optional;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Before;
import org.junit.Test;

@Feature(ERROR_HANDLING)
@Story(ERROR_TYPES)
public class DefaultErrorTypeRepositoryTestCase extends AbstractMuleTestCase {

  private ErrorTypeRepository errorTypeRepository = new DefaultErrorTypeRepository();
  private ComponentIdentifier MY_ERROR =
      ComponentIdentifier.builder().namespace("ns").name("name").build();

  @Before
  public void setUp() {
    errorTypeRepository.addErrorType(CONNECTIVITY, errorTypeRepository.getAnyErrorType());
    errorTypeRepository.addInternalErrorType(MY_ERROR, errorTypeRepository.getCriticalErrorType());
  }

  @Test
  public void lookupsAvailableErrorType() {
    Optional<ErrorType> errorType = errorTypeRepository.lookupErrorType(CONNECTIVITY);
    assertThat(errorType.isPresent(), is(true));
    assertThat(errorType.get().getIdentifier(), is(CONNECTIVITY.getName()));
    assertThat(errorType.get().getParentErrorType().getIdentifier(), is(ANY_IDENTIFIER));
  }

  @Test
  public void doesNotLookupUnavailableErrorType() {
    assertThat(errorTypeRepository.lookupErrorType(MY_ERROR).isPresent(), is(false));
  }

  @Test
  public void getsAvailableErrorTypes() {
    Optional<ErrorType> myErrorType = errorTypeRepository.getErrorType(CONNECTIVITY);
    assertThat(myErrorType.isPresent(), is(true));
    assertThat(myErrorType.get().getIdentifier(), is(CONNECTIVITY_ERROR_IDENTIFIER));
    assertThat(myErrorType.get().getParentErrorType().getIdentifier(), is(ANY_IDENTIFIER));
  }

  @Test
  public void getsUnavailableErrorTypes() {
    Optional<ErrorType> myErrorType = errorTypeRepository.getErrorType(MY_ERROR);
    assertThat(myErrorType.isPresent(), is(true));
    assertThat(myErrorType.get().getIdentifier(), is("name"));
    assertThat(myErrorType.get().getParentErrorType().getIdentifier(), is(CRITICAL_IDENTIFIER));
  }

}

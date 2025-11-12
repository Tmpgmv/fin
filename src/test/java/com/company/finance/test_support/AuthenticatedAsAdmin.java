package com.company.finance.test_support;

import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class AuthenticatedAsAdmin implements BeforeEachCallback, AfterEachCallback {
  @Override
  public void beforeEach(ExtensionContext context) {
    getSystemAuthenticator(context).begin("admin");
  }

  @Override
  public void afterEach(ExtensionContext context) {
    getSystemAuthenticator(context).end();
  }

  private SystemAuthenticator getSystemAuthenticator(ExtensionContext context) {
    ApplicationContext appContext = SpringExtension.getApplicationContext(context);
    return appContext.getBean(SystemAuthenticator.class);
  }
}

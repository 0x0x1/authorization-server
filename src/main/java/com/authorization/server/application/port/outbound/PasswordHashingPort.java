package com.authorization.server.application.port.outbound;

/**
 * Application Boundary. Defines the capabilities the application needs from the external systems. Outbound port.
 */
public interface PasswordHashingPort {

    String hash(String password);
}

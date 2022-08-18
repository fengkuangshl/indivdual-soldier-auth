/**
 *
 */
package com.key.win.security.service;

public interface RbacService {

    boolean permitAll();

    boolean denyAll();

    boolean anonymous();

    boolean authenticated();

    boolean hasRole(String... roles);

    boolean hasAnyRole(String... roles);

    boolean hasAuthority(String... authoritys);

    boolean hasAnyAuthority(String... authoritys);

}

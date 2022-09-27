/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.key.win.common.concurrent;

import java.util.concurrent.Callable;

import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import org.springframework.util.Assert;

/**
 * <p>
 * Wraps a delegate {@link Callable} with logic for setting up a
 * {@link Authentication} before invoking the delegate {@link Callable} and
 * then removing the {@link Authentication} after the delegate has completed.
 * </p>
 * <p>
 * If there is a {@link Authentication} that already exists, it will be
 * restored after the {@link #call()} method is invoked.
 * </p>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class DelegatingSecurityContextCallable<V> implements Callable<V> {

    private final Callable<V> delegate;


    /**
     * The {@link Authentication} that the delegate {@link Callable} will be
     * ran as.
     */
    private final Authentication delegateSecurityContext;

    /**
     * The {@link Authentication} that was on the {@link AuthenticationUtil}
     * prior to being set to the delegateSecurityContext.
     */
    private Authentication originalSecurityContext;

    /**
     * Creates a new {@link DelegatingSecurityContextCallable} with a specific
     * {@link Authentication}.
     *
     * @param delegate       the delegate {@link DelegatingSecurityContextCallable} to run with
     *                       the specified {@link Authentication}. Cannot be null.
     * @param Authentication the {@link Authentication} to establish for the delegate
     *                       {@link Callable}. Cannot be null.
     */
    public DelegatingSecurityContextCallable(Callable<V> delegate,
                                             Authentication Authentication) {
        Assert.notNull(delegate, "delegate cannot be null");
        Assert.notNull(Authentication, "Authentication cannot be null");
        this.delegate = delegate;
        this.delegateSecurityContext = Authentication;
    }

    /**
     * Creates a new {@link DelegatingSecurityContextCallable} with the
     * {@link Authentication} from the {@link AuthenticationUtil}.
     *
     * @param delegate the delegate {@link Callable} to run under the current
     *                 {@link Authentication}. Cannot be null.
     */
    public DelegatingSecurityContextCallable(Callable<V> delegate) {
        this(delegate, AuthenticationUtil.getAuthentication());
    }

    @Override
    public V call() throws Exception {
        this.originalSecurityContext = AuthenticationUtil.getAuthentication();

        try {
            AuthenticationUtil.setCurrentUser(delegateSecurityContext);
            return delegate.call();
        } finally {
            Authentication emptyContext = AuthenticationUtil.createEmptyContext();
            if (emptyContext.equals(originalSecurityContext)) {
                AuthenticationUtil.clearContext();
            } else {
                AuthenticationUtil.setCurrentUser(originalSecurityContext);
            }
            this.originalSecurityContext = null;
        }
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    /**
     * Creates a {@link DelegatingSecurityContextCallable} and with the given
     * {@link Callable} and {@link Authentication}, but if the Authentication is null
     * will defaults to the current {@link Authentication} on the
     * {@link AuthenticationUtil}
     *
     * @param delegate       the delegate {@link DelegatingSecurityContextCallable} to run with
     *                       the specified {@link Authentication}. Cannot be null.
     * @param Authentication the {@link Authentication} to establish for the delegate
     *                       {@link Callable}. If null, defaults to {@link AuthenticationUtil#getAuthentication()}
     * @return
     */
    public static <V> Callable<V> create(Callable<V> delegate,
                                         Authentication Authentication) {
        return Authentication == null ? new DelegatingSecurityContextCallable<>(
                delegate) : new DelegatingSecurityContextCallable<>(delegate,
                Authentication);
    }
}

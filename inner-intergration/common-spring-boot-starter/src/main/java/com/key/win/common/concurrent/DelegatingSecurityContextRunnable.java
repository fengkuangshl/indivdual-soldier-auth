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

import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import org.springframework.util.Assert;

/**
 * <p>
 * Wraps a delegate {@link Runnable} with logic for setting up a {@link Authentication}
 * before invoking the delegate {@link Runnable} and then removing the
 * {@link Authentication} after the delegate has completed.
 * </p>
 * <p>
 * If there is a {@link Authentication} that already exists, it will be
 * restored after the {@link #run()} method is invoked.
 * </p>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class DelegatingSecurityContextRunnable implements Runnable {

	private final Runnable delegate;

	/**
	 * The {@link Authentication} that the delegate {@link Runnable} will be
	 * ran as.
	 */
	private final Authentication delegateAuthentication;

	/**
	 * The {@link Authentication} that was on the {@link AuthenticationUtil}
	 * prior to being set to the delegateAuthentication.
	 */
	private Authentication originalAuthentication;

	/**
	 * Creates a new {@link DelegatingSecurityContextRunnable} with a specific
	 * {@link Authentication}.
	 * @param delegate the delegate {@link Runnable} to run with the specified
	 * {@link Authentication}. Cannot be null.
	 * @param Authentication the {@link Authentication} to establish for the delegate
	 * {@link Runnable}. Cannot be null.
	 */
	public DelegatingSecurityContextRunnable(Runnable delegate,
			Authentication Authentication) {
		Assert.notNull(delegate, "delegate cannot be null");
		Assert.notNull(Authentication, "Authentication cannot be null");
		this.delegate = delegate;
		this.delegateAuthentication = Authentication;
	}

	/**
	 * Creates a new {@link DelegatingSecurityContextRunnable} with the
	 * {@link Authentication} from the {@link AuthenticationUtil}.
	 * @param delegate the delegate {@link Runnable} to run under the current
	 * {@link Authentication}. Cannot be null.
	 */
	public DelegatingSecurityContextRunnable(Runnable delegate) {
		this(delegate, AuthenticationUtil.getAuthentication());
	}

	@Override
	public void run() {
		this.originalAuthentication = AuthenticationUtil.getAuthentication();

		try {
			AuthenticationUtil.setCurrentUser(delegateAuthentication);
			delegate.run();
		}
		finally {
			Authentication emptyContext = AuthenticationUtil.createEmptyContext();
			if (emptyContext.equals(originalAuthentication)) {
				AuthenticationUtil.clearContext();
			} else {
				AuthenticationUtil.setCurrentUser(originalAuthentication);
			}
			this.originalAuthentication = null;
		}
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	/**
	 * Factory method for creating a {@link DelegatingSecurityContextRunnable}.
	 *
	 * @param delegate the original {@link Runnable} that will be delegated to after
	 * establishing a {@link Authentication} on the {@link AuthenticationUtil}. Cannot
	 * have null.
	 * @param Authentication the {@link Authentication} to establish before invoking the
	 * delegate {@link Runnable}. If null, the current {@link Authentication} from the
	 * {@link AuthenticationUtil} will be used.
	 * @return
	 */
	public static Runnable create(Runnable delegate, Authentication Authentication) {
		Assert.notNull(delegate, "delegate cannot be  null");
		return Authentication == null ? new DelegatingSecurityContextRunnable(delegate)
				: new DelegatingSecurityContextRunnable(delegate, Authentication);
	}
}

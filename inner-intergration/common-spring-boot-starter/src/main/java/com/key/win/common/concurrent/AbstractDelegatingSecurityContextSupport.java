/*
 * Copyright 2002-2016 the original author or authors.
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

import com.key.win.common.auth.detail.Authentication;

/**
 * An internal support class that wraps {@link Callable} with
 * {@link DelegatingSecurityContextCallable} and {@link Runnable} with
 * {@link DelegatingSecurityContextRunnable}
 *
 * @author Rob Winch
 * @since 3.2
 */
abstract class AbstractDelegatingSecurityContextSupport {

	private final Authentication securityContext;

	/**
	 * Creates a new {@link AbstractDelegatingSecurityContextSupport} that uses the
	 * specified {@link Authentication}.
	 *
	 * @param securityContext the {@link Authentication} to use for each
	 * {@link DelegatingSecurityContextRunnable} and each
	 * {@link DelegatingSecurityContextCallable} or null to default to the current
	 * {@link Authentication}.
	 */
	AbstractDelegatingSecurityContextSupport(Authentication securityContext) {
		this.securityContext = securityContext;
	}

	protected final Runnable wrap(Runnable delegate) {
		return DelegatingSecurityContextRunnable.create(delegate, securityContext);
	}

	protected final <T> Callable<T> wrap(Callable<T> delegate) {
		return DelegatingSecurityContextCallable.create(delegate, securityContext);
	}
}

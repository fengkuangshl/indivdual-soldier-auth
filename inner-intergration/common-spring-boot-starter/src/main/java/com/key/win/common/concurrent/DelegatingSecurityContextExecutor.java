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

import java.util.concurrent.Executor;

import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;
import org.springframework.util.Assert;

/**
 * An {@link Executor} which wraps each {@link Runnable} in a
 * {@link DelegatingSecurityContextRunnable}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public class DelegatingSecurityContextExecutor extends
		AbstractDelegatingSecurityContextSupport implements Executor {
	private final Executor delegate;

	/**
	 * Creates a new {@link DelegatingSecurityContextExecutor} that uses the specified
	 * {@link Authentication}.
	 *
	 * @param delegateExecutor the {@link Executor} to delegate to. Cannot be null.
	 * @param Authentication the {@link Authentication} to use for each
	 * {@link DelegatingSecurityContextRunnable} or null to default to the current
	 * {@link Authentication}
	 */
	public DelegatingSecurityContextExecutor(Executor delegateExecutor,
			Authentication Authentication) {
		super(Authentication);
		Assert.notNull(delegateExecutor, "delegateExecutor cannot be null");
		this.delegate = delegateExecutor;
	}

	/**
	 * Creates a new {@link DelegatingSecurityContextExecutor} that uses the current
	 * {@link Authentication} from the {@link AuthenticationUtil} at the time the task
	 * is submitted.
	 *
	 * @param delegate the {@link Executor} to delegate to. Cannot be null.
	 */
	public DelegatingSecurityContextExecutor(Executor delegate) {
		this(delegate, null);
	}

	public final void execute(Runnable task) {
		task = wrap(task);
		delegate.execute(task);
	}

	protected final Executor getDelegateExecutor() {
		return delegate;
	}
}
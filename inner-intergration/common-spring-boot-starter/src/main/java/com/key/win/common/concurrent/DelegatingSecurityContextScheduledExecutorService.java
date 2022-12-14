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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.common.auth.detail.Authentication;

/**
 * An {@link ScheduledExecutorService} which wraps each {@link Runnable} in a
 * {@link DelegatingSecurityContextRunnable} and each {@link Callable} in a
 * {@link DelegatingSecurityContextCallable}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class DelegatingSecurityContextScheduledExecutorService extends
        DelegatingSecurityContextExecutorService implements ScheduledExecutorService {
    /**
     * Creates a new {@link DelegatingSecurityContextScheduledExecutorService} that uses
     * the specified {@link Authentication}.
     *
     * @param delegateScheduledExecutorService the {@link ScheduledExecutorService} to
     *                                         delegate to. Cannot be null.
     * @param Authentication                   the {@link Authentication} to use for each
     *                                         {@link DelegatingSecurityContextRunnable} and each
     *                                         {@link DelegatingSecurityContextCallable}.
     */
    public DelegatingSecurityContextScheduledExecutorService(
            ScheduledExecutorService delegateScheduledExecutorService,
            Authentication Authentication) {
        super(delegateScheduledExecutorService, Authentication);
    }

    /**
     * Creates a new {@link DelegatingSecurityContextScheduledExecutorService} that uses
     * the current {@link Authentication} from the {@link AuthenticationUtil}.
     *
     * @param delegate the {@link ScheduledExecutorService} to delegate to. Cannot be
     *                 null.
     */
    public DelegatingSecurityContextScheduledExecutorService(
            ScheduledExecutorService delegate) {
        this(delegate, null);
    }

    public final ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        command = wrap(command);
        return getDelegate().schedule(command, delay, unit);
    }

    public final <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay,
                                                 TimeUnit unit) {
        callable = wrap(callable);
        return getDelegate().schedule(callable, delay, unit);
    }

    public final ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                        long initialDelay, long period, TimeUnit unit) {
        command = wrap(command);
        return getDelegate().scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public final ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                           long initialDelay, long delay, TimeUnit unit) {
        command = wrap(command);
        return getDelegate().scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    private ScheduledExecutorService getDelegate() {
        return (ScheduledExecutorService) getDelegateExecutor();
    }
}
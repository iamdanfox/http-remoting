/*
 * Copyright 2015 Palantir Technologies, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.remoting.ssl;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

@JsonDeserialize(as = ImmutableKeyStoreConfiguration.class)
@JsonSerialize(as = ImmutableKeyStoreConfiguration.class)
@Value.Immutable
@Value.Style(visibility = ImplementationVisibility.PACKAGE)
public abstract class KeyStoreConfiguration {

    @Value.Parameter
    public abstract Path path();

    @Value.Parameter
    public abstract String password();

    @SuppressWarnings("checkstyle:designforextension")
    @Value.Default
    public String type() {
        return "JKS";
    }

    // alias of the key. If absent, first key returned by key store will be used.
    public abstract Optional<String> alias();

    @Value.Check
    protected final void check() {
        checkArgument(!path().equals(Paths.get("")), "path cannot be empty");
        checkArgument(!password().equals(""), "password cannot be empty");
    }

    public static KeyStoreConfiguration of(Path path, String password) {
        return ImmutableKeyStoreConfiguration.of(path, password);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends ImmutableKeyStoreConfiguration.Builder {}

}

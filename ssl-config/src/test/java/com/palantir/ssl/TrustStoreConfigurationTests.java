/*
 * Copyright 2016 Palantir Technologies, Inc. All rights reserved.
 */

package com.palantir.ssl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import com.google.common.io.Resources;
import com.palantir.remoting.ssl.TrustStoreConfiguration;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import org.junit.Before;
import org.junit.Test;

public final class TrustStoreConfigurationTests {

    private ObjectMapper mapper;

    @Before
    public void before() {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.registerModule(new Jdk7Module());
    }

    @Test(expected = JsonMappingException.class)
    public void deserializationShouldFailWithEmptyPath() throws Exception {
        mapper.readValue(getTestConfigYmlFile("trustStoreConfigEmptyPath.yml"), TrustStoreConfiguration.class);
    }

    private String getTestConfigYmlFile(String fileName) throws IOException {
        Charset defaultCharset = Charset.defaultCharset();
        URL url = Resources.getResource("testConfig/" + fileName);
        return Resources.toString(url, defaultCharset);
    }
}

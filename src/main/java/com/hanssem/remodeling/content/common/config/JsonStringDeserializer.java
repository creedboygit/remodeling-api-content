package com.hanssem.remodeling.content.common.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class JsonStringDeserializer extends com.fasterxml.jackson.databind.deser.std.StringDeserializer {

    private static final long serialVersionUID = 2477765420340669430L;

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return StringUtils.trim(super.deserialize(p, ctxt));
    }
}

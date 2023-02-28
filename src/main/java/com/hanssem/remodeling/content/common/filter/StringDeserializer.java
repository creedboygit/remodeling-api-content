package com.hanssem.remodeling.content.common.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class StringDeserializer extends com.fasterxml.jackson.databind.deser.std.StringDeserializer {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return StringUtils.trim(super.deserialize(p, ctxt));
    }
}

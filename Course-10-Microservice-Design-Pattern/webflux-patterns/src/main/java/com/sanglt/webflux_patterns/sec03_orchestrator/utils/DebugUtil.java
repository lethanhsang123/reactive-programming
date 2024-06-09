package com.sanglt.webflux_patterns.sec03_orchestrator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanglt.webflux_patterns.sec03_orchestrator.dtos.OrchestrationRequestContext;

public class DebugUtil {

    public static void print(OrchestrationRequestContext context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(context));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

package io.github.pratesjr.nutriledgerapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Set;

public class SanitizerUtil {
    private static final Set<String> SENSITIVE_FIELDS = Set.of("cpf", "email", "password", "senha", "ssn");
    private static final String MASK = "***";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String sanitize(Object obj) {
        if (obj == null) return null;
        try {
            ObjectNode node = (ObjectNode) mapper.valueToTree(obj);
            SENSITIVE_FIELDS.forEach(field -> {
                if (node.has(field)) {
                    node.put(field, MASK);
                }
            });
            return mapper.writeValueAsString(node);
        } catch (Exception e) {
            // fallback: return toString()
            return obj.toString();
        }
    }
}


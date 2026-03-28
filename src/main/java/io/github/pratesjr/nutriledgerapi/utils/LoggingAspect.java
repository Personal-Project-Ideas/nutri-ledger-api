package io.github.pratesjr.nutriledgerapi.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {
    private static final boolean LOG_DEBUG_ENABLED = Boolean.parseBoolean(System.getenv().getOrDefault("LOG_DEBUG_ENABLED", "true"));

    @Around("within(@org.springframework.web.bind.annotation.RestController *) || within(io.github.pratesjr.nutriledgerapi.infra.persistences..*) || within(@org.springframework.stereotype.Service *)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();
        String requestId = getRequestId();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        boolean isController = targetClass.isAnnotationPresent(org.springframework.web.bind.annotation.RestController.class);
        boolean isPersistence = targetClass.getPackageName().contains("infra.persistences");
        boolean isService = targetClass.isAnnotationPresent(org.springframework.stereotype.Service.class);

        // Select log level
        boolean logInfo = isController || isPersistence;
        boolean logDebug = isService && LOG_DEBUG_ENABLED && !logInfo;

        // Log method entry
        if (logInfo) {
            logger.info("[requestId={}] Entering {}.{} with args: {}", requestId, signature.getDeclaringTypeName(), methodName, SanitizerUtil.sanitize(args));
        } else if (logDebug) {
            logger.debug("[requestId={}] Entering {}.{} with args: {}", requestId, signature.getDeclaringTypeName(), methodName, SanitizerUtil.sanitize(args));
        }
        Object result;
        try {
            result = joinPoint.proceed();
            // Log method exit
            if (logInfo) {
                logger.info("[requestId={}] Exiting {}.{} with result: {}", requestId, signature.getDeclaringTypeName(), methodName, SanitizerUtil.sanitize(result));
            } else if (logDebug) {
                logger.debug("[requestId={}] Exiting {}.{} with result: {}", requestId, signature.getDeclaringTypeName(), methodName, SanitizerUtil.sanitize(result));
            }
            return result;
        } catch (Throwable ex) {
            // Log exception
            if (logInfo) {
                logger.error("[requestId={}] Exception in {}.{}: {}", requestId, signature.getDeclaringTypeName(), methodName, ex.getMessage(), ex);
            } else if (logDebug) {
                logger.error("[requestId={}] Exception in {}.{}: {}", requestId, signature.getDeclaringTypeName(), methodName, ex.getMessage(), ex);
            }
            throw ex;
        }
    }

    private String getRequestId() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest req = attrs.getRequest();
                Object attr = req.getAttribute("requestId");
                if (attr != null) return attr.toString();
            }
        } catch (Exception ignore) {}
        return "NO_REQUEST_ID";
    }
}

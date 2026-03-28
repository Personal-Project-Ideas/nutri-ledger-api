package io.github.pratesjr.nutriledgerapi.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.Mockito.*;

class LoggingAspectTest {
    @RestController
    static class DummyController {
        public String hello(String name) { return "Hello, " + name; }
    }

    @Test
    void shouldLogInfoForController() throws Throwable {
        LoggingAspect aspect = new LoggingAspect();
        DummyController controller = new DummyController();
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        MethodSignature signature = mock(MethodSignature.class);
        when(joinPoint.getTarget()).thenReturn(controller);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("hello");
        when(signature.getDeclaringTypeName()).thenReturn(controller.getClass().getName());
        when(joinPoint.getArgs()).thenReturn(new Object[]{"World"});
        when(joinPoint.proceed()).thenReturn("Hello, World");
        // Just ensure no exception is thrown and code runs
        aspect.logMethod(joinPoint);
    }
}


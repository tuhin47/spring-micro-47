package me.tuhin47.config;

import com.thoughtworks.xstream.XStream;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.axonframework.tracing.SpanFactory;
import org.axonframework.tracing.opentelemetry.OpenTelemetrySpanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();

        xStream.allowTypesByWildcard(new String[]{"me.tuhin47.**"});
        return xStream;
    }

    @Bean
    public SpanFactory spanFactory(Tracer tracer, TextMapPropagator textMapPropagator) {
        return OpenTelemetrySpanFactory.builder().tracer(tracer).contextPropagators(textMapPropagator).build();
    }
}
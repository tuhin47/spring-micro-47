package me.tuhin47.config;

import com.thoughtworks.xstream.XStream;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import org.axonframework.tracing.SpanFactory;
import org.axonframework.tracing.opentelemetry.OpenTelemetrySpanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();

        xStream.allowTypesByWildcard(new String[]{
            "me.tuhin47.**"
        });
        return xStream;
    }

    @Bean
    public Tracer openTelemetryTracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("axon-example-tracing");
    }

    @Bean
    public SpanFactory spanFactory(Tracer tracer) {
        return OpenTelemetrySpanFactory.builder()
                                       .tracer(tracer)
                                       .build();
    }
}
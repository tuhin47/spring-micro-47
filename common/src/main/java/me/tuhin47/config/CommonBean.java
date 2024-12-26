package me.tuhin47.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.exporter.*;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.*;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class CommonBean {

    public static final String ADMIN_USER_MAIL = "admin@tuhin47.com";

    @Bean
    public String[] whiteList(AppProperties appProperties) {
        if (appProperties.getConfig().getNoAuth()) {
            return new String[]{"/**"};
        }

        return new String[]{
            "/zipkin/**", "/auth/v3/api-docs/**", "/swagger**", "/actuator/**",
            "/order/v3/api-docs/**", "/payment/v3/api-docs/**", "/product/v3/api-docs/**"
        };
    }


    //https://stackoverflow.com/a/70892119/7499069
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment)
                                                                                                                       .equals(ManagementPortType.DIFFERENT));
    }

    @Bean(name = ExporterType.Constants.EXCEL)
    public ExcelGenerator<ExcelExporterDTO> getExcelExporter() {
        return new ExcelGenerator<>();
    }

    @Bean(name = ExporterType.Constants.CSV)
    public CSVGenerator<ExporterDTO> getCSVExporter() {
        return new CSVGenerator<>();
    }
}

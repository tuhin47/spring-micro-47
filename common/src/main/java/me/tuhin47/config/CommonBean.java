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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.service.ApiInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CommonBean {

    private final AppProperties appProperties;

    @Bean
    @ConditionalOnMissingBean(ApiInfo.class)
    public ApiInfo apiInfo() {
        return ApiInfo.DEFAULT;
    }

    @Bean
    public String[] whiteList() {
        List<String> whiteList = List.of(
            "/zipkin/**", "/auth/v3/api-docs/**", "/swagger**", "/actuator/**",
            "/order/v3/api-docs/**","/payment/v3/api-docs/**","/product/v3/api-docs/**"
        );
        if (appProperties.getConfig().getNoAuth()) {
            whiteList = List.of("/**");
        }

        return whiteList.toArray(new String[0]);
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

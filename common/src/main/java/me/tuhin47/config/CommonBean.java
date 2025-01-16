package me.tuhin47.config;

import lombok.RequiredArgsConstructor;
import me.tuhin47.exporter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class CommonBean {

    public static final String ADMIN_USER_MAIL = "admin@tuhin47.com";

    private final AppProperties appProperties;

    @Bean
    public String[] whiteList() {
        if (appProperties.getConfig().getNoAuth()) {
            return new String[]{"/**"};
        }

        return new String[]{
            "/zipkin/**",
            "/actuator/**",
            "/swagger**",
            "/auth/v3/api-docs/**",
            "/order/v3/api-docs/**",
            "/payment/v3/api-docs/**",
            "/product/v3/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
        };
    }


    //https://stackoverflow.com/a/70892119/7499069
    /*@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
    }*/

    @Bean(name = ExporterType.Constants.EXCEL)
    public ExcelGenerator<ExcelExporterDTO> getExcelExporter() {
        return new ExcelGenerator<>();
    }

    @Bean(name = ExporterType.Constants.CSV)
    public CSVGenerator<ExporterDTO> getCSVExporter() {
        return new CSVGenerator<>();
    }
}

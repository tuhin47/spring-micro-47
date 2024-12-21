package me.tuhin47.config.exception;

import me.tuhin47.config.exception.apierror.ApiError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        String statusStr = errorAttributes.get("status").toString();
        if (StringUtils.isNumeric(statusStr)) {
            HttpStatus status = HttpStatus.valueOf(NumberUtils.parseNumber(statusStr, Integer.class));
            errorAttributes.put("apierror", new ApiError(status));
        }
        return errorAttributes;
    }
}

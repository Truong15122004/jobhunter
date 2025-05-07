package com.util.format;

import com.dto.response.RestResponse;
import com.util.annotation.ApiMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatus(status);
        if (body instanceof String) {
            return body;
        }
        if (status >= 400) {
            return body;
        } else {
            res.setData(body);
            ApiMessage apiMessage = returnType.getMethodAnnotation(ApiMessage.class);
            res.setMessage(apiMessage != null ? apiMessage.value() : "Call api success");
        }
        return res;
    }
}
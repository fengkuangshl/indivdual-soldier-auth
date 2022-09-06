package com.key.win.rsa.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ParameterFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Content-Type
        String contentType = request.getContentType();
        String requestBody = null;
        boolean shouldEncrypt = false;
        if(!StringUtils.isEmpty(contentType)){
            if (StringUtils.substringMatch(contentType, 0, MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                shouldEncrypt = true;
                requestBody = convertFormToString(request);
            } else if (StringUtils.substringMatch(contentType, 0, MediaType.APPLICATION_JSON_VALUE)) {
                shouldEncrypt = true;
                requestBody = convertInputStreamToString(request.getInputStream());
            }
        }
        if (!shouldEncrypt) {
            filterChain.doFilter(request, response);
        } else {
            ParameterHttpWrapper wrapper = new ParameterHttpWrapper(request, requestBody);
            wrapper.putHeader("Content-Type", MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE);
            filterChain.doFilter(wrapper, response);
        }
    }

    private String convertFormToString(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>(8);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            result.put(name, request.getParameter(name));
        }
        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        return StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
    }
}
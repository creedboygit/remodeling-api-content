package com.hanssem.remodeling.content.common.filter;

import com.hanssem.remodeling.content.constant.MdcType;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class MdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        MDC.put(MdcType.CUST_NO.name(), Optional.ofNullable(request.getHeader(MdcType.CUST_NO.getCode())).orElse("-"));
        MDC.put(MdcType.REQUEST_URL.name(), Optional.of(request.getRequestURL().toString()).orElse(""));
        MDC.put(MdcType.TRACE_ID.name(), Optional.ofNullable(request.getHeader(MdcType.TRACE_ID.getCode())).orElse(""));
        filterChain.doFilter(request, response);
        MDC.clear();
    }

}

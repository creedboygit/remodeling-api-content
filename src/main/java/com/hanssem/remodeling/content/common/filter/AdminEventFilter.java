package com.hanssem.remodeling.content.common.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanssem.remodeling.content.common.model.AdminEventInfo;
import com.hanssem.remodeling.content.common.response.Response;
import com.hanssem.remodeling.content.constant.ResponseCode;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminEventFilter extends OncePerRequestFilter {


    @Lazy
    private final ObjectMapper mapper;

    @Lazy
    private final AdminEventInfo eventInfo;

    private static final String ALREADY_FILTERED_SUFFIX = "/admin/v1";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
        	if(request.getRequestURI().startsWith(ALREADY_FILTERED_SUFFIX)) {
	            String regId = request.getHeader("regId");
	            String pageNm = request.getHeader("pageNm");
	            int roleIdx = 0;
	            if(request.getHeader("roleIdx") != null)
	            	roleIdx = Integer.valueOf(request.getHeader("roleIdx"));
	            String pageNo = request.getHeader("pageNo");
	            String pageURL = request.getRequestURI();
	            String method = request.getMethod();
	            String queryString = "";
	            String srvNm = request.getLocalName();
	            String localPort = String.valueOf(request.getLocalPort());
	            String wasIp = request.getLocalAddr();
	            String clientIp = request.getHeader("X-Forwarded-For");
	            String ua = request.getHeader("User-Agent");

	            if(method.equals("GET"))
	            	queryString = request.getQueryString();

	            eventInfo.setPageNm(pageNm);
	            eventInfo.setRegId(regId);
	            eventInfo.setRoleIdx(roleIdx);
	            eventInfo.setPageNo(pageNo);
	            eventInfo.setPageURL(pageURL);
	            eventInfo.setMethod(method);
	            eventInfo.setSrvNm(srvNm);
	            eventInfo.setWasIP(wasIp);
	            eventInfo.setSrvPort(localPort);
	            eventInfo.setWasPort(localPort);
	            eventInfo.setQueryString(queryString);
	            eventInfo.setClientIPAddr(clientIp);
	            eventInfo.setClientAgent(ua);

	            log.debug(eventInfo.toString());
        	}

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            displayException(response);
        }
    }

    /**
     * Filter에서 오류 발생 시 에러 화면 출력 용
     * @param response 응답
     * @throws IOException 발생
     */
    private void displayException(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(new Response<>(
                ResponseCode.ERROR.getCode(),
                ResponseCode.ERROR.getMessage(),
                null)));
        response.flushBuffer();
    }
}

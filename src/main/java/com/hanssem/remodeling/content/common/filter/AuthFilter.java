package com.hanssem.remodeling.content.common.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanssem.remodeling.content.common.model.AuthClaim;
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
public class AuthFilter extends OncePerRequestFilter {


    @Lazy
    private final ObjectMapper mapper;

    @Lazy
    private final AuthClaim authClaim;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws IOException {
        try {
            String userId = request.getHeader("custNo");
            String membershipId = request.getHeader("intMbsCustNo");

            String authorization = request.getHeader("Authorization");

            if (userId != null) {
                try {
                    authClaim.setUserId(Long.parseLong(userId));
                } catch (NumberFormatException e) {
                    authClaim.setUserId(Float.valueOf(userId).longValue());
                }
            }
            if (membershipId != null) {
                try {
                    authClaim.setMembershipId(Long.parseLong(membershipId));
                } catch (NumberFormatException e) {
                    authClaim.setMembershipId(Float.valueOf(membershipId).longValue());
                }
            }

            if (authorization != null) {
                authClaim.setAuthorization(authorization);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            displayException(response);
        }
    }

    /**
     * Filter에서 오류 발생 시 에러 화면 출력 용
     *
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

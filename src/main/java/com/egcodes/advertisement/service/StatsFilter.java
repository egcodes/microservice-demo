package com.egcodes.advertisement.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.egcodes.advertisement.constants.Logging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@WebFilter("/*")
@Slf4j
public class StatsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        var start = Instant.now();
        try {
            chain.doFilter(req, resp);
        } finally {
            var finish = Instant.now();
            var time = Duration.between(start, finish).toMillis();
            var path = ((HttpServletRequest) req).getServletPath();

            if ((path.contains("/advertisement") || path.contains("/dashboard"))
                && time > Logging.MAX_LIMIT_TIME) {
                log.info("{}: {} ms ", ((HttpServletRequest) req).getRequestURI(), time);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
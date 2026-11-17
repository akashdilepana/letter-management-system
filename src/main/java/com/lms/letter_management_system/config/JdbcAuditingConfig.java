package com.lms.letter_management_system.config;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableJdbcAuditing(auditorAwareRef = "auditorProvider")
public class JdbcAuditingConfig {

    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return () -> {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            if (!(attributes instanceof ServletRequestAttributes servletAttributes)) {
                return Optional.empty();
            }

            HttpSession session = servletAttributes.getRequest().getSession(false);
            if (session == null) {
                return Optional.empty();
            }

            Object uid = session.getAttribute("uid");
            return uid instanceof Number number
                    ? Optional.of(number.intValue())
                    : Optional.empty();
        };
    }
}

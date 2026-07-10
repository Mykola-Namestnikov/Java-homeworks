package org.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.TimeZone;

@WebFilter("/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String tzParam = req.getParameter("timezone");

        if (tzParam != null && !tzParam.isEmpty()) {
            String formattedTz = tzParam.replace(" ", "+");
            if (!isValidTimezone(formattedTz)) {
                res.setStatus(400);
                res.setContentType("text/html; charset=utf-8");
                res.getWriter().write("Invalid timezone");
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private boolean isValidTimezone (String tz) {
        try {
            String id = tz.replace("UTC", "GMT").replace(" ", "+");
            java.time.ZoneId.of(id);
            //TimeZone.getTimeZone(tz.replace("UTC", "GMT"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

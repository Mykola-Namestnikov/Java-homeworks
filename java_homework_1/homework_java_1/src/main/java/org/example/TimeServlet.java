package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() {
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(getServletContext());
        WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(application);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);

        engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");

        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie c: cookies) {
                    if ("lastTimezone".equals(c.getName())) {
                        timezone = c.getValue();
                    }
                }
            }
        }

        if (timezone == null || timezone.isEmpty()) {
            timezone = "UTC";
        }

        if (timezone != null) {
            timezone = timezone.replace(" ", "+");
        }

        resp.addCookie(new Cookie("lastTimezone", timezone));

        String formattedTime = java.time.ZonedDateTime.now(ZoneId.of(timezone.replace("UTC", "GMT")))
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + " " + timezone;

        Context context = new Context();
        context.setVariable("time", formattedTime);

        resp.setContentType("text/html; charset=utf-8");
        engine.process("time", context, resp.getWriter());
    }
}

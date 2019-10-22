package org.api.mock.excp;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * The type Index controller (for errors).
 */
@RestController
public class IndexController implements ErrorController {

    @Resource
    private Environment environment;

    private static final String PATH = "/error";

    /**
     * Error string.
     *
     * @return the string
     */
    @GetMapping(value = PATH)
    public String error() {
        String context = environment.getProperty("server.servlet.context-path");
        String index = environment.getProperty("swagger.indexPage");
        String host = Objects.nonNull(environment.getProperty("server.port")) ? ":" + environment.getProperty("server.port") : "";
        try {
            host = Inet4Address.getLocalHost().getHostAddress() + host;
        } catch (UnknownHostException e) {
            host = "[hostname]";
        }
        return String.format("Error handling, for api infos show: <a href=\"http://%s%s/%s\">swagger</a>", host, context, index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorPath() {
        return PATH;
    }
}
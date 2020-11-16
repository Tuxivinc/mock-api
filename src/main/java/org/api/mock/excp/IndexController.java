package org.api.mock.excp;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @GetMapping(value = PATH, produces = "application/json")
    public ResponseEntity<ErrorMssage> error(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String context = environment.getProperty("server.servlet.context-path");
        String index = environment.getProperty("swagger.indexPage");
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return new ResponseEntity<>(
                new ErrorMssage(statusCode,
                        exception == null ? "N/A" : exception.getMessage(),
                        String.format("%s%s/%s", host, context, index)),
                Objects.isNull(statusCode) ? HttpStatus.CONFLICT : HttpStatus.resolve(statusCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorPath() {
        return PATH;
    }

    private class ErrorMssage {
        private Integer statusCode;
        private String exception;
        private String apiInfo;

        public ErrorMssage(Integer statusCode, String exception, String apiInfo) {
            this.statusCode = statusCode;
            this.exception = exception;
            this.apiInfo = apiInfo;
        }

        /**
         * Getter for property 'statusCode'.
         *
         * @return Value for property 'statusCode'.
         */
        public Integer getStatusCode() {
            return statusCode;
        }

        /**
         * Getter for property 'exception'.
         *
         * @return Value for property 'exception'.
         */
        public String getException() {
            return exception;
        }

        /**
         * Getter for property 'apiInfo'.
         *
         * @return Value for property 'apiInfo'.
         */
        public String getApiInfo() {
            return apiInfo;
        }
    }

}
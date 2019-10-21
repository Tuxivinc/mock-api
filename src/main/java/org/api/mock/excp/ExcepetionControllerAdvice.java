package org.api.mock.excp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Excepetion controller advice.
 */
@ControllerAdvice
class ExcepetionControllerAdvice {

    /**
     * Number format error message.
     *
     * @param ex the ex
     * @return the error message
     */
    @ResponseBody
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage numberFormat(NumberFormatException ex) {
        return new ErrorMessage(ex.getMessage(), ex.getClass().getName());
    }

    /**
     * Other error error message.
     *
     * @param ex the ex
     * @return the error message
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage otherError(Exception ex) {
        return new ErrorMessage(ex.getMessage(), ex.getClass().getName());
    }

    /**
     * The type Error message.
     */
    class ErrorMessage {
        private String message;
        private String errorClassName;

        /**
         * Instantiates a new Error message.
         *
         * @param message        the message
         * @param errorClassName the error class name
         */
        public ErrorMessage(String message, String errorClassName) {
            this.message = message;
            this.errorClassName = errorClassName;
        }

        /**
         * Getter for property 'message'.
         *
         * @return Value for property 'message'.
         */
        public String getMessage() {
            return message;
        }

        /**
         * Getter for property 'errorClassName'.
         *
         * @return Value for property 'errorClassName'.
         */
        public String getErrorClassName() {
            return errorClassName;
        }
    }

}
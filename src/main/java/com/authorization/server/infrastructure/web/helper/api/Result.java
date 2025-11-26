package com.authorization.server.infrastructure.web.helper.api;

import java.util.List;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Generic API response wrapper that represents either a successful or a failed operation.
 *
 * This class models both success and error scenarios in a single structure:
 *  - On success: {@code data} is populated, and {@code errors} is empty.
 *  - On failure: {@code data} is null or ignored, and {@code errors} contains one or more messages.
 *
 * Additional metadata such as {@code code}, {@code token}, and {@code message} provide context for
 * authentication, debugging, and status tracking.
 *
 * This class respects the OpenAPI contract defined in the application under classpath:/openapi.yaml.
 *
 * @param <T> The type of data returned on successful operations.
 *
 * @see org.springframework.http.ResponseEntity
 *
 */
public class Result<T> {

    private final T data;
    private final int code;
    private final String token;

    private final String message;
    @JsonIgnore
    private final MessageList errors;

    /**
     * Private constructor used by the builder.
     *
     * @param data    the response payload
     * @param code    the HTTP status code
     * @param token   optional token (e.g., auth token)
     * @param message the main response message (user-friendly)
     * @param errors  the errors message list
     *
     * @throws IllegalArgumentException if {@code message} or {@code errors} is {@code null}
     */
    private Result(T data, int code, String token, String message, MessageList errors) {
        Assert.notNull(message, "message must not be null");
        this.data = data;
        this.code = code;
        this.token = token;
        this.message = message;
        this.errors = errors;
    }

    /**
     * Sets the HTTP status code. The entry point of instantiating a Result<T>.
     *
     * @param code valid HTTP status code (100-599).
     * @return builder.
     */
    public static <T> ResultBuilder<T> status(int code) {
        return new ResultBuilder<T>().code(code);
    }

    /**
     * @return the HTTP status code associated with the response.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * @return the general user-friendly message summarizing the result.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return the instance of {@code MessageList} containing the error
     * messages (empty if operation is successful).
     */
    public MessageList getErrors() {
        return this.errors;
    }

    /**
     *
     * @return flatten errors to represent them in a simple format for consumption.
     */
    @JsonProperty("errors")
    public List<Message> getFlattenedErrors() {
        return ApiResponseProcessor.flattenErrors(errors);
    }

    /**
     * @return the associated token (e.g., auth token), or null if not set.
     */
    public String getToken() {
        return this.token;
    }

    /**
     * @return the payload data (null if operation failed).
     */
    public T getData() {
        return this.data;
    }

    /**
     * Begins building a {@link Result} with the given HTTP status code.
     *
     * @code the status code
     * @param <T>  the type of the response payload
     * @return a {@link ResultBuilder} instance
     */
    public static final class ResultBuilder<T> {
        private int code;
        private String message;
        private MessageList errors = new MessageList();
        private String token;
        private T data;

        /**
         * Prevents direct instantiation of the builder.
         */
        private ResultBuilder() {
        }

        /**
         * Sets the HTTP status code.
         *
         * @param code valid HTTP status code (100-599).
         * @return this builder.
         */
        public ResultBuilder<T> code(int code) {
            Assert.isTrue(code >= 100 && code <= 599, "Invalid HTTP status code: " + code);
            this.code = code;
            return this;
        }

        /**
         * Sets the human-readable response message.
         *
         * @param message a non-null message.
         * @return this builder.
         */
        public ResultBuilder<T> message(String message) {
            Assert.notNull(message, "message cannot be null");
            this.message = message;
            return this;
        }

        /**
         * Sets the error messages for a failed operation.
         *
         * @param errors a non-null list of error messages.
         * @return this builder.
         */
        public ResultBuilder<T> errors(MessageList errors) {
            Assert.notNull(errors, "errors cannot be null");
            this.errors = errors;
            return this;
        }

        /**
         * Sets the response payload.
         *
         * @param data the payload object (non-null for successful responses. Null for failed responses)
         * @return this builder
         */
        public ResultBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        /**
         * Sets the optional token (e.g., for authentication).
         *
         * @param token a token string. Null means not defined yet.
         * @return this builder
         */
        public ResultBuilder<T> token(String token) {
            this.token = token;
            return this;
        }

        /**
         * Builds a {@link Result} instance with the provided values.
         *
         * @return a new {@link Result} object.
         */
        public Result<T> build() {
            return new Result<T>(this.data, this.code, this.token, this.message, this.errors);
        }
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", code=" + code +
                ", token='" + token + '\'' +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
package com.authorization.server.web.api.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

public record Result<T>(HttpStatus code, T data, Success success, String message, List<Message> errors) {

    public final static class OnSuccess<T> {
        private HttpStatus code;
        private T data;
        private String message;
        private final List<Message> errors = new ArrayList<>();

        private OnSuccess() {
        }

        public OnSuccess<T> withCode(HttpStatus code) {
            this.code = code;
            return this;
        }

        public OnSuccess<T> withData(T data) {
            this.data = data;
            return this;
        }

        public OnSuccess<T> withMessage(String message) {
            Objects.requireNonNull(message, "message cannot be null");
            this.message = message;
            return this;
        }

        public Result<T> build() {
            return new Result<T>(this.code, this.data, Success.TRUE, this.message, this.errors);
        }
    }

    public static <T> OnSuccess<T> onSuccess() {
        return new OnSuccess<>();
    }

    public final static class OnFailure<T> {
        private HttpStatus code;
        private T data;
        private String message;
        private List<Message> errors;

        public OnFailure<T> withCode(HttpStatus code) {
            this.code = code;
            return this;
        }

        public OnFailure<T> withData(T data) {
            this.data = data;
            return this;
        }

        public OnFailure<T> withMessage(String message) {
            Objects.requireNonNull(message, "message cannot be null");
            this.message = message;
            return this;
        }

        public OnFailure<T> withErrors(List<Message> errors) {
            Objects.requireNonNull(errors, "errors cannot be null");
            this.errors = new ArrayList<>(errors);
            return this;
        }

        public Result<T> build() {
            return new Result<T>(this.code, this.data, Success.FALSE, this.message, this.errors);
        }
    }

    public static <T> OnFailure<T> onFailure() {
        return new OnFailure<>();
    }
}
package com.emlakjet.purchasing.shared;

public class GenericResponse<T> {

    private T data;
    private String message;
    private Integer code;
    private boolean success;

    private GenericResponse() {
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public boolean getSuccess() {
        return success;
    }

    public static class Builder<T> {
        private T data;
        private String message;
        private Integer code;
        private boolean success;

        Builder() {
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public GenericResponse<T> build() {
            GenericResponse<T> response = new GenericResponse<>();
            response.data = this.data;
            response.message = this.message;
            response.code = this.code;
            response.success = this.success;
            return response;
        }
    }
}
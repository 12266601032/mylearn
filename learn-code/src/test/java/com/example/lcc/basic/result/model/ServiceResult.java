package com.example.lcc.basic.result.model;

import java.io.Serializable;
import java.util.Objects;

public class ServiceResult<T, C> implements Serializable {

    private T data;
    private CodeMessage<C> message;
    private boolean isSuccess;

    ServiceResult(T data, boolean isSuccess, CodeMessage<C> message) {
        this.data = data;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public T getData() {
        return data;
    }

    public CodeMessage<C> getCodeMessage() {
        return message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public static <C> DefaultMessage<C> defaultMessage(C code, String message) {
        return new DefaultMessage<>(code, message);
    }

    public static <T, C> ServiceResultBuilder<T, C> success() {
        ServiceResultBuilder<T, C> builder = builder();
        return builder.isSuccess(true);
    }

    public static <T, C> ServiceResultBuilder<T, C> success(CodeMessage<C> codeMessage) {
        ServiceResultBuilder<T, C> builder = builder();
        return builder.isSuccess(true).code(codeMessage.getCode()).message(codeMessage.getMessage());
    }

    public static <T> ServiceResult<T, String> success(T data) {
        ServiceResultBuilder<T, String> success = success();
        return success.data(data).build();
    }

    public static <D, C> ServiceResult<D, C> error(CodeMessage<C> codeMessage) {
        ServiceResultBuilder<D, C> builder = builder();
        return builder.isSuccess(false).code(codeMessage.getCode()).message(codeMessage.getMessage()).build();
    }

    public static <D, C> ServiceResultBuilder<D, C> error() {
        ServiceResultBuilder<D, C> builder = builder();
        return builder.isSuccess(false);
    }


    private static <D, C> ServiceResultBuilder<D, C> builder() {
        return new ServiceResultBuilder<>();
    }

    public static class DefaultMessage<C> implements CodeMessage<C>, java.io.Serializable {

        private C code;
        private String message;

        public DefaultMessage(C code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public C getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }


        @Override
        public String toString() {
            return "DefaultMessage{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }


    public static class ServiceResultBuilder<T, C> {

        private T data;
        private C code;
        private String message;
        private boolean isSuccess;

        ServiceResultBuilder() { //package private
        }

        public ServiceResultBuilder<T, C> data(T data) {
            this.data = data;
            return this;
        }

        ServiceResultBuilder<T, C> isSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public ServiceResultBuilder<T, C> code(C code) {
            this.code = code;
            return this;
        }

        public ServiceResultBuilder<T, C> message(String message) {
            this.message = message;
            return this;
        }

        public ServiceResult<T, C> build() {
            Objects.requireNonNull(code, "code");
            Objects.requireNonNull(code, "message");
            return new ServiceResult<>(data, isSuccess, new DefaultMessage<>(code, message));
        }
    }

    @Override
    public String toString() {
        return "ServiceResult{" +
                "data=" + data +
                ", message=" + message +
                ", isSuccess=" + isSuccess +
                '}';
    }
}

package com.example.lcc.basic.result;

import java.io.Serializable;
import java.util.Objects;

/**
 * @date 2018/3/23
 */
public class ServiceResult<T extends Serializable, C extends Serializable> implements Serializable {

    public static final CodeMessage<String> SUCCESS = new DefaultMessage<>("00000000", "success");

    private T data;
    private CodeMessage<C> message;
    private boolean isSuccess;

    public ServiceResult(T data, boolean isSuccess, CodeMessage<C> message) {
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

    public static <D extends Serializable, C extends Serializable> ServiceResultBuilder<D, C> success() {
        ServiceResultBuilder<D, C> builder = builder();
        return builder.isSuccess(true);
    }

    public static <D extends Serializable, C extends Serializable> ServiceResultBuilder<D, C> success(CodeMessage<C> codeMessage) {
        ServiceResultBuilder<D, C> builder = builder();
        return builder.isSuccess(true).code(codeMessage.getCode()).message(codeMessage.getMessage());
    }

    public static <D extends Serializable> ServiceResult<D, String> success(D data) {
        ServiceResultBuilder<D, String> success = success(SUCCESS);
        return success.data(data).build();
    }

    public static <D extends Serializable, C extends Serializable> ServiceResult<D, C> error(CodeMessage<C> codeMessage) {
        ServiceResultBuilder<D, C> builder = builder();
        return builder.isSuccess(false).code(codeMessage.getCode()).message(codeMessage.getMessage()).build();
    }

    public static <D extends Serializable, C extends Serializable> ServiceResultBuilder<D, C> error() {
        ServiceResultBuilder<D, C> builder = builder();
        return builder.isSuccess(false);
    }


    static <D extends Serializable, C extends Serializable> ServiceResultBuilder<D, C> builder() {
        return new ServiceResultBuilder<>();
    }

    public static class DefaultMessage<C extends Serializable> implements CodeMessage<C> {

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

    }


    public static class ServiceResultBuilder<T extends Serializable, C extends Serializable> {

        private T data;
        private C code;
        private String message;
        private boolean isSuccess;


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
}

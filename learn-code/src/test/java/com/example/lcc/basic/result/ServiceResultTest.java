package com.example.lcc.basic.result;

import static org.junit.Assert.*;

import com.example.lcc.basic.result.model.CodeMessage;
import com.example.lcc.basic.result.model.ServiceResult;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @date 2018/3/23
 */
public class ServiceResultTest {

    /**
     * 使用示例：
     * 返回String类型的结果 结果码也为String类型
     * ServiceResult<String, String> success = ServiceResult.success("data");
     * <p>
     * 根据泛型信息自定义结果码类型
     * ServiceResult.<String, Integer>success(new ServiceResult.DefaultMessage<>(200, "ok")).data(null).build();
     * <p>
     * 返回错误结果
     * ServiceResult.<String, Integer>error().code(-1).message("error").build();
     * <p>
     * 使用枚举类型作为错误码
     * ServiceResult.<String, EnumCode>success().code(EnumCode.S000).message(EnumCode.S000.message).data("").build();
     */
    @Test
    public void serviceResult() throws Exception {
        ServiceResult<String, String> success = ServiceResult.success("");
        assertEquals("00000000", success.getCodeMessage().getCode());
        assertTrue(success.isSuccess());
        assertEquals("", success.getData());
        assertEquals("success", success.getCodeMessage().getMessage());


        ServiceResult<String, Integer> ok = ServiceResult.<String, Integer>success(new ServiceResult.DefaultMessage<>(200, "ok")).data(null).build();
        assertEquals(200, ok.getCodeMessage().getCode().intValue());
        assertEquals(null, ok.getData());
        assertTrue(ok.isSuccess());
        assertEquals("ok", ok.getCodeMessage().getMessage());

        ServiceResult<String, Long> success1 = ServiceResult.<String, Long>success()
                .code(1000l)
                .message("success")
                .build();

        assertEquals(1000l, success1.getCodeMessage().getCode().intValue());
        assertEquals(null, success1.getData());
        assertTrue(success1.isSuccess());
        assertEquals("success", success1.getCodeMessage().getMessage());


        ServiceResult<String, Integer> error = ServiceResult.<String, Integer>error().code(-1).message("error").build();

        assertEquals(-1, error.getCodeMessage().getCode().intValue());
        assertEquals(null, error.getData());
        assertFalse(error.isSuccess());
        assertEquals("error", error.getCodeMessage().getMessage());


        ServiceResult<String, EnumCode> enumCodeServiceResult = ServiceResult.<String, EnumCode>success().code(EnumCode.S000).message(EnumCode.S000.message).data("").build();

        assertEquals(EnumCode.S000, enumCodeServiceResult.getCodeMessage().getCode());
        assertEquals("", enumCodeServiceResult.getData());
        assertTrue(enumCodeServiceResult.isSuccess());
        assertEquals(EnumCode.S000.message, enumCodeServiceResult.getCodeMessage().getMessage());


        ServiceResult<String, Integer> error1 = ServiceResult.error(EnumMessage.E404);
        assertEquals(EnumMessage.E404.code, error1.getCodeMessage().getCode());
        assertEquals(null, error1.getData());
        assertFalse(error1.isSuccess());
        assertEquals(EnumMessage.E404.message, error1.getCodeMessage().getMessage());

        ServiceResult<String, Long> error2 = ServiceResult.error(new ServiceResult.DefaultMessage<Long>(1000l, "error"));
        assertEquals(1000l, error2.getCodeMessage().getCode().longValue());
        assertEquals(null, error2.getData());
        assertFalse(error2.isSuccess());
        assertEquals("error", error2.getCodeMessage().getMessage());

        ServiceResult<String, Integer> error3 = ServiceResult.error(EnumMessage.E500);
        assertEquals(EnumMessage.E500.getCode(), error3.getCodeMessage().getCode());
        assertEquals(null, error3.getData());
        assertFalse(error3.isSuccess());
        assertEquals(EnumMessage.E500.getMessage(), error3.getCodeMessage().getMessage());
    }

    @Test
    public void testServiceResult() {
        ServiceResult<List<String>, String> s = ServiceResult.success(Arrays.asList("1", "2", "3"));
        s.isSuccess(); //true
        ServiceResult<?, Integer> e1 = ServiceResult.error(CodeMessageEnum.some_error);
        e1.isSuccess(); //false
        ServiceResult<?, Long> e2 = ServiceResult.error(new ServiceResult.DefaultMessage<>(1000L, "error"));
        e2.isSuccess(); //false
        System.out.println(s);
        System.out.println(e1);
        System.out.println(e2);

    }


    enum CodeMessageEnum implements CodeMessage<Integer> {
        some_error(1001, "错误信息。");
        Integer code;
        String message;

        CodeMessageEnum(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }


    private enum EnumMessage implements CodeMessage<Integer> {
        S000(200, "success"),
        E404(400, "no found"),
        E500(500, "exception");

        Integer code;
        String message;

        EnumMessage(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    private enum EnumCode {
        S000("success"),
        E404("no found"),
        E500("exception");
        String message;

        EnumCode(String message) {
            this.message = message;
        }

    }
}

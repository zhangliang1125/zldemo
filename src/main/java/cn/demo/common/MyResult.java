package cn.demo.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static cn.demo.common.ResponseErrorCode.SYSTEM_ERROR;
import static cn.demo.common.ResponseErrorCode.SYSTEM_SUCCESS;


@Data
@Slf4j
@Component
public class MyResult<T extends Object> {
	/**
	 * 状态码
	 */
	private String error_code;

	/**
	 * 返回消息
	 */
	private String error_msg;

	private static String environmentStatic;



	private T data;

	public interface Command<D> {
		D execute() throws Exception;
	}

	private MyResult(){

	}

	public static <D> MyResult<D> build(Command<D> cmd) {
		D data = null;
		MyResult<D> result = new MyResult<D>();
		try {
			data = cmd.execute();
			result.setError_msg("");
			result.setError_code(SYSTEM_SUCCESS.code);
			result.setData(data);

		} catch (Throwable e) {
			log.error(!(StringUtils.isBlank(e.getMessage())) ? e.getMessage() : e.getClass().toString(), e);
			result.setError_code(SYSTEM_ERROR.code);
			result.setError_msg("系统开小差啦~");
		}
		return result;
	}


	@Nullable
	public static <D> MyResult<D> buildFailedDefault(ResponseErrorCode responseErrorCode, String errMsg) {
		if (responseErrorCode == null) {
			return null;
		}

		MyResult<D> result = new MyResult<D>();
		result.setError_code(responseErrorCode.code);
		result.setData(null);

		if (StringUtils.isBlank(errMsg)) {
			result.setError_msg(responseErrorCode.info);
		} else {
			result.setError_msg(errMsg);
		}

		return result;
	}

	public static <D> MyResult<D> buildFailedDefault(ResponseErrorCode responseErrorCode) {
		return buildFailedDefault(responseErrorCode, null);
	}

}
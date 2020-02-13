package cn.demo.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;

import static cn.demo.common.ResponseErrorCode.SYSTEM_ERROR;


/**
 * @author zhangliang
 */
public class BaseException extends RuntimeException{

	private ResponseErrorCode responseErrorCode;

	private ObjectError objectError;

	public BaseException(String message) {
		super(message);
	}

	public BaseException(ResponseErrorCode responseErrorCode) {
		super(responseErrorCode.info);
		this.responseErrorCode = responseErrorCode;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(ResponseErrorCode responseErrorCode, String message, Throwable cause) {
		super(message, cause);
		this.responseErrorCode = responseErrorCode;
	}

	public BaseException(ResponseErrorCode responseErrorCode, String message) {
		super(message);
		this.responseErrorCode = responseErrorCode;
	}

	public String getCode() {
		if (responseErrorCode != null) {
			return responseErrorCode.code;
		} else {
			return SYSTEM_ERROR.code;
		}
	}

	public ObjectError getObjectError() {
		return objectError;
	}

	public void setObjectError(ObjectError objectError) {
		this.objectError = objectError;
	}

	@Override
	public String getMessage() {
		if (objectError != null) {
			StringBuilder message = new StringBuilder();
			Object[] arguments = objectError.getArguments();
			if (arguments != null && arguments.length > 0) {
				if (arguments[0] instanceof DefaultMessageSourceResolvable) {
					DefaultMessageSourceResolvable defaultMessageSourceResolvable = (DefaultMessageSourceResolvable) arguments[0];
					message.append("Request Parameter [" + defaultMessageSourceResolvable.getDefaultMessage() + "],");
				}
			}
			if (!StringUtils.isBlank(objectError.getDefaultMessage())) {
				message.append("errInfo is [" + objectError.getDefaultMessage() + "].");
			}
			return message.toString();
		} else {
			return super.getMessage();
		}
	}
}
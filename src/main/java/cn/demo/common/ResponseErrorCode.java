package cn.demo.common;

/**
 * @ClassName : ResponseErrorCode
 * @Description : 错误码
 * @Author : zhangliang
 * @Date : 2019/12/2
 **/
public enum ResponseErrorCode {
    /**
     * 错误码
     */
    SYSTEM_SUCCESS("0", "success"),

    SYSTEM_ERROR("99999", "系统开小差"),
    ERROR_REQUEST_PARAM_MISSING("90001", "请求参数缺失"),
    ERROR_REQUEST_PARAM_ILLEGAL("90002", "请求参数非法"),
    ERROR_RESULT_NULL("90003", "返回数据为空"),

    ;

    public String code;

    public String info;

    ResponseErrorCode(String errCode, String errInfo) {
        this.code = errCode;
        this.info = errInfo;
    }
}

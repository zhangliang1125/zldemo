package cn.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangliang
 */
@Data
public class IdStringQuery {
    /**
     * message可以不写
     */
    @NotBlank(message = "id不能为空")
    private String id;
}

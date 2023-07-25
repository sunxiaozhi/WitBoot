package com.witboot.client.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

/**
 * 用户请求类
 *
 * @author sunxiaozhi
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class UserRequestDTO implements Serializable {

	@NotBlank(message = "用户名不能为空")
	private String name;

	@NotBlank(message = "年龄不能为空")
	private String age;
}

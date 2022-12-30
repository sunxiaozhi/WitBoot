package com.witboot.api.dto;

import lombok.*;
import java.io.Serializable;

/**
 * 用户数据返回类
 *
 * @author sunxiaozhi
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class UserResponseDTO implements Serializable {
	/**
	 * id
	 */
	private Long id;

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * 年龄
	 */
	private String age;
}

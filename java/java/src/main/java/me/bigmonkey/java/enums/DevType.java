package me.bigmonkey.java.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum DevType {

	MOBILE("mobile dev"), WEB("web dev"), SERVER("server dev");

	private String desc;
}
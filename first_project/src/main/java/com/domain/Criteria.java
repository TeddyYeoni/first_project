package com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Criteria {
	
	int nowPageNum;
	int pagingAmount;
	
	public Criteria() {
		this(1,15);
	}
}

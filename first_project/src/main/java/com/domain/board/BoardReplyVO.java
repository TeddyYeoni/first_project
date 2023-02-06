package com.domain.board;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardReplyVO {

	private int b_rno;
	private int bno;
	private String reply;
	private String writer;
	private Date replyDate;
	private Date modifyDate;
	
}

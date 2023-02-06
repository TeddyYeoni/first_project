package com.domain.qna;

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
public class QnaReplyVO {

	private int q_rno;
	private int qno;
	private String reply;
	private String writer;
	private Date replyDate;
	private Date modifyDate;
	
}

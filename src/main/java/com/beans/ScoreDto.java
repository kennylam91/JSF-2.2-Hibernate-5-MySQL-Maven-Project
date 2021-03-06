package com.beans;

import com.constant.FIELDS;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ScoreDto extends Score {

	private static final long serialVersionUID = -8691426536465934830L;

	private String studentCode;
	private String studentFirstname;
	private String studentLastname;
	private FIELDS studentField;

	public ScoreDto(Long id, Long studentId, Long courseId, float score, String studentCode, String studentFirstname,
			String studentLastname, FIELDS studentField) {
		super(id, studentId, courseId, score);
		this.studentCode = studentCode;
		this.studentFirstname = studentFirstname;
		this.studentLastname = studentLastname;
		this.studentField = studentField;

	}
}

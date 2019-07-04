package com.vipin.assessortesta.pojo.practical_que

import com.google.gson.annotations.SerializedName

data class PracticalQuesResponse(

	@field:SerializedName("Total_students")
	val totalStudents: Int? = null,

	@field:SerializedName("Total_questions")
	val totalQuestions: Int? = null,

	@field:SerializedName("Practical")
	val practical: List<PracticalItem?>? = null
)
package com.vipin.assessortesta.pojo.practical_que

import com.google.gson.annotations.SerializedName

data class PracticalItem(

	@field:SerializedName("rubrics")
	val rubrics: Rubrics? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("question_id")
	val questionId: Int? = null,

	@field:SerializedName("student_assigned")
	val studentAssigned: Int? = null


)
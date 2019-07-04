package com.vipin.assessortesta.pojo.feedback

import com.google.gson.annotations.SerializedName

data class JsonMember(

	@field:SerializedName("rubrics")
	val rubrics: Rubrics? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("question_id")
	val questionId: Int? = null
)
package com.vipin.assessortesta.pojo.feedback

import com.google.gson.annotations.SerializedName

data class FeedbackResponse(

		@field:SerializedName("Practical")
		val practical: Practical? = null,


		@field:SerializedName("msg")
		val msg: String? = null,

		@field:SerializedName("status")
		val status: Int? = null


)
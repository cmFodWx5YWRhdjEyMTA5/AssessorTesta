package com.vipin.assessortesta.pojo.feedback_stu_list

import com.google.gson.annotations.SerializedName

data class PracticalStuListResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("student_details")
	val studentDetails: List<StudentDetailsItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
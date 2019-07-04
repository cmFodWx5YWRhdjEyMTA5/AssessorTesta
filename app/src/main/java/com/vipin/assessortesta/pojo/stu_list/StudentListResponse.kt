package com.vipin.assessortesta.pojo.stu_list

import com.google.gson.annotations.SerializedName

data class StudentListResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("student_details")
	val studentDetails: List<StudentDetailsItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
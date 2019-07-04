package com.vipin.assessortesta.pojo.feedback_stu_list

import com.google.gson.annotations.SerializedName

data class StudentDetailsItem(

	@field:SerializedName("exam_status")
	val examStatus: Int? = null,

	@field:SerializedName("batch_id")
	val batchId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("student_id")
	val studentId: String? = null,

	@field:SerializedName("tc_name")
	val tcName: String? = null,

	@field:SerializedName("email")
	val email: Any? = null
)
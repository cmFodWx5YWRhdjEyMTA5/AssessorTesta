package com.vipin.assessortesta.pojo.stu_list

import com.google.gson.annotations.SerializedName

data class StudentDetailsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("student_id")
	val studentId: String? = null,

	@field:SerializedName("is_assigned")
	val isAssigned: Int? = null


)
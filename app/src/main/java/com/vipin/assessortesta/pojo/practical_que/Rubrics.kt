package com.vipin.assessortesta.pojo.practical_que

import com.google.gson.annotations.SerializedName

data class Rubrics(

	@field:SerializedName("Poor")
	val poor: List<String?>? = null,

	@field:SerializedName("Average")
	val average: List<String?>? = null,

	@field:SerializedName("Proficient")
	val proficient: List<String?>? = null,

	@field:SerializedName("Exception")
	val exception: List<String?>? = null
)
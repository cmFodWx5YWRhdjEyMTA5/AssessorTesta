package com.vipin.assessortesta.Ass_Registration.pojo.certificate

import com.google.gson.annotations.SerializedName

data class CertificateResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("jobroles")
	val jobroles: List<JobrolesItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
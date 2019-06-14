package com.vipin.assessortesta.Ass_Registration.pojo.category;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SscCateResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("ssc")
	private List<SscItem> ssc;

	@SerializedName("status")
	private int status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setSsc(List<SscItem> ssc){
		this.ssc = ssc;
	}

	public List<SscItem> getSsc(){
		return ssc;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"SscCateResponse{" + 
			"msg = '" + msg + '\'' + 
			",ssc = '" + ssc + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
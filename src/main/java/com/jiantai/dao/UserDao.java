package com.jiantai.dao;

import org.apache.ibatis.annotations.*;

import com.jiantai.entity.CompanyInfo;
@Mapper
public interface UserDao {


	@Update("update jt_company_info set `password` = #{new_password} where `user` = #{companyInfo.user}")
	@Options(useGeneratedKeys = false)
	public void changePassword(@Param("companyInfo") CompanyInfo companyInfo, @Param("new_password") String new_password);

	
	@Select("select * from jt_company_info where `user` = #{user}")
	public CompanyInfo companyLogin(String username);

	
	@Select("select * from jt_company_info where `id` = #{id}")
	public CompanyInfo findCompanyById(String id);


}

package me.bigmonkey.mybatis.dao.mybatis;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

@Mapper
@Repository
public interface MemberMapper {

    @Select("select member_no from t_member where member_no = #{id}")
    Member getMember(@Param("id") Long id);

    Page<MemberTermsHistoryResponse> getIntergretedMemberTermsHistory(Long memberNo);

    Page<MemberResponse> getIntegratedMemberList(MemberInfoRequest request);

    Page<MemberAccountResponse> getMemberAccountList(Map<String, Object> param);
}

package me.bigmoneky.mybatis.dao.mybatis;

import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import me.bigmoneky.mybatis.dto.MemberResponse;

@Mapper
public interface MemberMapper {

    MemberResponse getMember(Long id);
}

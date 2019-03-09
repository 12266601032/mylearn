package com.example.generated.simple.mapper;

import static com.example.generated.simple.mapper.VoteDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.example.generated.simple.model.Vote;
import java.util.List;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

@Mapper
public interface VoteMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.595+08:00", comments="Source Table: vote")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.596+08:00", comments="Source Table: vote")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.597+08:00", comments="Source Table: vote")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Vote> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.597+08:00", comments="Source Table: vote")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("VoteResult")
    Vote selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.598+08:00", comments="Source Table: vote")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="VoteResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT),
        @Result(column="faq_id", property="faqId", jdbcType=JdbcType.VARCHAR),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.BIGINT),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR)
    })
    List<Vote> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.599+08:00", comments="Source Table: vote")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.6+08:00", comments="Source Table: vote")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(vote);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.601+08:00", comments="Source Table: vote")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, vote);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.602+08:00", comments="Source Table: vote")
    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, vote)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.603+08:00", comments="Source Table: vote")
    default int insert(Vote record) {
        return insert(SqlBuilder.insert(record)
                .into(vote)
                .map(id).toProperty("id")
                .map(createTime).toProperty("createTime")
                .map(faqId).toProperty("faqId")
                .map(modifyTime).toProperty("modifyTime")
                .map(status).toProperty("status")
                .map(type).toProperty("type")
                .map(userId).toProperty("userId")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.603+08:00", comments="Source Table: vote")
    default int insertSelective(Vote record) {
        return insert(SqlBuilder.insert(record)
                .into(vote)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                .map(faqId).toPropertyWhenPresent("faqId", record::getFaqId)
                .map(modifyTime).toPropertyWhenPresent("modifyTime", record::getModifyTime)
                .map(status).toPropertyWhenPresent("status", record::getStatus)
                .map(type).toPropertyWhenPresent("type", record::getType)
                .map(userId).toPropertyWhenPresent("userId", record::getUserId)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.604+08:00", comments="Source Table: vote")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<Vote>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, createTime, faqId, modifyTime, status, type, userId)
                .from(vote);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.605+08:00", comments="Source Table: vote")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<Vote>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, createTime, faqId, modifyTime, status, type, userId)
                .from(vote);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.605+08:00", comments="Source Table: vote")
    default Vote selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, createTime, faqId, modifyTime, status, type, userId)
                .from(vote)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.606+08:00", comments="Source Table: vote")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(Vote record) {
        return UpdateDSL.updateWithMapper(this::update, vote)
                .set(id).equalTo(record::getId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(faqId).equalTo(record::getFaqId)
                .set(modifyTime).equalTo(record::getModifyTime)
                .set(status).equalTo(record::getStatus)
                .set(type).equalTo(record::getType)
                .set(userId).equalTo(record::getUserId);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.607+08:00", comments="Source Table: vote")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(Vote record) {
        return UpdateDSL.updateWithMapper(this::update, vote)
                .set(id).equalToWhenPresent(record::getId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(faqId).equalToWhenPresent(record::getFaqId)
                .set(modifyTime).equalToWhenPresent(record::getModifyTime)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(type).equalToWhenPresent(record::getType)
                .set(userId).equalToWhenPresent(record::getUserId);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.607+08:00", comments="Source Table: vote")
    default int updateByPrimaryKey(Vote record) {
        return UpdateDSL.updateWithMapper(this::update, vote)
                .set(createTime).equalTo(record::getCreateTime)
                .set(faqId).equalTo(record::getFaqId)
                .set(modifyTime).equalTo(record::getModifyTime)
                .set(status).equalTo(record::getStatus)
                .set(type).equalTo(record::getType)
                .set(userId).equalTo(record::getUserId)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.608+08:00", comments="Source Table: vote")
    default int updateByPrimaryKeySelective(Vote record) {
        return UpdateDSL.updateWithMapper(this::update, vote)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(faqId).equalToWhenPresent(record::getFaqId)
                .set(modifyTime).equalToWhenPresent(record::getModifyTime)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(type).equalToWhenPresent(record::getType)
                .set(userId).equalToWhenPresent(record::getUserId)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}
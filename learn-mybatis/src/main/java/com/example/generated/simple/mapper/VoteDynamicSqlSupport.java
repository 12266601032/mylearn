package com.example.generated.simple.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class VoteDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.592+08:00", comments="Source Table: vote")
    public static final Vote vote = new Vote();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.592+08:00", comments="Source field: vote.id")
    public static final SqlColumn<Integer> id = vote.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.593+08:00", comments="Source field: vote.create_time")
    public static final SqlColumn<Long> createTime = vote.createTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.593+08:00", comments="Source field: vote.faq_id")
    public static final SqlColumn<String> faqId = vote.faqId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.594+08:00", comments="Source field: vote.modify_time")
    public static final SqlColumn<Long> modifyTime = vote.modifyTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.594+08:00", comments="Source field: vote.status")
    public static final SqlColumn<String> status = vote.status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.594+08:00", comments="Source field: vote.type")
    public static final SqlColumn<String> type = vote.type;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.594+08:00", comments="Source field: vote.user_id")
    public static final SqlColumn<String> userId = vote.userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2018-11-02T12:02:07.592+08:00", comments="Source Table: vote")
    public static final class Vote extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<Long> createTime = column("create_time", JDBCType.BIGINT);

        public final SqlColumn<String> faqId = column("faq_id", JDBCType.VARCHAR);

        public final SqlColumn<Long> modifyTime = column("modify_time", JDBCType.BIGINT);

        public final SqlColumn<String> status = column("status", JDBCType.VARCHAR);

        public final SqlColumn<String> type = column("type", JDBCType.VARCHAR);

        public final SqlColumn<String> userId = column("user_id", JDBCType.VARCHAR);

        public Vote() {
            super("vote");
        }
    }
}
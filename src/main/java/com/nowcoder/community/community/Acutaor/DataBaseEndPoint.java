package com.nowcoder.community.community.Acutaor;

import com.nowcoder.community.community.util.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
@Endpoint(id = "database")
public class DataBaseEndPoint {

    @Autowired
    private DataSource dataSource;

    @ReadOperation
    private String connectDataBaseTest() {
        try(Connection connection = dataSource.getConnection()) {
            return CommunityUtil.getJsonString(0,"连接数据库成功！");
        } catch (SQLException throwables) {
            log.error("连接数据库失败！");
            return CommunityUtil.getJsonString(1,"连接数据库失败！");
        }
    }

}

package com.nowcoder.community.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("a2")
@Primary
public class ADao2Imp implements ADao{
    @Override
    public String select() {
        return "B";
    }
}

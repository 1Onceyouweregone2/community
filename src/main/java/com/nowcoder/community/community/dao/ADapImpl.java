package com.nowcoder.community.community.dao;

import org.springframework.stereotype.Repository;

@Repository("a1")
public class ADapImpl implements ADao{
    @Override
    public String select() {
        return "A";
    }
}

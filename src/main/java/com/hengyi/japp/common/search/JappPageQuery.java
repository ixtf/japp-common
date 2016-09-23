package com.hengyi.japp.common.search;

import java.security.Principal;

public abstract class JappPageQuery<T> extends JappQuery<T> {
    /**
     * 查询的第一条
     */
    protected final int first;
    /**
     * 一页条数
     */
    protected final Integer pageSize;

    public JappPageQuery(Principal principal, int first, Integer pageSize) {
        super(principal);
        this.first = first;
        this.pageSize = pageSize;
    }

    public int first() {
        return first;
    }

    public Integer pageSize() {
        return pageSize;
    }
}

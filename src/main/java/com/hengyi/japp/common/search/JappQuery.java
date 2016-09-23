package com.hengyi.japp.common.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

public abstract class JappQuery<T> {
    protected final Principal principal;
    protected Logger log = LoggerFactory.getLogger(getClass());
    protected long count;
    protected T result;

    protected JappQuery(Principal principal) {
        this.principal = principal;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public long count() {
        return count;
    }

    public T result() {
        return result;
    }
}

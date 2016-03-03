package com.journaldev.spring.dao.util;

@SuppressWarnings("serial")
public class InstanceNotFoundException extends InstanceException {

    public InstanceNotFoundException(Object key, String className) {
        super("Instance not found", key, className);
    }
    
}

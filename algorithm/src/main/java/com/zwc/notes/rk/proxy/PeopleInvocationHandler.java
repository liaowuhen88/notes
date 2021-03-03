package com.zwc.notes.rk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhangwenchao19
 * @date 2021-03-03 20:04
 */
public class PeopleInvocationHandler implements InvocationHandler {
    /**
     *
     */
    private Object people;

    /**
     * @param people
     */
    PeopleInvocationHandler(Object people) {
        this.people = people;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-------- start ---------");
        Object invoke = method.invoke(people, args);
        System.out.println("-------- end ---------");
        return invoke;

    }
}

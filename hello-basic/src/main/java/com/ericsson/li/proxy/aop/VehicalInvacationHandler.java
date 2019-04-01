package com.ericsson.li.proxy.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class VehicalInvacationHandler implements InvocationHandler {
    private IVehical vehical;

    public VehicalInvacationHandler(IVehical vehical) {
        this.vehical = vehical;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("--before running...");
        Object ret = null;
        ret = method.invoke(vehical, args);
        //((IVehical)proxy).run(); error
        //ret = method.invoke(proxy, args); error
        System.out.println("--after running...");

        return ret;
    }
}

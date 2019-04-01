package com.ericsson.li.proxy.aop;

import java.lang.reflect.Proxy;

public class VehicalProxy {
    private IVehical vehical;

    public VehicalProxy() {
    }

    public VehicalProxy(IVehical vehical) {
        this.vehical = vehical;
    }

    public IVehical create() {
        Class<?>[] interfaces = new Class[] {IVehical.class};
        VehicalInvacationHandler handler = new VehicalInvacationHandler(vehical);
        return (IVehical) Proxy.newProxyInstance(IVehical.class.getClassLoader(), interfaces, handler);
    }
}

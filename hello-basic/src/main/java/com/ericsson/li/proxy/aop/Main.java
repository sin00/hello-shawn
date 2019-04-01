package com.ericsson.li.proxy.aop;

public class Main {
    public static void main(String[] args) {
        VehicalProxy vehicalProxy = new VehicalProxy(new Car());
        IVehical vehical = vehicalProxy.create();
        vehical.run();
    }
}

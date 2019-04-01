package com.ericsson.li.ignite;

import org.apache.ignite.Ignition;

public class NodeMain {
    public static void main(String[] args){
        // 使用默认的配置 启动节点
        Ignition.start();
    }
}

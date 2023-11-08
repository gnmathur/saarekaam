package com.gnmathur.saarekaam.core.mgmt;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Optional;

public class SKMgmt {
    private static final String MGMT_DOMAIN = "saarekaam";
    private static MBeanServer mbs;

    static {
        mbs = ManagementFactory.getPlatformMBeanServer();
    }

    public static synchronized void registerMBean(Object mbean, Optional<String> name, Optional<String> type) {
        var objectName = MGMT_DOMAIN + ":" + type.map(t -> "type=" + t).orElse("") + name.map(n -> ",name=" + n).orElse("");
        try {
            if (!mbs.isRegistered(new ObjectName(objectName))) {
                mbs.registerMBean(mbean, new ObjectName(objectName));
            }
        } catch (JMException e) {
            throw new RuntimeException(e);
        }
    }
}

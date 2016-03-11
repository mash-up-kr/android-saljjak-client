package com.ewhappcenter.saljjak.common.anim;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

public class ReboundProvider {
    private static ReboundProvider instance;

    public static ReboundProvider getInstance() {
        if (instance == null) {
            synchronized (ReboundProvider.class) {
                if (instance == null) {
                    instance = new ReboundProvider();
                }
            }
        }

        return instance;
    }

    private final SpringSystem springSystem;

    protected ReboundProvider() {
        springSystem = SpringSystem.create();
    }

    public Spring getNewSpring() {
        return springSystem.createSpring();
    }
}

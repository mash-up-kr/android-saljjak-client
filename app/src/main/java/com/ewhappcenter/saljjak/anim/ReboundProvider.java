package com.ewhappcenter.saljjak.anim;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

/**
 * Created by makeus on 2016. 1. 19..
 */
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

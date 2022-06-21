/*
 * @(#)MemberApplicationListener 2021-10-15
 *
 * Copyright 2021 Etoos Education. All rights Reserved.
 * Etoos PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *//*

package me.bigmonkey.structure;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MemberApplicationListener implements ApplicationListener<ApplicationEvent> {
    String[] args;

    public MemberApplicationListener(String args[]) {
        this.args = args;
    }
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            new LoadAdditionalProperties().additionalProperties((ApplicationEnvironmentPreparedEvent)event, this.args);
        }
    }
}
*/

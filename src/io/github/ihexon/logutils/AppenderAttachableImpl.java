/***************************************************************************************************
 * Copyright (C) 2019 - 2020, IHEXON
 * This file is part of the WatchMe.
 *
 * WatchMe is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * WatchMe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WatchMe; see the file COPYING. If not, see <http://www.gnu.org/licenses/>.
 *
 * This Copyright copy from shadowsocks-libev. with little modified
 **************************************************************************************************/

package io.github.ihexon.logutils;

import io.github.ihexon.common.Appender;
import io.github.ihexon.spi.LoggingEvent;

import java.util.Enumeration;
import java.util.Vector;

public class AppenderAttachableImpl implements AppenderAttachable {

    protected Vector appenderList;

    @Override
    public void addAppender(Appender newAppender) {
        if (newAppender == null)
            return;
        if (appenderList == null) {
            appenderList = new Vector(1);
        }
        if (!appenderList.contains(newAppender)) {
            appenderList.addElement(newAppender);
        }
    }

    @Override
    public Enumeration getAllAppenders() {
        if (appenderList == null)
            return null;
        else
            return appenderList.elements();
    }

    @Override
    public Appender getAppender(String name) {
        if (appenderList == null || name == null)
            return null;
        int size = appenderList.size();
        Appender appender;
        for (int i = 0; i < size; i++) {
            appender = (Appender) appenderList.elementAt(i);
            if (name.equals(appender.getName()))
                return appender;
        }
        return null;
    }


    /**
     * Returns <code>true</code> if the specified appender is in the
     * list of attached appenders, <code>false</code> otherwise.
     */
    @Override
    public boolean isAttached(Appender appender) {
        if (appenderList == null || appender == null)
            return false;
        int size = appenderList.size();
        Appender a;
        for (int i = 0; i < size; i++) {
            a = (Appender) appenderList.elementAt(i);
            if (a == appender)
                return true;
        }
        return false;
    }

    @Override
    public void removeAllAppenders() {
        if (appenderList != null) {
            int len = appenderList.size();
            for (int i = 0; i < len; i++) {
                Appender a = (Appender) appenderList.elementAt(i);
                a.close();
            }
            appenderList.removeAllElements();
            appenderList = null;
        }
    }

    @Override
    public void removeAppender(Appender appender) {
        if (appender == null || appenderList == null)
            return;
        appenderList.removeElement(appender);
    }

    @Override
    public void removeAppender(String name) {
        if (name == null || appenderList == null) return;
        int size = appenderList.size();
        for (int i = 0; i < size; i++) {
            if (name.equals(((Appender) appenderList.elementAt(i)).getName())) {
                appenderList.removeElementAt(i);
                break;
            }
        }
    }

    public void appendLoopOnAppenders(LoggingEvent event) {
        Appender appender;
        if (appenderList != null) {
            for (int i = 0; i < appenderList.size(); i++) {
                appender = (Appender) appenderList.elementAt(i);
                appender.doAppend(event);
            }
        }
    }

    public synchronized void closeNestedAppenders() {
        Enumeration enumeration = this.getAllAppenders();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Appender a = (Appender) enumeration.nextElement();
                if (a != null) a.close();
            }
        }
    }
}

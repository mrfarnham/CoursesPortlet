/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.courses.mvc;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.jasig.portlet.courses.dao.ICoursesDao;
import org.jasig.portlet.courses.model.xml.Term;
import org.jasig.portlet.courses.model.xml.TermList;
import org.jasig.portlet.courses.model.xml.personal.CoursesByTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

public class MyCoursesMinimizedStateHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired(required = true)
    private ICoursesDao coursesDao;
    
    @Override
    public boolean preHandleRender(RenderRequest request, RenderResponse response, Object handler) throws Exception {
        if (WindowState.MINIMIZED.equals(request.getWindowState())) {
            
            final TermList termList = coursesDao.getTermList(request);
            final Term currentTerm = termList.getCurrentTerm();
            final String termCode = currentTerm.getCode();
            final CoursesByTerm coursesByTerm = coursesDao.getCoursesByTerm(request, termCode);
            final int newUpdateCount = coursesByTerm.getNewUpdateCount();
            response.setProperty("newItemCount", String.valueOf(newUpdateCount));
            
            return false;
        }
        
        return true;
    }
}

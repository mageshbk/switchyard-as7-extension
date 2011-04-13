/* 
 * JBoss, Home of Professional Open Source 
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved. 
 * See the copyright.txt in the distribution for a 
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use, 
 * modify, copy, or redistribute it subject to the terms and conditions 
 * of the GNU Lesser General Public License, v. 2.1. 
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details. 
 * You should have received a copy of the GNU Lesser General Public License, 
 * v.2.1 along with this distribution; if not, write to the Free Software 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 */
package org.switchyard.as7.extension.component;

import java.io.Serializable;
import java.util.List;

import org.jboss.as.ee.component.AbstractComponent;
import org.jboss.as.ee.component.AbstractComponentInstance;
import org.jboss.invocation.Interceptor;
import org.jboss.invocation.InterceptorFactoryContext;

/**
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2011 Red Hat Inc.
 *
 */
public class SwitchYardComponent extends AbstractComponent {

    /**
     * Construct a new instance.
     *
     * @param configuration the component configuration
     */
    public SwitchYardComponent(final SwitchYardComponentConfiguration configuration) {
        super(configuration);
    }

    /* (non-Javadoc)
     * @see org.jboss.as.ee.component.Component#createClientInterceptor(java.lang.Class)
     */
    @Override
    public Interceptor createClientInterceptor(Class<?> view) {
        return null; //not applicable
    }

    /* (non-Javadoc)
     * @see org.jboss.as.ee.component.Component#createClientInterceptor(java.lang.Class, java.io.Serializable)
     */
    @Override
    public Interceptor createClientInterceptor(Class<?> view, Serializable sessionId) {
        return null; //not applicable
    }

    /* (non-Javadoc)
     * @see org.jboss.as.ee.component.AbstractComponent#constructComponentInstance(java.lang.Object, java.util.List, org.jboss.invocation.InterceptorFactoryContext)
     */
    @Override
    protected AbstractComponentInstance constructComponentInstance(final Object instance, List<Interceptor> preDestroyInterceptors, InterceptorFactoryContext context) {
        return new SwitchYardComponentInstance(this, instance, preDestroyInterceptors, context);
    }

}

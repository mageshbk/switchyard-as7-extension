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
package org.switchyard.as7.extension.deployment;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
//import javax.naming.StringRefAddr;

import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.logging.Logger;
import org.jboss.modules.Module;
//import org.jboss.weld.integration.util.IdFactory;

/**
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2011 Red Hat Inc.
 *
 */
public class CDIJNDIDeploymentProcessor implements DeploymentUnitProcessor {

    private static final Logger LOG = Logger.getLogger("org.switchyard");

    /* (non-Javadoc)
     * @see org.jboss.as.server.deployment.DeploymentUnitProcessor#deploy(org.jboss.as.server.deployment.DeploymentPhaseContext)
     */
    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        /*final EEModuleDescription moduleDescription = deploymentUnit.getAttachment(EE_MODULE_DESCRIPTION);
        for(AbstractComponentDescription component : moduleDescription.getComponentDescriptions()) {
            System.out.println(component.getComponentName() + " : " + component.getNamingMode());
        }
        System.out.println(deploymentUnit);*/
        //Context javaComp = getJavaComp(deploymentUnit);

        //Reference reference = new Reference("javax.enterprise.inject.spi.BeanManager", "org.jboss.weld.integration.deployer.jndi.JBossBeanManagerObjectFactory", null);
        //final Module module = deploymentUnit.getAttachment(Attachments.MODULE);
        //reference.add(new StringRefAddr("id", IdFactory.getIdFromClassLoader(module.getClassLoader())));
        //javaComp.bind("BeanManager", reference);
    }

    /* (non-Javadoc)
     * @see org.jboss.as.server.deployment.DeploymentUnitProcessor#undeploy(org.jboss.as.server.deployment.DeploymentUnit)
     */
    @Override
    public void undeploy(DeploymentUnit arg0) {
        // TODO Auto-generated method stub

    }

    private Context getJavaComp(DeploymentUnit unit) throws DeploymentUnitProcessingException {
        Context javaComp = null;
        InitialContext initialContext = null;

        ClassLoader originalTCCL = Thread.currentThread().getContextClassLoader();
        try {
            final Module module = unit.getAttachment(Attachments.MODULE);
            Thread.currentThread().setContextClassLoader(module.getClassLoader());
            initialContext = new InitialContext();
            javaComp = (Context) initialContext.lookup("java:comp");
        } catch (Exception e) {
            throw new DeploymentUnitProcessingException("Unexpected retrieving java:comp from JNDI namespace.", e);
        } finally {
            try {
                if (initialContext != null) {
                    try {
                        initialContext.close();
                    } catch (NamingException e) {
                        throw new DeploymentUnitProcessingException("Unexpected error closing InitialContext.", e);
                    }
                }
            } finally {
                Thread.currentThread().setContextClassLoader(originalTCCL);
            }
        }

        return javaComp;
    }

}

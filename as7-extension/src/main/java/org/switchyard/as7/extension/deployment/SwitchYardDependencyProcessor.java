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

import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.as.server.deployment.module.ModuleDependency;
import org.jboss.as.server.deployment.module.ModuleSpecification;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoader;
import org.switchyard.as7.extension.SwitchYardDeploymentMarker;

/**
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2011 Red Hat Inc.
 */
public class SwitchYardDependencyProcessor implements DeploymentUnitProcessor {

    //private static final ModuleIdentifier JAVAX_EE_API = ModuleIdentifier.create("javaee.api");
    private static final ModuleIdentifier JBOSS_NAMING_ID = ModuleIdentifier.create("org.jboss.as.naming");
    private static final ModuleIdentifier JBOSS_SERVER_ID = ModuleIdentifier.create("org.jboss.as.server");
    private static final ModuleIdentifier SWITCHYARD_ID = ModuleIdentifier.create("org.switchyard");

    /* (non-Javadoc)
     * @see org.jboss.as.server.deployment.DeploymentUnitProcessor#deploy(org.jboss.as.server.deployment.DeploymentPhaseContext)
     */
    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        final ModuleSpecification moduleSpecification = deploymentUnit.getAttachment(Attachments.MODULE_SPECIFICATION);
        if (!SwitchYardDeploymentMarker.isSwitchYardDeployment(deploymentUnit)) {
            return;
        }
        final ModuleLoader moduleLoader = Module.getBootModuleLoader();
        //moduleSpecification.addDependency(new ModuleDependency(moduleLoader, JAVAX_EE_API, false, false, false));
        moduleSpecification.addDependency(new ModuleDependency(moduleLoader, JBOSS_NAMING_ID, false, false, false));
        moduleSpecification.addDependency(new ModuleDependency(moduleLoader, JBOSS_SERVER_ID, false, false, false));
        moduleSpecification.addDependency(new ModuleDependency(moduleLoader, SWITCHYARD_ID, false, false, false));

    }

    /* (non-Javadoc)
     * @see org.jboss.as.server.deployment.DeploymentUnitProcessor#undeploy(org.jboss.as.server.deployment.DeploymentUnit)
     */
    @Override
    public void undeploy(DeploymentUnit deploymentUnit) {

    }

}

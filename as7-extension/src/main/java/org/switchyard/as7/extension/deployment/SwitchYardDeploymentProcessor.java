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

import java.io.IOException;
import java.io.InputStream;

import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.logging.Logger;
import org.jboss.modules.ModuleIdentifier;
import org.switchyard.as7.extension.SwitchYardDeploymentMarker;
import org.switchyard.config.model.ModelResource;
import org.switchyard.config.model.switchyard.SwitchYardModel;

/**
 * @author Magesh Kumar B <mageshbk@jboss.com> (C) 2011 Red Hat Inc.
 *
 */
public class SwitchYardDeploymentProcessor implements DeploymentUnitProcessor {

    private static ModuleIdentifier SWITCHYARD_ID = ModuleIdentifier.create("org.switchyard");

    private static final Logger LOG = Logger.getLogger("org.switchyard");

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        final DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        if (!SwitchYardDeploymentMarker.isSwitchYardDeployment(deploymentUnit)) {
            return;
        }
        LOG.info("Deploying SwitchYard application '" + deploymentUnit.getName() + "'");
        SwitchYardMetaData metaData = deploymentUnit.getAttachment(SwitchYardMetaData.ATTACHMENT_KEY);
        try {
            InputStream is = metaData.getSwitchYardFile().openStream();
            SwitchYardModel switchyardModel = new ModelResource<SwitchYardModel>().pull(is);
            is.close();
            SwitchYardDeployment deployment = new SwitchYardDeployment(deploymentUnit, switchyardModel);
            deployment.create();
            deployment.start();
            deploymentUnit.putAttachment(SwitchYardDeployment.ATTACHMENT_KEY, deployment);
            LOG.info(deploymentUnit.getName() + " deployed.");
        } catch (IOException ioe) {
            throw new DeploymentUnitProcessingException(ioe);
        }
    }

    @Override
    public void undeploy(DeploymentUnit deploymentUnit) {
        LOG.info("Undeploying SwitchYard application '" + deploymentUnit.getName() + "'");
        SwitchYardDeployment deployment = deploymentUnit.removeAttachment(SwitchYardDeployment.ATTACHMENT_KEY);
        if (deployment == null) {
            return;
        }
        deployment.stop();
        deployment.destroy();
        LOG.info("Application '" + deploymentUnit.getName() + "' stopped.");
    }

}

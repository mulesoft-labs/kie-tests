package org.mnc.pruebas.drools;

import java.net.MalformedURLException;
import java.net.URL;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.services.client.api.RemoteRestRuntimeFactory;

/**
 *
 */
public class RemoteTest
{

    public static void main(String[] args) throws MalformedURLException
    {
        // Setup the factory class with the necessarry information to communicate with the REST services
        RemoteRestRuntimeFactory restSessionFactory = new RemoteRestRuntimeFactory(
                "mnc-prj-1",
                new URL("http://localhost:8080/kie-drools-wb-distribution-wars-6.0.1.Final-jboss-as7.0"),
                "pepito",
                "Pruebita1.");

        // Create KieSession and TaskService instances and use them
        RuntimeEngine engine = restSessionFactory.newRuntimeEngine();
        KieSession ksession = engine.getKieSession();
        ProcessInstance processInstance = ksession.startProcess("process-1");
        long procId = processInstance.getId();
        System.out.println(procId);

    }

}

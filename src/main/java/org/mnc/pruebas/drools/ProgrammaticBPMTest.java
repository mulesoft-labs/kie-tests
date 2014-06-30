package org.mnc.pruebas.drools;

import java.util.Collection;

import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public class ProgrammaticBPMTest
{

    public static void main(String[] args)
    {
        //KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        //builder.add(
        //        ResourceFactory.newInputStreamResource(ProgrammaticBPMTest.class.getResourceAsStream("/hello-world.bpmn2.xml")),
        //        ResourceType.BPMN2);
        //KieBase kBase = builder.newKnowledgeBase();


        KieHelper kieHelper = new KieHelper();
        kieHelper.addResource(ResourceFactory.newInputStreamResource(ProgrammaticBPMTest.class.getResourceAsStream("/hello-world.bpmn2.xml")),
                              ResourceType.BPMN2);
        KieBase kBase = kieHelper.build();
        Collection<org.kie.api.definition.process.Process> processes = kBase.getProcesses();
        for (org.kie.api.definition.process.Process p : processes)
        {
            System.out.println(p.getId());
        }
        KieSession ksession = kBase.newKieSession();
        ProcessInstance pi = ksession.startProcess("com.sample.HelloWorld");
        System.out.println(pi.getId());


        //// Para que ande esto hay que agregar dependencias de jbpm-runtime-manager, hibernate, y configurar el persistence.xml
        //RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultInMemoryBuilder();
        //builder.addAsset(ResourceFactory.newInputStreamResource(ProgrammaticBPMTest.class.getResourceAsStream("/hello-world.bpmn2.xml")),
        //                 ResourceType.BPMN2);
        //KieBase kBase = builder.get().getKieBase();
        //Collection<org.kie.api.definition.process.Process> processes = kBase.getProcesses();
        //for (org.kie.api.definition.process.Process p : processes)
        //{
        //    System.out.println(p.getId());
        //}
        //KieSession ksession = kBase.newKieSession();
        //ProcessInstance pi = ksession.startProcess("com.sample.HelloWorld");
        //System.out.println(pi.getId());
    }
}

package org.mnc.pruebas.drools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.process.ProcessInstance;

/**
 *
 */
public class ProgrammaticDroolTest
{

    public static void main(String[] args)
    {
        KieServices kieServices = KieServices.Factory.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("KBase1")
                .setDefault(true)
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                .setEventProcessingMode(EventProcessingOption.STREAM);

        KieSessionModel ksessionModel1 = kieBaseModel1.newKieSessionModel( "KSession1" )
                .setDefault(true)
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.get("realtime"));

        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/META-INF/kmodule.xml", kieModuleModel.toXML());
        kfs.write("src/main/resources/rule0.drl", kieServices.getResources().newInputStreamResource(ProgrammaticDroolTest.class.getResourceAsStream("/rule0.drl")));

        //kfs.write("src/main/resources/simple-process.jpdl.xml", kieServices.getResources().newInputStreamResource(ProgrammaticDroolTest.class.getResourceAsStream("/simple-process.jpdl.xml")));
        kfs.write("src/main/resources/hello-world.bpmn2.xml", kieServices.getResources().newInputStreamResource(ProgrammaticDroolTest.class.getResourceAsStream("/hello-world.bpmn2.xml")));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs);
        kieBuilder.buildAll();
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kb = kieContainer.getKieBase("KBase1");
        System.out.println(kb);

        KieSession kSession = kieContainer.newKieSession("KSession1");

        List<Integer> list = new ArrayList<Integer>();
        kSession.setGlobal("list", list);
        kSession.insert(2);
        kSession.fireAllRules();

        System.out.println(list.size());
        for(int i : list)
        {
            System.out.println(i);
        }

        System.out.println("----------");
        Collection<org.kie.api.definition.process.Process> processes = kb.getProcesses();
        for(org.kie.api.definition.process.Process process : processes)
        {
            System.out.println(process.getName());
        }
        System.out.println("----------");

        ProcessInstance processInstance = kSession.createProcessInstance("com.sample.HelloWorld", null);
        System.out.println(processInstance.getProcessId());
        System.out.println(processInstance.getProcessName());
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getState());
    }
}

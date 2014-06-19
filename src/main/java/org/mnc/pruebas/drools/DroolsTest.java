package org.mnc.pruebas.drools;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 *
 */
public class DroolsTest
{

    public static void main(String[] args)
    {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kb = kContainer.getKieBase("kbase1");
        System.out.println(kb);

        KieSession kSession = kContainer.newKieSession("ksession1");

        List<Integer> list = new ArrayList<Integer>();
        kSession.setGlobal("list", list);
        kSession.insert(2);
        kSession.fireAllRules();

        System.out.println(list.size());
        for(int i : list)
        {
            System.out.println(i);
        }
    }

}

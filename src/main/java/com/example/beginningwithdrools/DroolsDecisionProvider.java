package com.example.beginningwithdrools;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieContainerSessionsPool;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DroolsDecisionProvider {

    private final KieContainerSessionsPool sessionsPool;

    public DroolsDecisionProvider(@Qualifier("aws-zip") KieContainerFactory kieContainerFactory) {
        KieContainer kieContainer = kieContainerFactory.getKieContainer();
        sessionsPool = kieContainer.newKieSessionsPool(10);
    }

    public String getDecision(String param) {
        StatelessKieSession statelessKieSession = sessionsPool.newStatelessKieSession();
        List<Object> facts = new ArrayList<>();
        Result result = new Result();
        DecisionFact decisionFact = new DecisionFact();
        decisionFact.setValue1(getValue(param));
        facts.add(result);
        facts.add(decisionFact);
        statelessKieSession.execute(facts);
        return result.getResult();
    }

    private String getValue(String param) {
        switch (param.toCharArray()[0]) {
            case 'a':
                return "val1";
            case 'b':
                return "val2";
            case 'c':
                return "val3";
            case 'd':
                return "val4";
        }
        return "val5";
    }
}
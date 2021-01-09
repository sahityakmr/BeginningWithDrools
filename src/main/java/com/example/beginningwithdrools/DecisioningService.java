package com.example.beginningwithdrools;

import org.springframework.stereotype.Service;

@Service
public class DecisioningService {

    private final DroolsDecisionProvider droolsDecisionProvider;

    public DecisioningService(DroolsDecisionProvider droolsDecisionProvider) {
        this.droolsDecisionProvider = droolsDecisionProvider;
    }

    public String getDecision(String param) {
        return droolsDecisionProvider.getDecision(param);
    }
}

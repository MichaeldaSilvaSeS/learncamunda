package br.com.michaeldasilvases;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@ExternalTaskSubscription("MinhaAtividadeTopic") // create a subscription for this topic name
public class FazerMinhaAtividade implements ExternalTaskHandler {


    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String customerId = "C-" + UUID.randomUUID().toString().substring(32);
        int creditScore = (int) (Math.random() * 11);

        VariableMap variables = Variables.createVariables();
        variables.put("customerId", customerId);
        variables.put("creditScore", creditScore);

        // complete the external task
        externalTaskService.complete(externalTask, variables);

        Logger.getLogger("scoreProvider")
                .log(Level.INFO, "Credit score {0} for customer {1} provided!", new Object[]{creditScore, customerId});
    }
}

package io.github.tbarissatir.taskmanagement.client;

public class RuleEngineResponse {

    private boolean matched;
    private String ruleName;

    public boolean isMatched() {
        return matched;
    }

    public String getRuleName() {
        return ruleName;
    }
}

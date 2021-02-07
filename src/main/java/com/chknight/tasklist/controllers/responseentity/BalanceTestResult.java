package com.chknight.tasklist.controllers.responseentity;

public class BalanceTestResult {
    public String input;
    public Boolean isBalanced;

    public BalanceTestResult(String input, Boolean isBalanced) {
        this.input = input;
        this.isBalanced = isBalanced;
    }
}

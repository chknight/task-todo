package com.chknight.tasklist.controllers.response;

public class BalanceTestResult {
    public String input;
    public Boolean isBalanced;

    public BalanceTestResult(String input, Boolean isBalanced) {
        this.input = input;
        this.isBalanced = isBalanced;
    }
}

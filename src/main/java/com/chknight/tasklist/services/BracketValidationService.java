package com.chknight.tasklist.services;

import com.chknight.tasklist.exceptions.InvalidArgumentException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Stack;

@Service
public class BracketValidationService {

    private final Map<Character, Character> leftBracketMap = Map.of(
            '{', '}',
            '[', ']',
            '(', ')'
    );

    /**
     * Check if a input string have all validate brackets
     * Assume other char could exists in the string, and will be ignored
     * @param strToValidate string to validate
     * @return Whether a string is a valid sequence of brackets
     */
    public Boolean validateBrackets(String strToValidate) {
        Stack<Character> bracketsFound = new Stack<>();
        char[] charsInString = strToValidate.toCharArray();
        for (Character characterToCheck : charsInString) {
            // Is left bracket
            if (leftBracketMap.get(characterToCheck) != null) {
                bracketsFound.push(characterToCheck);
                // Is right bracket, check whether is matched
            } else if (leftBracketMap.containsValue(characterToCheck)) {
                if (bracketsFound.isEmpty() ||
                        !isBracketMatch(bracketsFound.pop(), characterToCheck)
                ) {
                    return false;
                }
            }
        }
        return bracketsFound.isEmpty();
    }

    private Boolean isBracketMatch(Character leftBracket, Character rightBracket) {
        return leftBracketMap.get(leftBracket).equals(rightBracket);
    }
}

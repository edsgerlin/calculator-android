package com.edsgerlin.calculator;

/**
 * Created by edsge on 2016-10-05.
 */

public class ExpressionParser {
    private final String expressionString;
    private int index = -1;
    private char character = 0;
    private Expression parsedExpression = null;
    /*
     * Grammar:
     * expression = term | expression `+` term | expression `-` term;
     * term = factor | term `*` factor | term `/` factor;
     * factor = `+` factor | `-` factor | number;
     * number = [0-9]+(\.[0.9]+)?;
     */
    public ExpressionParser(final String expressionString) {
        this.expressionString = expressionString;
    }

    public Expression parse() {
        if (parsedExpression == null) {
            nextCharacter();
            parsedExpression = parseExpression();
        }
        return parsedExpression;
    }

    private boolean isNumber() {
        if ((character >= '0' && character <= '9') || character == '.') {
            return true;
        } else {
            return false;
        }
    }

    private Expression parseExpression() {
        return parseExpression(parseTerm());
    }

    private Expression parseExpression(final Expression leftTerm) {
        while (true) {
            final Expression rightTerm;
            if (accept('+')) {
                rightTerm = parseTerm();
                return parseExpression(() -> leftTerm.eval() + rightTerm.eval());
            } else if (accept('-')) {
                rightTerm = parseTerm();
                return parseExpression(() -> leftTerm.eval() - rightTerm.eval());
            } else {
                return leftTerm;
            }
        }
    }

    private Expression parseTerm() {
        return parseTerm(parseFactor());
    }

    private Expression parseTerm(final Expression leftFactor) {
        while (true) {
            final Expression rightFactor;
            if (accept('*')) {
                rightFactor = parseFactor();
                return parseTerm(() -> leftFactor.eval() * rightFactor.eval());
            } else if (accept('/')) {
                rightFactor = parseFactor();
                return parseTerm(() -> leftFactor.eval() / rightFactor.eval());
            } else {
                return leftFactor;
            }
        }
    }

    private Expression parseFactor() {
        if (accept('+')) {
            return () -> parseFactor().eval();
        } else if (accept('-')) {
            return () -> -parseFactor().eval();
        } else if (isNumber()) {
            final int begin = index;
            while (isNumber()) {
                nextCharacter();
            }
            final int end = index;
            return () -> Double.parseDouble(expressionString.substring(begin, end));
        } else {
            return null;
        }
    }

    private void nextCharacter() {
        this.character = ++index < expressionString.length()
                ? expressionString.charAt(index) : 0;
    }


    private boolean accept(final char character) {
        if (this.character == character) {
            nextCharacter();
            return true;
        } else {
            return false;
        }
    }
}
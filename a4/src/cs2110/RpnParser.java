package cs2110;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class RpnParser {

    /**
     * Parse the RPN expression in `exprString` and return the corresponding expression tree. Tokens
     * must be separated by whitespace.  Valid tokens include decimal numbers (scientific notation
     * allowed), arithmetic operators (+, -, *, /, ^), function names (with the suffix "()"), and
     * variable names (anything else).  When a function name is encountered, the corresponding
     * function will be retrieved from `funcDefs` using the name (without "()" suffix) as the key.
     *
     * @throws IncompleteRpnException     if the expression has too few or too many operands
     *                                    relative to operators and functions.
     * @throws UndefinedFunctionException if a function name applied in `exprString` is not present
     *                                    in `funcDefs`.
     */
    public static Expression parse(String exprString, Map<String, UnaryFunction> funcDefs)
            throws IncompleteRpnException, UndefinedFunctionException {
        // Each token will result in a subexpression being pushed onto this stack.  If the
        // subexpression requires arguments, they are first popped off of this stack.
        Deque<Expression> stack = new ArrayDeque<>();

        // Loop over each token in the expression string from left to right
        for (Token token : Token.tokenizer(exprString)) {
            if (token instanceof Token.Number) {
                Token.Number numToken = (Token.Number) token;
                stack.push(new Constant(numToken.doubleValue()));
            }
            if (token instanceof Token.Variable) {
                Token.Variable variableToken = (Token.Variable) token;
                stack.push(new Variable(variableToken.value));
            }
            if (token instanceof Token.Operator) {
                Token.Operator operatorToken = (Token.Operator) token;
                if(stack.size()>=2){
                Expression k1=stack.pop();
                Expression k2=stack.pop();
                stack.push(new Operation(operatorToken.opValue(),k2,k1));}
                else{
                    throw new IncompleteRpnException(exprString, stack.size());
                }
            }

            if (token instanceof Token.Function) {
                Token.Function functionToken = (Token.Function) token;
                String funcName = functionToken.name();
                if (funcDefs.get(funcName) == null) {
                    throw new UndefinedFunctionException(funcName);
                } else {
                    stack.push(new Application(funcDefs.get(funcName), stack.pop()));
                }
            }
        }

        if (stack.isEmpty() || stack.size() > 1) {
            throw new IncompleteRpnException(exprString, stack.size());
        }
        Expression expression = null;
        while (!stack.isEmpty()) {
            expression = stack.pop();
        }
        return expression;
    }
}

package cs2110;

import java.util.HashSet;
import java.util.Set;

/**
 * An expression tree node representing the binary operator.
 */
public class Operation implements Expression {

    /**
     * The binary operator.
     */
    Operator op;
    /**
     * The child expression node of left operand.
     */
    Expression leftOperand;
    /**
     * The child expression node of right operand.
     */
    Expression rightOperand;

    /**
     * Return the result of evaluating the operator on numeric operands, by calling the operate()
     * method.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return op.operate(leftOperand.eval(vars), rightOperand.eval(vars));
    }

    /**
     * Return the number of operations and unary functions contained in this expression.
     */
    @Override
    public int opCount() {
        int result = 0;
        result += leftOperand.opCount() + rightOperand.opCount() + 1;
        return result;
    }

    /**
     * Return the infix representation of the operator and its child operands, enclosing every
     * binary operation in parentheses.
     */
    @Override
    public String infixString() {
        return String.format("(%s %s %s)", leftOperand.infixString(), op.symbol(),
                rightOperand.infixString());
    }

    /**
     * Return the postfix representation of the operator and its operands, separating every token
     * with spaces.
     */
    @Override
    public String postfixString() {
        return leftOperand.postfixString() + " " + rightOperand.postfixString() + " " + op.symbol();
    }

    /**
     * If the children can be evaluated to yield a number, the application node can fully be
     * optimized to a Constant. If a child depends on an unbound variable, the parent node can be
     * partially optimized by creating a new copy whose children are replaced with their optimized
     * forms.
     */
    @Override
    public Expression optimize(VarTable vars) {
        try {
            return new Constant(eval(vars));
        } catch (UnboundVariableException e) {
            return new Operation(op, leftOperand.optimize(vars), rightOperand.optimize(vars));
        }
    }


    /**
     * Return the names of all variables that this expression depends on. The returned set need not
     * be modifiable.
     */
    @Override
    public Set<String> dependencies() {
        Set<String> result = new HashSet<>();
        result.addAll(leftOperand.dependencies());
        result.addAll(rightOperand.dependencies());
        return result;
    }

    /**
     * Create a node representing the binary operator on two child operands.
     */
    public Operation(Operator op, Expression leftOperand, Expression rightOperand) {
        this.op = op;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    /**
     * Return whether `other` is an operator with the same operands.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Operation o = (Operation) other;
        return o.op.equals(op) && o.leftOperand.equals(leftOperand) && o.rightOperand.equals(
                rightOperand);
    }


}

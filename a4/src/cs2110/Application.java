package cs2110;

import java.util.Set;

/**
 * An expression tree node representing the application of a function to an argument.
 */
public class Application implements Expression {

    /**
     * The function of the expression.
     */
    UnaryFunction func;

    /**
     * The argument of the expression.
     */
    Expression argument;

    /**
     * Evaluate the function on a numeric argument(evaluate its argument child, then apply its
     * function to that value and return the result.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return func.apply(argument.eval(vars));
    }

    /**
     * Return the number of operations and unary functions contained in this expression.
     */
    @Override
    public int opCount() {
        int result = 1;
        result += argument.opCount();
        return result;
    }

    /**
     * Return the representation of the name of the function and the infix representation of the
     * argument of the function.
     */
    @Override
    public String infixString() {
        return String.format("%s(%s)", func.name(), argument.infixString());
    }

    /**
     * Return the postfix representation of the application node.
     */
    @Override
    public String postfixString() {
        return argument.postfixString() + " " + func.name() + "()";
    }

    /**
     * Create a node representing the application of a function to an argument.
     */
    public Application(UnaryFunction func, Expression argument) {
        this.func = func;
        this.argument = argument;
    }

    /**
     * Return whether `other` is an application with the same function and argument.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Application a = (Application) other;
        return a.func.equals(func) && a.argument.equals(argument);
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
            return new Constant(func.apply(argument.eval(vars)));
        } catch (UnboundVariableException e) {
            return new Application(func, argument.optimize(vars));
        }
    }

    /**
     * Return the names of all variables that this expression depends on. The returned set need not
     * be modifiable.
     */
    @Override
    public Set<String> dependencies() {
        return argument.dependencies();
    }

}

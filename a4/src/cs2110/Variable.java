package cs2110;

import java.util.Set;

/**
 * An expression tree node representing a named variable.
 */
public class Variable implements Expression {

    /**
     * The name of this expression.
     */
    String name;

    /**
     * Create a node representing the variable `name`.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Return the result of evaluating this expression, substituting any variables with their value
     * in `vars`.  Throws UnboundVariableExpression if this expression contains a variable whose
     * value is not in `vars`.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        if (!vars.contains(name)) {
            throw new UnboundVariableException(name);
        } else {
            return vars.get(name);
        }
    }

    /**
     * No operations are required to evaluate a variable's value.
     */
    @Override
    public int opCount() {
        return 0;
    }

    /**
     * return the name of this variable node.
     */
    @Override
    public String infixString() {
        return name;
    }

    /**
     * return the name of this variable node.
     */
    @Override
    public String postfixString() {
        return name;
    }

    /**
     * optimized to a constant if the variable has an assigned value in the provided variable table,
     * in which case it optimizes to a Constant. Otherwise, it optimizes to itself.
     */
    @Override
    public Expression optimize(VarTable vars) {
        if (vars.contains(name)) {
            try {
                return new Constant(vars.get(name));
            } catch (UnboundVariableException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }


    /**
     * Return the names of all variables that this expression depends on. The returned set need not
     * be modifiable.
     */
    @Override
    public Set<String> dependencies() {
        return Set.of(name);
    }

    /**
     * Return whether `other` is a variable of the same class with the same name.
     */

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Variable v = (Variable) other;
        return v.name.equals(name);
    }


}

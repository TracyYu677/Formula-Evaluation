package cs2110;

import java.util.Set;

public class Application implements Expression{
    UnaryFunction func;
    Expression argument;

    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return argument.eval(vars);
    }

    @Override
    public int opCount() {
        int result=1;
        result+=argument.opCount();
        return result;
    }

    @Override
    public String infixString() {
        return String.format("%s(%s)",func.name(),argument.infixString());
    }

    @Override
    public String postfixString() {
        return argument.postfixString()+func.name()+"()";
    }

    @Override
    public Expression optimize(VarTable vars) {
        return this;
    }

    @Override
    public Set<String> dependencies() {
        return argument.dependencies();
    }

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

    public Application(UnaryFunction func, Expression argument){
        this.func=func;
        this.argument=argument;
    }
}

package cs2110;

import java.util.Set;

public class Variable implements Expression {
    String name;

    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return vars.get(name);
    }

    @Override
    public int opCount() {
        return 0;
    }

    @Override
    public String infixString() {
        return name;
    }

    @Override
    public String postfixString() {
        return name;
    }

    @Override
    public Expression optimize(VarTable vars){
        if(vars.contains(name)){
            try {
                return new Constant(vars.get(name));
            } catch (UnboundVariableException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }

    @Override
    public Set<String> dependencies() {
        return Set.of(name);
    }

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

    public Variable(String name) {
        this.name=name;
    }
}

package cs2110;

import java.util.HashSet;
import java.util.Set;

public class Operation implements Expression{
    Operator op;
    Expression leftOperand;
    Expression rightOperand;

    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return op.operate(leftOperand.eval(vars),rightOperand.eval(vars));
    }

    @Override
    public int opCount() {
        int result=1;
        if(leftOperand.getClass()==getClass()){
            result+=1;
        }
        if(rightOperand.getClass()==getClass()){
            result+=1;
        }
        return result;
    }

    @Override
    public String infixString() {
        return String.format("(%s%s%s)",leftOperand.infixString(),op.symbol(),rightOperand.infixString());
    }

    @Override
    public String postfixString() {
        return leftOperand.postfixString()+rightOperand.postfixString()+op.symbol();
    }

    @Override
    public Expression optimize(VarTable vars) {
        return this;
    }

    @Override
    public Set<String> dependencies() {
        Set<String> result=new HashSet<>();
        result.addAll(leftOperand.dependencies());
        result.addAll(rightOperand.dependencies());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Operation o = (Operation) other;
        return o.op.equals(op)&&o.leftOperand.equals(leftOperand)&&o.rightOperand.equals(rightOperand);
    }

    public Operation(Operator op,Expression leftOperand,Expression rightOperand){
        this.op=op;
        this.leftOperand=leftOperand;
        this.rightOperand=rightOperand;
    }
}

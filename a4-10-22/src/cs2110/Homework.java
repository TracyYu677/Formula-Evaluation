package cs2110;
import java.util.Set;

public class Homework {
    public static void main(String[] args) {
        Expression arg = new Variable("x");
        Expression expr = new Application(UnaryFunction.SQRT, arg);
        Set<String> argDeps = arg.dependencies();
        Set<String> exprDeps = expr.dependencies();
        System.out.println(argDeps.equals(exprDeps));
    }
}

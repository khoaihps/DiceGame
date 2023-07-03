package player;

public class VirtualPlayer extends Player {
    private String failureExpression;
    private static final String[] FAILUREEXPRESSIONS = {"Biểu cảm 1", "Biểu cảm 2", "Biểu cảm 3", "Biểu cảm 4"};

    public VirtualPlayer(String name) {
        super(name);
    }

    public String getFailureExpression() {
        return failureExpression;
    }

    public void setFailureExpression(int id) {
        this.failureExpression = FAILUREEXPRESSIONS[id];
    }

    public void setFailureExpression(String failureExpression) {
        this.failureExpression = failureExpression;
    }

}

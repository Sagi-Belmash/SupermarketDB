package Commands;

public class exitCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Goodbye!");
    }
}

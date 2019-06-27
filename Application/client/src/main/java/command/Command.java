package command;

public interface Command<T>
{
    T execute();

    T undo();
}

namespace Common;

public interface IPuzzleResolver
{
    object GetDayOneResult(string input);
    object GetDayTwoResult(string input);
}
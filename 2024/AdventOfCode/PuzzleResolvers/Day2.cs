using Common;

namespace AdventOfCode.PuzzleResolvers;

public class Day2 : IPuzzleResolver
{
    public object GetDayOneResult(string input)
    {
        return input
            .Split("\n")
            .Select(line => line.Split(" "))
            .Select(line => line.Select(@char => int.Parse(@char)).ToList())
            .Where(line => !LineIsInvalid(line))
            .Count();
    }

    public object GetDayTwoResult(string input)
    {
        return input
            .Split("\n")
            .Select(line => line.Split(" "))
            .Select(line => line.Select(@char => int.Parse(@char)).ToList())
            .Select(CreateLineSubsets)
            .Where(lineSubsets => lineSubsets.Any(line => !LineIsInvalid(line)))
            .Count();
    }

    private List<List<int>> CreateLineSubsets(List<int> input)
    {
        return input.Select((_, i) => input.Where((_, index) => index != i).ToList()).ToList();
    }
    
    private static bool LineIsInvalid(List<int> line)
    {
        var tempListAscending = line.Order().ToList();
        var tempListDescending = line.OrderDescending().ToList();

        if (!line.SequenceEqual(tempListAscending) && !line.SequenceEqual(tempListDescending))
        {
            return true;
        }

        for (var i = 0; i < line.Count - 1; i++)
        {
            if (!Delta(line[i], line[i + 1]))
            {
                return true;
            }
        }

        return false;
    }

    private static bool Delta(int x2, int x1)
    {
        return Math.Abs(x2 - x1) >= 1 && Math.Abs(x2 - x1) <= 3;
    }
}
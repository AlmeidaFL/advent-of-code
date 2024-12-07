using System.Text.RegularExpressions;
using Common;

namespace AdventOfCode.PuzzleResolvers;

public class Day1 : IPuzzleResolver
{
    public object GetDayOneResult(string input)
    {
        var numbers = GetListOfNumbers(input);
        return GetTotalDistance(numbers, numberOfSets: 2);
    }

    public object GetDayTwoResult(string input)
    {
        var numbers = GetListOfNumbers(input);

        return numbers[0].Sum(number => (number * numbers[1].Count(n => n == number)));
    }

    private List<List<int>> GetListOfNumbers(string input, int numberOfNumbers = 1)
    {
        string[] columnsSeparated = input.Split(["   ", "\n"], StringSplitOptions.RemoveEmptyEntries);
        var listOfList = new List<List<int>>();
            
        // Initialize lists
        var numberOfSets = numberOfNumbers * 2;
        for (var i = 0; i < numberOfSets; i++)
        {
            listOfList.Add([]);
        }

        var isFirstSet = false;
        foreach (var distanceId in columnsSeparated)
        {
            isFirstSet = !isFirstSet;
            var numbers = GetNumbers(distanceId, numberOfNumbers);

            var setOffSet = isFirstSet ? 1 : 2;
            var listOffSet = isFirstSet ? 0 : 1;
            
            foreach (var (number, index) in numbers.Select((n, i) => (n, i)))
            {
                listOfList[(index * setOffSet) + listOffSet].Add(number);
            }
        }

        return listOfList;
    }

    private int GetTotalDistance(List<List<int>> listOfList, int numberOfSets)
    {
        // Sort lists
        foreach (var list in listOfList)
        {
            list.Sort();
        }

        var totalDistance = 0;
        var repetitions = (int)Math.Floor(numberOfSets / 2.0);
        for (var i = 0; i < repetitions; i++)
        {
            totalDistance += GetDistancesIdsNumbers(listOfList[i], listOfList[(i * 2) + 1]);
        }

        return totalDistance;
    }

    private List<int> GetNumbers(string distanceId, int numberOfNumbers = 1)
    {
        if (numberOfNumbers == 1)
        {
            var matches = Regex.Matches(distanceId, @"\d+");
            return matches.Select(m => int.Parse(m.Value)).ToList();
        }
        else
        {
            var matches = Regex.Matches(distanceId, @"\d");
            return matches.Take(numberOfNumbers).Select(m => int.Parse(m.Value)).ToList();
        }
    }


    private int GetDistancesIdsNumbers(List<int> distanceSetA, List<int> distanceSetB)
    {
        return distanceSetA.Select((t, i) => GetDistance(t, distanceSetB[i])).Sum();
    }

    private int GetDistance(int locationIdA, int locationIdB)
    {
        return Math.Abs(locationIdA - locationIdB);
    }
}
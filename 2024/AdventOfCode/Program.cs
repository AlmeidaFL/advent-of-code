using AdventOfCode.PuzzleResolvers;
var text = File.ReadAllText("Inputs/day_2.txt");

var day = new Day2();
Console.WriteLine(day.GetDayOneResult(text));
Console.WriteLine(day.GetDayTwoResult(text));

using AdventOfCode.PuzzleResolvers;
var text = File.ReadAllText("Inputs/day_1.txt");

var day = new Day1();
Console.WriteLine(day.GetDayOneResult(text));
Console.WriteLine(day.GetDayTwoResult(text));

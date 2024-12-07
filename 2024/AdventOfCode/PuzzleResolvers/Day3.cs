using System.Text.RegularExpressions;
using Common;

namespace AdventOfCode.PuzzleResolvers;

public class Day3 : IPuzzleResolver
{
    public object GetDayOneResult(string input)
    {
        return ExtractExpressions(input)
            .Sum(expression => expression.GetValue());
    }
    
    
    public object GetDayTwoResult(string input)
    {
        var permissions = GetPermissionActions(input);
        
        return ExtractExpressions(input)
            .Where((expression, index) => IsExpressionAllowed(permissions, expression))
            .Sum(expression => expression.GetValue());
    }

    private List<MultiplyExpression> ExtractExpressions(string input)
    {
        var list = Regex
                .Matches(input, @"mul\(\d{1,3},\d{1,3}\)")
                .Select((match, index) => new MultiplyExpression
                {
                    Operation = "mul",
                    ValueA = Regex.Matches(match.Value, "\\d{1,3}").First().Value.ToInt(),
                    ValueB = Regex.Matches(match.Value, "\\d{1,3}").Last().Value.ToInt(),
                    Index = match.Index
                }).ToList();
        return list;
    }

    private static LinkedList<PermissionAction> GetPermissionActions(string input)
    {
        var list = Regex
            .Matches(input, @"(do\(\))|(don\'t\(\))")
            .Select((match, index) => new PermissionAction()
            {
                Action = match.Value,
                Index = match.Index
            });
        
        var linkedList = new LinkedList<PermissionAction>();
        foreach (var action in list)
        {
            linkedList.AddFirst(action);
        }
        return linkedList;
    }

    private bool IsExpressionAllowed(LinkedList<PermissionAction> permissions, MultiplyExpression expression)
    {
        var permission = permissions.FirstOrDefault(permission => permission.Index < expression.Index);

        return permission == null || permission.IsAllowed;
    }
    
}

interface IExpression
{
    public int GetValue();
}

record PermissionAction
{
    public string Action { get; init; }
    
    public int Index { get; init; }

    public bool IsAllowed => Action == "do()";
}

record MultiplyExpression : IExpression
{
    public string Operation { get; set; }
    public int ValueA { get; set; }
    public int ValueB { get; set; }
    public int Index { get; set; }
    
    public int GetValue()
    {
        return ValueA * ValueB;
    }
}
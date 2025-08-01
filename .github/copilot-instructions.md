# GitHub Copilot Code Review Instructions

## Overview

When reviewing code, focus on identifying issues that impact code quality, maintainability, security, and performance. Provide constructive feedback with specific suggestions for improvement.

## Code Review Checklist

### 1. Code Quality and Readability

- **Naming Conventions**: Check if variables, methods, and classes have descriptive, meaningful names following language conventions
- **Code Clarity**: Ensure code is self-documenting and easy to understand
- **Comments**: Look for missing documentation on complex logic or outdated comments
- **Formatting**: Verify consistent indentation, spacing, and code formatting

### 2. Modern Language Features

- **Stream API Usage**: In Java, suggest replacing traditional loops with Stream API where appropriate
- **Lambda Expressions**: Recommend using lambdas for functional interfaces
- **Optional Usage**: Suggest using Optional instead of null checks where applicable
- **Modern Syntax**: Encourage use of var, switch expressions, text blocks, and other modern features

### 3. Design Patterns and Architecture

- **Single Responsibility Principle**: Each class/method should have one clear purpose
- **DRY Principle**: Identify and suggest eliminating code duplication
- **SOLID Principles**: Check adherence to object-oriented design principles
- **Design Patterns**: Suggest appropriate patterns (Strategy, Factory, Observer, etc.)

### 4. Error Handling and Robustness

- **Exception Handling**: Check for proper exception handling, specific exception types
- **Input Validation**: Ensure all inputs are properly validated
- **Resource Management**: Verify proper use of try-with-resources or similar patterns
- **Null Safety**: Check for potential null pointer exceptions

### 5. Performance and Efficiency

- **Algorithm Efficiency**: Identify inefficient algorithms or data structures
- **Memory Management**: Look for memory leaks or excessive object creation
- **Database Queries**: Check for N+1 problems, missing indexes, or inefficient queries
- **Caching**: Suggest caching for expensive operations

### 6. Security Best Practices

- **Input Sanitization**: Check for SQL injection, XSS vulnerabilities
- **Authentication/Authorization**: Verify proper access controls
- **Sensitive Data**: Ensure secrets aren't hardcoded or logged
- **Dependency Security**: Flag outdated or vulnerable dependencies

### 7. Testing and Quality Assurance

- **Test Coverage**: Suggest missing unit tests or integration tests
- **Test Quality**: Check if tests are meaningful and cover edge cases
- **Testability**: Ensure code is written in a testable manner
- **Mocking**: Suggest proper use of mocks and stubs

### 8. Code Smells to Flag

- **Long Methods**: Methods should be focused and concise
- **Large Classes**: Classes should have a single responsibility
- **Magic Numbers**: Replace with named constants
- **Duplicate Code**: Identify repeated logic that should be extracted
- **Dead Code**: Flag unused methods, variables, or imports

## Language-Specific Guidelines

### Java

- Prefer `List<String>` over `ArrayList<String>` for method parameters
- Use `StringBuilder` for multiple string concatenations
- Implement `equals()` and `hashCode()` together
- Use enums instead of constants
- Prefer composition over inheritance
- Use `@Override` annotations consistently


### Python

- Follow PEP 8 style guidelines
- Use list comprehensions where appropriate
- Prefer f-strings for string formatting
- Use type hints for better code documentation
- Handle exceptions specifically, not with bare `except:`
- Use context managers for resource handling

## Review Response Format

When providing feedback, use this structure:

### 🔴 Critical Issues

- Security vulnerabilities
- Performance bottlenecks
- Potential runtime errors

### 🟡 Improvements Suggested

- Code quality enhancements
- Better design patterns
- Modern language features

### 🟢 Good Practices Observed

- Well-implemented patterns
- Good naming conventions
- Proper error handling

### 💡 Suggestions

```java
// Instead of:
for (int i = 0; i < list.size(); i++) {
    if (list.get(i).startsWith("prefix")) {
        results.add(list.get(i));
    }
}

// Suggest:
List<String> results = list.stream()
    .filter(item -> item.startsWith("prefix"))
    .collect(Collectors.toList());
```

## Common Anti-Patterns to Avoid

1. **God Classes**: Classes that do too much
2. **Spaghetti Code**: Poorly structured, hard-to-follow logic
3. **Magic Numbers**: Unexplained numeric literals
4. **Copy-Paste Programming**: Duplicated code blocks
5. **Premature Optimization**: Optimizing before measuring performance
6. **Tight Coupling**: Classes that depend too heavily on each other
7. **Feature Envy**: Methods that use more data from other classes than their own

## Positive Reinforcement

Always acknowledge good practices when you see them:

- Clean, readable code
- Appropriate use of design patterns
- Good test coverage
- Proper error handling
- Well-structured architecture

## Questions to Ask During Review

1. Is this code easy to understand and maintain?
2. Are there any potential security vulnerabilities?
3. Could this code be simplified or made more efficient?
4. Are there appropriate tests for this functionality?
5. Does this follow the team's coding standards?
6. Are there any edge cases not handled?
7. Is the error handling comprehensive?
8. Could this benefit from modern language features?

Remember: The goal is to improve code quality while being constructive and educational. Focus on teaching better practices rather than just pointing out problems.

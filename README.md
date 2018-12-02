# ninety-nine-scala-problems
Convenient starting point for Phil Gold's [S99: Ninety-Nine Scala Problems](http://aperiodic.net/phil/scala/s-99/). Contains skeleton scala code (taken from the S99 snippets) and complete test coverage for all problems.

## Instructions
You need [SBT](http://www.scala-sbt.org/) installed.
Clone the project and run

    sbt test

You'll see most tests failing. Fill in the missing implementations using your favourite text editor or IDE, and make them pass.

## Problem Descriptions
[My problem descriptions](PROBLEMS.md) come in the form of a Gist (work in progress). They are tailored to the code and differ in some places from Phil's descriptions.

## Problem Solutions
You'll learn most if you design and implement your own solutions. However, if you need inspiration or want to compare solutions, you're welcome to look at my attempts on the [`solutions`](https://github.com/jayho/ninety-nine-scala-problems/tree/solutions) branch, or at Phil's solutions on his S99 website.

## Notes
The code is based on Phil's snippets with some changes in order to be able to add tests, or because a newer Scala version required them.

Libraries used:

| Library            | Version                                                                        |
| ------------------ | ------------------------------------------------------------------------------ |
| Scala              | [2.12.2](https://github.com/scala/scala/releases/tag/v2.12.2)                  |
| ScalaTest          | [3.0.1](http://www.scalatest.org/release_notes/3.0.1)                          |
| Parser Combinators | [1.0.5](https://github.com/scala/scala-parser-combinators/releases/tag/v1.0.6) |

Parser Combinators come in handy in those tree or graph problems that task us to convert from String representations to objects.

## Contributions
Contributions are welcome via GitHub issues or pull requests.

# N Queens algorithm

This is an application that solves n queens problem for provided n. It can also solve this problem that no three queens are in a straight line. 


 # Instructions
 
assemble jar and run it with:
```
java -jar queens-1.0.0.jar
```

To run iterative repair algorithm write in shell:
```
iterative-algorithm $BOARD_SIZE
```

to run backtracking algorithm write:
```
backtracking-algorithm $BOARD_SIZE
```

to print [staircase algorithm](https://juristr.com/blog/2019/04/intro-to-angular-elements/) run:
```
staircase-solution $BOARD_SIZE
```

if you want to turn off three in line problem write:
```
change-params --solve-nin-line FALSE
```


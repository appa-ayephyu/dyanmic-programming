The set of targets that the attacker chooses to attack: 1, 2, 3, 4, and 5.
The set of targets with a coverage > 0: 1, 2, 3, and 4.

So it looks like target 5 is chosen to be attacked by the attacker for a certain observation, but is not covered.

Looking at the file attacker_strat.txt, for only 1 observation does the attacker choose to attack target 5.  This is with an observation space of 252.

The coverages for the targets are:
1  0.410076
2  0.248573
3  0.212559
4  0.128791
5  3.29259e-09
6  1.51282e-09

When I try to output the coverage for the targets writing a float, I get:
1=0.410076
2=0.248573
3=0.212559
4=0.128791
5=0.000000
6=0.000000

param nDefStrategies;
param nFunctionPoints;
set defstrategies := {1..nDefStrategies};
set functionPoints := {1..nFunctionPoints};
param b {functionPoints, defstrategies};
param e {functionPoints};
 
var x {defstrategies} >= 0, <= 1;
var d;
 
maximize objective: d;
subject to
 
bound1: sum{i in defstrategies} x[i] = 1;
bound2{i in functionPoints}: sum{j in defstrategies} (b[i,j]*x[j]) + e[i] >= d;

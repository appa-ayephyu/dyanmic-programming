param nDefStrategies;
param nColPoints;
param nFunctionPoints;
set defstrategies := {1..nDefStrategies};
set collectionPoints := {1..nColPoints};
set functionPoints := {1..nFunctionPoints};
param x {collectionPoints, defstrategies};
param g {collectionPoints};
 
var b {functionPoints, defstrategies};
var e {functionPoints};
var d {functionPoints};
 
 
minimize objective: sum{i in functionPoints}( d[i] + e[i]);
subject to
 
bound1{i in functionPoints, j in defstrategies}: d[i] >= b[i,j];
bound2{i in functionPoints, j in defstrategies}: d[i] >= -b[i,j];
bound3{i in functionPoints, k in collectionPoints}: sum{j in defstrategies} (b[i,j]*x[k,j]) + e[i] >= g[k];

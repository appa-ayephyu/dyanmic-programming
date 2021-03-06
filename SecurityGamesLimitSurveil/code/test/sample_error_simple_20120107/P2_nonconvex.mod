param nTargets;
param observationSpace;
param nDefStrategies;
set targets := {1..nTargets};
set observations := {1..observationSpace};
set defstrategies := {1..nDefStrategies};
param Ospace {observations, defstrategies};
param preProb {observations};
param A {defstrategies, targets};
param defenderCoveredPayoff {targets};
param defenderUncoveredPayoff {targets};
param attackerUncoveredPayoff {targets};
param attackerCoveredPayoff {targets};
 
var x {defstrategies} >= 0, <= 1;
var marginal {targets};
var d{observations};
 
maximize objective: sum{o in observations}(preProb[o]*(x[1]^Ospace[o,1] * x[2]^Ospace[o,2] ) * d[o]); 
subject to
 
coverage: sum {i in defstrategies} x[i] = 1;
computeMarginals{t in targets}: marginal[t] = sum {i in defstrategies} x[i] * A[i,t];
bound1: d[1] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound2: d[2] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound3: d[3] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound4: d[4] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];

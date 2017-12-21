param nTargets;
param nDefStrategies;
param nResources;
param nObservations;
param observationSpace;
param Z default 99999;
set targets := {1..nTargets};
set observations := {1..observationSpace};
set defstrategies := {1..nDefStrategies};
param alphaD {defstrategies};
param sumAlphaD;
param Ospace {observations, defstrategies};
param preProb {observations};
param A {defstrategies, targets};
param defenderCoveredPayoff {targets};
param defenderUncoveredPayoff {targets};
param attackerUncoveredPayoff {targets};
param attackerCoveredPayoff {targets};
 
var coverage {targets} >= 0, <= 1;
var attack {observations, targets} binary;
var coverageAttacker {observations, targets} >= 0, <= 1;
var d{observations};
var k{observations};
 
 
maximize objective{o in observations}: d[o]; 
subject to
 
sumforattack{o in observations}: sum{t in targets} attack[o,t] = 1;
lobound1{o in observations, t in targets}: 
  d[o] - coverageAttacker[o,t] * (defenderCoveredPayoff[t] - defenderUncoveredPayoff[t]) - defenderUncoveredPayoff[t] <= (1 - attack[o,t]) * Z;
newcoverage{o in observations, t in targets}: 
  coverageAttacker[o,t] = sum{i in defstrategies}(A[i,t]*(alphaD[i] + Ospace[o,i] + 1)) / (sumAlphaD + nDefStrategies + nObservations);
lobound2{o in observations, t in targets}: 
  k[o] - coverageAttacker[o,t] * (attackerCoveredPayoff[t] - attackerUncoveredPayoff[t]) - attackerUncoveredPayoff[t] <= (1 - attack[o,t]) * Z;
lobound3{o in observations, t in targets}: 
  k[o] - coverageAttacker[o,t] * (attackerCoveredPayoff[t] - attackerUncoveredPayoff[t]) - attackerUncoveredPayoff[t] >= 0;
 
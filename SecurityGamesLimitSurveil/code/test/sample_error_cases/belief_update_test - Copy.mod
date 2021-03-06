param nTargets;
param nResources;
param nObservations;
param observationSpace;
param Z default 99999;
set targets := {1..nTargets};
set observations := {1..observationSpace};
param alphaD {targets};
param sumAlphaD;
param Ospace {observations, targets};
param preProb {observations};
param defenderCoveredPayoff {targets};
param defenderUncoveredPayoff {targets};
param attackerUncoveredPayoff {targets};
param attackerCoveredPayoff {targets};
 
var coverage {targets} >= 0, <= 1;
var attack {observations, targets} binary;
var coverageAttacker {observations, targets} >= 0, <= 1;
var d{observations};
var k{observations};
 
maximize objective: sum{o in observations}(preProb[o]*(coverage[1]^Ospace[o,1] * coverage[2]^Ospace[o,2] * coverage[3]^Ospace[o,3] ) * d[o]); 
subject to
 
coverageEqualsResources: sum {i in targets} coverage[i] = nResources;
sumforattack{o in observations}: sum{t in targets} attack[o,t] = 1;
lobound1{o in observations, t in targets}: 
  d[o] - coverage[t] * (defenderCoveredPayoff[t] - defenderUncoveredPayoff[t]) - defenderUncoveredPayoff[t] <= (1 - attack[o,t]) * Z;
newcoverage{o in observations, t in targets}: 
  coverageAttacker[o,t] = (alphaD[t] + Ospace[o,t] + 1) / (sumAlphaD + nTargets + nObservations);
lobound2{o in observations, t in targets}: 
  k[o] - coverageAttacker[o,t] * (attackerCoveredPayoff[t] - attackerUncoveredPayoff[t]) - attackerUncoveredPayoff[t] <= (1 - attack[o,t]) * Z;
lobound3{o in observations, t in targets}: 
  k[o] - coverageAttacker[o,t] * (attackerCoveredPayoff[t] - attackerUncoveredPayoff[t]) - attackerUncoveredPayoff[t] >= 0;

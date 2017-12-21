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
 
coverageEqualsResources: sum {i in targets} coverage[i] <= nResources;
bound1:  d[1] = coverage[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound2:  d[2] = coverage[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound3:  d[3] = coverage[3] * (defenderCoveredPayoff[3] - defenderUncoveredPayoff[3]) + defenderUncoveredPayoff[3];
bound4:  d[4] = coverage[3] * (defenderCoveredPayoff[3] - defenderUncoveredPayoff[3]) + defenderUncoveredPayoff[3];
bound5:  d[5] = coverage[3] * (defenderCoveredPayoff[3] - defenderUncoveredPayoff[3]) + defenderUncoveredPayoff[3];
bound6:  d[6] = coverage[3] * (defenderCoveredPayoff[3] - defenderUncoveredPayoff[3]) + defenderUncoveredPayoff[3];

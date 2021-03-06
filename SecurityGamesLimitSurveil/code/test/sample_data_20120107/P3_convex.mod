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
 
minimize objective: sum{o in observations}(-log(preProb[o]) - sum {i in defstrategies}(Ospace[o,i] * log(x[i])) - log(d[o]));
 
subject to
 
coverage: sum {i in defstrategies} x[i] = 1;
computeMarginals{t in targets}: marginal[t] = sum {i in defstrategies} x[i] * A[i,t];
bound1: d[1] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound2: d[2] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound3: d[3] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound4: d[4] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound5: d[5] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound6: d[6] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound7: d[7] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound8: d[8] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound9: d[9] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound10: d[10] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound11: d[11] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound12: d[12] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound13: d[13] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound14: d[14] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound15: d[15] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound16: d[16] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound17: d[17] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound18: d[18] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound19: d[19] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound20: d[20] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound21: d[21] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound22: d[22] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound23: d[23] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound24: d[24] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound25: d[25] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound26: d[26] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound27: d[27] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound28: d[28] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound29: d[29] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound30: d[30] = marginal[1] * (defenderCoveredPayoff[1] - defenderUncoveredPayoff[1]) + defenderUncoveredPayoff[1];
bound31: d[31] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound32: d[32] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound33: d[33] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound34: d[34] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound35: d[35] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound36: d[36] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound37: d[37] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound38: d[38] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound39: d[39] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound40: d[40] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound41: d[41] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound42: d[42] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound43: d[43] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound44: d[44] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound45: d[45] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound46: d[46] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound47: d[47] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound48: d[48] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound49: d[49] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound50: d[50] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound51: d[51] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound52: d[52] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound53: d[53] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound54: d[54] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound55: d[55] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound56: d[56] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound57: d[57] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound58: d[58] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound59: d[59] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound60: d[60] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound61: d[61] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound62: d[62] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound63: d[63] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound64: d[64] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound65: d[65] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];
bound66: d[66] = marginal[2] * (defenderCoveredPayoff[2] - defenderUncoveredPayoff[2]) + defenderUncoveredPayoff[2];

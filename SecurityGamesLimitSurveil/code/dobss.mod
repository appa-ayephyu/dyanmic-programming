param n1;  #Number of our actions
param n2;  #Number of adversary actions
param MM;  #Arbitrarily large number for equation bounding
param ADV; #Number of adversary types

set II := {1..n1};
set JJ := {1..n2};
set AA := {1..ADV};
param Reward {AA,II,JJ};  #Leader reward matrix
param AdvReward {AA,II,JJ}; #Attacker reward matrix
param p  {AA}; #Probability of facing each attacker type
var Z {AA, II, JJ} >= 0, <= 1;  #Final solution (probability distribution over leader actions)
var q {AA, JJ} integer >= 0, <= 1;  #Final attacker action chosen
var b {AA};
var pz {II, JJ};

var obj;

maximize objective: obj;
subject to partialsumz {a in AA}: sum{i in II, j in JJ}Z[a,i,j] = 1;
subject to partialsum {a in AA, i in II}: sum{j in JJ} Z[a,i,j] <= 1;
subject to sumoverj {a in AA, j in JJ}: sum{i in II} Z[a,i,j] <= 1;
subject to sumoverjlo {a in AA, j in JJ}: q[a,j] <= sum{i in II} Z[a,i,j];
subject to sumforq{a in AA}: sum{j in JJ} q[a,j] = 1;
subject to lobound {a in AA, j in JJ}: sum{i in II} (AdvReward[a,i,j] * (sum{k in JJ} Z[a,i,k])) <= b[a];
subject to lobound2 {a in AA, j in JJ}: b[a] - sum{i in II}(AdvReward[a,i,j] * (sum{k in JJ} Z[a,i,k]))  <= (1- q[a,j]) * MM;
subject to equality {a in AA, i in II} : sum{j in JJ} Z[a,i,j] = sum{k in JJ}Z[1,i,k];
subject to oneresult {i in II, j in JJ}: pz[i,j] = Z[1,i,j];
subject to varObjConst: obj = sum{a in AA, i in II, j in JJ}Z[a,i,j]*Reward[a,i,j]*p[a];

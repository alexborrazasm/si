clear;
close all;

it = 50;
arch = [96 72];

inputs  = readmatrix('data.xlsx','Sheet','Entradas RNA')';
targets = readmatrix('data.xlsx','Sheet','Salidas RNA')';

precisionTrain = zeros(1, it);
precisionValidation = zeros(1, it);
precisionTest = zeros(1, it); 

for i=1:it
    rna = patternnet(arch);
    rna.trainParam.showWindow = false; 
    [rna tr] = train(rna, inputs, targets);

    outputs = sim(rna, inputs);

    precisionTrain(i) = 1 - confusion(targets(:, tr.trainInd), ...
        outputs(:, tr.trainInd));
    precisionValidation(i) = 1 - confusion(targets(:, tr.valInd), ...
        outputs(:, tr.valInd));
    precisionTest(i) = 1 - confusion(targets(:, tr.testInd), ...
        outputs(:, tr.testInd));  
end

meanPrecisionTest = mean(precisionTest)
stdPrecisionTest = std(precisionTest) 
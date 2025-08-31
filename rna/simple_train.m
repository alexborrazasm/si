clear;
close all;

arch = [96 72];

inputs  = readmatrix('data.xlsx','Sheet','Entradas RNA')';
targets = readmatrix('data.xlsx','Sheet','Salidas RNA')';

rna = patternnet(arch);
[rna, tr] = train(rna, inputs, targets);

outputs = sim(rna, inputs);

mse(outputs - targets)

precision = 1 - confusion(targets, outputs)

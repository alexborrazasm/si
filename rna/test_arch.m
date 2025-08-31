clear;
close all;

% Load input and target data from excel
inputs  = readmatrix('data.xlsx','Sheet','Entradas RNA')';
targets = readmatrix('data.xlsx','Sheet','Salidas RNA')';

it = 50;

architectures = { ...
    [5], [10], [15], [5 3], [10 5], [15 10], ...
    [90], [94], [95], [96], [97], [100], ...
    [100 50], [96 48], [95 47], [94 47], [90 45], ...
    [90 60], [95 70], [96 72], ...
    [90 60 30], [95 63 31], [94 62 30], [96 64 32], [95 65 32], [96 66 33], [100 70 40], ...
    [150], [200], [150 100], [200 150], [200 150 100], ...
    [300], [400], ...
    [300 200], [400 300], ...
    [300 200 100], [400 300 200], [500 400 300]
};


% Preallocate results
numArchitectures = numel(architectures);
results = cell(numArchitectures, 7);

% Loop through each architecture
for a = 1:length(architectures)
    arch = architectures{a};
    
    % Vectors to store performance metrics
    precisionTrain = zeros(1, it);
    precisionValidation = zeros(1, it);
    precisionTest = zeros(1, it);

    for i = 1:it
        % Create and train neural network
        net = patternnet(arch);
        net.trainParam.showWindow = false;
        [net, tr] = train(net, inputs, targets);

        % Evaluate network
        outputs = net(inputs);

        precisionTrain(i) = 1 - confusion(targets(:, tr.trainInd), ...
            outputs(:, tr.trainInd)); % 70% data
        precisionValidation(i) = 1 - confusion(targets(:, tr.valInd), ...
            outputs(:, tr.valInd));   % 15% data
        precisionTest(i) = 1 - confusion(targets(:, tr.testInd), ...
            outputs(:, tr.testInd));  % 15% data
    end
    
    % Calculate statistics
    mTrain = mean(precisionTrain);
    sTrain = std(precisionTrain);
    mVal = mean(precisionValidation);
    sVal = std(precisionValidation);
    mTest = mean(precisionTest);
    sTest = std(precisionTest);
    
    % Save statistics
    results(a, :) = {mat2str(arch), mTrain, sTrain, mVal, sVal,  ...
        mTest, sTest};
    
    % Print progress
    fprintf('Architecture %s -> Mean Test Precision: %.4f\n' , ...
        mat2str(arch), mTest);
end

% Convert to table
resultsTable = cell2table(results, ...
    'VariableNames', {'Architecture', ...
                      'MeanTrain', 'StdTrain', ...
                      'MeanValidation', 'StdValidation', ...
                      'MeanTest', 'StdTest'});

% Save table to Excel
writetable(resultsTable, 'resultados_rna.xlsx');

% Display sorted by MeanTest precision
disp(sortrows(resultsTable, 'MeanTest', 'descend'));

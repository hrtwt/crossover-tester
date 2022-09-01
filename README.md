# crossover-tester

## Requirements
* jdk 11

## How to use

1. locate `kGenProg.jar` at `lib/kGenProg.jar`  
   `kGenProg.jar` is available at <https://github.com/kusumotolab/kGenProg/releases>
2. `./gradlew run --args=${PathOfProjectRoot} ${PathOfVariant.json} ${randomSeed}`  
ex.) `./gradlew run --args="../example/ABC139_A/ ../example/ABC139_A/variants.json 0"`

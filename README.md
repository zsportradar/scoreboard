# Scoreboard
A simple implementation of a sports score board

## Build and test
```
./gradlew build
```

## Code coverage report
```
./gradlew koverHtmlReport
```

Code coverage is generated at
```
build/reports/kover/html
```

## Developer remarks, limitations
* GameRepository stores games in memory
  * In a real app we'd another implementation using some infrastructure
* ScoreBoard calculates standings on the fly
  * On larger dataset this would potentially require additional optimization
* Managing games is moved to out from ScoreBoard focus on displaying results. A slight intentional deviation from the spec.
#!/usr/bin/groovy

def call(){
  sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent verify -DskipITs=true"
  step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', excludes: null, fingerprint: true, onlyIfSuccessful: true])
  try {
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
  } catch (err) {
    echo "No unit tests result were found: ${err}"
  }
}

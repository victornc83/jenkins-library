#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    waitDeployIsComplete(project, app)
    host = sh script: "oc get svc ${app} --template='{{.spec.clusterIP}}'",returnOutput: true
    port = sh script: "oc get svc ${app} --template='{{range .spec.ports}}{{.port}}{{end}}'",returnOutput: true
    sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent-integration verify -Dsurefire.skip=true -Dservice.endpoint=http://${host}:${port}"
    try {
      step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
    } catch (err) {
      echo "No integration test result were found: ${err}"
    }
  }
}

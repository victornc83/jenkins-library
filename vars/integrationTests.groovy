#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    waitDeployIsComplete(project, app)
    //def host = sh script: "",returnOutput: true
    //def port = sh script: "",returnOutput: true
    sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent-integration verify -Dsurefire.skip=true -Dservice.endpoint=http://\$(oc get svc ${app} --template='{{.spec.clusterIP}}' -n ${project}):\$(oc get svc ${app} --template='{{range .spec.ports}}{{.port}}{{end}}' -n ${project})"
    try {
      step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
    } catch (err) {
      echo "No integration test result were found: ${err}"
    }
  }
}

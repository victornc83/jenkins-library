#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    sh '''
    export host=$(oc get svc ${app} --template='{{.spec.clusterIP}}')
    export port=$(oc get svc ${app} --template='{{range .spec.ports}}{{.port}}{{end}}')
    mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent-integration verify -Dsurefire.skip=true -Dservice.endpoint=http://${host}:${p.port}
    '''
    try {
      step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
    } catch (err) {
      echo "No integration test result were found: ${err}"
    }
  }
}

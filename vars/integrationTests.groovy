#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    def host=["oc", "get", "svc", "${app}", "--template='{{.spec.clusterIP}}'" ].execute().text
    def port=["oc", "get", "svc", "${app}", "--template='{{range .spec.ports}}{{.port}}{{end}}'" ].execute().text
    sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent-integration verify -Dsurefire.skip=true -Dservice.endpoint=http://${host}:${p.port}"
    try {
      step([$class: 'JUnitResultArchiver', testResults: '**/target/failsafe-reports/TEST-*.xml'])
    } catch (err) {
      echo "No integration test result were found: ${err}"
    }
  }
}

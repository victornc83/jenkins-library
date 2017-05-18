#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    timeout(time: 2, unit: 'MINUTES') {
      waitUntil{
        sh "oc get dc ${app} --template '{{range .status.conditions}}{{.status|println}}{{end}}' > status"
        sh "cat status | while read line ; do if [ ${line} != "True" ] ; then return 1 ; fi ; done"
      }
    }
  }
}

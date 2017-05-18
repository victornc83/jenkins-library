#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    timeout(time: 2, unit: 'MINUTES') {
      waitUntil{
        sh "oc get dc ${app} --template '{{range .status.conditions}}{{.status|println}}{{end}}' > status"
        def = output = sh script:'''
        for line in `cat status` ; do if [ ${l} != "True" ] ; then return 1 ; fi ; done
        ''', returnStatus: true
        return (output==0)
      }
    }
  }
}

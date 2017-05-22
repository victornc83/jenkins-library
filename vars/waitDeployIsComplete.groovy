#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    timeout(time: 5, unit: 'MINUTES') {
      waitUntil{
        sh "oc get rc myapp-\$(oc get dc ${app} --template={{.status.latestVersion}} -n ${project}) --template={{.status.readyReplicas}} -n ${project} > status"
        def output = sh script:'''
        for line in `cat status` ; do [ ${line} == "1" ] ; done
        ''', returnStatus: true
        return (output==0)
      }
    }
  }
}

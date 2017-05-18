#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    timeout(time: 2, unit: 'MINUTES') {
      waitUntil{
        sh '''
        #!/bin/bash
        for e in `oc get dc ${app} --template '{{range .status.conditions}}{{.status|println}}{{end}}'` ; do
          if [ "${e}" != "False" ] ; then return 1 ; fi
        done
        return 0
        '''
      }
    }
  }
}

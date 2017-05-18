#!/usr/bin/groovy

def call(project, app){
  loginOpenshift(project){
    timeout(time: 2, unit: 'MINUTES') {
      waitUntil{
        sh "export status=$(oc get dc '${app}' --template '{{range .status.conditions}}{{.status|println}}{{end}}')"
        sh '''
        for e in $status ; do
          echo ${e}
          if [ "${e}" != "False" ] ; then echo false ; fi
        done
        echo true
        '''
      }
    }
  }
}
